package com.tenoku.hornsound.audiotool.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    
    public static final String DATABASE_NAME = "Sound.db";
    public static final String TABLE_NAME = "sound_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "SOUND";
    public static final String COL_3 = "SOUND_IMAGE";
    public static final String COL_4 = "SOUND_NAME";
    public static final String COL_5 = "SOUND_LIKE";
    public static final String COL_6 = "SOUND_PRO";
    public static final String COL_7 = "SOUND_TYPE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE "
                + TABLE_NAME + "(" + COL_1
                + " INTEGER PRIMARY KEY,"
                + COL_2 + " TEXT,"
                + COL_3 + " INTEGER,"
                + COL_4 + " TEXT,"
                + COL_5 + " BOOLEAN,"
                + COL_6 + " BOOLEAN,"
                + COL_7 + " TEXT " + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String sound, Integer soundIMage, String soundName, boolean like, boolean pro, String hornType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, sound);
        contentValues.put(COL_3, soundIMage);
        contentValues.put(COL_4, soundName);
        contentValues.put(COL_5, like);
        contentValues.put(COL_6, pro);
        contentValues.put(COL_7, hornType);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData(String hornType) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COL_7+" = ?", new String[] {hornType});
    }

    public boolean updateLike(String soundName, boolean like) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_5, like);
        db.update(TABLE_NAME, contentValues, "SOUND_NAME = ?", new String[]{soundName});
        return true;
    }
    public boolean updatePro(String soundName, boolean pro) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_6, pro);
        db.update(TABLE_NAME, contentValues, "SOUND_NAME = ?", new String[]{soundName});
        return true;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }

    public Cursor getFavouriteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COL_5+" = ?", new String[] {"1"});
    }
}