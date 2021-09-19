package com.starrynight.tourapiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.starrynight.tourapiproject.signUpPage.SignUpActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {
    Long userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getLogin()){
            Log.d("Login","유저 정보 있음");
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("userId",userId);
            startActivity(intent);
            finish();
        }else{
            Log.d("Login","유저 정보 없음");
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            intent.putExtra("userId",userId);
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