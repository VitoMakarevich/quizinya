package com.example.vito.quizinya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.ArrayList;

public class QuizPagerActivity extends AppCompatActivity implements QuizPagerFragment.OnAnswerSelectedListener{
    public static final String RIGHT_ANSWERS_COUNT = "com.example.quizinya.RIGHT_ANSWERS_COUNT";
    public static final String ANSWERS_COUNT = "com.example.quizinya.ANSWERS_COUNT";

    private ViewPager mViewPager;
    private ArrayList<Question> mQuestions;
    private Quiz mQuiz;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.prompt_menu, menu);
        return true;
    }

    public void onAnswerSelected(){
        mQuiz.answerQuestion();
        if(mQuiz.isFinished()){
            showResults(mQuiz.getRightAnsweredQuestionsCount());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mQuiz= new Quiz(this);
        mQuestions = (ArrayList<Question>) mQuiz.getQuestions();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Question question = mQuestions.get(position);
                return QuizPagerFragment.newInstance(question.getId());
            }

            @Override
            public int getCount() {
                return mQuestions.size();
            }
        });
    }

    private void showResults(int count){
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ANSWERS_COUNT, mQuiz.getQuestions().size());
        intent.putExtra(RIGHT_ANSWERS_COUNT, count);
        startActivity(intent);
    }
}
