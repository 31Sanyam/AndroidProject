package com.example.sanyam.ftt;

/**
 * Created by Sanyam on 10-08-2017.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public List<String> getResult(String classname) {
        String[] selectionArgs = new String[]{classname};
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Address,InstituteName,ContactNo1,ContactNo2 FROM Tuition where ClassName = ? ",  selectionArgs);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String instituteName = cursor.getString(cursor.getColumnIndex("InstituteName"));
            String address = cursor.getString(cursor.getColumnIndex("Address"));
            String contactNo1 = cursor.getString(cursor.getColumnIndex("ContactNo1"));
            String contactNo2 = cursor.getString(cursor.getColumnIndex("ContactNo2"));
            list.add("InstituteName: " + instituteName + "\n\n"+"Address: " + address +"\n"+"Contact No:"+contactNo1+","+contactNo2);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

   /* public List<String> getSearch(String classname) {
        String[] selectionArgs = new String[]{classname};
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT InstituteName FROM Tuition where ClassName = ? ",  selectionArgs);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }*/
}