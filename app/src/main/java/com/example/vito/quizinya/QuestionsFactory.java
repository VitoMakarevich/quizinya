package com.example.vito.quizinya;

import java.util.ArrayList;

/**
 * Created by vito on 11/3/17.
 */

public class QuestionsFactory {
    private ArrayList<Question> mQuestions;
    private static QuestionsFactory sQuestionsFactory;

    private QuestionsFactory(){
        ArrayList<Question> questions = new ArrayList<>(10);
        for(int i = 0; i < 10; i++) {
            ArrayList<Answer> answers = new ArrayList<>(4);
            for(int j = 0; j < 3; j++) {
                answers.add(j, new Answer("sss " + i + j, false));
            }
            answers.add(3, new Answer("truth" + i, true));
            Question question = new Question(i, "text", answers, R.drawable.im_03_09);
            questions.add(i, question);
        }
        mQuestions = questions;
    }

    public static QuestionsFactory get(){
        if(sQuestionsFactory == null) {
            sQuestionsFactory = new QuestionsFactory();
        }
        return sQuestionsFactory;
    }

    public Question getQuestion(int id){
        return mQuestions.get(id);
    }

    public ArrayList<Question> getQuestions(){
        return mQuestions;
    }
}
