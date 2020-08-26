package com.ciklum.lottoland.rockpaperscissors.config;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.WebSocketHandler;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class WebsocketHandshakeHandlerTest {

    @Mock
    private WebSocketHandler wsHandler;

    @Mock
    private Map<String, Object> attributes;

    @Mock
    private ServerHttpRequest aRequest;

    private WebsocketHandshakeHandler websocketHandshakeHandler;

    @Before
    public void setUp() {
        this.websocketHandshakeHandler = new WebsocketHandshakeHandler();
    }

    @Test
    public void testDetermineUserReturnsANewStompPrincipal() {
        when(this.aRequest.getPrincipal()).thenReturn(null);
        assertNotNull("Expected a UUID as principal name", this.websocketHandshakeHandler.determineUser(
                aRequest, this.wsHandler, this.attributes)
        );
    }

    @Test
    public void testDetermineUserReturnsAnExistingPrincipal() {
        String username = "aUUID";
        when(this.aRequest.getPrincipal()).thenReturn(new StompPrincipal(username));
        assertEquals("Expected same existing UUID", username, this.websocketHandshakeHandler.determineUser(
                aRequest, this.wsHandler, this.attributes).getName()
        );
    }
}