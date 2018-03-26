package za.co.ajk.logging.service.messaging.impl;

import java.io.IOException;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import za.co.ajk.logging.domain.GenericMessagesReceived;
import za.co.ajk.logging.enums.PubSubMessageType;
import za.co.ajk.logging.repository.GenericMessagesReceivedRepository;
import za.co.ajk.logging.service.messaging.IMMessageProcessor;
import za.co.ajk.logging.service.messaging.dto.InterModulePubSubMessage;


@Component(value = "imMessageProcessor")
public class IMMessageProcessorImpl implements IMMessageProcessor {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private GenericMessagesReceivedRepository repository;
    
    @Override
    public void processMessageReceived(Message<?> message) {
        PubSubMessageType messageType = PubSubMessageType.GENERIC;
        
        String messageId = null;
        if (message.getHeaders().containsKey("id")) {
            messageId = message.getHeaders().get("id").toString();
        }
        if (message.getHeaders().containsKey("PubSubMessageType")) {
            String mes = message.getHeaders().get("PubSubMessageType").toString();
            messageType = PubSubMessageType.findPubSubMessageType(mes);
        }
        log.info("MessageId : " + messageId);
        
        String payload = "";
        
        switch (messageType) {
            case GENERIC:
                try {
                    
                    log.info("Generic message received ...");
                    InterModulePubSubMessage inboundMessage = objectMapper
                        .readValue(message.getPayload().toString(), InterModulePubSubMessage.class);
    
                    GenericMessagesReceived gm = new GenericMessagesReceived();
    
                    gm.setDateReceived(Instant.now());
                    gm.setEventTypeCode(inboundMessage.getEventType().getEventTypeCode());
                    gm.setIncidentDescription(inboundMessage.getIncidentDescription());
                    gm.setIncidentHeader(inboundMessage.getIncidentHeader());
                    gm.setIncidentNumber(inboundMessage.getIncidentNumber() == null ? 0L: inboundMessage.getIncidentNumber());
                    gm.setIncidentPriorityCode(inboundMessage.getIncidentPriority().getPriorityCode());
                    gm.setMessageId(messageId);
                    gm.setOriginatingModule(inboundMessage.getOriginatingApplicationModuleName());
                    gm.setMessageDateCreated(inboundMessage.getMessageDateCreated());
                    gm.setOperatorName(inboundMessage.getOperatorName());
                    gm.setPayload( message.getPayload().toString());
                    gm.setPubSubMessageTypeCode(inboundMessage.getPubSubMessageType().getMessageTypeCode());
    
                    repository.save(gm);
                    
                } catch (IOException ioe) {
                    log.error("Error parsing payload : ", ioe.getMessage());
                }
                break;
            case INCIDENT:
                try {
                    
                    InterModulePubSubMessage inboundMessage = objectMapper
                        .readValue(message.getPayload().toString(), InterModulePubSubMessage.class);
                    
                    GenericMessagesReceived gm = new GenericMessagesReceived();
                    
                    gm.setDateReceived(Instant.now());
                    gm.setEventTypeCode(inboundMessage.getEventType().getEventTypeCode());
                    gm.setIncidentDescription(inboundMessage.getIncidentDescription());
                    gm.setIncidentHeader(inboundMessage.getIncidentHeader());
                    gm.setIncidentNumber(inboundMessage.getIncidentNumber());
                    gm.setIncidentPriorityCode(inboundMessage.getIncidentPriority().getPriorityCode());
                    gm.setMessageId(messageId);
                    gm.setOriginatingModule(inboundMessage.getOriginatingApplicationModuleName());
                    gm.setMessageDateCreated(inboundMessage.getMessageDateCreated());
                    gm.setOperatorName(inboundMessage.getOperatorName());
                    gm.setPayload( message.getPayload().toString());
                    gm.setPubSubMessageTypeCode(inboundMessage.getPubSubMessageType().getMessageTypeCode());
                    
                    repository.save(gm);
                    
                } catch (IOException io) {
                    io.printStackTrace();
                }
                break;
            default:
                payload = "Unknown message format received : ";
        }
        log.info("Payload   => " + payload);
    }
}
