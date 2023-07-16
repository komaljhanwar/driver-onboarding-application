package org.example.driverapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.example.driverapplication.constants.OnboardingStatus;
import org.example.driverapplication.constants.ResponseCodeMapping;
import org.example.driverapplication.dto.DriverProfileDto;
import org.example.driverapplication.exception.*;
import org.example.driverapplication.exception.IllegalArgumentException;
import org.example.driverapplication.entity.Driver;
import org.example.driverapplication.service.DriverServiceImpl;
import org.example.driverapplication.service.OnboardingService;
import org.example.driverapplication.utils.DocumentValidator;
import org.example.driverapplication.utils.DriverDetailsValidator;
import org.example.driverapplication.utils.FileUploadUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverServiceImpl driverServiceImpl;

    @Autowired
    private OnboardingService onboardingService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper mapper;

    @PostMapping("/signup")
    public ResponseEntity<Driver> signup(@RequestBody DriverProfileDto driverProfileDto) throws IllegalArgumentException, InternalServerErrorException, CustomException {
        DriverDetailsValidator.validateDriver(driverProfileDto);
        Driver savedDriver = driverServiceImpl.createDriverProfile(driverProfileDto);
        log.info(" Driver signed up successfully");
        return new ResponseEntity<>(savedDriver, HttpStatus.CREATED);
    }

//    @PostMapping("/{driverId}/documents")
//    public Document addDocument(@PathVariable Long driverId, @RequestBody Document document) {
//        document.setDriverId(driverId);
//        return documentService.saveDocument(document);
//    }

//    @PutMapping("/{driverId}/documents")
//    public Document updateDocument(@PathVariable Long driverId, @RequestBody Document document) {
//        document.setDriverId(driverId);
//        return documentService.updateDocument(document);
//    }

    /*@PostMapping("/onboarding")
    public ResponseEntity<Driver> onboarding(@RequestBody DriverProfileDto driverProfileDto) {
        Driver driver =  mapper.convertValue(driverProfileDto, Driver.class);
        Driver onboardedDriver = driverService.onboard(driver);
        return new ResponseEntity<>(onboardedDriver, HttpStatus.OK);
    }*/

    /*@PostMapping("/{driverId}/documents")
    public ResponseEntity<?> uploadDocuments(@RequestParam("file") MultipartFile file, @PathVariable Long driverId) {
        try {
            Driver driver = driverService.getDriver(driverId).get();
            String docUrl = uploadDocument.upload(file);
            driver.setDocumentUrl(docUrl);
            driver.setOnboardingStatus(OnboardingStatus.IN_PROGRESS);
            driverService.save(driver);
            //async
            DocumentMessage message = new DocumentMessage();
            message.setDriverId(driverId);
            message.setDocumentUrl(docUrl);
            rabbitTemplate.convertAndSend(BackgroundVerificationConfig.EXCHANGE, BackgroundVerificationConfig.ROUTING_KEY, message);
            return ResponseEntity.ok(new MessageResponse("Uploaded the file successfully: " + file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse("Could not upload the file: " + file.getOriginalFilename() + "!"));
        }
    }*/

    @PostMapping("/{driverId}/documents")
    public ResponseEntity<?> uploadDocuments(@RequestParam("file") MultipartFile file, @PathVariable Long driverId) throws CustomIOException, IllegalArgumentException, InternalServerErrorException {
        DocumentValidator.validate(file);
        String filePath = FileUploadUtil.saveFile(file.getOriginalFilename(),file);
            Driver driver = driverServiceImpl.getDriver(driverId).orElseThrow(() ->
                    new ResourceNotFoundException(ResponseCodeMapping.DRIVER_NOT_FOUND.getCode(),
                            ResponseCodeMapping.DRIVER_NOT_FOUND.getMessage() + driverId));
            driver.setDocumentUrl(filePath);
            driverServiceImpl.save(driver);
            log.info("Driver's document uploaded successfully");

            if(driver.getOnboardingStatus() == null) {
                log.info("Onboradring started");
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
