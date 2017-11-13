package com.example.vito.quizinya;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class QuizPagerActivity extends AppCompatActivity implements QuizPagerFragment.OnAnswerSelectedListener{
    public static final String RIGHT_ANSWERS_COUNT = "com.example.quizinya.RIGHT_ANSWERS_COUNT";
    public static final String ANSWERS_COUNT = "com.example.quizinya.ANSWERS_COUNT";
    public static final int PERMISSIONS = 1;

    private ViewPager mViewPager;
    private ProgressBar mProgressBar;
    private Toolbar mToolbar;
    private ArrayList<Question> mQuestions;
    private Quiz mQuiz;
    private File mImagePath;
    private boolean mWriteAllowed;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.prompt_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_exclude:
                if(!isQuestionAnswered()) {
                    item.setEnabled(false);
                    excludeAnswers();
                }
                return true;
            case R.id.menu_call:
                if(!isQuestionAnswered()){
                    if(!mWriteAllowed)
                        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if(mWriteAllowed){
                        item.setEnabled(false);
                        useHelp();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void useHelp(){
        Bitmap bitmap = takeScreenshot();
        saveBitmap(bitmap);
        shareIt();
    }

    private boolean checkPermission(String code){
        return ContextCompat.checkSelfPermission(this,
                code) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSIONS:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mWriteAllowed = true;
                    useHelp();
                }
                return;
        }
    }

    private void requestPermission(String code){
        ActivityCompat.requestPermissions(this,
                    new String[]{code}, PERMISSIONS);
    }

    public void onAnswerSelected(boolean result){
        mProgressBar.incrementSecondaryProgressBy(1);
        if(result){
            mProgressBar.incrementProgressBy(1);
        }
        mQuiz.answerQuestion();
        if(mQuiz.isFinished()){
            showResults(mQuiz.getRightAnsweredQuestionsCount());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_pager);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mWriteAllowed = checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mProgressBar.setProgress(0);
        mProgressBar.setSecondaryProgress(0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mQuiz= new Quiz(this);
        mQuestions = (ArrayList<Question>) mQuiz.getQuestions();
        mProgressBar.setMax(mQuestions.size());
        QuizFragmentStatePagerAdapter quizFragmentStatePagerAdapter =
                new QuizFragmentStatePagerAdapter(fragmentManager, mQuestions);
        mViewPager.setAdapter(quizFragmentStatePagerAdapter);
    }

    private void excludeAnswers(){
        QuizFragmentStatePagerAdapter quizFragmentStatePagerAdapter =
                (QuizFragmentStatePagerAdapter) mViewPager.getAdapter();
        QuizPagerFragment fragment = (QuizPagerFragment) quizFragmentStatePagerAdapter.getItem(mViewPager.getCurrentItem());
        fragment.excludeAnswers();
    }

    private boolean isQuestionAnswered(){
       return mQuestions.get(mViewPager.getCurrentItem()).isAnswered();
    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {

        mImagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(mImagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    private void shareIt() {
        Uri uri = Uri.fromFile(mImagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String helpText = getResources().getString(R.string.help_text, getResources().getString(R.string.app_name));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, helpText);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.help_title)));
    }

    private void showResults(int count){
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ANSWERS_COUNT, mQuiz.getQuestions().size());
        intent.putExtra(RIGHT_ANSWERS_COUNT, count);
        startActivity(intent);
    }
}
