package com.bitp3453.miniproject.mygpacgpatracker;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyGPAdb extends SQLiteOpenHelper {

    public static final String dbName = "dbMyGPA";
    public static final String tblName = "dbmygpa";
    public static final String tblName2 = "dbmysubj";
    public static final String colGpaId = "gpa_id";
    public static final String colGpaSem = "gpa_sem";
    public static final String colGpaTotal = "gpa_total";
    public static final String colSubjId = "subj_id";
    public static final String colSubjName = "subj_name";
    public static final String colSubjGred = "subj_gred";
    public static final String colSubjCredit = "subj_credit";
    public static final String colSubjSem = "subj_sem";

    public MyGPAdb(Context context)
    {
        super(context,dbName,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS dbmygpa(gpa_id INTEGER PRIMARY KEY,gpa_sem VARCHAR,gpa_total REAL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS dbmysubj(subj_id INTEGER PRIMARY KEY,subj_name VARCHAR,subj_gred VARCHAR, " +
                "subj_credit INTEGER, subj_sem VARCHAR, FOREIGN KEY (subj_sem) REFERENCES dbmygpa(gpa_sem));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS dbmygpa");
        db.execSQL("DROP TABLE IF EXISTS dbmysubj");
        onCreate(db);
    }

    public Cursor getDataById(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+tblName+" WHERE "+colGpaId+" = "+id,null);

        return cur;
    }

    public void fnExecuteSql(String strSql, Context appContext)
    {
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL(strSql);
        } catch (Exception e){
            Log.d("Unable to run query", "Error!");
        }
    }

    public int fnTotalRow()
    {
        int intRow;
        SQLiteDatabase db = this.getReadableDatabase();
        intRow = (int) DatabaseUtils.queryNumEntries(db, tblName);
        return intRow;
    }

    public int fnTotalRow2()
    {
        int intRow;
        SQLiteDatabase db = this.getReadableDatabase();
        intRow = (int) DatabaseUtils.queryNumEntries(db, tblName2);
        return intRow;
    }

}
