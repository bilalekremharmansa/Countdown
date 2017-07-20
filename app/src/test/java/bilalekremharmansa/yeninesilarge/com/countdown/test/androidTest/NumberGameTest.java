package bilalekremharmansa.yeninesilarge.com.countdown.test.androidTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bilalekremharmansa.yeninesilarge.com.countdown.game.NumberGame;
import bilalekremharmansa.yeninesilarge.com.countdown.game.NumberGameUtil;

import static org.junit.Assert.*;

/**
 * Created by bilalekremharmansa on 19.7.2017.
 */
public class NumberGameTest {

    NumberGame numberGame = new NumberGame();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void dealCards() throws Exception {

    }

    @Test
    public void scorePoint() {
        assertEquals(10, numberGame.score(5, 5));
        assertEquals(7, numberGame.score(5, 6));
        assertEquals(7, numberGame.score(5, 4));

        numberGame.evaluateExpression(numberGame.getNumbersList(), 1, 2, NumberGameUtil.Operator.ADD);
        numberGame.evaluateExpression(numberGame.getNumbersList(), 3, 4, NumberGameUtil.Operator.ADD);

    }


}