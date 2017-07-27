package bilalekremharmansa.yeninesilarge.com.countdown.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Created by bilalekremharmansa on 19.7.2017.
 */

public class NumberGameUtil {


    public NumberGameUtil() {

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




    public boolean[] updateNumbersState(boolean[] numbersState, int firstIndex, int secondIndex, boolean isActive) {
        numbersState[firstIndex] = isActive;
        numbersState[secondIndex] = isActive;
        return numbersState;
    }



}
