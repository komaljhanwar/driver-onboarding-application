package org.example.driverapplication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.example.driverapplication.constants.ResponseCodeMapping;
import org.example.driverapplication.dto.DriverProfileDto;
import org.example.driverapplication.exception.*;
import org.example.driverapplication.entity.Driver;
import org.example.driverapplication.constants.OnboardingStatus;
import org.example.driverapplication.model.DocumentInfo;
import org.example.driverapplication.producer.BackgroundVerificationTask;
import org.example.driverapplication.repository.DocumentRepository;
import org.example.driverapplication.repository.DriverRepository;
import org.example.driverapplication.utils.EncryptionUtil;
import org.example.driverapplication.utils.OnboardingValidator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
public class DriverServiceImpl implements IDriverService{

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    BackgroundVerificationTask backgroundVerificationTask;

    @Autowired
    private ObjectMapper mapper;

   /* @Autowired
    BCryptPasswordEncoder passwordEncoder;*/

    @Transactional
    public Driver save(Driver driver) throws InternalServerErrorException {
        try {
            return driverRepository.save(driver);
        } catch (Exception ex) {
            log.error(ResponseCodeMapping.DB_FAILURE.getMessage(), ex.getMessage());
            throw new InternalServerErrorException(ResponseCodeMapping.DB_FAILURE.getCode(), ResponseCodeMapping.DB_FAILURE.getMessage());
        }
    }

    @Transactional
    public Driver createDriverProfile(DriverProfileDto driverProfileDto) throws InternalServerErrorException, CustomException {
        try {
            Driver driver =  mapper.convertValue(driverProfileDto, Driver.class);
            checkDriverExists(driver.getEmail());
            driver.setPassword(EncryptionUtil.getEncryptedPwd(driver.getPassword()));
            return driverRepository.save(driver);
        } catch(CustomException ex) {
            throw new CustomException(ex.getErrorCode(), ex.getMessage());
        } catch (Exception ex) {
            log.error(ResponseCodeMapping.DB_FAILURE.getMessage(), ex.getMessage());
            throw new InternalServerErrorException(ResponseCodeMapping.DB_FAILURE.getCode(), ResponseCodeMapping.DB_FAILURE.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Optional<Driver> getDriver(Long id) {
        return Optional.of(driverRepository.findById(id).get());
    }

    @Transactional(readOnly = true)
    public void checkDriverExists(String email) throws CustomException {
        Optional<Driver> driver = driverRepository.findByEmail(email);
        if(driver.isPresent()) {
            throw new CustomException(ResponseCodeMapping.DRIVER_ALREADY_EXISTS.getCode(), ResponseCodeMapping.DRIVER_ALREADY_EXISTS.getMessage());
        }
    }
    @Transactional()
    public Driver updateStatus(Long driverId, Boolean status) throws InternalServerErrorException, DriverNotOnboardedException {
        // Check driver's background
        Driver driver = getDriver(driverId).get();
        OnboardingValidator.validate(driver);
        driver.setAvailable(status);
        try {
            return driverRepository.save(driver);
        } catch (Exception ex) {
            log.error(ResponseCodeMapping.DB_FAILURE.getMessage(), ex.getMessage());
            throw new InternalServerErrorException(ResponseCodeMapping.DB_FAILURE.getCode(),
                    ResponseCodeMapping.DB_FAILURE.getMessage());
        }
    }

    @Transactional()
    public void onboard(Driver driver) {
        driver.setOnboardingStatus(OnboardingStatus.IN_PROGRESS.name());
        driverRepository.save(driver);
        String docUrl = documentRepository.findByDriverId(driver.getId()).get().getUrl();
        DocumentInfo document = new DocumentInfo();
        document.setDocumentUrl(docUrl);
        document.setDriverId(driver.getId());
        // async task
        backgroundVerificationTask.verify(document);
    }
}
