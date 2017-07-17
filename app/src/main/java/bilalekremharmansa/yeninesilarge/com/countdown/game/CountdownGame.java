package bilalekremharmansa.yeninesilarge.com.countdown.game;

/**
 * Created by bilalekremharmansa on 17.7.2017.
 */

public abstract class CountdownGame {
    private int score;
    private int difficultyLevel;
    private double time;

    private enum GameMode{
        WORD,NUMBER
    }
}
