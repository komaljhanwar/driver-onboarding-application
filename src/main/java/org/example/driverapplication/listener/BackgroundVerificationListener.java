package org.example.driverapplication.listener;

import lombok.extern.log4j.Log4j2;
import org.example.driverapplication.async.backgroundtask.IBackgroundVerification;
import org.example.driverapplication.async.config.BackgroundVerificationConfig;
import org.example.driverapplication.constants.BackgroundCheckStatus;
import org.example.driverapplication.constants.ResponseCodeMapping;
import org.example.driverapplication.constants.ShipmentStatus;
import org.example.driverapplication.entity.Driver;
import org.example.driverapplication.exception.ResourceNotFoundException;
import org.example.driverapplication.model.Document;
import org.example.driverapplication.model.TrackingShipmentInfo;
import org.example.driverapplication.repository.DriverRepository;
import org.example.driverapplication.service.ShipmentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log4j2
public class BackgroundVerificationListener {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    IBackgroundVerification backgroundVerification;

    @Autowired
    ShipmentService shipmentService;


   @RabbitListener(queues = BackgroundVerificationConfig.QUEUE)
    public void listen(Document document) {
        Driver driver = driverRepository.findById(document.getDriverId()).orElseThrow(() ->
                new ResourceNotFoundException(ResponseCodeMapping.DRIVER_NOT_FOUND.getCode(),
                        ResponseCodeMapping.DRIVER_NOT_FOUND.getMessage()));
        try {
            log.info("Document received from queue : " + document);
            BackgroundCheckStatus status = backgroundVerification.verify(document);
            driver.setBackgroundCheckStatus(status.name());
            driverRepository.save(driver);

            if(driver.getBackgroundCheckStatus() == BackgroundCheckStatus.REJECTED.name()) {
                return;
            }
            // trackingId & Shipment Status --> Driver
            TrackingShipmentInfo trackingShipmentInfo = new TrackingShipmentInfo();
            trackingShipmentInfo.setDriverId(driver.getId());
            trackingShipmentInfo.setDeviceId(UUID.randomUUID().toString());
            trackingShipmentInfo.setTrackingId(UUID.randomUUID().toString());
            driver.setShipmentStatus(ShipmentStatus.IN_PROGRESS.name());
            driver.setTrackingId(trackingShipmentInfo.getTrackingId());
            driverRepository.save(driver);
            shipmentService.ship(trackingShipmentInfo);

        }  catch (Exception e) {
            log.info("Background verification failed for document : " + document);
            driver.setBackgroundCheckStatus(BackgroundCheckStatus.FAILED.name());
            driverRepository.save(driver);
        }

    }



}
