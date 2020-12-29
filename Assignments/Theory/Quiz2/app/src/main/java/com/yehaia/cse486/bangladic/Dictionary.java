package com.yehaia.cse486.bangladic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yeahia Muhammad Arif on 29,December,2020
 */
public class Dictionary implements Parcelable
{
    private String english;
    private String bangla;
    public final static Parcelable.Creator<Dictionary> CREATOR = new Creator<Dictionary>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Dictionary createFromParcel(Parcel in) {
            return new Dictionary(in);
        }

        public Dictionary[] newArray(int size) {
            return (new Dictionary[size]);
        }

    }
            ;

    protected Dictionary(Parcel in) {
        this.english = ((String) in.readValue((String.class.getClassLoader())));
        this.bangla = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Dictionary() {
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getBangla() {
        return bangla;
    }

    public void setBangla(String bangla) {
        this.bangla = bangla;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(english);
        dest.writeValue(bangla);
    }

    public int describeContents() {
        return 0;
    }

}
