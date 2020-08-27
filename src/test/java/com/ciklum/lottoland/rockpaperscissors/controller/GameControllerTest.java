package com.ciklum.lottoland.rockpaperscissors.controller;

import com.ciklum.lottoland.rockpaperscissors.config.StompPrincipal;
import com.ciklum.lottoland.rockpaperscissors.model.GameResult;
import com.ciklum.lottoland.rockpaperscissors.model.Hand;
import com.ciklum.lottoland.rockpaperscissors.model.PlayedRoundResult;
import com.ciklum.lottoland.rockpaperscissors.model.TotalResults;
import com.ciklum.lottoland.rockpaperscissors.service.MessagingService;
import com.ciklum.lottoland.rockpaperscissors.service.player.GamePlayService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;

import static com.ciklum.lottoland.rockpaperscissors.config.WebSocketConstants.PLAY_ROUND_ENDPOINT;
import static com.ciklum.lottoland.rockpaperscissors.config.WebSocketConstants.TOTAL_ROUNDS_ENDPOINT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GameControllerTest {

    private static final String USERNAME = "uuuu";
    private static final PlayedRoundResult EXPECTED_RESULT = new PlayedRoundResult(
            Hand.ROCK, Hand.SCISSORS, GameResult.WIN);
    private static final TotalResults EXPECTED_STATS = new TotalResults(1, 1, 0, 0);

    @InjectMocks
    private GameController gameController;

    @Mock
    private GamePlayService gamePlayService;

    @Mock
    private MessagingService messagingService;

    @Mock
    private SimpMessageHeaderAccessor headerMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(this.gamePlayService.playRound(any(), any())).thenReturn(EXPECTED_RESULT);
        when(this.gamePlayService.getTotalStats()).thenReturn(EXPECTED_STATS);
        when(this.headerMock.getUser()).thenReturn(new StompPrincipal(USERNAME));
    }

    @Test
    public void testPlayRoundResultIsSentToUser() {
        this.gameController.playRound(this.headerMock);

        verify(this.messagingService).sendMessage(sameUsername(USERNAME), eq(PLAY_ROUND_ENDPOINT),
                sameResult(EXPECTED_RESULT));
    }

    @Test
    public void testUserRoundIsSavedInMemory() {
        this.gameController.playRound(this.headerMock);
        verify(this.gamePlayService).saveUserRound(sameResult(EXPECTED_RESULT));
    }

    @Test
    public void testTotalStatsAreUpdatedAndBroadcastInRealTime() {
        this.gameController.playRound(this.headerMock);
        verify(this.messagingService).broadcastMessage(eq(TOTAL_ROUNDS_ENDPOINT),
                eq(EXPECTED_STATS));
    }

    @Test
    public void testTotalResultsAreSentToUser() {
        this.gameController.totalRounds(this.headerMock);

        verify(this.messagingService).sendMessage(sameUsername(USERNAME), eq(TOTAL_ROUNDS_ENDPOINT),
                sameStats(EXPECTED_STATS));
    }
    
    private TotalResults sameStats(TotalResults expectedStats) {
        return argThat(result -> result.getTotalRounds() == expectedStats.getTotalRounds() &&
                result.getTotalWins() == expectedStats.getTotalWins() &&
                result.getTotalLoses() == expectedStats.getTotalLoses() &&
                result.getTotalDraws() == expectedStats.getTotalDraws()
        );
    }

    private PlayedRoundResult sameResult(PlayedRoundResult expectedResult) {
        return argThat(result -> result.getPlayer1Hand().equals(expectedResult.getPlayer1Hand()) &&
                result.getPlayer2Hand().equals(expectedResult.getPlayer2Hand()) &&
                result.getGameResult().equals(expectedResult.getGameResult())
        );
    }

    private Principal sameUsername(String username) {
        return argThat(value -> username.equals(value.getName()));
    }
}