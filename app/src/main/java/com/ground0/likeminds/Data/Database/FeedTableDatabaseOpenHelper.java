package com.ground0.likeminds.Data.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Arjun on 16-09-2015.
 */
public class FeedTableDatabaseOpenHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "com.ground0.likeminds.data";
    private static final int DATABASE_VERSION = 1;


    //add table feed
    private static final String FEED_TABLE_NAME = "Feeds";
    //feed table columns
    private static final String FEED_TABLE_ID = "Id";
    private static final String FEED_TABLE_DATE = "Data";
    private static final String FEED_GROUP_NAME = "Group_Name";
    private static final String FEED_AUTHOR_NAME = "Author_Name";
    private static final String FEED_TYPE = "Type";
    private static final String FEED_SUBTYPE_FOREIGN_KEY = "Subtype_Id"; //Foreign key to table with subtype

    //add table subtype text
    private static final String SUBTYPE_TEXT = "Subtype_text";
    //subtype table columns
    private static final String SUBTYPE_TEXT_TABLE_ID = "Id";
    private static final String SUBTYPE_TEXT_CONTENT = "Content";
    private static final String SUBTYPE_TEXT_N_SHARE = "N_Share";
    private static final String SUBTYPE_TEXT_N_UPVOTES = "N_Upvotes";
    private static final String SUBTYPE_TEXT_N_COMMENTS = "N_Comments";

    //add table subtype image
    private static final String SUBTYPE_IMAGE = "Subtype_image";
    //subtype image columns;
    private static final String SUBTYPE_IMAGE_TABLE_ID = "Id";
    private static final String SUBTYPE_IMAGE_N = "N_Images";
    private static final String SUBTYPE_IMAGE_URL = "Image_List";


    //add other tables


    public FeedTableDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create the tables
        //create table subtype plain text
        String CREATE_SUBTYPE_TEXT_TABLE = "CREATE TABLE " + SUBTYPE_TEXT + "("
                + SUBTYPE_TEXT_TABLE_ID + " INTEGER PRIMARY KEY," + SUBTYPE_TEXT_CONTENT + " TEXT,"
                + SUBTYPE_TEXT_N_SHARE
                + " INTEGER," + SUBTYPE_TEXT_N_UPVOTES
                + " INTEGER," + SUBTYPE_TEXT_N_COMMENTS + " INTEGER" + ")";
        db.execSQL(CREATE_SUBTYPE_TEXT_TABLE);

        //create table subtype image
        String CREATE_SUBTYPE_IMAGE_TABLE = "CREATE TABLE" + SUBTYPE_IMAGE + "("
                + SUBTYPE_IMAGE_TABLE_ID + " INTEGER PRIMARY KEY, "+ SUBTYPE_IMAGE_N + " INTEGER,"
                + SUBTYPE_IMAGE_URL + " TEXT " + ")";
        db.execSQL(CREATE_SUBTYPE_IMAGE_TABLE);

        //create table feed
        String CREATE_FEEDS_TABLE = "CREATE TABLE " + FEED_TABLE_NAME + "("
                + FEED_TABLE_ID + " INTEGER PRIMARY KEY," + FEED_TABLE_DATE + " TEXT,"
                + FEED_GROUP_NAME + " TEXT," + FEED_AUTHOR_NAME + " TEXT,"
                + FEED_TYPE + " TEXT," + FEED_SUBTYPE_FOREIGN_KEY + " INTEGER FOREIGN KEY" + ")";

        db.execSQL(CREATE_FEEDS_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //drop the current tables and create new one
        db.execSQL("DROP TABLE IF EXISTS " + FEED_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SUBTYPE_TEXT);
        db.execSQL("DROP TABLE IF EXISTS " + SUBTYPE_IMAGE);


        // Create tables again
        onCreate(db);

    }
}
