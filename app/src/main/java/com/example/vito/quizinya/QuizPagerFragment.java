package com.example.vito.quizinya;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizPagerFragment extends Fragment {

    //TODO добавить вокруг imageView и TextView рамку, сделать фон кнопок белым, на весь экран добавить какой-то фон
    public OnAnswerSelectedListener mCallback;

    public interface OnAnswerSelectedListener {
        public void onAnswerSelected(boolean status);
    }

    private static final String QUESTION_ID = "id";

    private Question mQuestion;
    private ArrayList<Answer> mAnswers;
    private ImageView mImageView;
    private TextView mTextView;
    private QuizButton[] mButtons;

    public QuizPagerFragment() {}

    public static QuizPagerFragment newInstance(int questionId) {
        QuizPagerFragment fragment = new QuizPagerFragment();
        Bundle args = new Bundle();
        args.putInt(QUESTION_ID, questionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int questionId = getArguments().getInt(QUESTION_ID);
            mQuestion = QuestionsFactory.get(this.getContext()).getQuestion(questionId);
            mAnswers = mQuestion.getAnswers();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_quiz_pager, container, false);
        mImageView = v.findViewById(R.id.imageView);
        mTextView = v.findViewById(R.id.textView);
        mButtons = new QuizButton[]{v.findViewById(R.id.button1),
                    v.findViewById(R.id.button2),
                    v.findViewById(R.id.button3),
                    v.findViewById(R.id.button4)};
        mImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),mQuestion.getBitmapId()));
        mTextView.setText(mQuestion.getText());
        if(mQuestion.isAnswered()){
            restoreFragment();
        }
        else {
            int counter = 0;
            mAnswers = mQuestion.shuffleAnswers();
            for (QuizButton button : mButtons) {
                button.setAnswer(mAnswers.get(counter++));
                button.setOnClickListener(generateOnClickListener(button));
            }
        }
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnAnswerSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Listener");
        }
    }

    public void excludeAnswers(){
        mQuestion.excludeAnswers();
        updateButtons();
    }

    private void updateButtons(){
        for(QuizButton button: mButtons){
            if(button.getAnswer().isExcluded())
                button.setEnabled(false);
        }
    }

    private void restoreFragment(){
        int counter = 0;
        for(QuizButton button : mButtons){
            button.setAnswer(mAnswers.get(counter++));
            button.setClickable(false);
            if(button.getAnswer().getId() == mQuestion.getAnswerId()){
                if(mQuestion.isAnswerRight()){
                    button.setTintColor(R.color.colorRightAnswer);
                }
                else{
                    button.setTintColor(R.color.colorWrongAnswer);
                }
            }
        }
        updateButtons();
        showRightAnswer();
    }

    private View.OnClickListener generateOnClickListener(final QuizButton button){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuestion.answerQuestion(button.getAnswer().getId());
                boolean resultStatus = mQuestion.isAnswerRight();
                if(resultStatus){
                    ((QuizButton)view).setTintColor(R.color.colorRightAnswer);
                }
                else{
                    ((QuizButton)view).setTintColor(R.color.colorWrongAnswer);
                    showRightAnswer();
                }
                disableAllButtons();
                mCallback.onAnswerSelected(resultStatus);
            }
        };
    }

    private void showRightAnswer(){
        for(QuizButton button : mButtons){
            if(button.getAnswer().isTrue()){
                button.setTintColor(R.color.colorRightAnswer);
                break;
            }
        }
    }

    private void disableAllButtons(){
        for(Button button : mButtons){
            button.setClickable(false);
        }
    }
}
