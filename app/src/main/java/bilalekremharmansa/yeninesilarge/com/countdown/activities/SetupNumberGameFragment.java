package bilalekremharmansa.yeninesilarge.com.countdown.activities;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;


import bilalekremharmansa.yeninesilarge.com.countdown.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetupNumberGameFragment extends Fragment {

    private SetupNumberGameListener listener;

    public SetupNumberGameFragment() {
        // Required empty public constructor
    }


    @TargetApi(23)
    @Override
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (SetupNumberGameListener) context;

    }

    /*
     * Deprecated on API 23
     * Use onAttachToContext instead
     */
    @SuppressWarnings("deprecation")
    @Override
    public final void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            this.listener = (SetupNumberGameListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_setup_number_game, container, false);

        final NumberPicker numPickLargeNumber = (NumberPicker) view.findViewById(R.id.numPickLargeNumbers);
        final Button btnGetCards = (Button) view.findViewById(R.id.btnGetCards);

        numPickLargeNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                btnGetCards.setText(newVal + getResources().getString(R.string.please));
            }
        });


        btnGetCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfLarge = ((NumberPicker) view.findViewById(R.id.numPickLargeNumbers)).getValue();
                listener.getCards(numberOfLarge);
            }
        });
        return view;
    }

    interface SetupNumberGameListener {
        void getCards(int numberOfLarge);
    }


}
