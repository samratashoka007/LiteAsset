package com.websquareit.daasset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String ASSET_NUMBER = "ASSET_NUMBER";
    public static final String COLUMN_ID = "da_id";
    public static final String COLUMN_NAME = "SITES";
    public static final String COLUMN_STATUS = "status";
    public static final String COMMENTS = "OTHERS_COMMENTS";
    public static final String DATETIME = "DATE";
    public static final String DB_NAME = "LightAsset1";
    private static final int DB_VERSION = 4;
    public static final String EMERGENCY_LIGHTS_T5_2X14W = "EMERGENCY_LIGHTS_T5_2X14W";
    public static final String EMERGENCY_LIGHTS_T5_2X28W = "EMERGENCY_LIGHTS_T5_2X28W";
    public static final String EMERGENCY_LIGHTS_T8_2X18W = "EMERGENCY_LIGHTS_T8_2X18W";
    public static final String EMERGENCY_LIGHTS_T8_2X36W = "EMERGENCY_LIGHTS_T8_2X36W";
    public static final String EXIT_BLADE_TYPE = "EXIT_BLADE_TYPE";
    public static final String EXIT_BOX_TYPE = "EXIT_BOX_TYPE";
    public static final String EXIT_JUMBO = "EXIT_JUMBO";
    public static final String EXIT_QUICK_FIT = "EXIT_QUICK_FIT";
    public static final String EXIT_SWINGBLADE_TYPE = "EXIT_SWINGBLADE_TYPE";
    public static final String EXIT_WEATHERPROOF = "EXIT_WEATHERPROOF";
    public static final String LOCATION = "LOCATION";
    public static final String PROPERTY_LEVEL = "PROPERTY_LEVEL";
    public static final String TABLE_NAME = "SiteDetails";
    public static final String TWIN_SPITFIRE = "TWIN_SPITFIRE";
    public static final String EMERGENCY = "EMERGENCY_LIGHTS";
    public static final String BATTEN = "BATTENS";
    public static final String EXITSIGN = "EXIT_SIGNS";
//    String date = this.sdf.format(new Date());
   // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " VARCHAR, "+
                ASSET_NUMBER+ " VARCHAR, "+
                BATTEN+ " VARCHAR,"+
                EMERGENCY+" VARCHAR,"+
                EXITSIGN +" VARCHAR,"+
                LOCATION+ " VARCHAR,"+
                PROPERTY_LEVEL+ " VARCHAR,"+
                COMMENTS+" VARCHAR,"
                + COLUMN_STATUS +
                " TINYINT);";
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DAASSET");
        onCreate(db);
    }
    public boolean  addNewName(String s, String name1, String name2, String name3, String name4, String name5, String name6, String name, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, s);
        contentValues.put(ASSET_NUMBER, name1);
        contentValues.put(BATTEN, name2);
        contentValues.put(EMERGENCY, name3);
        contentValues.put(EXITSIGN, name4);
        contentValues.put(LOCATION, name5);
        contentValues.put(PROPERTY_LEVEL, name6);
        contentValues.put(COMMENTS, name);
        contentValues.put(COLUMN_STATUS, status);


        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }
  /*  public boolean addName(String name, String assetNo, String twin, String er2cross14, String er2cross18, String er2cross28, String er2cross36, String quickfit, String boxType, String bladeType, String swing, String weather, String jumbo, String location, String property, String cmnt, int status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(ASSET_NUMBER, assetNo);
        contentValues.put(TWIN_SPITFIRE, twin);
        contentValues.put(EMERGENCY_LIGHTS_T5_2X14W, er2cross14);
        contentValues.put(EMERGENCY_LIGHTS_T8_2X18W, er2cross18);
        contentValues.put(EMERGENCY_LIGHTS_T5_2X28W, er2cross28);
        contentValues.put(EMERGENCY_LIGHTS_T8_2X36W, er2cross36);
        contentValues.put(EXIT_QUICK_FIT, quickfit);
        contentValues.put(EXIT_BOX_TYPE, boxType);
        contentValues.put(EXIT_BLADE_TYPE, bladeType);
        contentValues.put(EXIT_SWINGBLADE_TYPE, swing);
        contentValues.put(EXIT_WEATHERPROOF, weather);
        contentValues.put(EXIT_JUMBO, jumbo);
        contentValues.put(LOCATION, location);
        contentValues.put(PROPERTY_LEVEL, property);
        contentValues.put(COMMENTS, cmnt);
        contentValues.put("status", Integer.valueOf(status));
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }*/

    public boolean updateNameStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }

    public Cursor getNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public Cursor getUnsyncedNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }


}
