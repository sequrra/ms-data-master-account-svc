//package com.ms.data.master.account.service.event;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthEventProducer {
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    @Value("${kafka.topics[0].name}")
//    private String authEventsTopic;
//
//    public AuthEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void sendAuthEvent(String userId, String token, String email, String accountType) {
//        String message = String.format(
//                "{\"userId\": \"%s\", \"token\": \"%s\", \"email\": \"%s\", \"accountType\": \"%s\"}",
//                userId, token, email, accountType
//        );
//        kafkaTemplate.send(authEventsTopic, message);
//    }
//}
