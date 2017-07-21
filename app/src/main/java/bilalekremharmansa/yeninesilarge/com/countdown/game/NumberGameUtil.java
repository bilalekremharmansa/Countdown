package bilalekremharmansa.yeninesilarge.com.countdown.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Created by bilalekremharmansa on 19.7.2017.
 */

public class NumberGameUtil {

    public enum Operator {
        ADD, SUB, DIV, MUL
    }

    private Stack<MementoNumbersList> mementoNumbersListStack;

    public NumberGameUtil() {
        this.mementoNumbersListStack = new Stack<>();
    }

    public int[] dealCards(int numberOfLarge) {
        int[] deal = new int[24];
        Random randomGenerator = new Random();

        //small numbers
        for (int i = 0; i < deal.length - numberOfLarge; i++) {
            deal[i] = randomGenerator.nextInt(10) + 1;
        }

        //javada9
        //List<Integer> largeNumbersList = List.of(25, 50, 75,100);

        List<Integer> largeNumbersList = Arrays.asList(25, 50, 75, 100);
        Collections.shuffle(largeNumbersList);

        //largeNumbers
        int j = 0;//incremented j is in for loop's scope
        for (int i = deal.length - numberOfLarge; i < deal.length; i++, j++) {
            deal[i] = largeNumbersList.get(j);
        }

        return deal;
    }

    public int[] shuffleDeal(int[] deal) {
        Random randomGenerator = new Random();
        for (int i = 0; i < deal.length; i++) {
            swap(deal, i, randomGenerator.nextInt(deal.length));
        }
        return deal;
    }

    //helper method
    private int[] swap(int[] deal, int index, int randomIndex) {
        deal[index] = deal[index] ^ deal[randomIndex];
        deal[randomIndex] = deal[index] ^ deal[randomIndex];
        deal[index] = deal[index] ^ deal[randomIndex];
        return deal;

    }

    public void saveMemento(MementoNumbersList memento) {
        mementoNumbersListStack.push(memento);
    }

    public void saveMemento(List<Integer> numbersList, boolean[] numbersState) {
        mementoNumbersListStack.add(new MementoNumbersList(numbersList, numbersState));
    }

    public MementoNumbersList restoreMemento() {
        if (mementoNumbersListStack.size() < 0) return null;
        return mementoNumbersListStack.pop();
    }

    public boolean[] updateNumbersState(boolean[] numbersState, int firstIndex, int secondIndex, boolean isActive) {
        numbersState[firstIndex] = isActive;
        numbersState[secondIndex] = isActive;
        return numbersState;
    }

    public static int evaluateExpression(List<Integer> numbersList, int firstIndex, int secondIndex, Operator op) {
        int result;
        switch (op) {
            case ADD:
                result = add(numbersList, firstIndex, secondIndex);
                break;
            case SUB:
                result = subtract(numbersList, firstIndex, secondIndex);
                break;
            case MUL:
                result = multiply(numbersList, firstIndex, secondIndex);
                break;
            case DIV:
                result = divide(numbersList, firstIndex, secondIndex);
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

}
