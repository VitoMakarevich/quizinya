package com.example.vito.quizinya;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vito on 11/9/17.
 */

public class QuizFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Question> mQuestions;
    private HashMap<Integer, Fragment> mFragments;
    private Fragment mFragment;

    public QuizFragmentStatePagerAdapter(FragmentManager fm, ArrayList<Question> questions) {
        super(fm);
        mQuestions = questions;
        mFragments = new HashMap<>();
    }

    public Fragment getFragment() {
        return mFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if(mFragments.get(position) != null)
            return mFragments.get(position);
        Question question = mQuestions.get(position);
        mFragment = QuizPagerFragment.newInstance(question.getId());
        return mFragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object superClass = super.instantiateItem(container, position);
        mFragments.put(position, (QuizPagerFragment) superClass);
        return superClass;
    }

    @Override
    public int getCount() {
        return mQuestions.size();
    }
}
