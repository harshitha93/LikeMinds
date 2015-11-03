package com.ground0.likeminds.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ground0.likeminds.Adapters.MemberListAdapter;
import com.ground0.likeminds.Background.MemberListAsyncTask;
import com.ground0.likeminds.Data.Database.MemberListTableOpenHelper;
import com.ground0.likeminds.Data.Member;
import com.ground0.likeminds.R;
import com.ground0.likeminds.ServerAddress.ServerDetails;
import com.ground0.likeminds.UserProfile;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arjun on 01-09-2015.
 */
public class MembersFragment extends Fragment{

    Context context;
    View rootView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }


    private void sendRequest()
    {
        //Start AsyncTask

        //create the json for request
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uniqueid", ServerDetails.USER_ID);
            //create the URL
//            Uri.Builder builder = new Uri.Builder();
//            builder.scheme("http")
//                    .authority("requestb.in")
//                    .appendPath("11npqs61");


            //create the URL



            try {
                URL url = new URL(ServerDetails.API_VIEW_MEMBERS);
                MemberListAsyncTask memberListAsyncTask = new MemberListAsyncTask(url, context);
                memberListAsyncTask.execute(jsonObject);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_group_members, container, false);
        sendRequest();

//        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.frag_group_members_swipe_refresh);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                sendRequest();
//            }
//        });
//        swipeRefreshLayout.setColorSchemeColors
//                (R.color.primary,R.color.accent,R.color.primary_dark,R.color.accent,R.color.primary_light);


        rootView.findViewById(R.id.me_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, UserProfile.class);
                //Add parameters as arguments
                Bundle userData = new Bundle();
                userData.putString("user_name","Arjun Biju");
                userData.putString("user_image","");
//            userData.putString("user_image","http://www.cherrywork.net/img/block1.jpg");
                userData.putString("user_id",ServerDetails.USER_ID);
                intent.putExtra("user_data",userData);
                intent.putExtra("username","Arjun Biju");

                context.startActivity(intent);

            }
        });

//        Vector<String> data = new Vector<>();
//        for(int i=0;i<10;i++)
//        {
//            data.add("Arjun Biju");
//        }
//
//        data.add("Arjun Biju");
//        data.add("Shashank Sahay");
//        data.add("Vikram Thomas");
//        data.add("Pavan Gopal");
//        data.add("Sachin Verma");


//        List<Member> data = new ArrayList<>();
//        Member member = new Member(""+0, "Arjun Biju", "https://lh3.googleusercontent.com/-cuA4RwdUckw/AAAAAAAAAAI/AAAAAAAAABY/jrsXJB0VVMc/s80-c-k-no/photo.jpg");
//        data.add(member);
//
//        for(int i=1;i<10;i++) {
//            member = new Member(""+i, "John Doe", "http://www.cherrywork.net/img/block1.jpg");
//            data.add(member);
//
//        }
//
//        member = new Member("560bcf62710179600c526d2e", "Vikram Thomas", "https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-xtp1/v/t1.0-9/11377110_10152894409945849_8513138878071599046_n.jpg?oh=4bd2304929ffefd96c3f02e49dd78e1e&oe=569885F1&__gda__=1452912948_ba7c7b9522a768c2679dc51fce70021e");
//        data.add(member);

//        MemberListTableOpenHelper memberListTableOpenHelper = new MemberListTableOpenHelper(context);
//        List<Member> data = memberListTableOpenHelper.getAllMembers();



//        PinnedHeaderListView pinnedHeaderListView = (PinnedHeaderListView)rootView.findViewById(R.id.listView);
//        pinnedHeaderListView.
        List<Member> o = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        MemberListAdapter memberListAdapter = new MemberListAdapter(context, (List<Member>) o);
        recyclerView.setAdapter(memberListAdapter);

        return rootView;
    }
}
