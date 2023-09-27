package com.chitras.gotine.chipkavo.imagesearch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HistoryDataBase extends SQLiteOpenHelper {

    public HistoryDataBase(@Nullable Context context) {
        super(context, "searchHistoryDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table searchHistory (id integer primary key autoincrement ,name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertSearch(String search) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", search);

        sqLiteDatabase.insert("searchHistory", null, values);
    }

    public Cursor GetAllHistory() {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String qry = "select * from searchHistory";
        return sqLiteDatabase.rawQuery(qry, null);
    }

    public void deleteHistory(String name) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String qry = "delete from searchHistory where name = '" + name + "'";
        sqLiteDatabase.execSQL(qry);
    }

    public void deleteAllHistory() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String qry = "delete from searchHistory";
        sqLiteDatabase.execSQL(qry);
    }
}
