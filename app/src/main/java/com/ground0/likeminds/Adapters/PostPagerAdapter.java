package com.ground0.likeminds.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ground0.likeminds.Fragments.CreateEventFragment;
import com.ground0.likeminds.Fragments.CreatePollFragment;
import com.ground0.likeminds.Fragments.CreatePostFragment;

/**
 * Created by Arjun on 26-08-2015.
 */
public class PostPagerAdapter extends FragmentStatePagerAdapter {

    private final int mNumOfTabs;

    public PostPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                CreatePostFragment tab1 = new CreatePostFragment();
                return tab1;
            case 1:
                CreateEventFragment tab2 = new CreateEventFragment();
                return tab2;
            case 2:
                CreatePollFragment tab3 = new CreatePollFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
