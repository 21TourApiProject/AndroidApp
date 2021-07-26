package com.starrynight.tourapiproject.signUpPage;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.starrynight.tourapiproject.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.regex.Pattern;

public class GeneralSingUpActivity extends AppCompatActivity {
    private TextView birth;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private EditText password;
    private EditText passwordCheck;


    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH) + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_sing_up);

        birth = (TextView) findViewById(R.id.birth);
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear += 1;
                birth.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
            }
        };

        password = findViewById(R.id.password);
        passwordCheck = findViewById(R.id.passwordCheck);
        TextView isPwdCorrect = findViewById(R.id.isPwdCorrect);

        passwordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력란에 변화가 있을 때
                if (!password.getText().toString().equals(s)){
                    isPwdCorrect.setText("일치하지 않는 비밀번호입니다.");}
                else{isPwdCorrect.setText("일치하는 비밀번호입니다.");}
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                if (!password.getText().toString().equals(arg0.toString())){
                    isPwdCorrect.setText("일치하지 않는 비밀번호입니다.");}
                else{isPwdCorrect.setText("일치하는 비밀번호입니다.");}
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //입력하기 전
            }
        });


        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(GeneralSingUpActivity.this, SmsCertificationActivity.class);
//                startActivity(intent);
                String pwd = password.getText().toString();
                if (!isCorrectPwdRule(pwd)){
                    System.out.println("is not right.........");
                    return;
                }
                System.out.println("is right!!!!!!!!!!!!!");

//                new Thread() {
//                    public void run() {
//                        String result = null;
//                        try {
//                            URL url = new URL("http://172.30.1.21:8080/v1/user/post");
//                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                            conn.setRequestMethod("POST");
//                            InputStream is = conn.getInputStream();
//
//                            StringBuilder builder = new StringBuilder();
//                            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//                            String line;
//                            while ((line = reader.readLine()) != null) {
//                                builder.append(line);
//                            }
//
//                            result = builder.toString();
//                            System.out.println("result = " + result);
//
//                        }
//                        catch (Exception e) {
//                            Log.e("REST_API", "POST method failed: " + e.getMessage());
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();
            }
        });

        Button duplicationCheck = findViewById(R.id.duplicationCheck);
        duplicationCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = (EditText) findViewById(R.id.loginId);
                String loginId = editText.getText().toString();
                String url0 = "http://172.30.1.21:8080/v1/user/duplicate/"+loginId;
                System.out.println("url0 = " + url0);

                new Thread() {
                    public void run() {
                        String result = null;
                        TextView isDuplicate = findViewById(R.id.isDuplicate);
                        try {
                            URL url = new URL(url0);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            InputStream is = conn.getInputStream();

                            StringBuilder builder = new StringBuilder();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                builder.append(line);
                            }

                            result = builder.toString();
                            System.out.println("result = " + result);

                            if (result.equals("true")) {
                                isDuplicate.setText("사용가능한 아이디 입니다.");
                            }else{
                                isDuplicate.setText("사용 불가능한 아이디 입니다.");
                            }

                        }
                        catch (Exception e) {
                            Log.e("REST_API", "GET method failed: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });



    }

    private Boolean isCorrectPwdRule(String pwd) {
        String pattern = "^.*(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^*&()+=]).*$";
        return Pattern.matches(pattern, pwd);
    }

    public void onClickBirthPicker(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, callbackMethod, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.show();

    }

}