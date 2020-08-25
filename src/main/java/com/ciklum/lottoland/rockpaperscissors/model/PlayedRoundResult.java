package com.ciklum.lottoland.rockpaperscissors.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Pojo that defines the result of a played round
 */
@AllArgsConstructor
@Getter
public class PlayedRoundResult {

    private Hand player1Hand;
    private Hand player2Hand;
    private GameResult gameResult;

}
