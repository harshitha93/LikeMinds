package com.ground0.likeminds.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ground0.likeminds.Data.Feed;
import com.ground0.likeminds.R;
import com.ground0.likeminds.Adapters.FeedAdapter;
import com.ground0.likeminds.UnifiedPostActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Arjun on 25-08-2015.
 */
public class FeedFragment extends Fragment {

    View rootView;
    Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.feeds_layout, container, false);

        //add data

        Random rand = new Random();

        Vector<Feed> dummyFeedData = new Vector<Feed>();

        String[] listArray = {"No","Obviously","Its subtle","Teal?"};
        List<String> answerList = new ArrayList<>();
        answerList.addAll(Arrays.asList(listArray));

        HashMap<String, Integer> answerValue = new HashMap<>();
        for(String string:answerList)
            answerValue.put(string,rand.nextInt((10 - 2) + 1) +2 );


        Feed feed = new Feed("Reminder: If you write OSS, don't answer private email about it. Public support is free. Charge for expert, one-on-one support.","25th August 2015","Cherrywork","Arjun Biju",null);
        feed.setN(rand.nextInt((20 - 0) + 1) + 0,rand.nextInt((20 - 0) + 1) + 0,rand.nextInt((20 - 0) + 1) + 0);
        dummyFeedData.add(feed);

        feed = new Feed("Is Teal over used ?","29th August 2015","Cherrywork","Shashank Sahay",null);
        feed.setProfileImage(getResources().getDrawable(R.drawable.prof_shashank));
        feed.setN(rand.nextInt((20 - 0) + 1) + 0, rand.nextInt((20 - 0) + 1) + 0, rand.nextInt((20 - 0) + 1) + 0);
        feed.setType("poll");
        feed.setPoll(new Feed.Polls(answerList, answerValue));
        dummyFeedData.add(feed);




        feed = new Feed("Handsontable - JavaScript component for creating Excel-like spreadsheets in modern web apps.","29th August 2015","Cherrywork","Pavan Gopal",null);
        feed.setProfileImage(getResources().getDrawable(R.drawable.prof_pavan));
        feed.setN(rand.nextInt((20 - 0) + 1) + 0,rand.nextInt((20 - 0) + 1) + 0,rand.nextInt((20 - 0) + 1) + 0);
        dummyFeedData.add(feed);

        for(int i=0;i<10;i++)
        {
            feed = new Feed("“To conquer oneself is the best and noblest victory; to be vanquished by one's own nature is the worst and most ignoble defeat.” - Plato","25th August 2015","Cherrywork","Arjun Biju",null);
            feed.setProfileImage(getResources().getDrawable(R.drawable.profile_picture));
            feed.setN(rand.nextInt((20 - 0) + 1) + 0,rand.nextInt((20 - 0) + 1) + 0,rand.nextInt((20 - 0) + 1) + 0);
            if(i%4==0)feed.setImage(getResources().getDrawable(R.drawable.feed_picture)); //setImage
            else feed.setImage(null);
            dummyFeedData.add(feed);
        }


        rootView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UnifiedPostActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.feeds);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new FeedAdapter(getActivity(), dummyFeedData));

        return rootView;

    }
}
