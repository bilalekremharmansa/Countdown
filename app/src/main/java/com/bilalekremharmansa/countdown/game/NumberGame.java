package com.bilalekremharmansa.countdown.game;


import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Button;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import com.bilalekremharmansa.countdown.webapi.APINumberGame;

/**
 * Created by bilalekremharmansa on 17.7.2017.
 */

public class NumberGame extends CountdownGame implements Parcelable, NumberGameExpression.GameOverListener {

    private Logger log = LoggerFactory.getLogger(NumberGame.class);

    private List<NumberGameExpression> expressionList;

    public static int target;

    private List<Wrapper> wrapperList;

    private List<List<String>> solutionList;

    private GameOverListener gameOverListener;

    public interface GameOverListener {
        void onGameOver(int score);
    }


    public NumberGame(List<Integer> numbersList) {
        initWrapper(numbersList);
        this.target = 125;

        this.expressionList = new ArrayList<>();

        NumberGameExpression.setGameOverListener(this);
    }

    public NumberGame(APINumberGame apiGame) {
        initWrapper(apiGame.getNumbersList());
        this.solutionList = apiGame.getSolutionList();
        NumberGame.target = apiGame.getTarget();

        this.expressionList = new ArrayList<>(5);

        NumberGameExpression.setGameOverListener(this);
    }


    public void restoreNumberGame(MementoNumberGame memento) {
        this.wrapperList = new ArrayList<>(memento.getWrapperList());
        expressionList = new ArrayList<>(memento.getExpressionList());
    }

    public void initWrapper(List<Integer> numbersList) {
        this.wrapperList = new ArrayList<>(numbersList.size());
        for (int i : numbersList) {
            wrapperList.add(new Wrapper(i));
        }
    }

    public Wrapper getWrapperByID(int wrapperID) {
        for (Wrapper wrapper : wrapperList) {
            if (wrapperID == wrapper.getIdOfButton())
                return wrapper;
        }
        return null;
    }

    @Override
    public void onResultFound() {
        gameOverListener.onGameOver(score(target, target));
    }

    @Override
    public void onGameOver() {
        int higherScore = 0;
        int score;
        for (Wrapper wrapper : wrapperList) {
            // if(wrapper.getState() <= 2){
            score = score(wrapper.getValue(), target);
            higherScore = score > higherScore ? score : higherScore;
            // }
        }

        for (NumberGameExpression expression : expressionList) {
            if (expression.isDone() && expression.getCustomViewExpression().getBtnResult().isEnabled()) {
                score = score(Integer.valueOf(expression.getCustomViewExpression().getBtnResult().getText().toString()), target);
                higherScore = score > higherScore ? score : higherScore;
            }
        }

        gameOverListener.onGameOver(higherScore);
    }

    public void setGameOverListener(GameOverListener gameOverListener) {
        this.gameOverListener = gameOverListener;
    }

    private int score(int calculated, int target) {
        int away = Math.abs(target - calculated);

        /*if(away == 0) scorePoint = 10;
        else if( away < 6 ) scorePoint = 7;
        else if( away < 11 ) scorePoint = 5;*/

        //return score ;
        return (away == 0) ? 10 : (away < 6) ? 7 : (away < 11) ? 5 : 0;
    }

    public int getTarget() {
        return target;
    }

    public List<NumberGameExpression> getExpressionList() {
        return expressionList;
    }

    public List<Wrapper> getWrapperList() {
        return wrapperList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.expressionList);
        dest.writeList(this.wrapperList);
        dest.writeList(this.solutionList);
    }

    protected NumberGame(Parcel in) {
        this.expressionList = new ArrayList<>();
        in.readList(this.expressionList, NumberGameExpression.class.getClassLoader());
        this.wrapperList = new ArrayList<>();
        in.readList(this.wrapperList, Wrapper.class.getClassLoader());
        this.solutionList = new ArrayList<>();
        in.readList(this.solutionList, List.class.getClassLoader());
    }

    public static final Creator<NumberGame> CREATOR = new Creator<NumberGame>() {
        @Override
        public NumberGame createFromParcel(Parcel source) {
            return new NumberGame(source);
        }

        @Override
        public NumberGame[] newArray(int size) {
            return new NumberGame[size];
        }
    };


}
