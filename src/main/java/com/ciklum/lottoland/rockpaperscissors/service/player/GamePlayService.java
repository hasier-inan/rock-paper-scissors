package com.ciklum.lottoland.rockpaperscissors.service.player;

import com.ciklum.lottoland.rockpaperscissors.model.PlayedRoundResult;

/**
 * Round service definition to specify a round play
 */
public interface GamePlayService {

    PlayedRoundResult playRound(PlayerService player1, PlayerService player2);
    
}
