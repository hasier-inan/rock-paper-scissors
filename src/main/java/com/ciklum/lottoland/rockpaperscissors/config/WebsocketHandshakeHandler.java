package com.ciklum.lottoland.rockpaperscissors.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * This custom handshake handler overrides the DefaultHandshakeHandler so that every client can have its own uuid.
 */
public class WebsocketHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        if (isEmpty(request.getPrincipal())) {
            return new StompPrincipal(UUID.randomUUID().toString());
        }
        return request.getPrincipal();
    }

}
