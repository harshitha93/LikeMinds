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

import com.ground0.likeminds.Adapters.GroupAdapter;
import com.ground0.likeminds.Data.Group;
import com.ground0.likeminds.R;

import java.util.Vector;

/**
 * Created by Arjun on 26-08-2015.
 */
public class GroupFragment extends Fragment{

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.group_layout,container,false);
        Vector<Group> dummyFeedData = new Vector<Group>();
//        for(int i=0;i<10;i++)
        {
            Group group = new Group("CherryWork","The one app solution to your enterprise problems",R.color.system_group);
            group.setImage(getResources().getDrawable(R.drawable.cw_logo));
            dummyFeedData.add(group);

            group = new Group("Unilever","Unilever is a British-Dutch multinational consumer goods company. Its products include food, beverages, cleaning agents and personal care products.",R.color.system_group);
            group.setImage(getResources().getDrawable(R.drawable.uni_logo));
            dummyFeedData.add(group);

            group = new Group("Book Readers Club","Club for all the avid book readers out there",R.color.user_group);
            group.setImage(getResources().getDrawable(R.drawable.bookclub));
            dummyFeedData.add(group);

            group = new Group("One App","A team dedicated to overcome the tech barriers",R.color.system_group);
            group.setImage(getResources().getDrawable(R.drawable.group_picture));
            dummyFeedData.add(group);

            group = new Group("CBR Lovers","Hooooooonda",R.color.user_group);
            group.setImage(getResources().getDrawable(R.drawable.cbr_logo));
            dummyFeedData.add(group);

            group = new Group("Incture Hikers","Climb high or don't climb at all",R.color.user_group);
            group.setImage(getResources().getDrawable(R.drawable.hike_logo));
            dummyFeedData.add(group);



            // dummyFeedData.add(new Post("Batman","25th August 2015","Its not who I am underneath the mask, but what I do that defines me"));

        }

        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.groups);

        //calculate the screen size to set the span
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        Log.i("com.ground0.likeminds", "Screen size : " + screenWidth);

        int span=3;
        if(screenWidth<=720)
        {
            span = 2;
        }



        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(span, StaggeredGridLayoutManager.VERTICAL));

        recyclerView.setAdapter(new GroupAdapter(getActivity(), dummyFeedData));


        return rootView;
    }
}
