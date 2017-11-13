package com.example.vito.quizinya;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.io.IOException;
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
        reinit();
    }

    public void reinit(){
        Resources r = sContext.getResources();
        ArrayList<Question> questions = new ArrayList<>(10);
        for(int i = 0; i < 10; i++) {
            ArrayList<Answer> answers = new ArrayList<>(4);
            int stringId = r.getIdentifier("question_" + String.valueOf(i + 1), "array", sContext.getPackageName());
            String[] array = r.getStringArray(stringId);
            answers.add(0, new Answer(0, array[1], true));
            for(int j = 1; j < 4; j++) {
                answers.add(j, new Answer(j, array[j + 1], false));
            }
            Drawable draw = null;
            try {
                draw = Drawable.createFromStream(sContext.getAssets().open("im_" +
                        String.valueOf(i + 1) + ".jpg"), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Question question = new Question(i, array[0], answers, draw);
            questions.add(question);
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
