package com.example.docapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //table 1 for adding patient record history
    public static final String DATABASE_NAME = "medicalrecord.db";
    public static final String TABLE_NAME = "patient_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "DATE";
    public static final String COL3 = "NAME";
    public static final String COL4 = "AGE";
    public static final String COL5 = "BP";
    public static final String COL6 = "SUGAR";
    public static final String COL7 = "TEMPA";
    public static final String COL8 = "KCO";
    public static final String COL9 = "COMPLAINTS";
    public static final String COL10 = "MEDICINE";

    //table 2 for adding medicine list / allopathy medicine
    public static final String TABLE_NAME1 = "medicine_table";
    public static final String COL11 = "NAME";
    public static final String COL22 = "QTY";

    //table 3 for balance history
    public static final String TABLE_NAME2 = "balance_history";
    public static final String COLDATE = "DATE";
    public static final String COLNAME = "NAME";
    public static final String COLBAL = "BALANCE";
    public static final String COLAGE = "AGE";

    //table 4 for unani medicine stocks
    public static final String TABLE_NAME3 = "unani_medicine";
    public static final String COLUNNAME = "NAME";
    public static final String COLUNQTY  = "QTY";







    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE TEXT,NAME TEXT,AGE INTEGER,BP TEXT,SUGAR TEXT,TEMPA TEXT,KCO TEXT,COMPLAINTS TEXT,MEDICINE TEXT)");
        db.execSQL("create table " + TABLE_NAME1 +" (NAME TEXT, QTY INTEGER)"); //TABLE 2
        db.execSQL("create table "+ TABLE_NAME2 +" (DATE TEXT, NAME TEXT, BALANCE INTEGER, AGE INTEGER)"); //TABLE 3
        db.execSQL("create table " + TABLE_NAME3 +" (NAME TEXT, QTY INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME); //TABLE 1
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1); //TABLE 2
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2); //TABLE 3
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3); //TABLE 4
        onCreate(db);

    }

    public boolean insertData(String date,String name, String age, String bp, String sugar, String temp,String kco, String complaints, String medicine){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,date);
        contentValues.put(COL3,name);
        contentValues.put(COL4,age);
        if(bp.length() == 0){
            contentValues.put(COL5,"--");
        }else{
            contentValues.put(COL5,bp);
        }
        if(sugar.length() == 0){
            contentValues.put(COL6,"--");
        }else{
            contentValues.put(COL6,sugar);
        }
        if(temp.length() == 0){
        contentValues.put(COL7,"--");
        }else {
            contentValues.put(COL7,temp);
        }
        if(kco.length() == 0){
        contentValues.put(COL8,"--");
        }else {
            contentValues.put(COL8,kco);
        }
        contentValues.put(COL9,complaints);
        contentValues.put(COL10,medicine);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }



    public boolean insertData2(String name, String qty){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL11,name);
        contentValues.put(COL22,qty);
        long result = db.insert(TABLE_NAME1,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertBalance(String date, String name, String balance, String age){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLDATE,date);
        contentValues.put(COLNAME,name);
        contentValues.put(COLBAL,balance);
        contentValues.put(COLAGE,age);
        long result = db.insert(TABLE_NAME2,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    public boolean updateData(String name, String qty){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL11,name);
        contentValues.put(COL22,qty);
        db.update(TABLE_NAME1, contentValues, "NAME = ?", new String[] { name });
        return true;
    }

    //UNANI ADD
    public boolean insertUnaniData(String name, String qty){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUNNAME,name);
        contentValues.put(COLUNQTY,qty);
        long result = db.insert(TABLE_NAME3,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Unani update
    public boolean unani_update(String name, String qty){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUNNAME,name);
        contentValues.put(COLUNQTY,qty);
        db.update(TABLE_NAME3, contentValues, "NAME = ?", new String[] { name });
        return true;
    }






}
