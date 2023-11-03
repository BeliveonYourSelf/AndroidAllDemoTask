package com.cashmoney.calculate.report.manager.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.cashmoney.calculate.report.manager.Model.TransactionModel;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "user_details.db";

    /* User Details Table */

    public static final String TABLE_NAME_1 = "user_details";
    public static final String COL_1_1 = "ID";
    public static final String COL_2_1 = "PHONE_NUMBER";
    public static final String COL_3_1 = "NAME";
    public static final String COL_4_1 = "PASSCODE";
    public static final String COL_5_1 = "BUSSINESS_NAME";
    public static final String COL_6_1 = "LOCATION";
    public static final String COL_7_1 = "CREATED_TIME";
    public static final String COL_8_1 = "STATUS";      // 1] Personal 2] Add Contact 3] Delete
    public static final String COL_9_1 = "IMAGE";

    /* Transaction Table */

    public static final String TABLE_NAME_2 = "transaction_details";
    public static final String ID = "ID";
    public static final String SENDER_NAME = "SENDER_NAME";
    public static final String RECEIVER_NAME = "RECEIVER_NAME";
    public static final String AMOUNT = "AMOUNT";
    public static final String STATUS = "STATUS";
    public static final String REMARKS = "REMARKS";
    public static final String CREATED_TIME = "CREATED_TIME";
    public static final String DATE = "DATE";


    /* Contact Group */

    public static final String TABLE_NAME_3 = "friend_details";
    public static final String COL_1_3 = "ID";
    public static final String COL_2_3 = "OWNER_ID";
    public static final String COL_3_3 = "FRIEND_ID";
    public static final String COL_4_3 = "CREATED_TIME";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME_1 +
                " (" + COL_1_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_2_1 + " INTEGER,"
                + COL_3_1 + " TEXT,"
                + COL_4_1 + " TEXT,"
                + COL_5_1 + " TEXT," + COL_6_1 + " TEXT," + COL_7_1 + " DEFAULT CURRENT_TIMESTAMP," + COL_8_1 + " INTEGER," + COL_9_1 + " blob)");
        db.execSQL("create table " + TABLE_NAME_2 + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SENDER_NAME + " INTEGER," + RECEIVER_NAME + " INTEGER," + AMOUNT + " INTEGER," + STATUS + " INTEGER," + REMARKS + " TEXT," + CREATED_TIME + " DEFAULT CURRENT_TIMESTAMP," + DATE + " TEXT)");
        db.execSQL("create table " + TABLE_NAME_3 + " (" + COL_1_3 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2_3 + " INTEGER," + COL_3_3 + " INTEGER," + COL_4_3 + " DEFAULT CURRENT_TIMESTAMP)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_3);
        onCreate(db);

    }
    public Cursor user_friend_transaction(String user_id, String friend_id) {
//        String Query = "SELECT " + COL_2_2 + "," + COL_4_2 + "," + COL_6_2 + "," + COL_7_2 + "," + COL_1_2 + " FROM " + TABLE_NAME_2 + " WHERE " + COL_4_2 + "!= 0 " +
//                "AND ( (" + COL_2_2 + "=" + user_id + " AND " + COL_3_2 + "=" + friend_id + ")OR(" + COL_2_2 + "=" + friend_id + " AND " + COL_3_2 + "=" + user_id + "))";
//
//      String query = "SELECT * FROM " + TABLE_NAME_2 + " WHERE " + COL_2_2 + "=" + " user_id";
        String query = "SELECT * FROM transaction_details WHERE SENDER_NAME = '" + user_id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public boolean storeNewExistingFriendUserData(int user_id, int friend_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2_3, user_id);
        cv.put(COL_3_3, friend_id);
        db.insert(TABLE_NAME_3, null, cv);

        ContentValues cv2 = new ContentValues();
        cv2.put(COL_3_3, user_id);
        cv2.put(COL_2_3, friend_id);
        db.insert(TABLE_NAME_3, null, cv2);

        ContentValues cv3 = new ContentValues();
        cv3.put(SENDER_NAME, user_id);
        cv3.put(RECEIVER_NAME, friend_id);
        cv3.put(AMOUNT, 0);
        cv3.put(STATUS, 2);
        db.insert(TABLE_NAME_2, null, cv3);

        ContentValues cv4 = new ContentValues();
        cv3.put(SENDER_NAME, friend_id);
        cv3.put(RECEIVER_NAME, user_id);
        cv3.put(AMOUNT, 0);
        cv3.put(STATUS, 2);
        long result = db.insert(TABLE_NAME_2, null, cv3);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean storeNewCreditTransaction(TransactionModel transactionModel) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(SENDER_NAME, transactionModel.getTranscationSenderid());
        cv.put(RECEIVER_NAME, transactionModel.getTranscationSenderid());
        cv.put(AMOUNT, transactionModel.getTranscationAmount());
        cv.put(STATUS, transactionModel.getCreditOrDebit());
        cv.put(REMARKS, transactionModel.getTranscationRemarks());
        cv.put(CREATED_TIME, transactionModel.getTranscationDate());

        long result = db.insert(TABLE_NAME_2, null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void UpdateinTranscationDetails(TransactionModel transactionModel, String remakrs) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SENDER_NAME, transactionModel.getTranscationSenderid());
        values.put(RECEIVER_NAME, transactionModel.getTranscationSenderid());
        values.put(AMOUNT, transactionModel.getTranscationAmount());
        if (remakrs.equals("YOUGOT") || remakrs.equals("YOUGIVE")) {
            Log.e("TAGC", "Change in Database From DR TO CR: ");
            values.put(STATUS, remakrs);
        } else {
            Log.e("TAGC", "ELSE IN DB");
            values.put(STATUS, transactionModel.getCreditOrDebit());
        }
        values.put(REMARKS, transactionModel.getTranscationRemarks());
        values.put(CREATED_TIME, transactionModel.getTranscationDate());

        if(remakrs.equals("YOUGOT") || remakrs.equals("YOUGIVE"))
        {
            Log.e("TAGC", "update---- Where STATUS === GOT OR GIVE: " );
            sqLiteDatabase.update(TABLE_NAME_2, values, AMOUNT + " LIKE ?", new String[]{"%" + transactionModel.getTranscationAmount() + "%"});
        }else {
            Log.e("TAGC", "ELSE" );
            sqLiteDatabase.update(TABLE_NAME_2, values, REMARKS + " LIKE ?", new String[]{"%" + remakrs + "%"});
        }
        sqLiteDatabase.close();
    }

    public void deleteTransaction(String remakrs) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME_2, REMARKS + " LIKE ?", new String[]{"%" + remakrs + "%"});
        sqLiteDatabase.close();
    }
    public boolean storeNewUserData(String user_phone_no, String user_name, String user_passcode, String user_business_name, String user_location, byte[] user_image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2_1, user_phone_no);
        cv.put(COL_3_1, user_name);
        cv.put(COL_4_1, user_passcode);
        cv.put(COL_5_1, user_business_name);
        cv.put(COL_6_1, user_location);
        cv.put(COL_8_1, 1);
        cv.put(COL_9_1, user_image);
        long result = db.insert(TABLE_NAME_1, null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean storeUpdateUserData(String user_id, String user_name, String user_business_name, String user_location, byte[] user_image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_3_1, user_name);
        cv.put(COL_5_1, user_business_name);
        cv.put(COL_6_1, user_location);
        cv.put(COL_8_1, 1);
        cv.put(COL_9_1, user_image);
        String where = COL_1_1 + "=?";
        String[] whereArgs = new String[]{String.valueOf(user_id)};
        long result = db.update(TABLE_NAME_1, cv, where, whereArgs);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean storeUpdateUserPasscode(String user_phone_no, String user_passcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_4_1, user_passcode);
        String where = COL_2_1 + "=? AND " + COL_8_1 + "=?";
        String[] whereArgs = new String[]{String.valueOf(user_phone_no), String.valueOf(1)};
        long result = db.update(TABLE_NAME_1, cv, where, whereArgs);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor check_usernumber_exist(String user_phone_no, int status) {
        String query = "SELECT * FROM " + TABLE_NAME_1 + " WHERE " + COL_2_1 + " =" + user_phone_no + " AND " + COL_8_1 + " = " + status;
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();

        if (db != null) {
            cursor = db.rawQuery(query, null);
            return cursor;
        } else {
            return cursor;
        }
    }

    public int debit_transaction_amount(String Sender_id) {
        int amount = 0;
        String query = "SELECT SUM(" + AMOUNT + ") FROM " + TABLE_NAME_2 + " WHERE " + SENDER_NAME + "=" + Sender_id;
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null) {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                if (cursor.getString(0) != null) {
                    amount = Integer.parseInt(cursor.getString(0));
                }
            }
        }
        return amount;
    }

    public int credit_transaction_amount(String Sender_id) {
        int amount = 0;
        String query = "SELECT SUM(" + AMOUNT + ") FROM " + TABLE_NAME_2 + " WHERE " + RECEIVER_NAME + "=" + Sender_id;
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null) {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                if (cursor.getString(0) != null) {
                    amount = Integer.parseInt(cursor.getString(0));
                }
            }
        }
        return amount;
    }

    public Cursor all_transaction(String Sender_id) {
        int status = 2;
        String Query = "SELECT " + TABLE_NAME_1 + "." + COL_1_1 + "," + TABLE_NAME_1 + "." + COL_2_1 + "," + TABLE_NAME_1 + "." + COL_3_1 + "," + TABLE_NAME_1 + "." + COL_9_1 + "," + TABLE_NAME_2 + "." + ID + "," + TABLE_NAME_2 + "." + SENDER_NAME + "," + TABLE_NAME_2 + "." + RECEIVER_NAME + "," + TABLE_NAME_2 + "." + AMOUNT + "," + TABLE_NAME_2 + "." + CREATED_TIME + "," + TABLE_NAME_2 + "." + ID + " FROM " + TABLE_NAME_1 + "," + TABLE_NAME_2 + " WHERE " + TABLE_NAME_2 + "." + STATUS + "!=" + status + " AND (" + TABLE_NAME_2 + "." + SENDER_NAME + "=" + Sender_id + " OR " + TABLE_NAME_2 + "." + RECEIVER_NAME + "=" + Sender_id + ") AND (" + TABLE_NAME_1 + "." + COL_1_1 + "=" + TABLE_NAME_2 + "." + SENDER_NAME + " OR " + TABLE_NAME_1 + "." + COL_1_1 + " = " + TABLE_NAME_2 + "." + RECEIVER_NAME + ") AND " + TABLE_NAME_1 + "." + COL_1_1 + " != " + Sender_id + " ORDER BY " + TABLE_NAME_2 + "." + ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(Query, null);
        }
        return cursor;
    }

    public Cursor credit_transaction(String Sender_id) {
        int status = 2;
        String Query = "SELECT " + TABLE_NAME_1 + "." + COL_1_1 + "," + TABLE_NAME_1 + "." + COL_2_1 + "," + TABLE_NAME_1 + "." + COL_3_1 + "," + TABLE_NAME_1 + "." + COL_9_1 + "," + TABLE_NAME_2 + "." + ID + "," + TABLE_NAME_2 + "." + SENDER_NAME + "," + TABLE_NAME_2 + "." + RECEIVER_NAME + "," + TABLE_NAME_2 + "." + AMOUNT + "," + TABLE_NAME_2 + "." + CREATED_TIME + "," + TABLE_NAME_2 + "." + ID + " FROM " + TABLE_NAME_1 + "," + TABLE_NAME_2 + " WHERE " + TABLE_NAME_2 + "." + STATUS + "!=" + status + " AND  (" + TABLE_NAME_2 + "." + RECEIVER_NAME + "=" + Sender_id + ") AND (" + TABLE_NAME_1 + "." + COL_1_1 + "=" + TABLE_NAME_2 + "." + SENDER_NAME + ")" + " ORDER BY " + TABLE_NAME_2 + "." + ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(Query, null);
        }
        return cursor;
    }

    public Cursor debit_transaction(String Sender_id) {
        int status = 2;
        String Query = "SELECT " + TABLE_NAME_1 + "." + COL_1_1 + "," + TABLE_NAME_1 + "." + COL_2_1 + "," + TABLE_NAME_1 + "." + COL_3_1 + "," + TABLE_NAME_1 + "." + COL_9_1 + "," + TABLE_NAME_2 + "." + ID + "," + TABLE_NAME_2 + "." + SENDER_NAME + "," + TABLE_NAME_2 + "." + RECEIVER_NAME + "," + TABLE_NAME_2 + "." + AMOUNT + "," + TABLE_NAME_2 + "." + CREATED_TIME + "," + TABLE_NAME_2 + "." + ID + " FROM " + TABLE_NAME_1 + "," + TABLE_NAME_2 + " WHERE " + TABLE_NAME_2 + "." + STATUS + "!=" + status + " AND (" + TABLE_NAME_2 + "." + SENDER_NAME + "=" + Sender_id + ") AND (" + TABLE_NAME_1 + "." + COL_1_1 + " = " + TABLE_NAME_2 + "." + RECEIVER_NAME + ")" + " ORDER BY " + TABLE_NAME_2 + "." + ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(Query, null);
        }
        return cursor;
    }

    public Cursor customer_list_credit_transaction(String Sender_id) {
        String Query = "SELECT " + TABLE_NAME_1 + "." + COL_1_3 + " , " + TABLE_NAME_1 + "." + COL_3_1 + " , " + TABLE_NAME_1 + "." + COL_2_1 + ", " + TABLE_NAME_1 + "." + COL_9_1 + " , SUM(" + TABLE_NAME_2 + "." + AMOUNT + ") FROM " + TABLE_NAME_1 + " , " + TABLE_NAME_2 + " , " + TABLE_NAME_3 + " WHERE " + TABLE_NAME_3 + "." + COL_2_3 + " = " + Sender_id + " AND " + TABLE_NAME_1 + "." + COL_1_3 + " = " + TABLE_NAME_3 + "." + COL_3_3 + " AND (" + TABLE_NAME_2 + "." + SENDER_NAME + " = " + TABLE_NAME_3 + "." + COL_3_3 + " AND " + TABLE_NAME_2 + "." + RECEIVER_NAME + " = " + Sender_id + ") GROUP BY " + TABLE_NAME_1 + "." + COL_1_3 + " ORDER BY " + TABLE_NAME_1 + "." + COL_1_1 + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(Query, null);
        }
        return cursor;
    }

    public Cursor customer_list_debit_transaction(String Sender_id) {
        String Query = "SELECT " + TABLE_NAME_1 + "." + COL_1_3 + " , " + TABLE_NAME_1 + "." + COL_3_1 + " , " + TABLE_NAME_1 + "." + COL_2_1 + ", " + TABLE_NAME_1 + "." + COL_9_1 + " , SUM(" + TABLE_NAME_2 + "." + AMOUNT + ") FROM " + TABLE_NAME_1 + " , " + TABLE_NAME_2 + " , " + TABLE_NAME_3 + " WHERE " + TABLE_NAME_3 + "." + COL_2_3 + " = " + Sender_id + " AND " + TABLE_NAME_1 + "." + COL_1_3 + " = " + TABLE_NAME_3 + "." + COL_3_3 + " AND (" + TABLE_NAME_2 + "." + SENDER_NAME + " = " + Sender_id + " AND " + TABLE_NAME_2 + "." + RECEIVER_NAME + " = " + TABLE_NAME_3 + "." + COL_3_3 + ") GROUP BY " + TABLE_NAME_1 + "." + COL_1_3 + " ORDER BY " + TABLE_NAME_1 + "." + COL_1_1 + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(Query, null);
        }
        return cursor;
    }


    public boolean storeNewFriendUserData(String user_id, String friend_phone_number, String friend_name, byte[] user_image) {
        String friend_id = null;

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db1 = this.getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_2_1, friend_phone_number);
        cv.put(COL_3_1, friend_name);
        cv.put(COL_8_1, 2);
        cv.put(COL_9_1, user_image);
        db.insert(TABLE_NAME_1, null, cv);

        String Query = "SELECT * FROM " + TABLE_NAME_1 + " WHERE " + COL_2_1 + " = " + friend_phone_number + " AND " + COL_8_1 + "=2";
        Cursor cursor = db1.rawQuery(Query, null);

        while (cursor.moveToNext()) {
            friend_id = cursor.getString(0);
        }
        if (storeNewExistingFriendUserData(Integer.parseInt(user_id), Integer.parseInt(friend_id))) {
            return true;
        } else {
            return false;
        }

    }

    public boolean storeNewDebitTransaction(String user_id, String friend_id, String transaction_balance, String transaction_reason, String transaction_date) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(SENDER_NAME, user_id);
        cv.put(RECEIVER_NAME, friend_id);
        cv.put(AMOUNT, transaction_balance);
        cv.put(STATUS, 1);
        cv.put(REMARKS, transaction_reason);
        cv.put(DATE, transaction_date);

        long result = db.insert(TABLE_NAME_2, null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }



    Cursor check_friend_exist(String user_id, String friend_phone_number) {
        String query = "SELECT * FROM " + TABLE_NAME_1 + " , " + TABLE_NAME_3 + " WHERE " + TABLE_NAME_3 + "." + COL_2_3 + " = " + user_id + " AND " + TABLE_NAME_1 + "." + COL_2_1 + " = " + friend_phone_number + " AND " + TABLE_NAME_1 + "." + COL_1_1 + "=" + TABLE_NAME_3 + "." + COL_3_3;
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor get_user_details(String user_id) {
        String Query = "SELECT " + COL_2_1 + "," + COL_3_1 + "," + COL_5_1 + "," + COL_6_1 + "," + COL_9_1 + " FROM " + TABLE_NAME_1 + " WHERE " + COL_1_1 + "=" + user_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery(Query, null);
        return cursor;
    }

    Cursor get_user_id(String user_phone_number) {
        String Query = "SELECT " + COL_1_1 + " FROM " + TABLE_NAME_1 + " WHERE " + COL_2_1 + "=" + user_phone_number + " AND " + COL_8_1 + "=1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery(Query, null);
        return cursor;
    }

    Cursor get_transaction_details(String transaction_id) {
        String Query = "SELECT * FROM " + TABLE_NAME_2 + " WHERE " + ID + "=" + transaction_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery(Query, null);
        return cursor;
    }


}
