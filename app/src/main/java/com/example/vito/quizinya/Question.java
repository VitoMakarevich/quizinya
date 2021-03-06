package com.example.vito.quizinya;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by vito on 11/3/17.
 */

public class Question {

    private int mId;
    private String mText;
    private ArrayList<Answer> mAnswers;
    private Drawable mDrawable;
    private int mAnswerId;
    private boolean mIsAnswered;
    private boolean mIsExcluded;

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

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    public Question(int id, String text, ArrayList<Answer> answers, Drawable drawable) {
        mId = id;
        mText = text;
        mAnswers = answers;
        mDrawable = drawable;
        mIsAnswered = false;
        mIsExcluded = false;
    }

    public void answerQuestion(int id){
        mAnswerId = id;
        mIsAnswered = true;
    }

    public int getRightAnswerId(){
        int rightAnswerId = -1;
        for (Answer answer : mAnswers){
            if(answer.isTrue())
                rightAnswerId = answer.getId();
        }
        return rightAnswerId;
    }

    public int getAnswerId() {
        return mAnswerId;
    }

    public boolean isAnswered(){

        return mIsAnswered;
    }

    public boolean isAnswerRight(){
        boolean isTrue = false;
        for(Answer answer : mAnswers){
            if(answer.getId() == mAnswerId)
                isTrue = answer.isTrue();
        }
        return isTrue;
    }

    public ArrayList<Answer> shuffleAnswers(){
        Collections.shuffle(mAnswers);
        return mAnswers;
    }

    public boolean isExcluded() {
        return mIsExcluded;
    }

    public void excludeAnswers(){
        mIsExcluded = true;

        excludeAnswer();
        excludeAnswer();
    }

    private void excludeAnswer(){
        for(Answer answer: mAnswers){
            if(!answer.isTrue() && !answer.isExcluded()){
                answer.setExcluded(true);
                break;
            }
        }
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
