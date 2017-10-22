package com.bilalekremharmansa.countdown.activities;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilalekremharmansa.countdown.R;
import com.bilalekremharmansa.countdown.customcomponents.NumberGameButton;
import com.bilalekremharmansa.countdown.game.NumberGameUtil;
import com.bilalekremharmansa.countdown.game.Wrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumberGameCardListFragment extends Fragment {

    private int numberOfLargeNums;

    private NumbersListListener listener;

    private int[] buttonIDs = {R.id.btn, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10,
            R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16, R.id.btn17, R.id.btn18, R.id.btn19, R.id.btn20, R.id.btn21,
            R.id.btn22, R.id.btn23, R.id.btn24};

    private List<Integer> numbersList;


    public NumberGameCardListFragment() {
        // Required empty public constructor
    }

    @TargetApi(23)
    @Override
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (NumbersListListener) context;

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
            this.listener = (NumbersListListener) activity;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_number_game_card_list, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        numbersList = new ArrayList<>();
        View view = getView();
        NumberGameUtil numberGame = new NumberGameUtil();
        final int[] cardsList = numberGame.shuffleDeal(numberGame.dealCards(numberOfLargeNums));

        for (int i = 0; i < buttonIDs.length; i++) {

            final int valueOfCard = cardsList[i];
            NumberGameButton btnTemp = ((NumberGameButton) view.findViewById(buttonIDs[i]));
            btnTemp.setWrapper(new Wrapper(valueOfCard));


            if (valueOfCard >= 25) {
                btnTemp.setEnabled(false);
                btnTemp.setPicked(true);
                numbersList.add(valueOfCard);

            } else {
                btnTemp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NumberGameButton btn = (NumberGameButton) v;

                        //stackoverflow.com/questions/22648627/how-java-auto-boxing-unboxing-works
                        //bkz:This(Boxing) is beneficial since it allows for caching, and doesn't force the creation of a new object on each boxing operation.
                        //so I can use remove(object o ) method, when add two different cards with 2 seperate but same values, like 2 and 2. It both gets same reference and
                        //its not going to any problem for remove method().
                        if (btn.isPicked()) {
                            numbersList.remove(new Integer(valueOfCard));
                            btn.setPicked(false);
                        } else {
                            numbersList.add(valueOfCard);
                            btn.setPicked(true);

                            if (numbersList.size() == 6) {
                                listener.numbersListReady(numbersList);
                            }

                        }


                    }
                });
            }


            //TODO: burası silenecek, geçici.
            btnTemp.setText(String.valueOf(valueOfCard));
        }

    }

    public NumberGameCardListFragment setNumberOfLargeNums(int numberOfLargeNums) {
        this.numberOfLargeNums = numberOfLargeNums;

        return this;
    }

    interface NumbersListListener {
        void numbersListReady(List<Integer> numbersList);
    }
}
