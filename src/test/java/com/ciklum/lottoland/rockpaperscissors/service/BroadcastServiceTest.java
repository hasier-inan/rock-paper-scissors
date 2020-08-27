package com.ciklum.lottoland.rockpaperscissors.service;

import com.ciklum.lottoland.rockpaperscissors.config.StompPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static com.ciklum.lottoland.rockpaperscissors.config.WebSocketConstants.BROADCAST_PREFIX;
import static com.ciklum.lottoland.rockpaperscissors.config.WebSocketConstants.BROKER_PREFIX;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class BroadcastServiceTest {

    private static final String DUMMY_TOPIC = "/aTopic";

    @InjectMocks
    private BroadcastService broadcastService;

    @Mock
    private SimpMessagingTemplate template;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEmptyUserTriggersNoMessage() {
        this.broadcastService.sendMessage(null, DUMMY_TOPIC, "something");
        verify(this.template, never()).convertAndSendToUser(anyString(), anyString(), any());
    }

    @Test
    public void testMessageIsDelivered() {
        String message = "something";
        String userUUID = "user";
        this.broadcastService.sendMessage(new StompPrincipal(userUUID), DUMMY_TOPIC, message);
        verify(this.template, times(1)).convertAndSendToUser(eq(userUUID), eq(BROKER_PREFIX + DUMMY_TOPIC),
                eq(message));
    }

    @Test
    public void testMessageIsBroadcast() {
        String aMessage = "something";
        this.broadcastService.broadcastMessage(DUMMY_TOPIC, aMessage);
        verify(this.template, times(1)).convertAndSend(eq(BROADCAST_PREFIX + BROKER_PREFIX + DUMMY_TOPIC), eq(aMessage));
    }
}