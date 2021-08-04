package com.starrynight.tourapiproject.myPage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.starrynight.tourapiproject.R;

import java.util.regex.Pattern;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText originPwd;
    EditText newPwd;
    EditText newPwdCheck;
    TextView newPwdGuide;
    TextView newPwdCheckGuide;

    String pwd;
    String pwdCheck = "";
    Boolean isPwd = false; //형식에 맞는 비밀번호인지
    Boolean isSame = false; //같은 비밀번호인지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        newPwd = findViewById(R.id.newPwd);
        newPwdCheck = findViewById(R.id.newPwdCheck);
        newPwdGuide = findViewById(R.id.newPwdGuide);
        newPwdCheckGuide = findViewById(R.id.newPwdCheckGuide);

        newPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력란에 변화가 있을 때
                pwd = s.toString();
                showPwdGuide(pwd);
                if(!pwdCheck.isEmpty()){
                    showPwdCheckGuide(pwd, pwdCheck);
                }
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                pwd = arg0.toString();
                showPwdGuide(pwd);
                if(!pwdCheck.isEmpty()){
                    showPwdCheckGuide(pwd, pwdCheck);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });


        newPwdCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력란에 변화가 있을 때
                pwdCheck = s.toString();
                showPwdCheckGuide(pwd, pwdCheck);
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                pwdCheck = arg0.toString();
                showPwdCheckGuide(pwd, pwdCheck);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });
    }

    //비밀번호 두개가 맞는지 실시간으로
    private void showPwdCheckGuide(String pwd, String pwdCheck) {
        if (!pwd.equals(pwdCheck)){
            newPwdCheckGuide.setText("비밀번호가 일치하지 않습니다.");
            isSame = false;
        } else {
            newPwdCheckGuide.setText("");
            isSame = true;
        }
    }


    //비밀번호 규칙 맞는지 실시간으로
    private void showPwdGuide(String pwd) {
        if (!isCorrectPwdRule(pwd)) {
            newPwdGuide.setText("비밀번호 형식이 올바르지 않습니다. (특수문자, 영문, 숫자 조합해서 8자 이상)");
            isPwd = false;
        } else {
            newPwdGuide.setText("");
            isPwd = true;
        }
    }

    //비밀번호 규칙 함수
    private Boolean isCorrectPwdRule(String pwd) {
        String pattern = "^.*(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!\\\"\\[\\]\\\\\\/#$%&'()*+,.;:<=>?@^_`{|}^`_~-]).*$";
        return Pattern.matches(pattern, pwd);
    }
}