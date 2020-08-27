package com.ciklum.lottoland.rockpaperscissors.controller;

import com.ciklum.lottoland.rockpaperscissors.model.PlayedRoundResult;
import com.ciklum.lottoland.rockpaperscissors.model.TotalResults;
import com.ciklum.lottoland.rockpaperscissors.service.MessagingService;
import com.ciklum.lottoland.rockpaperscissors.service.player.GamePlayService;
import com.ciklum.lottoland.rockpaperscissors.service.player.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import static com.ciklum.lottoland.rockpaperscissors.config.WebSocketConstants.PLAY_ROUND_ENDPOINT;
import static com.ciklum.lottoland.rockpaperscissors.config.WebSocketConstants.TOTAL_ROUNDS_ENDPOINT;

/**
 * Game controller that handles game interactions for each user and offers data related to previous interactions.
 */
@Controller
public class GameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GamePlayService gamePlayService;

    @Autowired
    @Qualifier("randomPlayer")
    private PlayerService player1;

    @Autowired
    @Qualifier("rockPlayer")
    private PlayerService player2;

    @Autowired
    private MessagingService messagingService;

    @MessageMapping(PLAY_ROUND_ENDPOINT)
    public void playRound(SimpMessageHeaderAccessor header) {
        PlayedRoundResult roundResult = this.gamePlayService.playRound(this.player1, this.player2);
        LOGGER.debug("A new round has been played with the following result: {} vs {} ({})",
                roundResult.getPlayer1Hand(), roundResult.getPlayer2Hand(), roundResult.getGameResult());
        this.gamePlayService.saveUserRound(roundResult);
        this.messagingService.sendMessage(header.getUser(), PLAY_ROUND_ENDPOINT, roundResult);
        this.messagingService.broadcastMessage(TOTAL_ROUNDS_ENDPOINT, this.gamePlayService.getTotalStats());
    }

    @MessageMapping(TOTAL_ROUNDS_ENDPOINT)
    public void totalRounds(SimpMessageHeaderAccessor header) {
        TotalResults totalResults = this.gamePlayService.getTotalStats();
        this.messagingService.sendMessage(header.getUser(), TOTAL_ROUNDS_ENDPOINT, totalResults);
    }

}
