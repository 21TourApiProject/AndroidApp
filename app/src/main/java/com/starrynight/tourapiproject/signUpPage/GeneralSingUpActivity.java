package com.starrynight.tourapiproject.signUpPage;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.signUpPage.signUpRetrofit.RetrofitClient;
import com.starrynight.tourapiproject.signUpPage.signUpRetrofit.UserParams;

import java.util.Calendar;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneralSingUpActivity extends AppCompatActivity{

    private TextView emailGuide; //이메일 칸 바로 밑에 글칸
    private Boolean isEmailEmpty = true; //이메일이 비어있는지
    private Boolean isNotEmail = false; //올바른 이메일 형식이 아닌지
    private Boolean isEmailDuplicate = true; //이메일이 중복인지

    private TextView pwdGuide;
    private Boolean isNotPwd = true; //올바른 비밀번호 형식이 아닌지
    private String passwordCheck;

    private Boolean isError = false; //서버 에러가 발생했는지

    private Button male;
    private Button female;
    private TextView birth;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private Button marketing;
    int sex2;

    String realName, birthDay, email="", password;
    Boolean sex;
    Boolean isMarketing;

    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH) + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_sign_up);
        sex2 = 0;
        isMarketing = false;

        //성별
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sex2 == 0){
                    System.out.println("남 비어있는거 클릭");
                    male.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_male));
                    sex2 = 1;
                }
                else if(sex2 == 2){
                    male.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_male));
                    female.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_female_non));
                    sex2 = 1;
                }
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sex2 == 0){
                    System.out.println("여 비어있는거 클릭");
                    female.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_female));
                    sex2 = 2;
                }
                else if(sex2 == 1){
                    female.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_female));
                    male.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_male_non));
                    sex2 = 2;
                }
            }
        });

        //마케팅 정보 수신 동의
        marketing = findViewById(R.id.marketing);
        marketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isMarketing){
                    marketing.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_marketing));
                    isMarketing = true;
                } else{
                    marketing.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_marketing_non));
                    isMarketing = false;
                }
            }
        });
        TextView marketInfo = findViewById(R.id.marketInfo);
        marketInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //마케팅 페이지로 이동
            }
        });

        //생년월일
        birth = (TextView)findViewById(R.id.birthDay);
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear += 1;
                String month = Integer.toString(monthOfYear);
                String day = Integer.toString(dayOfMonth);

                if (monthOfYear < 10){
                    month = "0"+ Integer.toString(monthOfYear);
                }

                if(dayOfMonth < 10){
                    day = "0"+ Integer.toString(dayOfMonth);
                }

                birth.setText(year + "-" + month + "-" + day);
            }
        };

        EditText emailEdit = findViewById(R.id.email); //이메일 작성칸
        emailGuide = findViewById(R.id.emailGuide);

        //이메일 칸에 글씨가 입력됨에 따라 실시간으로 emailGuide 뜨게
        emailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력란에 변화가 있을 때
                showEmailGuide(s);
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                showEmailGuide(arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

        //비밀번호가 규칙에 맞는지
        EditText editPassword = findViewById(R.id.password);
        password = editPassword.getText().toString();
        pwdGuide = findViewById(R.id.pwdGuide);

        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력란에 변화가 있을 때
                password = s.toString();
                showPwdGuide(password);
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                password = arg0.toString();
                showPwdGuide(password);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

        EditText passwordCheckEdit = findViewById(R.id.passwordCheck);

        passwordCheckEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력란에 변화가 있을 때
                passwordCheck = s.toString();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                passwordCheck = arg0.toString();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

        //다음 버튼 눌렀을 때
        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realName = ((EditText) (findViewById(R.id.realName))).getText().toString();
                if(realName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (sex2 == 1){
                    sex = true;
                } else if(sex2 == 2){
                    sex = false;
                } else{
                    Toast.makeText(getApplicationContext(), "성별을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                birthDay = ((TextView) (findViewById(R.id.birthDay))).getText().toString();
                if(birthDay.isEmpty()){
                    Toast.makeText(getApplicationContext(), "생년월일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(isEmailEmpty){
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(isNotEmail){
                    Toast.makeText(getApplicationContext(), "이메일 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(isEmailDuplicate){
                    Toast.makeText(getApplicationContext(), "이메일 중복확인이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //password = ((EditText) (findViewById(R.id.password))).getText().toString();
                if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (isNotPwd) {
                    Toast.makeText(getApplicationContext(), "비밀번호 형식이 올바르지 않습니다. (특수문자, 영문, 숫자 조합해서 8자 이상)", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!password.equals(passwordCheck)){
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //칸에 적힌 데이터 가지고 전화번호 인증 페이지로 이동
                UserParams userParams = new UserParams(realName, sex, birthDay, email, password, isMarketing);
                Intent intent = new Intent(getApplicationContext(), PhoneAuthActivity.class);
                intent.putExtra("userParams", userParams);
                startActivity(intent);
            }
        });
    }

    private void showPwdGuide(String pwd) {
        if (!isCorrectPwdRule(pwd)) {
            pwdGuide.setText("비밀번호 형식이 올바르지 않습니다. (특수문자, 영문, 숫자 조합해서 8자 이상)");
            isNotPwd = true;
        } else {
            pwdGuide.setText("");
            isNotPwd = false;
        }
    }

    private void showEmailGuide(CharSequence s) {
        String text = s.toString();

        if (text.isEmpty()) {
            emailGuide.setText("이메일을 입력해주세요.");
            isEmailEmpty = true;
        } else if (!isCorrectEmailRule(text)) {
            emailGuide.setText("이메일 형식이 올바르지 않습니다.");
            isEmailEmpty = false;
            isNotEmail = true;
        } else {
            //이메일이 중복인지 아닌지를 위한 get api
            emailGuide.setText("");
            Call<Boolean> call = RetrofitClient.getApiService().checkDuplicateEmail(text);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        Boolean result = response.body();
                        if (result) {
                            emailGuide.setText("사용가능한 이메일입니다.");
                            email = ((EditText) (findViewById(R.id.email))).getText().toString();
                            isEmailEmpty = false;
                            isEmailDuplicate = false;
                            isNotEmail = false;
                        } else if (!result) {
                            emailGuide.setText("이미 가입된 이메일입니다.");
                            isEmailEmpty = false;
                            isEmailDuplicate = false;
                            isNotEmail = true;
                        }
                    } else {
                        System.out.println("중복 체크 실패");
                        emailGuide.setText("오류가 발생했습니다. 다시 시도해주세요.");
                        isError = true;
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e("연결실패", t.getMessage());
                    emailGuide.setText("오류가 발생했습니다. 다시 시도해주세요.");
                    isError = true;
                }
            });
        }
    }

    //비밀번호 규칙 함수
    private Boolean isCorrectPwdRule(String pwd) {
        String pattern = "^.*(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!\\\"\\[\\]\\\\\\/#$%&'()*+,.;:<=>?@^_`{|}^`_~-]).*$";
        return Pattern.matches(pattern, pwd);
    }

    //이메일 규칙 함수
    private Boolean isCorrectEmailRule(String email) {
        String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(pattern, email);
    }

    //생년월일 datePicker
    public void onClickBirthPicker(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, callbackMethod, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.show();
    }

}