package org.example.driverapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.example.driverapplication.constants.OnboardingStatus;
import org.example.driverapplication.constants.ResponseCodeMapping;
import org.example.driverapplication.dto.DriverProfileDto;
import org.example.driverapplication.entity.Document;
import org.example.driverapplication.exception.*;
import org.example.driverapplication.exception.IllegalArgumentException;
import org.example.driverapplication.entity.Driver;
import org.example.driverapplication.model.AuthenticationRequest;
import org.example.driverapplication.model.AuthenticationResponse;
import org.example.driverapplication.service.CustomUserDetailsService;
import org.example.driverapplication.service.DocumentServiceImpl;
import org.example.driverapplication.service.DriverServiceImpl;
import org.example.driverapplication.service.OnboardingService;
import org.example.driverapplication.utils.DocumentValidator;
import org.example.driverapplication.utils.DriverDetailsValidator;
import org.example.driverapplication.utils.FileUploadUtil;
import org.example.driverapplication.utils.JwtUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverServiceImpl driverServiceImpl;

    @Autowired
    private DocumentServiceImpl documentServiceImpl;

    @Autowired
    private OnboardingService onboardingService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUser(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUser());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<Driver> signup(@RequestBody DriverProfileDto driverProfileDto) throws IllegalArgumentException, InternalServerErrorException, CustomException {
        DriverDetailsValidator.validateDriver(driverProfileDto);
        Driver savedDriver = driverServiceImpl.createDriverProfile(driverProfileDto);
        log.info(" Driver signed up successfully");
        return new ResponseEntity<>(savedDriver, HttpStatus.CREATED);
    }

    @PostMapping("/{driverId}/documents")
    public ResponseEntity<?> uploadDocuments(@RequestParam("file") MultipartFile file, @PathVariable Long driverId) throws CustomIOException, IllegalArgumentException, InternalServerErrorException {
        DocumentValidator.validate(file);
        String filePath = FileUploadUtil.saveFile(file.getOriginalFilename(),file);
        Driver driver = driverServiceImpl.getDriver(driverId).orElseThrow(() ->
                new ResourceNotFoundException(ResponseCodeMapping.DRIVER_NOT_FOUND.getCode(), ResponseCodeMapping.DRIVER_NOT_FOUND.getMessage() + driverId));
        //driver.setDocumentUrl(filePath);
        //driverServiceImpl.save(driver);
        Document document = new Document().builder()
                .driverId(driverId)
                .url(filePath)
                .type(FilenameUtils.getExtension(file.getOriginalFilename()))
                .build();
        documentServiceImpl.saveDocument(document);

        log.info("Driver's document uploaded successfully");

        if(driver.getOnboardingStatus() == null) {
            log.info("Onboarding started");
            onboardingService.trigger(driver);
        }
        return ResponseEntity.ok(new MessageResponse("Uploaded the file successfully: " + file.getOriginalFilename()));
    }

    @PutMapping("/{driverId}/status")
    public ResponseEntity<Driver> status(@RequestParam("status") boolean status, @PathVariable Long driverId) throws DriverNotOnboardedException, InternalServerErrorException {
        Driver readyDriver = driverServiceImpl.updateStatus(driverId, status);
        log.info(" Driver status updated successfully");
        return new ResponseEntity<>(readyDriver, HttpStatus.OK);
    }

    @GetMapping("/{driverId}/info")
    public ResponseEntity<Driver> getDriverProfileInfo(@PathVariable Long driverId) throws DriverNotOnboardedException, InternalServerErrorException {
        Driver driverProfileIfo = driverServiceImpl.getDriver(driverId).orElseThrow(() ->
                new ResourceNotFoundException(ResponseCodeMapping.DRIVER_NOT_FOUND.getCode(),
                        ResponseCodeMapping.DRIVER_NOT_FOUND.getMessage() + driverId));
        return new ResponseEntity<>(driverProfileIfo, HttpStatus.OK);
    }
}
