package bilalekremharmansa.yeninesilarge.com.countdown.game;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Created by bilalekremharmansa on 17.7.2017.
 */

public class NumberGame extends CountdownGame {

    private Logger log = LoggerFactory.getLogger(NumberGame.class);

    public final static int NUMBER_OF_NUMBERS = 6;

    private static List<Integer> numbersList;
    private byte[] numbersState;
    private static List<NumberGameExpression> expressionList = new ArrayList<>();
    private final int target;

    private Container container;

    private NumberGameUtil gameUtil = new NumberGameUtil();

    private Stack<MementoNumberGame> mementoStack;

    public NumberGame(List<Integer> numbersList) {
        this.numbersList = numbersList;
        this.target = new Random().nextInt(900) + 100;
    }

    public NumberGame() {
        //this.target = new Random().nextInt(900) + 100;
        numbersList = new ArrayList<>(11); //List might has maximum 11 value.


        numbersList.add(75);
        numbersList.add(50);
        numbersList.add(2);
        numbersList.add(3);
        numbersList.add(8);
        numbersList.add(7);
        target = 812;
    }

    public void saveMemento() {
        mementoStack.add(new MementoNumberGame(numbersList, numbersState, expressionList));

    }

    public MementoNumberGame restoreMemento() {
        if (mementoStack.size() > 1) {
            return mementoStack.pop();
        } else if (mementoStack.size() == 1)
            return mementoStack.peek();
        else
            return null;
    }


    public int score(int declared, int target) {
        int away = Math.abs(target - declared);

        /*if(away == 0) scorePoint = 10;
        else if( away < 6 ) scorePoint = 7;
        else if( away < 11 ) scorePoint = 5;*/

        //return score ;
        return (away == 0) ? 10 : (away < 6) ? 7 : (away < 11) ? 5 : 0;
    }

    public static int evaluateExpression(NumberGameExpression expression) {
        int result;
        switch (expression.getOperator()) {
            case '+':
                result = add(numbersList, expression.getFirstNumberIndex(), expression.getSecondNumberIndex());
                break;
            case '-':
                result = subtract(numbersList, expression.getFirstNumberIndex(), expression.getSecondNumberIndex());
                break;
            case 'x':
                result = multiply(numbersList, expression.getFirstNumberIndex(), expression.getSecondNumberIndex());
                break;
            case '/':
                result = divide(numbersList, expression.getFirstNumberIndex(), expression.getSecondNumberIndex());
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }

    private static int add(List<Integer> numbersList, int firstIndex, int secondIndex) {
        int firstNumber = numbersList.get(firstIndex);
        int secondNumber = numbersList.get(secondIndex);
        return firstNumber + secondNumber;
    }

    private static int subtract(List<Integer> numbersList, int firstIndex, int secondIndex) {
        int firstNumber = numbersList.get(firstIndex);
        int secondNumber = numbersList.get(secondIndex);

        //If result is negative integer, this expression is not valid so return -1.
        if (firstNumber < secondNumber) return -1;

        return firstNumber - secondNumber;
    }

    private static int divide(List<Integer> numbersList, int firstIndex, int secondIndex) {
        int firstNumber = numbersList.get(firstIndex);
        int secondNumber = numbersList.get(secondIndex);

        if (secondNumber == 0) return -1;

        //We need to specify that we expect our result as double, so we need to cast first or second number to double
        double result = (double) firstNumber / secondNumber;

        //If result is an integer(we check it with result %1 ==0), return that result. If it's not return -1.
        return (result % 1 == 0) ? (int) result : -1;
    }

    private static int multiply(List<Integer> numbersList, int firstIndex, int secondIndex) {
        int firstNumber = numbersList.get(firstIndex);
        int secondNumber = numbersList.get(secondIndex);

        return firstNumber * secondNumber;
    }

    public List<Integer> getNumbersList() {
        return numbersList;
    }

    public int getTarget() {
        return target;
    }

    public NumberGameUtil getGameUtil() {
        return gameUtil;
    }

    public byte[] getNumbersState() {
        if (numbersState == null) {
            numbersState = new byte[11];
            Arrays.fill(numbersState, (byte) 0);
        }

        return numbersState;
    }

    public List<NumberGameExpression> getExpressionList() {
        return expressionList;
    }
}
