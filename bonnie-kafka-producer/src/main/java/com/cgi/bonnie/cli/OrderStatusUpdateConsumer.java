package com.cgi.bonnie.cli;

import com.cgi.bonnie.schema.OrderStatusUpdateJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class OrderStatusUpdateConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderStatusUpdateConsumer.class);

    @Value("${spring.bonnie.kafka.topic.message}")
    private String orderUpdateTopic;

    @Value("${spring.kafka.consumer.group-id}")
    private String bonnieGroupId;

    @PostConstruct
    public void postConstruct() {
        LOGGER.info("Topic: {}, groupId: {}", orderUpdateTopic, bonnieGroupId);
    }

    @KafkaListener(topics = "${spring.bonnie.kafka.topic.message}", groupId = "${spring.kafka.consumer.group-id}")
    public void orderStatusUpdate(OrderStatusUpdateJson statusUpdate) {
        LOGGER.info("Status update has been arrived: {}", statusUpdate);
    }
}
