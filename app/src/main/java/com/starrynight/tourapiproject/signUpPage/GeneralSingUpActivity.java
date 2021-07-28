package com.starrynight.tourapiproject.signUpPage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.auth.User;
import com.squareup.okhttp.ResponseBody;
import com.starrynight.tourapiproject.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class GeneralSingUpActivity extends AppCompatActivity{
    private TextView birth;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private EditText passwordCheck;
    private Boolean isIdDuplicate = true;
    private Boolean isPwdSame = false;

    String realName, birthDay, email, loginId, password;
    Boolean sex;

    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH) + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_sing_up);

        //생년월일
        birth = (TextView)findViewById(R.id.birthDay);
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear += 1;
                birth.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        };

        //중복 id 체크를 위한 get api
        Button duplicationCheck = findViewById(R.id.duplicationCheck);
        duplicationCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = (EditText) findViewById(R.id.loginId);
                String loginId = editText.getText().toString();
                String url0 = "http://192.168.1.93:8080/v1/user/duplicate/"+loginId;
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
                                isIdDuplicate = false;
                            }else{
                                isDuplicate.setText("사용불가능한 아이디 입니다.");
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

        //비밀번호 확인이 비밀번호랑 같은지
        EditText editPassword = findViewById(R.id.password);
        password = editPassword.getText().toString();

        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable arg0) {
                password = arg0.toString();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

        passwordCheck = findViewById(R.id.passwordCheck);
        TextView isPwdCorrect = findViewById(R.id.isPwdCorrect);

        passwordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력란에 변화가 있을 때
                if (!password.equals(s)){
                    isPwdCorrect.setText("일치하지 않는 비밀번호입니다.");}
                else{isPwdCorrect.setText("일치하는 비밀번호입니다.");
                    isPwdSame = true;}
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                if (!password.equals(arg0.toString())){
                    isPwdCorrect.setText("일치하지 않는 비밀번호입니다.");}
                else{isPwdCorrect.setText("일치하는 비밀번호입니다.");
                    isPwdSame = true;}
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });


        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realName = ((EditText) (findViewById(R.id.realName))).getText().toString();
                if(realName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton male = findViewById(R.id.male);
                RadioButton female = findViewById(R.id.female);
                if (male.isChecked()){
                    sex = true;
                } else if(female.isChecked()){
                    sex = false;
                } else{
                    Toast.makeText(getApplicationContext(), "성별을 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                birthDay = ((TextView) (findViewById(R.id.birthDay))).getText().toString();
                if(birthDay.isEmpty()){
                    Toast.makeText(getApplicationContext(), "생년월일을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                email = ((EditText) (findViewById(R.id.email))).getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginId = ((EditText) (findViewById(R.id.loginId))).getText().toString();
                if(loginId.isEmpty()){
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(isIdDuplicate){
                    Toast.makeText(getApplicationContext(), "아이디 중복확인이 필요합니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                password = ((EditText) (findViewById(R.id.password))).getText().toString();
                if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!isCorrectPwdRule(password)) {
                    Toast.makeText(getApplicationContext(), "규칙에 맞지 않는 비밀번호입니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!isPwdSame){
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                //회원가입 post api
                UserParams userParams = new UserParams(realName, sex, birthDay, email, loginId, password);
                Call<Void> call = RetrofitClient.getApiService().signUp(userParams);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            System.out.println("회원가입 성공");

                            Intent intent = new Intent(GeneralSingUpActivity.this, PhoneAuthActivity.class);
                            startActivity(intent);
                        } else{
                            System.out.println("회원가입 실패");
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });

            }
        });

    }

    //비밀번호 규칙 함수
    private Boolean isCorrectPwdRule(String pwd) {
        String pattern = "^.*(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!\\\"\\[\\]\\\\\\/#$%&'()*+,.;:<=>?@^_`{|}^`_~-]).*$";
        return Pattern.matches(pattern, pwd);
    }

    //생년월일 datePicker
    public void onClickBirthPicker(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, callbackMethod, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.show();
    }

}