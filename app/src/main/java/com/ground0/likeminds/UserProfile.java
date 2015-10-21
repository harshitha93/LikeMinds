package com.ground0.likeminds;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.Gson;
import com.ground0.likeminds.Adapters.PersonalDirectoryDetailsAdapter;
import com.ground0.likeminds.Adapters.PersonalDirectoryPagerAdapter;
import com.ground0.likeminds.Background.MemberViewAsyncTask;
import com.ground0.likeminds.Data.Profile;
import com.ground0.likeminds.ServerAddress.ServerDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Arjun on 04-09-2015.
 *
 */

public class UserProfile extends AppCompatActivity {

    static String User_Name = "Mr Arjun Biju";
    Bundle userData;
    Context context;

    public Handler handler= new Handler()
    {
        @Override
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData();
            String stringJson = bundle.getString("G_Json_data");
            Log.i("com.ground0.likeminds","Data @ Handler"+stringJson);

            updateUI(stringJson);
    }
    };

    private void updateUI(final String data)
    {
        this.context = this;
        final TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        if(tabLayout!=null && viewPager!=null) {
            Log.i("com.ground0.likeminds","Setting listener to tabLayout");

            viewPager.setOffscreenPageLimit(5);

            //get the adapter for switching between the fragments
            final PersonalDirectoryPagerAdapter adapter = new PersonalDirectoryPagerAdapter
                    (getSupportFragmentManager(), tabLayout.getTabCount(), userData, data);

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



            SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.frag_contact_details_swipe);
            if(swipeRefreshLayout!=null)
                swipeRefreshLayout.setRefreshing(false);
        }
    }

    public Bundle getArguments() {
        return userData;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        this.context = context;

        String username = getIntent().getStringExtra("username");
        if(username!=null)User_Name = username;

        Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(User_Name);


        Bundle userData = getIntent().getBundleExtra("user_data");
        this.userData = userData;


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);





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
        tabLayout.addTab(tabLayout.newTab().setText("Personal"));
        tabLayout.addTab(tabLayout.newTab().setText("Org Data"));
        tabLayout.addTab(tabLayout.newTab().setText("Emergency"));
        tabLayout.addTab(tabLayout.newTab().setText("Family"));
        tabLayout.addTab(tabLayout.newTab().setText("Events"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //set up the view pager for the swiping between the tabs
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(5);

        //get the adapter for switching between the fragments
        final PersonalDirectoryPagerAdapter adapter = new PersonalDirectoryPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), userData, null);

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


        //Disable the swipe refresh to viewPager
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                switch (state)
                {
                    case ViewPager.SCROLL_STATE_DRAGGING :
                        ((SwipeRefreshLayout)findViewById(R.id.frag_contact_details_swipe)).setEnabled(false);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE :
                        ((SwipeRefreshLayout)findViewById(R.id.frag_contact_details_swipe)).setEnabled(true);
                        break;
                }
            }
        });


    }


    private void sendRequest(Bundle userData)
    {

        Log.i("sendRequest", "userdata : " + userData.getString("user_id") + "   CurrentUser :" + ServerDetails.USER_ID);

        if(!(userData.getString("user_id")).equals(ServerDetails.USER_ID)) {
            //Start AsyncTask

            //create the json for request
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("uniqueid", ServerDetails.USER_ID);
                jsonObject.put("searchid", userData.getString("user_id"));
                Log.i("com.ground0.likeminds", "userData @Fragment : "+userData.getString("user_id"));
                //create the URL
                //            Uri.Builder builder = new Uri.Builder();
                //            builder.scheme("http")
                //                    .authority("requestb.in")
                //                    .appendPath("11npqs61");


                //create the URL


                try {
                    //                URL url = new URL("http://172.31.98.176:3000/viewinformation");
                    URL url = new URL(ServerDetails.API_VIEW_PROFILE);
                    MemberViewAsyncTask memberViewAsyncTask = new MemberViewAsyncTask(url, this, getArguments(),handler);
                    memberViewAsyncTask.execute(jsonObject);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            //Start AsyncTask

            //create the json for request
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("uniqueid", ServerDetails.USER_ID);
//                jsonObject.put("searchid", userData.getString("user_id"));
                Log.i("com.ground0.likeminds", "ownData @Fragment : " + userData.getString("user_id"));
                //create the URL
                //            Uri.Builder builder = new Uri.Builder();
                //            builder.scheme("http")
                //                    .authority("requestb.in")
                //                    .appendPath("11npqs61");


                //create the URL


                try {
                    URL url = new URL(ServerDetails.API_VIEW_OWN_PROFILE);
//                    URL url = new URL("http://172.31.98.176:3000/viewsearchinfo");
                    MemberViewAsyncTask memberViewAsyncTask = new MemberViewAsyncTask(url, this, getArguments(), handler);
                    memberViewAsyncTask.execute(jsonObject);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }




    }


    @Override
    protected void onResume() {
        super.onResume();

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.frag_contact_details_swipe);
        if(swipeRefreshLayout!=null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    sendRequest(getArguments());
                }
            });
            swipeRefreshLayout.setColorSchemeColors
                    (R.color.primary, R.color.accent, R.color.primary_dark, R.color.accent, R.color.primary_light);

        }



        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        sendRequest(getArguments());
    }


    @Override
    public void onStop() {
        super.onStop();

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.frag_contact_details_swipe);
        if(swipeRefreshLayout!=null)
                swipeRefreshLayout.setRefreshing(false);

    }
}
