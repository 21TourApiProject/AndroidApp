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

        //권한 설정
        int permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission3 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);//denied면 -1

        Log.d("test", "onClick: location clicked");
        if (permission == PackageManager.PERMISSION_GRANTED&&permission2 == PackageManager.PERMISSION_GRANTED&&permission3==PackageManager.PERMISSION_GRANTED) {
            Log.d("MyTag","읽기,쓰기,인터넷 권한이 있습니다.");

        } else if (permission == PackageManager.PERMISSION_DENIED){
            Log.d("test", "permission denied");
            Toast.makeText(getApplicationContext(), "쓰기권한이 없습니다.", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(LoginActivity.this, WRITE_PERMISSION, PERMISSIONS_REQUEST_CODE);
            ActivityCompat.requestPermissions(LoginActivity.this, READ_PERMISSION, PERMISSIONS_REQUEST_CODE);
            ActivityCompat.requestPermissions(LoginActivity.this, INTERNET_PERMISSION, PERMISSIONS_REQUEST_CODE);
        }

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