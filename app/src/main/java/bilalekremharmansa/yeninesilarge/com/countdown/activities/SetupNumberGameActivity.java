package bilalekremharmansa.yeninesilarge.com.countdown.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;

import bilalekremharmansa.yeninesilarge.com.countdown.CustomNumberPicker;
import bilalekremharmansa.yeninesilarge.com.countdown.R;

public class SetupNumberGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_number_game);

        CustomNumberPicker numberPicker = (CustomNumberPicker) findViewById(R.id.numPickLargeNumbers);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Button btnGetCards = (Button) findViewById(R.id.btnGetCards);
                btnGetCards.setText(newVal + getResources().getString(R.string.please));
            }
        });


    }





}
