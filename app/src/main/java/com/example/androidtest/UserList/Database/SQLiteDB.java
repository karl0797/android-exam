package com.example.androidtest.UserList.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.androidtest.GlobalsVariable;
import com.example.androidtest.UserList.Model.HolderInfo;

import java.util.ArrayList;

public class SQLiteDB extends SQLiteOpenHelper {

    public SQLiteDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Phone.db", factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table TBlInfo(ID INTEGER PRIMARY KEY AUTOINCREMENT, fname TEXT, lname TEXT, bday TEXT, age TEXT, email TEXT, mnumber TEXT, address TEXT, cperson TEXT, cpersonnumber TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS TBlInfo;");

        onCreate(db);
    }

    public void insertInfo (String fname, String lname, String bday, String age, String email, String mnumber, String address, String cperson, String cpersonnumber){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT ID FROM TBlInfo WHERE fname = '"+fname+"' AND lname = '"+lname+"' AND bday = '"+bday+"' AND age = '"+age+
                "' AND email = '"+email+"' AND mnumber = '"+mnumber+"' AND address = '"+address+"' AND cperson = '"+cperson+"' AND cpersonnumber = '"+cpersonnumber+"'",null);

        String Exist = null;
        while (cursor.moveToNext()){
            Exist = cursor.getString(0);
        }

        if(Exist == null){
            ContentValues CV = new ContentValues();
            CV.put("fname", fname);
            CV.put("lname", lname);
            CV.put("bday", bday);
            CV.put("age", age);
            CV.put("email", email);
            CV.put("mnumber", mnumber);
            CV.put("address", address);
            CV.put("cperson", cperson);
            CV.put("cpersonnumber", cpersonnumber);
            this.getReadableDatabase().insertOrThrow("TBlInfo","",CV);
        }

        db.close();
    }


    public void List_Info(){
        SQLiteDatabase db = this.getReadableDatabase();
        GlobalsVariable.holderInfo = new ArrayList<>();

        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM TBlInfo",null);
            while (cursor.moveToNext()){
                GlobalsVariable.holderInfo.add(new HolderInfo(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9)));
            }
        db.close();
    }

    public long get_info_count() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, "TBlInfo");
        db.close();
        return count;
    }

}
