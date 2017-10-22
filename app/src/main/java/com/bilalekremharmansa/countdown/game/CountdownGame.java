package com.bilalekremharmansa.countdown.game;

/**
 * Created by bilalekremharmansa on 17.7.2017.
 */

public abstract class CountdownGame {
    private int score;
    private int difficultyLevel;
    private double time;

    //private target

    private enum GameMode {
        WORD, NUMBER
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
