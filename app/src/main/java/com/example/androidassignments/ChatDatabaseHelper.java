package com.example.androidassignments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME = "message1.db";
    static int VERSION_NUM =2;
    final static String KEY_ID ="Id";
    final static String KEY_MESSAGE="Message";
    public static final String TABLE_Name = "messages1";


    public ChatDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            String query = "CREATE TABLE IF NOT EXISTS " +TABLE_Name+"("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_MESSAGE+ " TEXT NOT NULL)";
            sqLiteDatabase.execSQL(query);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Error","DB not mde");
        }

        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_Name);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + i + " newVersion=" + i1);
        onCreate(sqLiteDatabase);
    }
}
