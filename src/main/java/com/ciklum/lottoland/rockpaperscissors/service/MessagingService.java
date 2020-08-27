package com.ciklum.lottoland.rockpaperscissors.service;

import java.security.Principal;

/**
 * Messaging service definition to send and broadcast data
 */
public interface MessagingService {

    void sendMessage(Principal user, String topic, Object data);
    void broadcastMessage(String topic, Object data);
}
