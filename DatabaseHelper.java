package com.example.sanyam.ftt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Sanyam on 02-08-2017.
 */
class DataBaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "signup.db";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE = "create table " + "SIGNUP" +
            "( " + "ID" + " integer primary key autoincrement," + "USERNAME  text,ADDRESS TEXT,EMAIL TEXT UNIQUE ,MOBILE_NO text UNIQUE,PASSWORD text,CONFIRM_PASSWORD TEXT); ";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when no database exists in disk and the helper class needs
    // to create a new one.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("Sanyam ", "Database Created");
        db.execSQL(DATABASE_CREATE);
        //Toast.makeText(this,"Database Created",Toast.LENGTH_LONG).show();
        //Log.i("Sanyam","Data")

    }

    // Called when there is a database version mismatch meaning that the version
    // of the database on disk needs to be upgraded to the current version.
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
        // Log the version upgrade.
        Log.w("TaskDBAdapter", "Upgrading from version " + _oldVersion + " to " + _newVersion + ", which will destroy all old data");

        // Upgrade the existing database to conform to the new version. Multiple
        // previous versions can be handled by comparing _oldVersion and _newVersion
        // values.
        // The simplest case is to drop the old table and create a new one.
        _db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");
        // Create a new one.
        onCreate(_db);
    }

    public void insertEntry(String userName, String address, String mobileno, String reEnterPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME", userName);
        newValues.put("ADDRESS", address);
        //newValues.put("EMAIL", email);
        newValues.put("MOBILE_NO", mobileno);
        //newValues.put("PASSWORD", password);
        newValues.put("CONFIRM_PASSWORD", reEnterPassword);

        // Insert the row into your table
        db.insert("Signup", null, newValues);
        Log.i("Sanyam", "Record Inserted");
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public int deleteEntry(String UserName) {
        SQLiteDatabase db = this.getWritableDatabase();
        //String id=String.valueOf(ID);
        String where = "USERNAME=?";
        int numberOFEntriesDeleted = db.delete("LOGIN", where, new String[]{UserName});


        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    public String getSinlgeEntry(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }

    public void updateEntry(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("Email", email);
        updatedValues.put("PASSWORD", password);
        String where = "Email = ?";
        db.update("LOGIN", updatedValues, where, new String[]{email});
    }

    public boolean Login(String email, String password) {
        Log.i("Sanyam", "logincalled");
        SQLiteDatabase db = this.getWritableDatabase();
        String[] selectionArgs = new String[]{email, password};
        try {
            int i = 0;
            Cursor c = null;
            c = db.rawQuery("select * from SIGNUP where Email=? and PASSWORD=?", selectionArgs);
            c.moveToFirst();
            int cursorCount = c.getCount();
            Log.i("Sanyam","cursorCount"+cursorCount);
            c.close();
            db.close();
            if (cursorCount > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("Sanyam", "loginEnd");
        return false;

    }
    public boolean Signup(String email, String mobile) {
        Log.i("Sanyam","signupcalled");
        SQLiteDatabase db = this.getWritableDatabase();
        String[] selectionArgs = new String[]{email, mobile};
        try {
            int i = 0;
            Cursor c = null;
            c = db.rawQuery("select * from SIGNUP where Email=? OR MOBILE_NO=?", selectionArgs);
            c.moveToFirst();
            int cursorCount = c.getCount();
            Log.i("Sanyam","cursorCount"+cursorCount);
            c.close();
            db.close();
            if (cursorCount > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("Sanyam","signupEnd");
        return false;

    }
}