package com.ciklum.lottoland.rockpaperscissors.controller;

import com.ciklum.lottoland.rockpaperscissors.config.StompPrincipal;
import com.ciklum.lottoland.rockpaperscissors.service.MessagingService;
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
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GameControllerTest {

    private static final String USERNAME = "uuuu";

    @InjectMocks
    private GameController gameController;

    @Mock
    private MessagingService messagingService;

    @Mock
    private SimpMessageHeaderAccessor headerMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(this.headerMock.getUser()).thenReturn(new StompPrincipal(USERNAME));
    }

    @Test
    public void testPlayRoundResultIsSentToUser() {
        this.gameController.playRound(this.headerMock);
        verify(this.messagingService).sendMessage(sameUsername(USERNAME), eq(PLAY_ROUND_ENDPOINT), eq("something"));
    }

    private Principal sameUsername(String username) {
        return argThat(value -> username.equals(value.getName()));
    }
}