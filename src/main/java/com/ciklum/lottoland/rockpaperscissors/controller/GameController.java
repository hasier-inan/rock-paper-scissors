package com.ciklum.lottoland.rockpaperscissors.controller;

import com.ciklum.lottoland.rockpaperscissors.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import static com.ciklum.lottoland.rockpaperscissors.config.WebSocketConstants.PLAY_ROUND_ENDPOINT;

/**
 * Game controller that handles game interactions for each user and offers data related to previous interactions.
 */
@Controller
public class GameController {

    @Autowired
    private MessagingService messagingService;

    @MessageMapping(PLAY_ROUND_ENDPOINT)
    public void playRound(SimpMessageHeaderAccessor header) {
        this.messagingService.sendMessage(header.getUser(), PLAY_ROUND_ENDPOINT, "something");
    }
}
