package com.starrynight.tourapiproject.signUpPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;

import java.util.concurrent.TimeUnit;

public class SmsCertificationActivity extends AppCompatActivity {
//    String phoneNum = "+10123456789";
//    String testVerificationCode = "000211";
//
//
//    FirebaseAuth auth = FirebaseAuth.getInstance();
//
//    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(phoneNum)
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setActivity(this)
//            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                @Override
//                public void onCodeSent(String verificationId,
//                                       PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                    // Save the verification id somewhere
//                    // ...
//
//                    // The corresponding whitelisted code above should be used to complete sign-in.
//                    SmsCertificationActivity.this.enableUserManuallyInputCode();
//                }
//
//                @Override
//                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                    // Sign in with the credential
//                    // ...
//                }
//
//                @Override
//                public void onVerificationFailed(FirebaseException e) {
//                    // ...
//                }
//            })
//            .build();
//    PhoneAuthProvider.verifyPhoneNumber(options);
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sms_certification);
//
//        EditText mobilePhoneNumber = findViewById(R.id.mobilePhoneNumber);
//        Button sendSms = findViewById(R.id.sendSms);
//        sendSms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String phoneNumber = mobilePhoneNumber.getText().toString();
//                System.out.println("phoneNumber = " + phoneNumber);
//                if (phoneNumber.length() != 11)
//                    Toast.makeText(getApplicationContext(), "잘못된 번호입니다.", Toast.LENGTH_SHORT).show();
//                else
//                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
//                                    .setPhoneNumber(phoneNumber)       // Phone number to verify
//                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                                    .setActivity(this)                 // Activity (for callback binding)
//                                    .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                                    .build();
//                                    PhoneAuthProvider.verifyPhoneNumber(options);
//            }
//        });
//    }
//
//    PhoneAuthProvider mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential credential) {
//            // This callback will be invoked in two situations:
//            // 1 - Instant verification. In some cases the phone number can be instantly
//            //     verified without needing to send or enter a verification code.
//            // 2 - Auto-retrieval. On some devices Google Play services can automatically
//            //     detect the incoming verification SMS and perform verification without
//            //     user action.
//            //Log.d(TAG, "onVerificationCompleted:" + credential);
//
//            Toast.makeText(getApplicationContext(), "인증코드가 전송되었습니다. 90초 이내에 입력해주세요.", Toast.LENGTH_SHORT).show();
//
//        signInWithPhoneAuthCredential(credential);
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
//            // This callback is invoked in an invalid request for verification is made,
//            // for instance if the the phone number format is not valid.
//            //Log.w(TAG, "onVerificationFailed", e);
//
//            if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                // Invalid request
//            } else if (e instanceof FirebaseTooManyRequestsException) {
//                // The SMS quota for the project has been exceeded
//            }
//            Toast.makeText(getApplicationContext(), "인증에 실패하였습니다.", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onCodeSent(@NonNull String verificationId,
//                @NonNull PhoneAuthProvider.ForceResendingToken token) {
//            // The SMS verification code has been sent to the provided phone number, we
//            // now need to ask the user to enter the code and then construct a credential
//            // by combining the code with a verification ID.
//            //Log.d(TAG, "onCodeSent:" + verificationId);
//
//            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
//            // Save verification ID and resending token so we can use them later
//            mVerificationId = verificationId;
//            mResendToken = token;
//        }
//    };
//
//    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//
//                            FirebaseUser user = task.getResult().getUser();
//                            // Update UI
//                        } else {
//                            // Sign in failed, display a message and update the UI
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                // The verification code entered was invalid
//                            }
//                        }
//                    }
//                });
//    }
}