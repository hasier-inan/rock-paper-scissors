package com.ciklum.lottoland.rockpaperscissors.model;

/**
 * A definition for every result may happen in a game
 */

public enum GameResult {
    WIN(true),
    LOSE(false),
    DRAW(false);

    private Boolean result;

    GameResult(final Boolean result) {
        this.result = result;
    }

    public Boolean getName() {
        return this.result;
    }
}
