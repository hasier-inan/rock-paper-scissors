package com.ciklum.lottoland.rockpaperscissors.service.player;

import com.ciklum.lottoland.rockpaperscissors.model.Hand;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.ciklum.lottoland.rockpaperscissors.model.Hand.PAPER;
import static com.ciklum.lottoland.rockpaperscissors.model.Hand.ROCK;
import static com.ciklum.lottoland.rockpaperscissors.model.Hand.SCISSORS;

/**
 * A player service that selects a random hand
 */
@Service("randomPlayer")
public class RandomPlayerService implements PlayerService {

    private static final List<Hand> HANDS = Arrays.asList(PAPER, ROCK, SCISSORS);

    @Override
    public Hand play() {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, HANDS.size());
        return HANDS.get(randomIndex);
    }
}
