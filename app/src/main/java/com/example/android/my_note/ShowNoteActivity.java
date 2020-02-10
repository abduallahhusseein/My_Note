package com.example.android.my_note;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.Set;

public class ShowNoteActivity extends AppCompatActivity {
     EditText editText;
     TextView textView;
     Button share,edit,delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        editText=(EditText)findViewById(R.id.edit_text_final);
        share   =(Button) findViewById(R.id.share_button);
        edit    =(Button) findViewById(R.id.edit_button);
        delete  =(Button) findViewById(R.id.delete_button);
        textView=(TextView)findViewById(R.id.text_date);

        final Bundle B=getIntent().getExtras();
         if(B!=null)
         {
             String value=B.getString("Name");
             editText.setText(value);
             String date=B.getString("key");
             textView.setText(date);
         }

         share.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String s=editText.getText().toString();
                 Intent in=new Intent();
                 in.setAction(Intent.ACTION_SEND);
                 in.putExtra(Intent.EXTRA_TEXT,s);
                 in.setType("text/plain");
                 startActivity(in);
             }
         });
         edit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String str=editText.getText().toString();
                 SaveNotes saveNotes=new SaveNotes(ShowNoteActivity.this);
                 SQLiteDatabase sql=saveNotes.getWritableDatabase();
                 ContentValues cv=new ContentValues();
                 cv.put(SaveNotes.COLUMN_NAME,str);
                 String value=B.getString("Name");
                 sql.update(SaveNotes.TABLE_NAME,cv,SaveNotes.COLUMN_NAME+"=?",new String[]{value});
                 Intent in=new Intent(ShowNoteActivity.this,MainActivity.class);
                 startActivity(in);
                 finish();
             }
         });
         delete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 SaveNotes saveNotes=new SaveNotes(ShowNoteActivity.this);
                 SQLiteDatabase sql=saveNotes.getWritableDatabase();
                 String value=B.getString("Name");
                 sql.delete(SaveNotes.TABLE_NAME,SaveNotes.COLUMN_NAME+"=?",new String[]{value});
                 Intent in=new Intent(ShowNoteActivity.this,MainActivity.class);
                 startActivity(in);
                 finish();
             }
         });

        }
}
