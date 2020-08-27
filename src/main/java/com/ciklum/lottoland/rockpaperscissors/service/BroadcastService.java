package com.ciklum.lottoland.rockpaperscissors.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;

import static com.ciklum.lottoland.rockpaperscissors.config.WebSocketConstants.BROADCAST_PREFIX;
import static com.ciklum.lottoland.rockpaperscissors.config.WebSocketConstants.BROKER_PREFIX;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * A service to handle user messaging and message broadcasting
 */
@Service
public class BroadcastService implements MessagingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BroadcastService.class);

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public void sendMessage(Principal user, String topic, Object data) {
        if (!isEmpty(user)) {
            String fullTopicUrl = String.format("%s%s", BROKER_PREFIX, topic);
            LOGGER.debug("Writing info for {} in websocket topic {}", user.getName(), fullTopicUrl);
            this.template.convertAndSendToUser(user.getName(), fullTopicUrl, data);
        }
    }

    @Override
    public void broadcastMessage(String topic, Object data) {
        String fullTopicUrl = String.format("%s%s%s", BROADCAST_PREFIX, BROKER_PREFIX, topic);
        LOGGER.info("Broadcasting all rounds information into {} topic: {}", fullTopicUrl, data);
        this.template.convertAndSend(fullTopicUrl, data);
    }
}
