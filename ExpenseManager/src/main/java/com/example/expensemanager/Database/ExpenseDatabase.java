package com.example.expensemanager.Database;

import static com.example.expensemanager.Util.Params.BILL_TABLE_NAME;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.expensemanager.Model.BillModel;
import com.example.expensemanager.Model.OtherModel;
import com.example.expensemanager.Model.TransactionModel;
import com.example.expensemanager.Util.Params;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDatabase extends SQLiteOpenHelper {

    public ExpenseDatabase(Context context) {
        super(context, Params.EXPENSE_DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + BILL_TABLE_NAME + " ("
                + Params.ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Params.NAME_COL + " INTEGER,"
                + Params.AMOUNT_COL + " TEXT)";

        String query1 = "CREATE TABLE " + Params.TRANSACTION_TABLE_NAME + " ("
                + Params.ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Params.NAME_COL + " TEXT,"
                + Params.AMOUNT_COL + " TEXT,"
                + Params.DATE_COL + " TEXT)";

        String query2 = "CREATE TABLE " + Params.OTHER_TABLE_NAME + " ("
                + Params.ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Params.NAME_COL + " TEXT,"
                + Params.AMOUNT_COL + " TEXT,"
                + Params.CATEGORY_COL + " TEXT)";

        db.execSQL(query);
        db.execSQL(query1);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BILL_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Params.TRANSACTION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Params.OTHER_TABLE_NAME);
        onCreate(db);
    }

    public void addBill(BillModel billModel){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Params.NAME_COL, billModel.getName());
        contentValues.put(Params.AMOUNT_COL, billModel.getAmount());
        sqLiteDatabase.insert(BILL_TABLE_NAME, null, contentValues);
    }
    public void insertArrayList(ArrayList<Integer> arrayList) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String arrayListJson = new Gson().toJson(arrayList);
        values.put(Params.NAME_COL, arrayListJson);
        db.insert(BILL_TABLE_NAME, null, values);
    }
    public ArrayList<Integer>  getArrayList() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BILL_TABLE_NAME, null);
        ArrayList<Integer> imageList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String arrayListJson = cursor.getString(cursor.getColumnIndex(Params.NAME_COL));
                Log.e("TAG", "arrayListJson===> : "+arrayListJson );
                Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
                imageList =new Gson().fromJson(arrayListJson,type);
            } while (cursor.moveToNext());
        }
        return imageList;
    }

    public void addTransaction(TransactionModel transactionModel){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Params.NAME_COL, transactionModel.getName());
        contentValues.put(Params.AMOUNT_COL, transactionModel.getAmount());
        contentValues.put(Params.DATE_COL, String.valueOf(transactionModel.getDate()));
        sqLiteDatabase.insert(Params.TRANSACTION_TABLE_NAME, null, contentValues);
    }

    public void addOther(OtherModel otherModel){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Log.e("tag", otherModel.getName() );

        contentValues.put(Params.NAME_COL, otherModel.getName());
        contentValues.put(Params.AMOUNT_COL, otherModel.getAmount());
        contentValues.put(Params.CATEGORY_COL, otherModel.getCategory());
        sqLiteDatabase.insert(Params.OTHER_TABLE_NAME, null, contentValues);
    }

    @SuppressLint("Range")
    public List<BillModel> getBill(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BILL_TABLE_NAME, null);
        
        List<BillModel> billModelList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                BillModel billModel = new BillModel();
                billModel.setId(cursor.getString(cursor.getColumnIndex(Params.ID_COL)));
                billModel.setName(cursor.getString(cursor.getColumnIndex(Params.NAME_COL)));
                billModel.setAmount(cursor.getString(cursor.getColumnIndex(Params.AMOUNT_COL)));
                billModelList.add(new BillModel(cursor.getString(cursor.getColumnIndex(Params.ID_COL)),
                        cursor.getString(cursor.getColumnIndex(Params.NAME_COL)),cursor.getString(cursor.getColumnIndex(Params.AMOUNT_COL))));

            } while (cursor.moveToNext());
        }
       
        cursor.close();
        return billModelList;
    }

    @SuppressLint("Range")
    public List<TransactionModel> getTransaction(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Params.TRANSACTION_TABLE_NAME, null);

        List<TransactionModel> transactionModelList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                TransactionModel transactionModel = new TransactionModel();
                transactionModel.setId(cursor.getString(cursor.getColumnIndex(Params.ID_COL)));
                transactionModel.setName(cursor.getString(cursor.getColumnIndex(Params.NAME_COL)));
                transactionModel.setAmount(cursor.getString(cursor.getColumnIndex(Params.AMOUNT_COL)));
                transactionModel.setDate(cursor.getLong(cursor.getColumnIndex(Params.DATE_COL)));
                transactionModelList.add(transactionModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return transactionModelList;
    }

    @SuppressLint("Range")
    public List<OtherModel> getOther(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Params.OTHER_TABLE_NAME, null);

        List<OtherModel> otherModelList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                OtherModel otherModel = new OtherModel();
                otherModel.setId(cursor.getString(cursor.getColumnIndex(Params.ID_COL)));
                otherModel.setName(cursor.getString(cursor.getColumnIndex(Params.NAME_COL)));
                otherModel.setAmount(cursor.getString(cursor.getColumnIndex(Params.AMOUNT_COL)));
                otherModel.setCategory(cursor.getString(cursor.getColumnIndex(Params.CATEGORY_COL)));
                otherModelList.add(otherModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return otherModelList;
    }
    public boolean isUserExists(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] colums = {Params.ID_COL};
        String selection = Params.NAME_COL + "= ? " + " AND " + Params.AMOUNT_COL +"= ?";
        Cursor cursor = db.query(BILL_TABLE_NAME, colums, selection, new String[]{username,password}, null, null, null);
        boolean userExists = cursor.moveToFirst();
        cursor.close();
        db.close();
        Log.e("TAG", "isUserExists: "+userExists );
        return userExists;
    }


}
