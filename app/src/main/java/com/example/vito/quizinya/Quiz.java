package com.example.vito.quizinya;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vito on 11/3/17.
 */

public class Quiz {

    private ArrayList<Question> mQuestions;
    private boolean mIsPromptUsed;
    private boolean mIsHelpUsed;
    private int mAnsweredQuestionsCount;

    public Quiz(Context context) {
        mQuestions = QuestionsFactory.get(context).getQuestions();
        mIsHelpUsed = false;
        mIsPromptUsed = false;
        mAnsweredQuestionsCount = 0;
    }

    public List<Question> getQuestions() {
        return mQuestions;
    }

    public Question getQuestion(int id){
        return mQuestions.get(id);
    }

    public void setQuestions(ArrayList<Question> questions) {
        mQuestions = questions;
    }

    public boolean isPromptUsed() {
        return mIsPromptUsed;
    }

    public void setPromptUsed(boolean promptUsed) {
        mIsPromptUsed = promptUsed;
    }

    public boolean isHelpUsed() {
        return mIsHelpUsed;
    }

    public void setHelpUsed(boolean helpUsed) {
        mIsHelpUsed = helpUsed;
    }

    public int getAnsweredQuestionsCount(){
        int counter = 0;
        for(Question question : mQuestions){
            if(question.isAnswered()) counter++;
        }
        return counter;
    }

    public int getRightAnsweredQuestionsCount(){
        int counter = 0;
        for(Question question : mQuestions){
            if(question.isAnswerRight()) counter++;
        }
        return counter;
    }

    public void answerQuestion(){
        mAnsweredQuestionsCount++;
    }

    public boolean isFinished(){
        return mAnsweredQuestionsCount == mQuestions.size();
    }
}
