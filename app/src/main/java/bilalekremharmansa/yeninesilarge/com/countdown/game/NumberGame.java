package bilalekremharmansa.yeninesilarge.com.countdown.game;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Created by bilalekremharmansa on 17.7.2017.
 */

public class NumberGame extends CountdownGame {

    private Logger log = LoggerFactory.getLogger(NumberGame.class);

    public final static int NUMBER_OF_NUMBERS = 6;

    private List<Integer> numbersList;
    private boolean[] numbersState;
    private final int target;

    private NumberGameUtil gameUtil = new NumberGameUtil();

    public NumberGame(List<Integer> numbersList) {
        this.numbersList = numbersList;
        this.target = new Random().nextInt(900) + 100;
    }

    public NumberGame() {
        //this.target = new Random().nextInt(900) + 100;
        numbersList = new ArrayList<>(11); //List might has maximum 11 value.
        numbersState = new boolean[11];

        numbersList.add(75);
        numbersList.add(50);
        numbersList.add(2);
        numbersList.add(3);
        numbersList.add(8);
        numbersList.add(7);
        target = 812;
    }


    public int score(int declared, int target) {
        int away = Math.abs(target - declared);

        /*if(away == 0) scorePoint = 10;
        else if( away < 6 ) scorePoint = 7;
        else if( away < 11 ) scorePoint = 5;*/

        //return score ;
        return (away == 0) ? 10 : (away < 6) ? 7 : (away < 11) ? 5 : 0;
    }

    public List<Integer> evaluateExpression(List<Integer> numbersList, int firstIndex, int secondIndex, NumberGameUtil.Operator op) {

        int result = NumberGameUtil.evaluateExpression(numbersList, firstIndex, secondIndex, op);

        if (result != -1) {
            gameUtil.saveMemento(numbersList, numbersState);
            numbersList.add(result);
            gameUtil.updateNumbersState(this.numbersState, firstIndex, secondIndex, false);
        } else {
            log.error("result is not valid", String.format("number one %d number two %d, operator:" + op),
                    numbersList.get(firstIndex), numbersList.get(secondIndex));
        }

        return numbersList;
    }

    public List<Integer> getNumbersList() {
        return numbersList;
    }

    public int getTarget() {
        return target;
    }
}
