package com.cashmoney.calculate.report.manager.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cashmoney.calculate.report.manager.Model.BalanceModel;
import com.cashmoney.calculate.report.manager.Model.BusinessCardModel;
import com.cashmoney.calculate.report.manager.Model.UserModel;

import java.util.ArrayList;

public class PersonDataBase extends SQLiteOpenHelper {
    private static final String DB_NAME = "person";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "mybusiness";
    private static final String TABLE_BUSINESS = "businesscard";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String MOBILE_COL = "mobileno";
    private static final String EMILA_COL = "email";
    private static final String InputStream = "inputstream";
    private static final String IdCard = "idCard";
    private static final String isPremium = "ispremium";


    public PersonDataBase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + MOBILE_COL + " TEXT,"
                + EMILA_COL + " TEXT)";

        String query2 = "CREATE TABLE " + TABLE_BUSINESS + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + IdCard + " TEXT,"
                + InputStream + " TEXT,"
                + isPremium + " TEXT)";
        db.execSQL(query);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUSINESS);
        onCreate(db);
    }

    public void insertUserInDataBase(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, userModel.getName());
        values.put(MOBILE_COL, userModel.getMobile());
        values.put(EMILA_COL, userModel.getEmail());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void insertBusinessCardDetails(ArrayList<BusinessCardModel> arrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < arrayList.size(); i++) {
            if (!isRecordExists(db, arrayList.get(i))) {
                ContentValues values = new ContentValues();
                values.put(IdCard, arrayList.get(i).getId());
                values.put(InputStream, arrayList.get(i).getPath());
                values.put(isPremium, arrayList.get(i).isPremium());
                db.insert(TABLE_BUSINESS, null, values);
            }
        }
        db.close();
    }
    public ArrayList<BusinessCardModel> getAllDataFromBusiness() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_BUSINESS, null);
        ArrayList<BusinessCardModel> courseModalArrayList = new ArrayList<>();
        if (cursorCourses.moveToFirst()) {
            do {
                courseModalArrayList.add(new BusinessCardModel(cursorCourses.getInt(1),cursorCourses.getString(2),cursorCourses.getInt(3)>0));
            } while (cursorCourses.moveToNext());
        }

        cursorCourses.close();
        return courseModalArrayList;
    }
    public boolean updatePremium(String idCard, boolean like) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(isPremium, like);
//        db.update(TABLE_BUSINESS, contentValues, "SOUND_NAME = ?", new String[]{cardName});
        db.update(TABLE_BUSINESS, contentValues, IdCard + " LIKE ?",new String[]{"%" +idCard+ "%"});
        db.close();
        return true;
    }

    private boolean isRecordExists(SQLiteDatabase db, BusinessCardModel model) {

        Cursor cursor = db.rawQuery("select InputStream from " + TABLE_BUSINESS
                + " where InputStream=?", new String[]{model.getPath()});
        boolean recordExists = cursor.getCount() > 0;
        cursor.close();
        Log.e("TAG", "isRecordExists:----> "+recordExists );
        return recordExists;
    }


    public ArrayList<UserModel> getAllDataFromDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<UserModel> courseModalArrayList = new ArrayList<>();
        if (cursorCourses.moveToFirst()) {
            do {

                courseModalArrayList.add(new UserModel(
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3)));
            } while (cursorCourses.moveToNext());
        }

        cursorCourses.close();
        return courseModalArrayList;
    }

    public void  updateInUserDataBase(UserModel userModel,String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.e("TAG", "NAME_COL: "+userModel.getName() );
        Log.e("TAG", "MOBILE_COL: "+userModel.getMobile() );
        Log.e("TAG", "EMILA_COL: "+userModel.getEmail() );
        values.put(NAME_COL,userModel.getName());
        values.put(MOBILE_COL,userModel.getMobile());
        values.put(EMILA_COL,userModel.getEmail());
        db.update(TABLE_NAME,values,"name=?",new String[]{name});
        db.close();
        Log.e("TAG", "updateInUserDataBase: " );

    }
    public void deleteUsers(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "name=?", new String[]{username});
        db.close();
    }
//    public ArrayList<BalanceModel> getAllDataFromBlanaceSheet() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_BALANCE, null);
//        ArrayList<BalanceModel> courseModalArrayList = new ArrayList<>();
//        if (cursorCourses.moveToFirst()) {
//            do {
//
//                courseModalArrayList.add(new BalanceModel(
//                        cursorCourses.getString(1),
//                        cursorCourses.getString(2),
//                        cursorCourses.getString(3),
//                        cursorCourses.getString(4),
//                        cursorCourses.getString(5)));
//            } while (cursorCourses.moveToNext());
//        }
//
//        cursorCourses.close();
//        return courseModalArrayList;
//    }
}
