package com.example.vito.quizinya;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;

/**
 * Created by vito on 11/8/17.
 */

public class QuizButton extends android.support.v7.widget.AppCompatButton {

    private Answer mAnswer;

    public QuizButton(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    public void setAnswer(Answer answer){
        mAnswer = answer;
        this.setText(answer.getText());
    }

    public void setTintColor(int color){
        ViewCompat.setBackgroundTintList(this,
                ContextCompat.getColorStateList(getContext(), color));
    }

    public Answer getAnswer(){
        return mAnswer;
    }

}
