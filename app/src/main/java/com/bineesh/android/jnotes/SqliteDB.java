package com.bineesh.android.jnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SqliteDB extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "notes",
            TITTLE_COL = "tittle",
            NOTES_COL = "note",
            CREATED_COL = "cd",
            LAST_COL = "lud",
            DB_NAME = "notes_db";

    public SqliteDB(Context context){
        super(context,DB_NAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+"(note_id integer PRIMARY KEY AUTOINCREMENT,"+TITTLE_COL+" text,"+NOTES_COL+" text,"+CREATED_COL+" text,"+LAST_COL+" text);");
   }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addNewNote(NoteModel noteModel){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITTLE_COL,noteModel.getTittle());
        values.put(NOTES_COL,noteModel.getNote());
        values.put(CREATED_COL,noteModel.getCreatedDate());
        values.put(LAST_COL,noteModel.getLastUpdatedDate());

        sqLiteDatabase.insert(TABLE_NAME,null,values);
    }


    public List<NoteModel> getAllNotes(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        cursor.moveToFirst();
        List<NoteModel> noteModelList = new ArrayList<>();
        while(!cursor.isAfterLast()){
            String t = cursor.getString(cursor.getColumnIndexOrThrow(TITTLE_COL));
            String n = cursor.getString(cursor.getColumnIndexOrThrow(NOTES_COL));
            String c = cursor.getString(cursor.getColumnIndexOrThrow(CREATED_COL));
            String l = cursor.getString(cursor.getColumnIndexOrThrow(LAST_COL));
            int i = cursor.getInt(cursor.getColumnIndexOrThrow("note_id"));
            noteModelList.add(new NoteModel(n,t,c,l,i));
            cursor.moveToNext();
        }
        cursor.close();
        return noteModelList;
    }

    public void updateNote(String tittle,String note, int id,String ldate){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTES_COL,note);
        values.put(TITTLE_COL,tittle);
        values.put(LAST_COL,ldate);
        sqLiteDatabase.update(TABLE_NAME,values,"note_id = ?",new String[]{Integer.toString(id)});

    }
}
