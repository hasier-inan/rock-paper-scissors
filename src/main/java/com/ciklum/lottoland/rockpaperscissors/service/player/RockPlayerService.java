package com.ciklum.lottoland.rockpaperscissors.service.player;

import com.ciklum.lottoland.rockpaperscissors.model.Hand;
import org.springframework.stereotype.Service;

import static com.ciklum.lottoland.rockpaperscissors.model.Hand.ROCK;

/**
 * A player service that selects rock as a hand
 */
@Service("rockPlayer")
public class RockPlayerService implements PlayerService {

    @Override
    public Hand play() {
        return ROCK;
    }
}
