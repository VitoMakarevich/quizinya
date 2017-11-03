package com.example.vito.quizinya;

import java.util.ArrayList;

/**
 * Created by vito on 11/3/17.
 */

public class Question {

    private int mId;
    private String mText;
    private ArrayList<Answer> mAnswers;
    private int mBitmapId;

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public ArrayList<Answer> getAnswers() {
        return mAnswers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        mAnswers = answers;
    }

    public int getBitmapId() {
        return mBitmapId;
    }

    public void setBitmapId(int bitmapId) {
        mBitmapId = bitmapId;
    }

    public Question(int id, String text, ArrayList<Answer> answers, int bitmapId) {
        mId = id;
        mText = text;
        mAnswers = answers;
        mBitmapId = bitmapId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
