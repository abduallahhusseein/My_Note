package com.example.android.my_note;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.Permission;

public class ThreadActivity extends AppCompatActivity {
     Button download;
     ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        download=(Button) findViewById(R.id.download);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Download d=new Download();
                //to take permission of mainfiest
                int result= ActivityCompat.checkSelfPermission(ThreadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(result== PackageManager.PERMISSION_GRANTED)
                {
                   d.execute("http://programmerguru.com/android-tutorial/wp-content/uploads/2014/01/jai_ho.mp3");
                }
                else
                {
                    ActivityCompat.requestPermissions(ThreadActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
            }
        });

    }
    class Download extends AsyncTask<String,Void,Void>
    {

        //...mean array of what you are using
        @Override
        protected Void doInBackground(String... S) {
            try {
                 int line;
                 String link=S[0];
                //any connection  we must write these lines
                URL url= new URL(link);
                URLConnection urlConnection=url.openConnection();
                urlConnection.connect();
                InputStream input=url.openStream();
                //write file which environment is external  storage +type of what we download
                FileOutputStream out=new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/song.mp3");
                while ((line=input.read())!=-1){
                    out.write(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==1)
        {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Would You Like To Download ?", Toast.LENGTH_SHORT).show();
                Download d=new Download();
                d.execute("http://programmerguru.com/android-tutorial/wp-content/uploads/2014/01/jai_ho.mp3");
            }
        }
        else
        {
            Toast.makeText(this, "Sorry You Can't", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
