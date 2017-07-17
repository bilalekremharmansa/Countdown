package bilalekremharmansa.yeninesilarge.com.countdown;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bilalekremharmansa on 17.7.2017.
 */

public class Player implements Parcelable{

    private String name;

    private int score;

    public Player() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.score);
    }

    protected Player(Parcel in) {
        this.name = in.readString();
        this.score = in.readInt();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
