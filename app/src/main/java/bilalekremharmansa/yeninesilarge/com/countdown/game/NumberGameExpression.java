package bilalekremharmansa.yeninesilarge.com.countdown.game;

/**
 * Created by bilalekremharmansa on 24.7.2017.
 */

public class NumberGameExpression {
    private int firstNumberIndex;
    private char operator; //'\u0000'
    private int secondNumberIndex;
    private int resultIndex;
    private boolean isDone;

    public enum EnumExpression {
        FIRST, OPERATOR, SECOND, RESULT, DONE
    }

    private ValueChangedListener listener;

    public interface ValueChangedListener {
        void operatorValueChanged(String c);

        void oneOfIndexesValueChanged(NumberGameExpression expression, EnumExpression e, int value, int oldIndex); //1 is firstNumberIndex, 2 secondNumberIndex, 3 is resultIndex
    }

    public NumberGameExpression(int firstNumberIndex) {
        this.firstNumberIndex = firstNumberIndex;
        this.secondNumberIndex = -1;
        this.resultIndex = -1;
    }

    public void updateExpression(String c) {
        if (secondNumberIndex == -1) {
            operator = c.charAt(0);
            listener.operatorValueChanged(c);
        }

    }

    public void updateExpression(int index, int value) {
        boolean isFirstUpdated = false;
        int oldIndex;
        if (operator == '\u0000') {
            oldIndex = firstNumberIndex;
            firstNumberIndex = index;
            isFirstUpdated = true;
        } else {
            oldIndex = secondNumberIndex;
            secondNumberIndex = index;
            isDone = true;
        }
        listener.oneOfIndexesValueChanged(this, isFirstUpdated ? EnumExpression.FIRST : EnumExpression.SECOND, value, oldIndex);
    }

    public int getFirstNumberIndex() {
        return firstNumberIndex;
    }

    public void setFirstNumberIndex(int firstNumberIndex) {
        this.firstNumberIndex = firstNumberIndex;
    }

    public char getOperator() {
        return operator;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }

    public int getSecondNumberIndex() {
        return secondNumberIndex;
    }

    public void setSecondNumberIndex(int secondNumberIndex) {
        this.secondNumberIndex = secondNumberIndex;
    }

    public int getResultIndex() {
        return resultIndex;
    }

    public void setResultIndex(int resultIndex) {
        this.resultIndex = resultIndex;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public void setValueChangedListener(ValueChangedListener listener) {
        this.listener = listener;
    }
}
