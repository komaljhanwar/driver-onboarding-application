package org.example.driverapplication.service;

import lombok.extern.log4j.Log4j2;
import org.example.driverapplication.async.config.TrackingShipmentConfig;
import org.example.driverapplication.model.TrackingShipmentInfo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ShipmentService {

    private RabbitTemplate rabbitTemplate;

    public ShipmentService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void ship(TrackingShipmentInfo trackingShipmentInfo) {
        log.info("Publishing shipping event : " + trackingShipmentInfo);
        rabbitTemplate.convertAndSend(TrackingShipmentConfig.EXCHANGE,  TrackingShipmentConfig.ROUTING_KEY, trackingShipmentInfo);
    }

}
