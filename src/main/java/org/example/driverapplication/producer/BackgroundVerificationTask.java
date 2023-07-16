package org.example.driverapplication.producer;


import lombok.extern.log4j.Log4j2;
import org.example.driverapplication.async.config.BackgroundVerificationConfig;
import org.example.driverapplication.model.Document;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class BackgroundVerificationTask {
    private RabbitTemplate rabbitTemplate;

    public BackgroundVerificationTask(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void verify(Document document) {
        log.info("Publishing document : " + document.getDocumentUrl());
        rabbitTemplate.convertAndSend(BackgroundVerificationConfig.EXCHANGE, BackgroundVerificationConfig.ROUTING_KEY, document);
    }
}
