package com.example.shi.abacusmentalmaths;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    // Database Name
    public static String DATABASE_NAME = "clientDB5.db";
    // Current version of database
    private static final int DATABASE_VERSION = 1;
    // Name of table
    private static final String TABLE_PLAYER = "playerInfo";
    // All fields used in database table
    private static final String KEY_ID = "id";
    private static final String NAME = "name";
    private static final String PHONENUMBER = "phone_number";
    private static final String TESTDATE = "test_date";
    private static final String SCORE = "score";
    //Date date= new Date();
    //int score=100;

    public static String TAG = "my_tag";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table "+TABLE_PLAYER+" ( id integer primary key autoincrement, name text, test_date date, score numeric, phone_number text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLAYER);
        onCreate(db);
    }
    public long addClientDetail(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(NAME, client.name);
        values.put(PHONENUMBER, client.phone_number);
        values.put(TESTDATE, client.test_date);
        values.put(SCORE, client.score);

        // insert row in client table

        long insert = db.insert(TABLE_PLAYER, null, values);
        return insert;
    }
    public List<Client> getAllClientsList(String phone_number) {
        List<Client> clientArrayList = new ArrayList<Client>();

        String selectQuery = "SELECT  * FROM " + TABLE_PLAYER +" where "+PHONENUMBER+"='"+ phone_number +"'";

        Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                Client client = new Client();
                client.id = c.getInt(c.getColumnIndex(KEY_ID));
                client.phone_number = c.getString(c
                        .getColumnIndex(PHONENUMBER));
                client.name = c.getString(c.getColumnIndex(NAME));
                client.test_date = c.getString(c.getColumnIndex(TESTDATE));
                client.score = c.getLong(c.getColumnIndex(SCORE));

                // adding to Clients list
                clientArrayList.add(client);
            } while (c.moveToNext());
        }
        return clientArrayList;
    }
}

