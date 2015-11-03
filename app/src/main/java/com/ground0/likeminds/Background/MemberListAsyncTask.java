package com.ground0.likeminds.Background;

import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ground0.likeminds.Adapters.MemberListAdapter;
import com.ground0.likeminds.Data.Database.MemberListTableOpenHelper;
import com.ground0.likeminds.Data.Database.ProfileTableDatabaseOpenHelper;
import com.ground0.likeminds.Data.Member;
import com.ground0.likeminds.Data.Profile;
import com.ground0.likeminds.Data.ProfileListItem;
import com.ground0.likeminds.R;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arjun on 30-09-2015.
 */
public class MemberListAsyncTask extends AsyncTask<Object, Void, Object> {

    URL url;
    Context context;

    public MemberListAsyncTask(URL url, Context context)
    {
        this.url = url; this.context = context;
    }

    @Override
    protected Object doInBackground(Object... params) {

        String responseString = new String();

        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(5000);

            httpURLConnection.setRequestMethod("POST");   //change to post
            httpURLConnection.setRequestProperty("Content-Type",
                    "application/json");




            /*
            //Create the Json object to send to the server
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user-id :","Blah blah");   //change the object
            String request = jsonObject.toString();
            */

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());

            //Add the param objects to the request
            for(int i=0;i<params.length;i++)
            {
                outputStreamWriter.write(params[i].toString());
            }



            outputStreamWriter.close();

            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
            {

                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuffer response = new StringBuffer();
                while((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                bufferedReader.close();

                responseString = response.toString();
            }
            else
            {
                MemberListTableOpenHelper memberListTableOpenHelper = new MemberListTableOpenHelper(context);
                return memberListTableOpenHelper.getAllMembers();

            }


           //id response successful add to table
            if(responseString!=null && responseString.length()!=0) {
                try {

                    JSONArray jsonArray = new JSONArray(responseString);

                    //Parse the JSON and insert into DB;
                    List<Member> listMember = new ArrayList<>();
                    MemberListTableOpenHelper memberListTableOpenHelper = new MemberListTableOpenHelper(context);
                    //Parsing JSON
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("_id");
                        JSONObject personalData = jsonObject.getJSONObject("personaldata");
                        String firstName = personalData.getString("first name");
                        String lastName = personalData.getString("last name");

                        Member member = new Member(id, firstName + " " + lastName, ""); //no image url yet


                        memberListTableOpenHelper.addMember(member);
                        listMember.add(member);
                        Log.i("com.ground0.likeminds", "Profile List Item added to DB");

                    }
                    return listMember;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                //if response failed then read from database

                MemberListTableOpenHelper memberListTableOpenHelper = new MemberListTableOpenHelper(context);
                return memberListTableOpenHelper.getAllMembers();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(final Object o) {

        //argument could be null
        if(o!=null) {

//            NavigationView navigationView = (NavigationView)((AppCompatActivity)context).findViewById(R.id.navigation_view);
            RecyclerView recyclerView = (RecyclerView) ((AppCompatActivity) context).findViewById(R.id.listView);
            if (recyclerView!=null)
            {   recyclerView.setLayoutManager(new LinearLayoutManager(context));
                MemberListAdapter memberListAdapter = new MemberListAdapter(context, (List<Member>) o);
                recyclerView.setAdapter(memberListAdapter);
                final StickyRecyclerHeadersDecoration headersDecoration = new StickyRecyclerHeadersDecoration(memberListAdapter);
                recyclerView.addItemDecoration(headersDecoration);
                memberListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        headersDecoration.invalidateHeaders();
                    }
                });

//                ((SwipeRefreshLayout)((AppCompatActivity)context)
//                        .findViewById(R.id.frag_group_members_swipe_refresh))
//                        .setRefreshing(false);
            }


        }

    }
}
