package com.example.tanvir.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.jar.Attributes;

import static java.util.jar.Attributes.*;

//extending sqliteHelper for accessing Sqlite properties
public class Database extends SQLiteOpenHelper {
    public static final String DatabaseName = "Student_db";
    public static final String Table_name = "Student_table";
    public static final String Col_1 = "Id";
    public static final String Col_2 = "Name";
    public static final String Col_3 = "Surname";
    public static final String Col_4 = "Marks";

    public Database(Context context) {
        super(context, DatabaseName, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }
    //for create sqlite database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table_name + " (Id INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Surname TEXT,Marks INTEGER)");
    }

    //for upgrade sqlite database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_name);
        onCreate(db);
    }
    //function for inserting on sqlite database
    public long insertData(String name, String surname, String marks) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_2, name);
        contentValues.put(Col_3, surname);
        contentValues.put(Col_4, marks);
        long id = sqLiteDatabase.insert(Table_name, null, contentValues);
        return id;
    }
    //for update sqlite database
    public boolean update(String Name,String Surname,String Marks,String id){
        try{
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Col_1,id);
            contentValues.put(Col_2, Name);
            contentValues.put(Col_3, Surname);
            contentValues.put(Col_4, Marks);
            sqLiteDatabase.update(Table_name, contentValues, Col_1+" =?", new String[]{id});
            return true;
        }catch (Exception e){
            return false;
        }

    }
    //function for displaying database values
   public Cursor display() {
       SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+Table_name, null);
        return cursor;
    }
    //function for deletion database row
    public boolean delete(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(Table_name,Col_1+"= ?",new String[]{id});
        return  true;
    }
}
