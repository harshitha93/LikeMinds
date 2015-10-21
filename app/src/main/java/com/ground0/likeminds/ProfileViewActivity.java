package com.ground0.likeminds;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import com.ground0.likeminds.Adapters.GroupViewAdapter;

/**
 * Created by Arjun on 26-08-2015.
 */
public class ProfileViewActivity extends AppCompatActivity {

    static final String group_title = "Cherrywork";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(group_title);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle(group_title);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.primary_text_default_material_dark));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.primary_text_default_material_dark));

        collapsingToolbar.setCollapsedTitleTextColor(Color.TRANSPARENT);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        ((CoordinatorLayout)findViewById(R.id.coordinator_layout)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });





//        Vector<Feed> dummyFeedData = new Vector<Feed>();
//        for(int i=0;i<10;i++)
//        {
//            Feed feed = new Feed("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed facilisis, sapien eget vestibulum tempus, massa justo fermentum elit, vestibulum pharetra sem dui a diam. Cras sit amet semper sapien","25th August 2015","Group Joe","John Doe");
//            dummyFeedData.add(feed);
//        }
//
//
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//
//
//        RecyclerView listView = (RecyclerView)findViewById(R.id.listView);
//        listView.setLayoutManager(mLayoutManager);
//        listView.setAdapter(new FeedAdapter(getApplicationContext(), dummyFeedData));


        //TAB code

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        //Set the title of the tabs in the tab layout
        tabLayout.addTab(tabLayout.newTab().setText("Feeds"));
        tabLayout.addTab(tabLayout.newTab().setText("Members"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //set up the view pager for the swiping between the tabs
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        //get the adapter for switching between the fragments
        final GroupViewAdapter adapter = new GroupViewAdapter
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
