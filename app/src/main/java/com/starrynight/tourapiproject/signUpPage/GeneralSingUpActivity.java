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

import com.starrynight.tourapiproject.R;

import java.util.Calendar;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneralSingUpActivity extends AppCompatActivity{
    private TextView birth;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    private TextView emailGuide; //이메일 칸 바로 밑에 글칸
    private Boolean isEmailEmpty = true; //이메일이 비어있는지
    private Boolean isNotEmail = false; //올바른 이메일 형식이 아닌지
    private Boolean isEmailDuplicate = true; //이메일이 중복인지

    private TextView pwdGuide;
    private Boolean isPwdSame = false; //비밀번호, 비밀번호 확인이 같은지

    String realName, birthDay, email="", password;
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

        //비밀번호 확인이 비밀번호랑 같은지
        EditText editPassword = findViewById(R.id.password);
        password = editPassword.getText().toString();

        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = s.toString();
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                password = arg0.toString();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

        EditText passwordCheck = findViewById(R.id.passwordCheck);
        pwdGuide = findViewById(R.id.pwdGuide);

        passwordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력란에 변화가 있을 때
                showPwdGuide(s);
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                showPwdGuide(arg0);
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

                if(isEmailEmpty){
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(isNotEmail){
                    Toast.makeText(getApplicationContext(), "올바른 이메일이 아닙니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(isEmailDuplicate){
                    Toast.makeText(getApplicationContext(), "이메일 중복확인이 필요합니다", Toast.LENGTH_SHORT).show();
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

                //칸에 적힌 데이터 가지고 전화번호 인증 페이지로 이동
                UserParams userParams = new UserParams(realName, sex, birthDay, email, password);
                Intent intent = new Intent(getApplicationContext(), PhoneAuthActivity.class);
                intent.putExtra("userParams", userParams);
                startActivity(intent);

            }
        });
    }

    private void showPwdGuide(CharSequence s) {
        String text = s.toString();

        if (!password.equals(text)) {
            pwdGuide.setText("일치하지 않는 비밀번호입니다.");
        } else {
            pwdGuide.setText("일치하는 비밀번호입니다.");
            isPwdSame = true;
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
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e("연결실패", t.getMessage());
                    emailGuide.setText("오류가 발생했습니다. 다시 시도해주세요.");
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