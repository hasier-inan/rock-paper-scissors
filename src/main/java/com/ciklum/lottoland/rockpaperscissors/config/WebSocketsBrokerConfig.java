package com.ciklum.lottoland.rockpaperscissors.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static com.ciklum.lottoland.rockpaperscissors.config.WebSocketConstants.APP_PREFIX;
import static com.ciklum.lottoland.rockpaperscissors.config.WebSocketConstants.BROADCAST_PREFIX;
import static com.ciklum.lottoland.rockpaperscissors.config.WebSocketConstants.BROKER_PREFIX;
import static com.ciklum.lottoland.rockpaperscissors.config.WebSocketConstants.WS_ENDPOINT;

/**
 * Defines web socket broker details for the main application
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketsBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${stomp.allowed-origins}")
    public String allowedOrigins;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(BROKER_PREFIX, BROADCAST_PREFIX);
        config.setApplicationDestinationPrefixes(APP_PREFIX);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(WS_ENDPOINT)
                .setHandshakeHandler(new WebsocketHandshakeHandler())
                .setAllowedOrigins(allowedOrigins)
                .withSockJS();
    }
}
