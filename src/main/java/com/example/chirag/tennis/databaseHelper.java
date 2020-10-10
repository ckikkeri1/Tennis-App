
package com.example.chirag.tennis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class databaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Playersss.db";
    public static final String TABLE_NAME = "player_table1";
    public static final String COL_1 = "Rank";
    public static final String COL_2 = "FirstN";
    public static final String COL_3 = "LastN";
    public static final String COL_4 = "Year";
    public static final String COL_6 = "ID";
    public static final String COL_5 = "Email";


    public databaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (RANK TEXT, FirstN TEXT, LastN TEXT, Year TEXT, Email TEXT, ID INTEGER PRIMARY KEY AUTOINCREMENT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData (String Rank, String FirstN, String LastN, String Year, String Email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, Rank);
        contentValues.put(COL_2, FirstN);
        contentValues.put(COL_3, LastN);
        contentValues.put(COL_4, Year);
        contentValues.put(COL_5, Email);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }

    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor curse =  db.rawQuery("select * from " + TABLE_NAME, null);
        return curse;
    }

    public boolean updateData (String rank, String FirstN, String LastN, String Year,String Email, String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, rank);
        contentValues.put(COL_2, FirstN);
        contentValues.put(COL_3, LastN);
        contentValues.put(COL_4, Year);
        contentValues.put(COL_5, Email);
        contentValues.put(COL_6, ID+"");
        db.update(TABLE_NAME, contentValues, "ID = ?", new String [] {ID});
        return true;
    }

    public Integer deleteData (String id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "ID = ?", new String [] {id});
    }

    public void createT (){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table " + TABLE_NAME + " (RANK TEXT, FirstN TEXT, LastN TEXT, Year TEXT, ID INTEGER PRIMARY KEY AUTOINCREMENT)");

    }
}
