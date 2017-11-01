package com.example.vito.quizinya;

public class Answer {

    private String mText;
    private boolean mIsTrue;

    public Answer(String text, boolean isTrue){
        this.mText = text;
        this.mIsTrue = isTrue;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public boolean isTrue() {
        return mIsTrue;
    }

    public void setTrue(boolean aTrue) {
        mIsTrue = aTrue;
    }
}
