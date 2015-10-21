package com.ground0.likeminds.Fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ground0.likeminds.Adapters.InboxAdapter;
import com.ground0.likeminds.Data.Post;
import com.ground0.likeminds.R;

import java.util.Random;
import java.util.Vector;

/**
 * Created by Arjun on 25-08-2015.
 */
public class InboxFragment extends Fragment {
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.wall_layout,container,false);




        Random rand = new Random();
        Vector<Post> dummyFeedData = new Vector<Post>();
//        for(int i=0;i<10;i++)
        {
            Post post = new Post("Shashank Sahay","29th August 2015","Designs are ready");
            post.setImage(getResources().getDrawable(R.drawable.prof_shashank));
            post.setN(rand.nextInt((1 - 0) + 1) + 0, rand.nextInt((1 - 0) + 1) + 0, rand.nextInt((1 - 0) + 1) + 0);
            dummyFeedData.add(post);

            post = new Post("Vikram Thomas","25th August 2015","Static APIs have been prepared");
            post.setImage(getResources().getDrawable(R.drawable.prof_victor));
            post.setN(rand.nextInt((1 - 0) + 1) + 0, rand.nextInt((1 - 0) + 1) + 0, rand.nextInt((1 - 0) + 1) + 0);
            dummyFeedData.add(post);

            post = new Post("Pavan Gopal","1st September 2015","Waiting for the Mac");
            post.setImage(getResources().getDrawable(R.drawable.prof_pavan));
            post.setN(rand.nextInt((1 - 0) + 1) + 0, rand.nextInt((1 - 0) + 1) + 0, rand.nextInt((1 - 0) + 1) + 0);
            dummyFeedData.add(post);

            post = new Post("Pavan Gopal","2nd September 2015","Still waiting for the Mac");
            post.setImage(getResources().getDrawable(R.drawable.prof_pavan));
            post.setN(rand.nextInt((1 - 0) + 1) + 0, rand.nextInt((1 - 0) + 1) + 0, rand.nextInt((1 - 0) + 1) + 0);
            dummyFeedData.add(post);

            post = new Post("Pavan Gopal","3rd September 2015","Still waiting for the Mac");
            post.setImage(getResources().getDrawable(R.drawable.prof_pavan));
            post.setN(rand.nextInt((1 - 0) + 1) + 0, rand.nextInt((1 - 0) + 1) + 0, rand.nextInt((1 - 0) + 1) + 0);
            dummyFeedData.add(post);

            post = new Post("Pavan Gopal","4th September 2015","Still waiting for the Mac");
            post.setImage(getResources().getDrawable(R.drawable.prof_pavan));
            post.setN(rand.nextInt((1 - 0) + 1) + 0, rand.nextInt((1 - 0) + 1) + 0, rand.nextInt((1 - 0) + 1) + 0);
            dummyFeedData.add(post);

            post = new Post("Pavan Gopal","5th September 2015","Still waiting for the Mac");
            post.setImage(getResources().getDrawable(R.drawable.prof_pavan));
            post.setN(rand.nextInt((1 - 0) + 1) + 0, rand.nextInt((1 - 0) + 1) + 0, rand.nextInt((1 - 0) + 1) + 0);
            dummyFeedData.add(post);

            post = new Post("Pavan Gopal","6th September 2015","Still waiting for the Mac");
            post.setImage(getResources().getDrawable(R.drawable.prof_pavan));
            post.setN(rand.nextInt((1 - 0) + 1) + 0, rand.nextInt((1 - 0) + 1) + 0, rand.nextInt((1 - 0) + 1) + 0);
            dummyFeedData.add(post);

            post = new Post("Pavan Gopal","7th September 2015","Still waiting for the Mac");
            post.setImage(getResources().getDrawable(R.drawable.prof_pavan));
            post.setN(rand.nextInt((1 - 0) + 1) + 0,rand.nextInt((1 - 0) + 1) + 0,rand.nextInt((1 - 0) + 1) + 0);
            dummyFeedData.add(post);

        }

        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.feeds);

        //calculate the screen size to set the span
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        Log.i("com.ground0.likeminds","Screen size : "+screenWidth);

        int span=2;
        if(screenWidth<=720)
        {
            span = 1;
        }



        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(span, StaggeredGridLayoutManager.VERTICAL));

        recyclerView.setAdapter(new InboxAdapter(getActivity(), dummyFeedData));


        return rootView;
    }
}
