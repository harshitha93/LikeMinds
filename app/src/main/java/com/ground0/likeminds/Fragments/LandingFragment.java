package com.ground0.likeminds.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ground0.likeminds.Adapters.LandingPagerAdapter;
import com.ground0.likeminds.R;

/**
 * Created by Arjun on 18-09-2015.
 */
public class LandingFragment extends Fragment {


    Context context;
    View rootView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_landing, container, false);

        ((AppCompatActivity)context).getSupportActionBar().setElevation(0);

        //TAB code

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);

        //Set the title of the tabs in the tab layout
        tabLayout.addTab(tabLayout.newTab().setText("Feeds"));
        tabLayout.addTab(tabLayout.newTab().setText("Inbox"));
        tabLayout.addTab(tabLayout.newTab().setText("Groups"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //set up the view pager for the swiping between the tabs
        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);

        //get the adapter for switching between the fragments
        final LandingPagerAdapter adapter = new LandingPagerAdapter
                (((FragmentActivity)context).getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return rootView;
    }
}
