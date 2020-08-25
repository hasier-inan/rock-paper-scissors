package com.ciklum.lottoland.rockpaperscissors.service.player;

import com.ciklum.lottoland.rockpaperscissors.model.GameResult;
import com.ciklum.lottoland.rockpaperscissors.model.Hand;
import com.ciklum.lottoland.rockpaperscissors.model.PlayedRoundResult;
import org.springframework.stereotype.Service;

import static com.ciklum.lottoland.rockpaperscissors.model.Hand.PAPER;
import static com.ciklum.lottoland.rockpaperscissors.model.Hand.ROCK;
import static com.ciklum.lottoland.rockpaperscissors.model.Hand.SCISSORS;

/**
 * A service that defines the rules for the Rock Papers Scissors game and retrieves results for played rounds.
 */
@Service
public class RockPaperScissorsGamePlayService implements GamePlayService {

    @Override
    public PlayedRoundResult playRound(PlayerService player1, PlayerService player2) {
        Hand player1Selection = player1.play();
        Hand player2Selection = player2.play();
        return new PlayedRoundResult(player1Selection, player2Selection, checkResult(player1Selection, player2Selection));
    }

    private GameResult checkResult(Hand player1Hand, Hand player2Hand) {
        if (player1Hand.equals(player2Hand)) {
            return GameResult.DRAW;
        } else if (isAPlayer1Win(player1Hand, player2Hand)) {
            return GameResult.WIN;
        }
        return GameResult.LOSE;
    }

    private Boolean isAPlayer1Win(Hand player1Hand, Hand player2Hand) {
        switch (player1Hand) {
            case ROCK:
                return SCISSORS.equals(player2Hand);
            case SCISSORS:
                return PAPER.equals(player2Hand);
            default:
                return ROCK.equals(player2Hand);

        }
    }
}
