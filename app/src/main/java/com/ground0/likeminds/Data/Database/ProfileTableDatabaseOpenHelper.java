package com.ground0.likeminds.Data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ground0.likeminds.Data.Profile;
import com.ground0.likeminds.Data.ProfileListItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arjun on 16-09-2015.
 */
public class ProfileTableDatabaseOpenHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "com.ground0.likeminds.profiles";
    private static final int DATABASE_VERSION = 1;

    //Profile Table
    private static String PROFILE_TABLE_NAME = "Profiles";
    //Profile table columns
    private static String PROFILE_ID = "Id";
//    private static String PROFILE_TITLE = "Title";
//    private static String PROFILE_NAME = "Name";
//    private static String PROFILE_IMAGE = "Image";
//    private static String PROFILE_EMAIL_ID = "Email_Id";
    private static String PROFILE_PERSONAL_DETAILS = "Personal_Details";
    private static String PROFILE_EMERGENCY_DETAILS = "Emergency_Details";
    private static String PROFILE_FAMILY_DETAILS = "Family_Details";
    private static String PROFILE_EVENT_DETAILS = "Event_Details";
    private static String PROFILE_ORG_DATA_DETAILS = "Org_Data_Details";
//    private static String PROFILE_ADDRESS_DETAILS = "Address_Details";

    private Context context;


    public ProfileTableDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_PROFILE = "CREATE TABLE " + PROFILE_TABLE_NAME + " ("
                + PROFILE_ID + " TEXT PRIMARY KEY ,"
                + PROFILE_PERSONAL_DETAILS + " TEXT ,"
                + PROFILE_EMERGENCY_DETAILS + " TEXT ,"
                + PROFILE_FAMILY_DETAILS + " TEXT ,"
                +PROFILE_EVENT_DETAILS + " TEXT ,"
                +PROFILE_ORG_DATA_DETAILS + " TEXT "
                /*+PROFILE_ADDRESS_DETAILS + " BLOB"*/
                + ")";
        db.execSQL(CREATE_TABLE_PROFILE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_TABLE_NAME);

        onCreate(db);
    }

    public String getBlob(Object object)
    {
//        try {

            Gson gson = new Gson();
            String stringjson = gson.toJson(object);
            Log.i("com.ground0.likeminds","getBlob JSON:"+ stringjson);

            return stringjson;

//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//            objectOutputStream.writeObject(stringjson);
//            objectOutputStream.close();
//            byteArrayOutputStream.close();
//
//            return byteArrayOutputStream.toByteArray();

//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        return null;
    }

    public Object getObjectFromBlob(String json, Class classDef)
    {
//        try {

            Gson gson = new Gson();
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//            String object = (String) objectInputStream.readObject();
            JsonArray jsonArray = new JsonParser().parse(json).getAsJsonArray();
            JsonObject jsonObject = new JsonObject();
//            jsonObject.add("List",jsonArray);

            List<Object>list = new ArrayList<>();
            for(int i=0; i < jsonArray.size();i++)
            {
                jsonObject = (JsonObject)jsonArray.get(i);
                Object finalObj = gson.fromJson(jsonObject,classDef);
                list.add(finalObj);
            }


            return list;

//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        return null;
    }


    public void addMember(Profile profile)
    {
        SQLiteDatabase db = this.getWritableDatabase();

//        String query =
//                "INSERT INTO "+PROFILE_TABLE_NAME+" ("+PROFILE_ID+", "
//                        +PROFILE_PERSONAL_DETAILS+", "
//                        +PROFILE_EMERGENCY_DETAILS+", "
//                        +PROFILE_FAMILY_DETAILS+", "
//                        +PROFILE_EVENT_DETAILS+", "
//                        +PROFILE_ORG_DATA_DETAILS+")" +
//                        "VALUES ( `"+profile.getId()+"` , `"+getBlob((Object)profile.getPersonalDetails())+
//                        "`, `"+getBlob((Object)profile.getEmergencyDetails())+
//                        "`,` "+getBlob((Object)profile.getFamilyDetails())+
//                        "`,` "+getBlob((Object)profile.getEventDetails())+
//                        "`, `"+getBlob((Object)profile.getOrgData())+"` )";
//
//        db.execSQL(query);

        ContentValues values = new ContentValues();
        values.put( PROFILE_ID , profile.getId());
//        values.put( PROFILE_TITLE, profile.getTitle());
//        values.put( PROFILE_NAME, profile.getName());
//        values.put( PROFILE_IMAGE, profile.getImage());
//        values.put( PROFILE_EMAIL_ID, profile.getEmailId());
        values.put( PROFILE_PERSONAL_DETAILS, getBlob((Object) profile.getPersonalDetails()));
        values.put( PROFILE_EMERGENCY_DETAILS, getBlob((Object)profile.getEmergencyDetails()));
        values.put( PROFILE_FAMILY_DETAILS, getBlob((Object)profile.getFamilyDetails()));
        values.put( PROFILE_EVENT_DETAILS, getBlob((Object)profile.getEventDetails()));
        values.put( PROFILE_ORG_DATA_DETAILS, getBlob((Object)profile.getOrgData()));
//        values.put( PROFILE_ADDRESS_DETAILS , getBlob((Object)profile.getAddress()));

        db.insert(PROFILE_TABLE_NAME,null,values);
        db.close();
    }

    public Profile getMember(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PROFILE_TABLE_NAME, new String[] { PROFILE_ID,
                        /*PROFILE_TITLE, PROFILE_NAME, PROFILE_IMAGE,*/ PROFILE_PERSONAL_DETAILS,
                PROFILE_EMERGENCY_DETAILS, PROFILE_FAMILY_DETAILS, PROFILE_EVENT_DETAILS,
                PROFILE_ORG_DATA_DETAILS /*, PROFILE_ADDRESS_DETAILS*/}, PROFILE_ID + "=?",
                new String[] { (id) }, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();

            Profile profile = new Profile(cursor.getString(0)
                    , (List<ProfileListItem>)getObjectFromBlob(cursor.getString(1),ProfileListItem.class)
                    , (List<ProfileListItem>)getObjectFromBlob(cursor.getString(2),ProfileListItem.class)
                    , (List<ProfileListItem>)getObjectFromBlob(cursor.getString(3),ProfileListItem.class)
                    , (List<ProfileListItem>)getObjectFromBlob(cursor.getString(4),ProfileListItem.class)
                    , (List<ProfileListItem>)getObjectFromBlob(cursor.getString(5),ProfileListItem.class)
                    /*, (List<ProfileListItem>)getObjectFromBlob(cursor.getBlob(6),ProfileListItem.class)*/);

            // return member
            return profile;
        }
        return new Profile();
    }

    public List<Profile> getAllMembers()
    {
        List<Profile> memberList = new ArrayList<Profile>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PROFILE_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Profile profile = new Profile();
                profile.setId((cursor.getString(0)));
//                profile.setTitle(cursor.getString(1));
//                profile.setName(cursor.getString(2));
//                profile.setImage(cursor.getString(3));
//                profile.setEmailId(cursor.getString(3));
                profile.setPersonalDetails((List<ProfileListItem>) getObjectFromBlob(cursor.getString(1),ProfileListItem.class));
                profile.setEmergencyDetails((List<ProfileListItem>) getObjectFromBlob(cursor.getString(2),ProfileListItem.class));
                profile.setFamilyDetails((List<ProfileListItem>) getObjectFromBlob(cursor.getString(3),ProfileListItem.class));
                profile.setEventDetails((List<ProfileListItem>) getObjectFromBlob(cursor.getString(4),ProfileListItem.class));
                profile.setOrgData((List<ProfileListItem>) getObjectFromBlob(cursor.getString(5),ProfileListItem.class));
                /*profile.setAddress((List<ProfileListItem>) getObjectFromBlob(cursor.getBlob(5),ProfileListItem.class));*/
                // Adding contact to list
                memberList.add(profile);
            } while (cursor.moveToNext());
        }

        // return contact list
        return memberList;
    }

    public int getMemberCount() {
        String countQuery = "SELECT  * FROM " + PROFILE_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int updateMember(Profile profile)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


//        values.put( PROFILE_TITLE, profile.getTitle());
//        values.put(PROFILE_NAME, profile.getName());
//        values.put( PROFILE_IMAGE, profile.getImage());
//        values.put( PROFILE_EMAIL_ID, profile.getEmailId());
        values.put( PROFILE_PERSONAL_DETAILS, getBlob((Object) profile.getPersonalDetails()));
        values.put( PROFILE_EMERGENCY_DETAILS, getBlob((Object)profile.getEmergencyDetails()));
        values.put( PROFILE_FAMILY_DETAILS, getBlob((Object)profile.getFamilyDetails()));
        values.put( PROFILE_EVENT_DETAILS, getBlob((Object) profile.getEventDetails()));
        values.put( PROFILE_ORG_DATA_DETAILS, getBlob((Object) profile.getOrgData()));
       /* values.put( PROFILE_ADDRESS_DETAILS, getBlob(profile.getAddress()));*/


        // updating row
        return db.update(PROFILE_TABLE_NAME, values, PROFILE_ID + " = ?",
                new String[] { (profile.getId()) });
    }

    public void removeMember(Profile profile)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PROFILE_TABLE_NAME, PROFILE_ID + " = ?",
                new String[]{profile.getId()});
        db.close();
    }

}
