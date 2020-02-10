package com.example.android.my_note;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button add,show;
   String data;
   SaveNotes saveNotes;
   SQLiteDatabase sql;
   ContentValues cv;
    String date="";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit_text);
        add = (Button) findViewById(R.id.add_button);
        show = (Button) findViewById(R.id.view_button);
        saveNotes=new SaveNotes(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               data=editText.getText().toString();
               sql=saveNotes.getWritableDatabase();
                Calendar c=Calendar.getInstance();
                DateFormat dateFormat =new SimpleDateFormat("EEE,dd MMM yyy,HH:MM");
                date= dateFormat.format(c.getTime());
               cv=new ContentValues();
               cv.put(SaveNotes.COLUMN_NAME,data);
               cv.put(SaveNotes.COLUMN_DATE,date);
               sql.insert(SaveNotes.TABLE_NAME,null,cv);
               editText.setText("");
               Log.v("MainActivity","DataBase Created");
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity.this,ViewNoteActivity.class);
                startActivity(in);
            }
        });
    }
}
