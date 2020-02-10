package com.example.android.my_note;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by MY on 3/26/2018.
 */
public class SaveNotes extends SQLiteOpenHelper {
    static final String DATABASE_NAME="MyNote.db";
    static final String TABLE_NAME="NoteBook";
    static final String COLUMN_NAME="YourNoteBook";
    static final String COLUMN_ID="Id";
    static final String COLUMN_DATE="date";
    static final int VERSION=1;

    public SaveNotes(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE = "CREATE TABLE " +  TABLE_NAME  + " (" +
                COLUMN_ID  + " INTEGER  PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME+ " TEXT NOT NULL," +
                COLUMN_DATE+ " TEXT NOT NULL "+");";
                db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          onCreate(db);
    }

}
