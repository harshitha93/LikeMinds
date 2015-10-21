package com.ground0.likeminds.Data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ground0.likeminds.Data.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arjun on 18-09-2015.
 */
public class MemberListTableOpenHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "com.ground0.likeminds.memberlist";
    private static final int DATABASE_VERSION = 1;

    //Profile Table
    private static String MEMBER_LIST_TABLE_NAME = "MemberList";
    //Profile table columns
    private static String PROFILE_ID = "Id";
//    private static String PROFILE_TITLE = "Title";
    private static String PROFILE_NAME = "Name";
    private static String PROFILE_IMAGE_URL = "Profile_Image";
//    private static String PROFILE_EMAIL_ID = "Email_Id";
//    private static String PROFILE_PERSONAL_DETAILS = "Personal_Details";
//    private static String PROFILE_EMERGENCY_DETAILS = "Emergency_Details";
//    private static String PROFILE_FAMILY_DETAILS = "Family_Details";
//    private static String PROFILE_EVENT_DETAILS = "Event_Details";
//    private static String PROFILE_ORG_DATA_DETAILS = "Org_Data_Details";

    public MemberListTableOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_TABLE_MEMBER_LIST = "CREATE TABLE " + MEMBER_LIST_TABLE_NAME + "( "
                + PROFILE_ID + " TEXT PRIMARY KEY ," + PROFILE_NAME + " TEXT ,"
                + PROFILE_IMAGE_URL + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE_MEMBER_LIST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + MEMBER_LIST_TABLE_NAME);

        onCreate(db);

    }


    public void addMember(Member member)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( PROFILE_ID , member.getId());
        values.put( PROFILE_NAME, member.getName());
        values.put( PROFILE_IMAGE_URL, member.getImage());

        db.insert(MEMBER_LIST_TABLE_NAME, null, values);
        db.close();
    }

    public Member getMember(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(MEMBER_LIST_TABLE_NAME, new String[] { PROFILE_ID,
                        PROFILE_NAME, PROFILE_IMAGE_URL }, PROFILE_ID + "=?",
                new String[] { (id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Member member = new Member((cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return member
        return member;
    }

    public List<Member> getAllMembers()
    {
        List<Member> contactList = new ArrayList<Member>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MEMBER_LIST_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        /*if*/ cursor.moveToFirst(); {
            do {
                Member member = new Member(cursor.getString(0),cursor.getString(1),cursor.getString(2));
//                member.setId((cursor.getString(0)));
//                member.setName(cursor.getString(1));
//                member.setImage(cursor.getString(2));
                // Adding contact to list
                contactList.add(member);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public int getMemberCount() {
        String countQuery = "SELECT  * FROM " + MEMBER_LIST_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int updateMember(Member member)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PROFILE_NAME, member.getName());
        values.put(PROFILE_IMAGE_URL, member.getImage());


        // updating row
        return db.update(MEMBER_LIST_TABLE_NAME, values, PROFILE_ID + " = ?",
                new String[] { (member.getId()) });
    }

    public void removeMember(Member member)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MEMBER_LIST_TABLE_NAME, PROFILE_ID + " = ?",
                new String[] { (member.getId()) });
        db.close();
    }
}
