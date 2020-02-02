package com.personal.sagapattern.orchestration.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SagaOrchestrationService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void orchestrate(String event, List<String> eventTopics) {
        logger.info("START ::: Event triggered");

        for (String eventTopic : eventTopics) {
            logger.info("PROGRESS ::: Begin to orchestrate event {} to {}", event, eventTopic);

            kafkaTemplate.send(eventTopic, event);
        }

        kafkaTemplate.flush();
        logger.info("FINISH ::: Finish to orchestrate");
    }
}