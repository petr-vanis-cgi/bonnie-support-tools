package com.cgi.bonnie.cli;

import com.cgi.bonnie.schema.OrderJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        order.withGoodsId(goods)
                .withQuantity(quantity)
                .withPlacementDate(LocalDateTime.now())
                .withShopOrderId(generateShopId(year, dayOfYear));
        kafkaTemplate.send(orderTopic, order);
    }

    private String generateShopId(int year, int dayOfYear) {
        return year + "/" + dayOfYear + "/" + new Random().nextInt(1000000);
    }
}
