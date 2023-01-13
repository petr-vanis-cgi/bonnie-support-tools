package com.cgi.bonnie.cli;

import com.example.types.OrderJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
public class PlaceOrderKafkaProducer {

    @Value("${spring.bonnie.kafka.topic.order}")
    private String orderTopic;

    @Autowired
    private KafkaTemplate<String, OrderJson> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void placeOrder(String goods, int quantity) {
        int year = LocalDate.now().getYear();
        int dayOfYear = LocalDate.now().getDayOfYear();
        OrderJson order = new OrderJson();
        order.setGoodsId(goods);
        order.setQuantity(quantity);
        order.setShopOrderId(year + "/" + dayOfYear + "/" + new Random().nextInt(1000000));
        order.setMetadata(objectMapper.createObjectNode().asText());
        kafkaTemplate.send(orderTopic, order);
    }
}
