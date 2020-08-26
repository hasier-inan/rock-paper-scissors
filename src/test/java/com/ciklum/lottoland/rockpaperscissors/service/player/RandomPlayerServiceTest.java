package com.ciklum.lottoland.rockpaperscissors.service.player;

import com.ciklum.lottoland.rockpaperscissors.model.Hand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static com.ciklum.lottoland.rockpaperscissors.model.Hand.PAPER;
import static com.ciklum.lottoland.rockpaperscissors.model.Hand.ROCK;
import static com.ciklum.lottoland.rockpaperscissors.model.Hand.SCISSORS;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
public class RandomPlayerServiceTest {

    private final RandomPlayerService randomPlayerService = new RandomPlayerService();

    @Test
    public void testRandomPlayerRetrievesARandomHand() {
        List<Hand> allSelections = Arrays.asList(new Hand[20]);
        allSelections.replaceAll(hand -> this.randomPlayerService.play());

        Arrays.asList(PAPER, ROCK, SCISSORS).forEach(hand ->
                assertTrue("Expected every hand to be found within 20 hand selections",
                        countHands(allSelections, hand) > 1)
        );
    }

    private long countHands(List<Hand> allSelections, Hand hand) {
        return allSelections.stream().filter(hand::equals).count();
    }
}