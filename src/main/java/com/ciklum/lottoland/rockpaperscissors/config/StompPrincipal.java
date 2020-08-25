package com.ciklum.lottoland.rockpaperscissors.config;

import java.security.Principal;

/**
 * Model to store subjects data
 */
public class StompPrincipal implements Principal {

    private String name;

    public StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
