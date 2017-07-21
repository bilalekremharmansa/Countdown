package bilalekremharmansa.yeninesilarge.com.countdown.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bilalekremharmansa on 19.7.2017.
 */

public class MementoNumbersList {

    private final List<Integer> numbersList;
    private boolean[] numbersState;

    public MementoNumbersList(List<Integer> numbersList, boolean[] numbersState) {
        this.numbersList = new ArrayList<>(numbersList);
        this.numbersState = numbersState;
    }

    public List<Integer> getNumbersList() {
        return numbersList;
    }

    public boolean[] getNumbersState() {
        return numbersState;
    }
}
