package com.example.halimahanafy.aes_halima;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainActivity extends AppCompatActivity {

    String fileName="NTUwNzEyZmFlNGZhNGNhLnppcA.zip";
    String DECRPTED_FILE_PATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Permissions.verifyStoragePermissions(this);

        /*
        Decrypt 256 file
         */
        DECRPTED_FILE_PATH=Decrypt.decryptFile(fileName);

        openDownloadedFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "/zzs.zip"),MainActivity.this);

    }


    public static void openDownloadedFile(File f , Context myContext){
        Log.e("tag","found");
        try {
            if (!f.isDirectory())
                f.mkdir();
            Intent testIntent = new Intent(Intent.ACTION_VIEW);
            testIntent.setType("application/x-wav");
            testIntent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(f);
            testIntent.setDataAndType(uri, "application/x-wav");
            testIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            myContext.startActivity(testIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




