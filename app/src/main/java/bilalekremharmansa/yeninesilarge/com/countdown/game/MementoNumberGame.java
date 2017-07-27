package bilalekremharmansa.yeninesilarge.com.countdown.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bilalekremharmansa on 19.7.2017.
 */

public final class MementoNumberGame {

    private final List<Integer> numbersList;
    private final byte[] numbersState;
    private final List<NumberGameExpression> expressionList;

    public enum MementoType {
        NUMBERS, STATE, EXPRESSION, NUMBERANDEXPRESSION
    }

    public MementoNumberGame(List<Integer> numbersList, byte[] numbersState, List<NumberGameExpression> expressionList) {
        this.numbersList = new ArrayList<>(numbersList);
        this.numbersState = Arrays.copyOf(numbersState, numbersState.length);
        this.expressionList = new ArrayList<>(expressionList);
    }


    public List<Integer> getNumbersList() {
        return numbersList;
    }

    public byte[] getNumbersState() {
        return numbersState;
    }

    public List<NumberGameExpression> getExpressionList() {
        return expressionList;
    }
}
