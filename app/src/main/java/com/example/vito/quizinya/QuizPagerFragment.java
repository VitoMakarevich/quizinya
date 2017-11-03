package com.example.vito.quizinya;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class QuizPagerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String QUESTION_ID = "id";

    // TODO: Rename and change types of parameters
    private Question mQuestion;
    private ImageView mImageView;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;

    public QuizPagerFragment() {
        // Required empty public constructor
    }

    public static QuizPagerFragment newInstance(int questionId) {
        QuizPagerFragment fragment = new QuizPagerFragment();
        Log.d("newInstance", String.valueOf(questionId));
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
            mQuestion = QuestionsFactory.get().getQuestion(questionId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_quiz_pager, container, false);
        mImageView = v.findViewById(R.id.imageView2);
        mButton1 = v.findViewById(R.id.button5);
        mButton2 = v.findViewById(R.id.button6);
        mButton3 = v.findViewById(R.id.button7);
        mButton4 = v.findViewById(R.id.button8);
        mImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),mQuestion.getBitmapId()));
        ArrayList<Answer> answers = mQuestion.getAnswers();
        mButton1.setText(answers.get(0).getText());
        mButton2.setText(answers.get(1).getText());
        mButton3.setText(answers.get(2).getText());
        mButton4.setText(answers.get(3).getText());
        return v;
    }
}
