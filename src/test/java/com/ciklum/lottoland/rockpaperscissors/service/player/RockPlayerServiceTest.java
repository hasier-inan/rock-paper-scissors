package com.ciklum.lottoland.rockpaperscissors.service.player;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static com.ciklum.lottoland.rockpaperscissors.model.Hand.ROCK;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringRunner.class)
public class RockPlayerServiceTest {

    @Test
    public void testRockPlayerSelectsRock() {
        RockPlayerService rockplayerService = new RockPlayerService();
        assertEquals("Expected hand to be Rock", ROCK, rockplayerService.play());
    }
}
