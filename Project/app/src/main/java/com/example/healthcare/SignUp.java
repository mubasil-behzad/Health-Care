package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    TextView logInTxt;
    ImageView passwordImgView, c_PasswordImgView;
    Button signUpBtn;
    EditText emailEditTxtSignUp, passwordEditTxtSignUp, c_passwordEditTxt, fullNameTxt;
    boolean isPasswordVisible = false;
    boolean isC_PasswordVisible = false;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";

        logInTxt = findViewById(R.id.textLogIn);
        passwordImgView = findViewById(R.id.imagePasswordSinUp);
        emailEditTxtSignUp = findViewById(R.id.editTextEmailSignUP);
        fullNameTxt = findViewById(R.id.editTextFullName);
        passwordEditTxtSignUp = findViewById(R.id.editTextPasswordSignUP);
        c_passwordEditTxt = findViewById(R.id.editTextConfirmPasswordSignUP);
        c_PasswordImgView = findViewById(R.id.imageC_PasswordSinUp);
        signUpBtn = findViewById(R.id.buttonSignUp);

        firebaseAuth = FirebaseAuth.getInstance();

        passwordImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPasswordVisible = !isPasswordVisible;

                if (isPasswordVisible) {
                    passwordEditTxtSignUp.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordImgView.setImageResource(R.drawable.view);
                } else {
                    passwordEditTxtSignUp.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordImgView.setImageResource(R.drawable.hide);
                }
                passwordEditTxtSignUp.setSelection(passwordEditTxtSignUp.length());
            }
        });

        c_PasswordImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isC_PasswordVisible = !isC_PasswordVisible;

                if (isC_PasswordVisible) {
                    c_passwordEditTxt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    c_PasswordImgView.setImageResource(R.drawable.view);
                } else {
                    c_passwordEditTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    c_PasswordImgView.setImageResource(R.drawable.hide);
                }
                c_passwordEditTxt.setSelection(passwordEditTxtSignUp.length());
            }
        });

        logInTxt.setPaintFlags(logInTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        logInTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            String fullName, email, passWord, c_PassWord;
            @Override
            public void onClick(View view) {
                firestore = FirebaseFirestore.getInstance();
                fullName = fullNameTxt.getText().toString();
                email = emailEditTxtSignUp.getText().toString();
                passWord = passwordEditTxtSignUp.getText().toString();
                c_PassWord = c_passwordEditTxt.getText().toString();

                Pattern pattern = Pattern.compile(emailPattern);
                Matcher matcher = pattern.matcher(email);

                if (fullName.isEmpty()){
                    if (matcher.matches()) {
                        fullNameTxt.setError("Full Name is required");
                        return;
                    }else {
                        Toast.makeText(SignUp.this, "Invalid email format!", Toast.LENGTH_SHORT).show();
                    }
                }
                if (email.isEmpty()){
                    emailEditTxtSignUp.setError("Email is required");
                    return;
                }
                if (passWord.isEmpty()){
                    passwordEditTxtSignUp.setError("Password is required");
                    return;
                }
                if (c_PassWord.isEmpty()){
                    c_passwordEditTxt.setError("Confirm password is required");
                    return;
                }
                if(!passWord.equals(c_PassWord)){
                    Toast.makeText(SignUp.this, "Password do not match", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    firebaseAuth.createUserWithEmailAndPassword(email,passWord).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        Map<String,Object> user = new HashMap<>();
                        String uID = firebaseAuth.getUid();
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            user.put("Full Name",fullName);
                            user.put("Email",email);
                            firestore.collection("user")
                                    .document(firebaseAuth.getCurrentUser().getUid())
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Intent intent = new Intent(SignUp.this, Home.class);
                                            Toast.makeText(SignUp.this, "Register Successful", Toast.LENGTH_SHORT).show();
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}