package com.starrynight.tourapiproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.starrynight.tourapiproject.signUpPage.SignUpActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {
    Long userId;
    String[] WRITE_PERMISSION = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String[] READ_PERMISSION = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    String[] INTERNET_PERMISSION = new String[]{Manifest.permission.INTERNET};
    int PERMISSIONS_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getLogin()){
            Log.d("Login","유저 정보 있음"+userId);
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("userId",userId);
            startActivity(intent);
            finish();
        }else{
            Log.d("Login","유저 정보 없음");
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public boolean getLogin(){

        String fileName = "userId";
        try{
            FileInputStream fis = openFileInput(fileName);
            String line = new BufferedReader(new InputStreamReader(fis)).readLine();
            userId = Long.parseLong(line);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (userId!=null){return true;}
        else return false;
    }
}