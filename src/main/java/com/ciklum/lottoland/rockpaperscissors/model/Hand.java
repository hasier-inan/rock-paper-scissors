package com.ciklum.lottoland.rockpaperscissors.model;

/**
 * A definition for every option a player can choose
 */
public enum Hand {
    ROCK("Rock"),
    PAPER("Paper"),
    SCISSORS("Scissors");

    private String name;

    Hand(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
