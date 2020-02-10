package com.example.android.my_note;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewNoteActivity extends AppCompatActivity  {
 ListView listView;
 SaveNotes saveNotes;
 ArrayList<String>Notes;
 SQLiteDatabase sql;
 String query=null,name,date;
 Cursor cr;
 ArrayAdapter adapter;
 Button deleteAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);
        listView=(ListView) findViewById(R.id.list_id);
        deleteAll=(Button)findViewById(R.id.delete_all_button);
        saveNotes=new SaveNotes(this);
        sql=saveNotes.getReadableDatabase();
        query="SELECT * FROM "+SaveNotes.TABLE_NAME;
        Notes=new ArrayList<>();
        cr=sql.query(SaveNotes.TABLE_NAME,null,null,null,null,null,null);
        if(cr.moveToFirst())
        {
            do {
                 name=cr.getString(cr.getColumnIndexOrThrow(SaveNotes.COLUMN_NAME));
                Notes.add(name);
                date=cr.getString(cr.getColumnIndexOrThrow(SaveNotes.COLUMN_DATE));
            }
            while (cr.moveToNext());
            adapter=new ArrayAdapter(this,R.layout.row,R.id.text_view_id,Notes);
            listView.setAdapter(adapter);

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String data=Notes.get(position);
                    Intent in = new Intent(ViewNoteActivity.this, ShowNoteActivity.class);
                    in.putExtra("Name", data);
                    in.putExtra("key",date);
                    startActivity(in);
                    finish();
            }
        });
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotes=new SaveNotes(ViewNoteActivity.this);
                SQLiteDatabase sql=saveNotes.getReadableDatabase();
                sql.delete(SaveNotes.TABLE_NAME,null,null);
                Intent in=new Intent(ViewNoteActivity.this,MainActivity.class);
                startActivity(in);
                finish();
            }
        });
   }
}
