package com.example.vito.quizinya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        int answersCount = intent.getIntExtra(QuizPagerActivity.ANSWERS_COUNT, 0);
        int rightAnswersCount = intent.getIntExtra(QuizPagerActivity.RIGHT_ANSWERS_COUNT, 0);

        TextView textView = findViewById(R.id.textView2);
        textView.setText(getString(R.string.result_text, rightAnswersCount, answersCount));
    }
}
