package org.example.driverapplication.listener;

import lombok.extern.log4j.Log4j2;
import org.example.driverapplication.async.backgroundtask.ITrackingShipment;
import org.example.driverapplication.async.config.TrackingShipmentConfig;
import org.example.driverapplication.constants.OnboardingStatus;
import org.example.driverapplication.constants.ShipmentStatus;
import org.example.driverapplication.entity.Driver;
import org.example.driverapplication.model.TrackingShipmentInfo;
import org.example.driverapplication.repository.DriverRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TrackingShipmentListener {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    ITrackingShipment trackingShipment;

    @RabbitListener(queues = TrackingShipmentConfig.QUEUE)
    public void listen(TrackingShipmentInfo trackingShipmentInfo) {
        Driver driver = driverRepository.findById(trackingShipmentInfo.getDriverId()).get();
        try {
            log.info("Shipment Event received from queue : " + trackingShipmentInfo);
            ShipmentStatus status = trackingShipment.ship(trackingShipmentInfo);
            driver.setShipmentStatus(status.name());

            if(driver.getShipmentStatus() == ShipmentStatus.COMPLETED.name()) {
                driver.setOnboardingStatus(OnboardingStatus.COMPLETED.name());
            }
            driverRepository.save(driver);
        }  catch (Exception e) {
            log.info("Shipment of tracking device failed for driver : " + driver.getId());
        }

    }
}
