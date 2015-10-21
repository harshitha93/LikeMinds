package com.ground0.likeminds;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.ground0.likeminds.Adapters.PostPagerAdapter;

/**
 * Created by Arjun on 26-08-2015.
 */
public class UnifiedPostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Toolbar toolbar = (Toolbar)findViewById(R.id.post_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Create Post");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        //Set the title of the tabs in the tab layout
        tabLayout.addTab(tabLayout.newTab().setText("Post"));
        tabLayout.addTab(tabLayout.newTab().setText("Event"));
        tabLayout.addTab(tabLayout.newTab().setText("Polls"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //set up the view pager for the swiping between the tabs
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        //get the adapter for switching between the fragments
        final PostPagerAdapter adapter = new PostPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
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


    }

}
