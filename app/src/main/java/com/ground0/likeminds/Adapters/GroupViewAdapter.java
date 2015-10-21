package com.ground0.likeminds.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ground0.likeminds.Fragments.FeedFragment;
import com.ground0.likeminds.Fragments.MembersFragment;

/**
 * Created by Arjun on 01-09-2015.
 */
public class GroupViewAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public GroupViewAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                FeedFragment tab1 = new FeedFragment();
                return tab1;
            case 1:
                MembersFragment tab2 = new MembersFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
