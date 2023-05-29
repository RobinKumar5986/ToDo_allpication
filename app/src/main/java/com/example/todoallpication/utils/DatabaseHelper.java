package com.example.todoallpication.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todoallpication.Models.ToDoModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    private static final  String DATABASE_NAME="TODO_DATABASE";
    private static final  String TABLE_NAME="TODO_TABLE";
    private static final  String COL_1="ID";
    private static final  String COL_2="TASK";
    private static final  String COL_3="STATUS";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+TABLE_NAME +"(ID integer primary key autoincrement " +
                ",TASK TEXT, STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }

    public void insertTask(ToDoModel model){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_2,model.getTask());
        values.put(COL_3,0);
        db.insert(TABLE_NAME,null,values);
    }

    public void updateTask(int id ,String task){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_2,task);
        db.update(TABLE_NAME,values,"ID=?", new String[]{id+""});
    }
    public void updateStatus(int id,int status){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_3,status);
        db.update(TABLE_NAME,values,"ID=?", new String[]{id+""});
    }
    public void deleteTask(int id){
        db=this.getWritableDatabase();
        db.delete(TABLE_NAME,"ID=?",new String[]{id+""});
    }

    @SuppressLint("Range")
    public List<ToDoModel> allTask(){
        db=this.getWritableDatabase();
        Cursor cursor=null;
        List<ToDoModel> modelList=new ArrayList<>();

        db.beginTransaction();
        try{
            cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
            if(cursor!=null){
                if(cursor.moveToFirst()){
                    do{
                        ToDoModel model=new ToDoModel();
                        model.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        model.setTask(cursor.getString(cursor.getColumnIndex(COL_2)));
                        model.setStatus(cursor.getInt(cursor.getColumnIndex(COL_3)));

                        modelList.add(model);

                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }
}
