package com.bilalekremharmansa.countdown.webapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bilalekremharmansa on 15.8.2017.
 */

public class APINumberGame implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("numberOfLargeNumbers")
    private int numberOfLargeNumbers;

    @SerializedName("numbers")
    private List<Integer> numbersList;

    @SerializedName("target")
    private int target;

    @SerializedName("solutionList")
    private List<List<String>> solutionList;

    @SerializedName("active")
    private boolean isActive;


    public int getId() {
        return id;
    }


    public int getNumberOfLargeNumbers() {
        return numberOfLargeNumbers;
    }

    public void setNumberOfLargeNumbers(int numberOfLargeNumbers) {
        this.numberOfLargeNumbers = numberOfLargeNumbers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getNumbersList() {
        return numbersList;
    }

    public void setNumbersList(List<Integer> numbersList) {
        this.numbersList = numbersList;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public List<List<String>> getSolutionList() {
        return solutionList;
    }

    public void setSolutionList(List<List<String>> solutionList) {
        this.solutionList = solutionList;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {

        return "ID :" + id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.numberOfLargeNumbers);
        dest.writeList(this.numbersList);
        dest.writeInt(this.target);
        dest.writeList(this.solutionList);
        dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
    }

    protected APINumberGame(Parcel in) {
        this.id = in.readInt();
        this.numberOfLargeNumbers = in.readInt();
        this.numbersList = new ArrayList<Integer>();
        in.readList(this.numbersList, Integer.class.getClassLoader());
        this.target = in.readInt();
        this.solutionList = new ArrayList<List<String>>();
        in.readList(this.solutionList, List.class.getClassLoader());
        this.isActive = in.readByte() != 0;
    }

    public static final Creator<APINumberGame> CREATOR = new Creator<APINumberGame>() {
        @Override
        public APINumberGame createFromParcel(Parcel source) {
            return new APINumberGame(source);
        }

        @Override
        public APINumberGame[] newArray(int size) {
            return new APINumberGame[size];
        }
    };
}
