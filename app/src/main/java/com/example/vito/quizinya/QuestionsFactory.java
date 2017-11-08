package com.example.vito.quizinya;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by vito on 11/3/17.
 */

public class QuestionsFactory {
    private ArrayList<Question> mQuestions;
    private static QuestionsFactory sQuestionsFactory;
    private static Context sContext;

    private QuestionsFactory(Context context){
        sContext = context;
        ArrayList<Question> questions = new ArrayList<>(10);
        for(int i = 0; i < 10; i++) {
            ArrayList<Answer> answers = new ArrayList<>(4);
            String[] array = sContext.getResources().getStringArray(R.array.question_1);
            answers.add(0, new Answer(0, array[1], true));
            for(int j = 1; j < 4; j++) {
                answers.add(j, new Answer(j, array[j + 1], false));
            }
            Question question = new Question(i, array[0], answers, R.drawable.im_03_09);
            questions.add(i, question);
        }
        mQuestions = questions;
    }

    public static QuestionsFactory get(Context context){
        if(sQuestionsFactory == null) {
            sQuestionsFactory = new QuestionsFactory(context);
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
