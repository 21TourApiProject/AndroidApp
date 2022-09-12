package com.starrynight.tourapiproject.signUpPage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

/**
 * @className : GeneralSingUpActivity.java
 * @description : 일반 회원가입 페이지 입니다.
 * @modification : 2022-09-07(sein) 수정
 * @author : sein
 * @date : 2022-09-07
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-09-07     sein        주석 생성

 */
public class GeneralSingUpActivity extends AppCompatActivity {

    private static final String TAG = "GeneralSingUp";

    private TextView emailGuide; //이메일 가이드
    private Boolean isEmailEmpty = true; //이메일이 비어있는지
    private Boolean isNotEmail = false; //올바른 이메일 형식이 아닌지
    private Boolean isEmailDuplicate = true; //이메일이 중복인지

    private TextView pwdGuide;
    private Boolean isNotPwd = true; //올바른 비밀번호 형식이 아닌지
    private String passwordCheck;

    private Button male;
    private Button female;
    private Button noSex;
    int sex2;
    Boolean noSex2;

    private TextView birth;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    private Button ageLimit;
    private Button service;
    private Button personal;
    private Button locationService;
    private Button marketing;
    private Button allAgree;
    Boolean isAge;
    Boolean isService;
    Boolean isPersonal;
    Boolean isLocationService;
    Boolean isMarketing;
    Boolean isAllAgree;

    String realName, birthDay, email = "", password;
    Boolean sex;

    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH) + 1;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_sign_up);

        sex2 = 0;
        noSex2 = false;
        isAge = false;
        isService = false;
        isPersonal = false;
        isLocationService = false;
        isMarketing = false;
        isAllAgree = false;

        ImageView generalBack = findViewById(R.id.generalBack);
        generalBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //성별
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        noSex = findViewById(R.id.noSex);
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noSex2) {
                    noSex.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_nosex_non));
                    male.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_male));
                    noSex2 = false;
                    sex2 = 1;
                } else {
                    switch (sex2) {
                        case (0):
                            male.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_male));
                            sex2 = 1;
                            break;
                        case (1):
                            break;
                        case (2):
                            male.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_male));
                            female.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_female_non));
                            sex2 = 1;
                            break;
                    }
                }
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noSex2) {
                    noSex.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_nosex_non));
                    female.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_female));
                    noSex2 = false;
                    sex2 = 2;
                } else {
                    switch (sex2) {
                        case (0):
                            female.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_female));
                            sex2 = 2;
                            break;
                        case (1):
                            female.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_female));
                            male.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_male_non));
                            sex2 = 2;
                            break;
                        case (2):
                            break;
                    }
                }
            }
        });
        noSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!noSex2) {
                    noSex.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_nosex));
                    male.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_male_non));
                    female.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_female_non));
                    sex2 = 0;
                    noSex2 = true;
                }
            }
        });

        //만 14세 이상
        ageLimit = findViewById(R.id.ageLimit);
        ageLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllAgree) {
                    ageLimit.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    allAgree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    isAge = false;
                    isAllAgree = false;
                } else if (isAge) {
                    ageLimit.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    isAge = false;
                } else {
                    ageLimit.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree));
                    isAge = true;
                }
            }
        });

        //서비스 동의
        service = findViewById(R.id.service);
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllAgree) {
                    service.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    allAgree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    isService = false;
                    isAllAgree = false;
                } else if (isService) {
                    service.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    isService = false;
                } else {
                    service.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree));
                    isService = true;
                }
            }
        });
        TextView serviceInfo = findViewById(R.id.serviceInfo);
        serviceInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://luxuriant-router-7fe.notion.site/d74df94b11ce4e8f8592a77425fd403b"));
                startActivity(intent);
            }
        });

        //개인정보 동의
        personal = findViewById(R.id.personal);
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllAgree) {
                    personal.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    allAgree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    isPersonal = false;
                    isAllAgree = false;
                } else if (isPersonal) {
                    personal.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    isPersonal = false;
                } else {
                    personal.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree));
                    isPersonal = true;
                }
            }
        });
        TextView personalInfo = findViewById(R.id.personalInfo);
        personalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://luxuriant-router-7fe.notion.site/3bae231c3cb34a6e9ce6585df8b96233"));
                startActivity(intent);
            }
        });

        //위치정보 동의
        locationService = findViewById(R.id.locationService);
        locationService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllAgree) {
                    locationService.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    allAgree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    isLocationService = false;
                    isAllAgree = false;
                } else if (isLocationService) {
                    locationService.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    isLocationService = false;
                } else {
                    locationService.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree));
                    isLocationService = true;
                }
            }
        });
        TextView locationServiceInfo = findViewById(R.id.locationServiceInfo);
        locationServiceInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://luxuriant-router-7fe.notion.site/f3305181536d41a9997961f6516d57ac"));
                startActivity(intent);
            }
        });

        //마케팅 정보 수신 동의
        marketing = findViewById(R.id.marketing);
        marketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllAgree) {
                    marketing.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    allAgree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    isMarketing = false;
                    isAllAgree = false;
                } else if (isMarketing) {
                    marketing.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    isMarketing = false;
                } else {
                    marketing.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree));
                    isMarketing = true;
                }
            }
        });

        //전체 동의
        allAgree = findViewById(R.id.allAgree);
        allAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllAgree) {
                    allAgree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    isAllAgree = false;
                    ageLimit.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    service.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    personal.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    locationService.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    marketing.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree_non));
                    isAge = false;
                    isService = false;
                    isPersonal = false;
                    isLocationService = false;
                    isMarketing = false;
                } else {
                    allAgree.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree));
                    isAllAgree = true;
                    ageLimit.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree));
                    service.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree));
                    personal.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree));
                    locationService.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree));
                    marketing.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.signup_agree));
                    isAge = true;
                    isService = true;
                    isPersonal = true;
                    isLocationService = true;
                    isMarketing = true;
                }
            }
        });

        //생년월일
        birth = (TextView) findViewById(R.id.birthDay);
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear += 1;
                String month = Integer.toString(monthOfYear);
                String day = Integer.toString(dayOfMonth);

                if (monthOfYear < 10) {
                    month = "0" + monthOfYear;
                }

                if (dayOfMonth < 10) {
                    day = "0" + dayOfMonth;
                }

                birth.setText(year + "/" + month + "/" + day);
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        //다음 버튼 눌렀을 때
        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmailEmpty) {
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (isNotEmail) {
                    Toast.makeText(getApplicationContext(), "이메일 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (isEmailDuplicate) {
                    Toast.makeText(getApplicationContext(), "이메일 중복확인이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //password = ((EditText) (findViewById(R.id.password))).getText().toString();
                if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (isNotPwd) {
                    Toast.makeText(getApplicationContext(), "비밀번호 형식이 올바르지 않습니다. (특수문자, 영문, 숫자 조합해서 8자 이상)", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!password.equals(passwordCheck)) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                realName = ((EditText) (findViewById(R.id.realName))).getText().toString();
                if (realName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (sex2 == 0 && !noSex2) {
                    Toast.makeText(getApplicationContext(), "성별을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sex2 == 1) {
                    sex = true; //남자
                }
                if (sex2 == 2) {
                    sex = false; //여자
                }
                if (noSex2) {
                    sex = null; //성별 없음
                }

                birthDay = ((TextView) (findViewById(R.id.birthDay))).getText().toString();
                if (birthDay.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "생년월일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isAge) {
                    Toast.makeText(getApplicationContext(), "만 14세 미만은 이용하실 수 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isService) {
                    Toast.makeText(getApplicationContext(), "서비스 이용약관 동의는 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isPersonal) {
                    Toast.makeText(getApplicationContext(), "개인정보 수집 및 이용동의는 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isLocationService) {
                    Toast.makeText(getApplicationContext(), "위치서비스 이용 동의는 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //칸에 적힌 데이터 가지고 전화번호 인증 페이지로 이동
                UserParams userParams = new UserParams(realName, sex, birthDay, email, password, isMarketing, false);
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
                        Log.e(TAG, "중복 체크 실패");
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
    public void onClickBirthPicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog_NoActionBar, callbackMethod, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.show();
    }

}