package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    Button resetBtn;
    ImageButton forgetBackBtn;
    EditText emailForget;
    FirebaseAuth firebaseAuth;
    ProgressBar progressReset;
    String resetEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        firebaseAuth = FirebaseAuth.getInstance();
        forgetBackBtn = findViewById(R.id.backForget);
        emailForget = findViewById(R.id.editTextEmailForget);
        resetBtn = findViewById(R.id.reset);
        progressReset = findViewById(R.id.progressBarReset);

        int color = getResources().getColor(R.color.offWhite);
        progressReset.getIndeterminateDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);
        forgetBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgetPassword.this, Login.class));
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetEmail = emailForget.getText().toString().trim();
                if (!TextUtils.isEmpty(resetEmail)){
                        resetPassword();
                }else {
                    Toast.makeText(ForgetPassword.this, "Field is Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void resetPassword (){
        progressReset.setVisibility(View.VISIBLE);
        resetBtn.setVisibility(View.INVISIBLE);

        firebaseAuth.sendPasswordResetEmail(resetEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgetPassword.this, "Reset Password link has been sent to your registered Email", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ForgetPassword.this, Login.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgetPassword.this, "Error : "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                progressReset.setProgress(View.INVISIBLE);
                resetBtn.setVisibility(View.VISIBLE);
            }
        });
    }
}