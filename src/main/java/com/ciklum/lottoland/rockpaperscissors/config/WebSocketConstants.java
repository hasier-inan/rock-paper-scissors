package com.ciklum.lottoland.rockpaperscissors.config;

/**
 * Utility class that defines every endpoint in the game
 */
public class WebSocketConstants {

    public static final String PLAY_ROUND_ENDPOINT = "/play-round";
    public static final String APP_PREFIX = "/rps";
    public static final String BROKER_PREFIX = "/topic";
    public static final String BROADCAST_PREFIX = "/user";
    public static final String WS_ENDPOINT = "/ws";

    private WebSocketConstants() {
        throw new IllegalStateException("Utility class");
    }
}
