package com.example.vito.quizinya;

/**
 * Created by vito on 11/3/17.
 */

public class Answer {

    private String text;
    private boolean isTrue;

    public String getText() {
        return text;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public Answer(String text, boolean isTrue) {
        this.text = text;
        this.isTrue = isTrue;
    }
}
