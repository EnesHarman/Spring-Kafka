package com.netmera.urlshortener.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class Starter implements ApplicationRunner {

    private final Producer kafkaProducerService;

    @Autowired
    public Starter(Producer kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String topic = "q_cmd_audit_log";

        // Execute this code when the application starts

        AuditLogCmd cmd = new AuditLogCmd("1","1","1","1","1","1","1","1",new Date());

        for(int i = 0; i < 1 ; i++) {
            String payload = "test";
            kafkaProducerService.sendMessage(topic, convertObjectToJson(cmd));
            System.out.println("Mesaj bırakıldı.");
        }

    }

    @Data
    @ToString
    class AuditLogCmd implements Serializable {
        private String userIp;
        private String userName;
        private String userEmail;
        private String requestAction;
        private String requestResource;
        private String affectedUserId;
        private String affectedExtId;
        private String userAction;
        private Date time;

        public AuditLogCmd(String userIp, String userName, String userEmail, String requestAction, String requestResource, String affectedUserId, String affectedExtId, String userAction, Date time) {
            this.userIp = userIp;
            this.userName = userName;
            this.userEmail = userEmail;
            this.requestAction = requestAction;
            this.requestResource = requestResource;
            this.affectedUserId = affectedUserId;
            this.affectedExtId = affectedExtId;
            this.userAction = userAction;
            this.time = time;
        }
    }

    public String convertObjectToJson(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Handle the exception as needed
            return null;
        }
    }
}
