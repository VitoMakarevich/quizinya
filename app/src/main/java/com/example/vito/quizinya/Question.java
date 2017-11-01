package com.example.vito.quizinya;

import android.graphics.Bitmap;

public class Question {

    private String mText;
    private Answer[] mAnswers;
    private Bitmap mImage;

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Answer[] getAnswers() {
        return mAnswers;
    }

    public void setAnswers(Answer[] answers) {
        mAnswers = answers;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap image) {
        mImage = image;
    }

    public Question(String text, Answer[] answers, Bitmap image) {
        mText = text;
        mAnswers = answers;
        mImage = image;
    }
}
