package bilalekremharmansa.yeninesilarge.com.countdown;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by bilalekremharmansa on 25.7.2017.
 */

public class NumberGameButton extends android.support.v7.widget.AppCompatButton {

    private int idOfNumbersList;

    public NumberGameButton(Context context, int idOfNumbersList) {
        super(context);
        this.idOfNumbersList = idOfNumbersList;
    }

    public NumberGameButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getIdOfNumbersList() {
        return idOfNumbersList;
    }

    public void setIdOfNumbersList(int idOfNumbersList) {
        this.idOfNumbersList = idOfNumbersList;
    }
}
