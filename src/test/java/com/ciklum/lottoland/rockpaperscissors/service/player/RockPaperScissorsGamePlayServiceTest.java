package com.ciklum.lottoland.rockpaperscissors.service.player;

import com.ciklum.lottoland.rockpaperscissors.model.GameResult;
import com.ciklum.lottoland.rockpaperscissors.model.Hand;
import com.ciklum.lottoland.rockpaperscissors.model.PlayedRoundResult;
import com.ciklum.lottoland.rockpaperscissors.model.TotalResults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static com.ciklum.lottoland.rockpaperscissors.model.GameResult.DRAW;
import static com.ciklum.lottoland.rockpaperscissors.model.GameResult.LOSE;
import static com.ciklum.lottoland.rockpaperscissors.model.GameResult.WIN;
import static com.ciklum.lottoland.rockpaperscissors.model.Hand.PAPER;
import static com.ciklum.lottoland.rockpaperscissors.model.Hand.ROCK;
import static com.ciklum.lottoland.rockpaperscissors.model.Hand.SCISSORS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class RockPaperScissorsGamePlayServiceTest {

    private final RockPaperScissorsGamePlayService gamePlayService = new RockPaperScissorsGamePlayService();

    @Mock
    private PlayerService player1Service;

    @Mock
    private PlayerService player2Service;

    @Test
    public void testRockWinsToScissors() {
        playRoundWithMockedHandsAndExpectResult(buildExpectedResult(ROCK, SCISSORS, WIN));
    }

    @Test
    public void testPaperWinsToRock() {
        playRoundWithMockedHandsAndExpectResult(buildExpectedResult(PAPER, ROCK, WIN));
    }

    @Test
    public void testScissorsWinsToPaper() {
        playRoundWithMockedHandsAndExpectResult(buildExpectedResult(SCISSORS, PAPER, WIN));
    }

    @Test
    public void testPlayer1Draws() {
        playRoundWithMockedHandsAndExpectResult(buildExpectedResult(ROCK, ROCK, DRAW));
    }

    @Test
    public void testPlayer1Loses() {
        playRoundWithMockedHandsAndExpectResult(buildExpectedResult(ROCK, PAPER, LOSE));
    }

    @Test
    public void testPlayedRoundIsSavedInMemory() {
        PlayedRoundResult aResult = buildExpectedResult(ROCK, PAPER, LOSE);
        this.gamePlayService.saveUserRound(aResult);
        List<PlayedRoundResult> allPlayedRounds = (List<PlayedRoundResult>)
                ReflectionTestUtils.getField(this.gamePlayService, "allPlayedRounds");
        assertThat("Expected played round to be saved in memory", allPlayedRounds.get(0), samePropertyValuesAs(aResult));
    }

    @Test
    public void testTotalStatsAreRetrievedFromSavedRounds() {
        this.gamePlayService.saveUserRound(buildExpectedResult(SCISSORS, PAPER, WIN));
        this.gamePlayService.saveUserRound(buildExpectedResult(ROCK, ROCK, DRAW));
        this.gamePlayService.saveUserRound(buildExpectedResult(ROCK, PAPER, LOSE));
        TotalResults expectedResults = new TotalResults(3, 1, 1, 1);

        assertThat("Expected total stats to match", this.gamePlayService.getTotalStats(),
                samePropertyValuesAs(expectedResults));
    }

    private void playRoundWithMockedHandsAndExpectResult(PlayedRoundResult expectedResult) {
        PlayedRoundResult playedRoundResult = this.gamePlayService.playRound(this.player1Service, this.player2Service);
        assertThat("Expected played round result to match", playedRoundResult, samePropertyValuesAs(expectedResult));
    }

    private PlayedRoundResult buildExpectedResult(Hand player1Hand, Hand player2Hand, GameResult gameResult) {
        when(this.player1Service.play()).thenReturn(player1Hand);
        when(this.player2Service.play()).thenReturn(player2Hand);
        return new PlayedRoundResult(player1Hand, player2Hand, gameResult);
    }

}