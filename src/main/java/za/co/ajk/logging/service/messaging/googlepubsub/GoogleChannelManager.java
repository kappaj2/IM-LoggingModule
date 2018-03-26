package za.co.ajk.logging.service.messaging.googlepubsub;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import za.co.ajk.logging.service.messaging.dto.InterModulePubSubMessage;


public interface GoogleChannelManager {
    
    void pubSubMessageSender(InterModulePubSubMessage interModulePubSubMessage);
    
    MessageChannel pubsubInputChannel();
    
    MessageHandler messageReceiver();
    
}
