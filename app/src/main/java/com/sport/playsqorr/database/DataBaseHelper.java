package com.sport.playsqorr.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "sqorr.db";

    SQLiteDatabase myDB;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + DB_Constants.TABLE_USERINFO
                + " (" //+ DB_Constants.CUST_ID + " ,"
                + DB_Constants.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DB_Constants.USER_NAME + " TEXT,"
                + DB_Constants.USER_EMAIL + " TEXT,"
                + DB_Constants.USER_DOB + " TEXT,"
                + DB_Constants.USER_IMAGE + " TEXT,"
                + DB_Constants.USER_WINS + " TEXT,"
                + DB_Constants.USER_STATE + " TEXT,"
                + DB_Constants.USER_CITY + " TEXT,"
                + DB_Constants.USER_COUNTRY + " TEXT,"
                + DB_Constants.USER_SESSIONTOKEN + " TEXT,"
                + DB_Constants.USER_TOKEN + " TEXT,"
                + DB_Constants.USER_NUMBER + " TEXT,"
                + DB_Constants.USER_MODETYPE + " TEXT,"
                + DB_Constants.USER_REF + " TEXT,"
                + DB_Constants.USER_GENDER + " TEXT,"
                + DB_Constants.USER_SPORTSPRE + " TEXT,"
                + DB_Constants.USER_TOTALCASHBALANCE + " TEXT,"
                + DB_Constants.USER_CASHBALANCE + " TEXT,"
                + DB_Constants.USER_PROMOBALANCE + " TEXT,"
                + DB_Constants.USER_TOKENBALANCE + " TEXT" + ");");

            db.execSQL("CREATE TABLE " + DB_Constants.TABLE_DEEPLINK
                + " (" //+ DB_Constants.CUST_ID + " ,"
                + DB_Constants.USER_DEEPID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DB_Constants.USER_DEEPLINK_CODE + " TEXT,"
                + DB_Constants.USER_DEEPLINK_SIGNUP + " TEXT,"
                + DB_Constants.USER_DEEPLINK_ADDFUNDS + " TEXT,"
                + DB_Constants.USER_DEEPLINK_ROLE + " TEXT,"
                + DB_Constants.USER_DEEPLINK_STATUS + " TEXT" + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_Constants.TABLE_USERINFO);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Constants.TABLE_DEEPLINK);

        onCreate(db);
    }


    //Insert
    public boolean insertUserInfo(ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DB_Constants.TABLE_USERINFO, null, contentValues);
        Log.d("inserted order", String.valueOf(contentValues));
        //	db.close();
        return true;
    }


    //Insert
    public boolean insertDeepInfo(ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DB_Constants.TABLE_DEEPLINK, null, contentValues);
        Log.d("inserted order", String.valueOf(contentValues));
        //	db.close();
        return true;
    }

    //    // GetUsers
    public Cursor getAllUserInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DB_Constants.TABLE_USERINFO, null);
        return res;
    }

    public Cursor getDeepInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DB_Constants.TABLE_DEEPLINK, null);
        return res;
    }
    //Inside your SQLite helper class
    @Override
    public synchronized void close () {
        SQLiteDatabase db = this.getWritableDatabase();

        if (db != null) {
            db.close();
            super.close();
        }
    }
    //Update
    public boolean updateUser(ContentValues cv) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.update(DB_Constants.TABLE_USERINFO, cv, "", null);
        return true;
    }

    //Update Promo
    public boolean updateUserDeep(ContentValues cv) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.update(DB_Constants.TABLE_DEEPLINK, cv, "", null);
        return true;
    }

    public void clearTableMobileUser() {

        SQLiteDatabase db = this.getWritableDatabase(); //get database
//        db.execSQL("DELETE FROM " + DB_Constants.TABLE_USERINFO); //delete all rows in a table
        db.delete(DB_Constants.TABLE_USERINFO,null,null);
        db.close();
    }

    public void resetLocalData(){
        SQLiteDatabase db = this.getWritableDatabase(); //get database
        db.execSQL("delete from "+ DB_Constants.TABLE_USERINFO);
        db.execSQL("delete from "+ DB_Constants.TABLE_DEEPLINK);
        db.close();
    }
}
