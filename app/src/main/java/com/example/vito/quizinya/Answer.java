package com.example.vito.quizinya;

/**
 * Created by vito on 11/3/17.
 */

public class Answer {

    private String mText;
    private boolean mIsTrue;
    private int mId;

    public String getText() {
        return mText;
    }

    public boolean isTrue() {
        return mIsTrue;
    }

    public int getId() {
        return mId;
    }

    public Answer(int id, String text, boolean isTrue) {
        mId = id;
        mText = text;
        mIsTrue = isTrue;
    }
}
