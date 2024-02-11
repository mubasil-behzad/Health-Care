package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    TextView signUpTxt, forgetPasswordTxt;
    EditText emailEditTxtSignIn, passwordEditTxtSignIn;
    Button loginBtn;
    ImageView passwordHideShow;
    boolean isPasswordVisible = false;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";

        signUpTxt = findViewById(R.id.textSignUp);
        forgetPasswordTxt = findViewById(R.id.textForgetPassword);
        emailEditTxtSignIn = findViewById(R.id.editTextEmail);
        passwordEditTxtSignIn = findViewById(R.id.editTextPassword);
        loginBtn = findViewById(R.id.button);
        passwordHideShow = findViewById(R.id.imagePassword);

        signUpTxt.setPaintFlags(signUpTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        forgetPasswordTxt.setPaintFlags(forgetPasswordTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        passwordHideShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPasswordVisible = !isPasswordVisible;

                if (isPasswordVisible) {
                    passwordEditTxtSignIn.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordHideShow.setImageResource(R.drawable.view);
                } else {
                    passwordEditTxtSignIn.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordHideShow.setImageResource(R.drawable.hide);
                }
                passwordEditTxtSignIn.setSelection(passwordEditTxtSignIn.length());
            }
        });

        signUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            String email, password;
            @Override
            public void onClick(View view) {
                email = emailEditTxtSignIn.getText().toString();
                password = passwordEditTxtSignIn.getText().toString();

                Pattern pattern = Pattern.compile(emailPattern);
                Matcher matcher = pattern.matcher(email);

                if(email.isEmpty()){
                    Toast.makeText(Login.this, "Email is missing", Toast.LENGTH_SHORT).show();
                }else if (password.isEmpty()){
                    Toast.makeText(Login.this, "Password is missing", Toast.LENGTH_SHORT).show();
                }else {
                    if (matcher.matches()){
                        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                Intent intent = new Intent(Login.this, Home.class);
                                startActivity(intent);
                                Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }else {
                        Toast.makeText(Login.this, "Invalid email format!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        forgetPasswordTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,ForgetPassword.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();

    }
}