package org.example.driverapplication.async.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrackingShipmentConfig {
    public static final String QUEUE = "driver.tracking.shipment.queue";
    public static final String EXCHANGE = "driver.tracking.shipment.exchange";
    public static final String ROUTING_KEY = "driver.tracking.shipment.routing.key";
    @Bean
    public Queue queue() {
        return new Queue("QUEUE", false);
    }
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("EXCHANGE");
    }
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("ROUTING_KEY");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
