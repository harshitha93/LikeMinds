package com.ground0.likeminds.Background;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.ground0.likeminds.Adapters.PersonalDirectoryDetailsAdapter;
import com.ground0.likeminds.Data.Database.ProfileTableDatabaseOpenHelper;
import com.ground0.likeminds.Data.Profile;
import com.ground0.likeminds.Data.ProfileListItem;
import com.ground0.likeminds.R;
import com.ground0.likeminds.ServerAddress.ServerDetails;
import com.ground0.likeminds.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arjun on 22-09-2015.
 */
public class MemberViewAsyncTask extends AsyncTask<Object, Void, Object> {

    URL url;
    Context context;
    Handler handler;

    private Bundle getArguments() {
        return arguments;
    }

    Bundle arguments;

    public MemberViewAsyncTask(URL url, Context context, Bundle arguments, Handler handler)
    {
        this.url = url; this.context = context; this.arguments = arguments;this.handler=handler;
    }

    @Override
    protected Object doInBackground(Object... params) {

        String stringResponse = new String();

        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
            for (int i = 0; i < params.length; i++) {
                outputStreamWriter.write(params[i].toString());
            }


            outputStreamWriter.close();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                bufferedReader.close();

                stringResponse = response.toString();
            }
            else
            {

                JSONObject jsonObject = new JSONObject(params[0].toString());
                String searchId = new String();
                if(url.equals(ServerDetails.API_VIEW_PROFILE))
                    searchId = jsonObject.optString("searchid");
                if(url.equals(ServerDetails.API_VIEW_OWN_PROFILE))
                    searchId = jsonObject.optString("uniqueid");

                ProfileTableDatabaseOpenHelper profileTableDatabaseOpenHelper = new ProfileTableDatabaseOpenHelper(context);
                return profileTableDatabaseOpenHelper.getMember(searchId);
            }


          //if response is successful then add it to the database
            if (stringResponse != null && stringResponse.length()!=0) {
                try {


                    JSONObject jsonObject = new JSONObject(stringResponse);

                    //Parse the JSON and insert into DB;

                    //Parsing JSON

                    JSONArray personalDetails = jsonObject.getJSONArray("personaldata");
                    JSONArray emergencyDetails = jsonObject.getJSONArray("emergencycontact");
                    JSONArray familyDetails = jsonObject.getJSONArray("familydetails");
                    JSONArray eventDetails = jsonObject.getJSONArray("eventdetails");
                    JSONArray orgDetails = jsonObject.getJSONArray("organizationaldata");
//            JSONArray addressDetails = jsonObject.getJSONArray("address");


//            String userName = jsonObject.getString("username");
                    String userId = jsonObject.optString("userid");
//            String userImageUrl = jsonObject.getString("userimage");
//            String userTitle = jsonObject.getString("usertitle");


                    List<ProfileListItem> profileListItemList1 = new ArrayList<>();

                    for (int i = 0; i < personalDetails.length(); i++) {
                        JSONObject item = personalDetails.getJSONObject(i);
                        ProfileListItem profileListItem = new ProfileListItem(item.getString("value"), item.getString("title"));
                        profileListItemList1.add(profileListItem);
                    }

                    List<ProfileListItem> profileListItemList2 = new ArrayList<>();

                    for (int i = 0; i < emergencyDetails.length(); i++) {
                        JSONObject item = emergencyDetails.getJSONObject(i);
                        ProfileListItem profileListItem = new ProfileListItem(item.getString("value"), item.getString("title"));
                        profileListItemList2.add(profileListItem);
                    }

                    List<ProfileListItem> profileListItemList3 = new ArrayList<>();

                    for (int i = 0; i < familyDetails.length(); i++) {
                        JSONObject item = familyDetails.getJSONObject(i);
                        ProfileListItem profileListItem = new ProfileListItem(item.getString("value"), item.getString("title"));
                        profileListItemList3.add(profileListItem);
                    }

                    List<ProfileListItem> profileListItemList4 = new ArrayList<>();

                    for (int i = 0; i < eventDetails.length(); i++) {
                        JSONObject item = eventDetails.optJSONObject(i);
                        ProfileListItem profileListItem = new ProfileListItem(item.optString("value"), item.optString("title"));
                        profileListItemList4.add(profileListItem);
                    }

                    List<ProfileListItem> profileListItemList5 = new ArrayList<>();

                    for (int i = 0; i < orgDetails.length(); i++) {
                        JSONObject item = orgDetails.getJSONObject(i);
                        ProfileListItem profileListItem = new ProfileListItem(item.optString("value"), item.optString("title"));
                        profileListItemList5.add(profileListItem);
                    }

//            List<ProfileListItem> profileListItemList6 = new ArrayList<>();

//            for(int i=0;i<addressDetails.length();i++)
//            {
//                JSONObject item = addressDetails.getJSONObject(i);
//                ProfileListItem profileListItem = new ProfileListItem(item.getString("value"),item.getString("title"));
//                profileListItemList6.add(profileListItem);
//            }

                    Profile profile = new Profile(userId, profileListItemList1, profileListItemList2
                            , profileListItemList3, profileListItemList4, profileListItemList5);


                    ProfileTableDatabaseOpenHelper profileTableDatabaseOpenHelper = new ProfileTableDatabaseOpenHelper(context);
                    profileTableDatabaseOpenHelper.addMember(profile);
                    Log.i("com.ground0.likeminds", "Profile List Item added to DB");

                    return profile;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {

                //if the response failed, then check for data inside database

                JSONObject jsonObject = new JSONObject(params[0].toString());
                String searchId = new String();
                if(url.equals(ServerDetails.API_VIEW_PROFILE))
                    searchId = jsonObject.optString("searchid");
                if(url.equals(ServerDetails.API_VIEW_OWN_PROFILE))
                    searchId = jsonObject.optString("uniqueid");

                ProfileTableDatabaseOpenHelper profileTableDatabaseOpenHelper = new ProfileTableDatabaseOpenHelper(context);
                return profileTableDatabaseOpenHelper.getMember(searchId);  //if the read failed will return null
            }

        } catch (ProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;

    }


    @Override
    protected void onPostExecute(Object data) {


        if(data!=null)
        {

            Gson gson = new Gson();
            String stringJson = gson.toJson(data);

            Bundle bundle = new Bundle();
            Log.i("com.ground0.likeminds","JSON at MemberViewAsync :" + stringJson);
            bundle.putString("G_Json_data", stringJson);
            Message message = new Message();
            message.setData(bundle);

            handler.sendMessage(message);


//
//            Log.i("com.ground0.likeminds","Sending data Profile data to Adapter");
//            RecyclerView recyclerView = (RecyclerView)((AppCompatActivity)context).findViewById(R.id.contact_details_list);
//            recyclerView.setLayoutManager(new LinearLayoutManager(context));
//
//            //recycler View add the adapter
//            Bundle arguments = getArguments();
//            recyclerView.setAdapter(new PersonalDirectoryDetailsAdapter(context,(Profile)data ,arguments.getString("type"))); //get type from the argument
//
//            ((SwipeRefreshLayout)((AppCompatActivity)context)
//                    .findViewById(R.id.frag_contact_details_swipe))
//                    .setRefreshing(false);


        }

    }
}

