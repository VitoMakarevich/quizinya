package com.example.vito.quizinya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vito.quizinya.realm.Result;

import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

public class ResultActivity extends AppCompatActivity {
    private TextView mTextView;
    private EditText mEditText;
    private Button mSubmitButton;
    private RecyclerView mRecyclerView;
    private Realm mRealm;

    private class ResultHolder extends RecyclerView.ViewHolder {
        private TextView mPositionTextView;
        private TextView mNameTextView;
        private TextView mCountTextView;
        private Result mResult;

        public ResultHolder (View itemView){
            super(itemView);

            mPositionTextView = (TextView) itemView.findViewById(R.id.position_text_view);
            mNameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            mCountTextView = (TextView) itemView.findViewById(R.id.count_text_view);
        }

        public void bindResult(Result result ,int position){
            mResult = result;

            String text = String.valueOf(result.getId());
            mPositionTextView.setText(String.valueOf(position));
            mNameTextView.setText(mResult.getName());
            mCountTextView.setText(String.valueOf(mResult.getScore()));
        }
    }

    private class ResultAdapter extends RecyclerView.Adapter<ResultHolder>{
        private List<Result> mResultList;

        public ResultAdapter(List<Result> resultList){
            mResultList = resultList;
        }

        @Override
        public int getItemCount() {
            int size = mResultList.size();
            return size;
        }

        @Override
        public void onBindViewHolder(ResultHolder holder, int position) {
            Result result = mResultList.get(position);
            holder.bindResult(result, position + 1);
        }

        @Override
        public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.list_item_result, parent, false);
            return new ResultHolder(view);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, QuizPagerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarResult);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        int answersCount = intent.getIntExtra(QuizPagerActivity.ANSWERS_COUNT, 0);
        final int rightAnswersCount = intent.getIntExtra(QuizPagerActivity.RIGHT_ANSWERS_COUNT, 10);
        mEditText = (EditText) findViewById(R.id.editText);
        mTextView = (TextView) findViewById(R.id.textView2);
        mTextView.setText(getString(R.string.result_text, rightAnswersCount, answersCount));
        mSubmitButton = (Button) findViewById(R.id.button_submit);
        mRecyclerView = (RecyclerView) findViewById(R.id.result_recycler_view);
        Realm.init(getApplicationContext());
        mRealm = Realm.getDefaultInstance();
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEditText.getText().toString().length() > 0) {
                    Number maxId = mRealm.where(Result.class).max("id");
                    if (maxId == null)
                        maxId = 0;
                    mRealm.beginTransaction();
                    Result result = mRealm.createObject(Result.class, maxId.longValue() + 1);
                    result.setScore(rightAnswersCount);
                    result.setName(mEditText.getText().toString());
                    mRealm.commitTransaction();
                    updateUI();
                    view.setClickable(false);
                    mEditText.setEnabled(false);
                }
                else{
                    showEmptyNameError();
                }
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateUI();

    }

    private void showEmptyNameError() {
        Toast.makeText(this, R.string.empty_name, Toast.LENGTH_SHORT).show();
    }

    private void updateUI(){
        List<Result> results =  mRealm.where(Result.class).findAllSorted("score", Sort.DESCENDING);
        mRecyclerView.setAdapter(new ResultAdapter(results));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}

