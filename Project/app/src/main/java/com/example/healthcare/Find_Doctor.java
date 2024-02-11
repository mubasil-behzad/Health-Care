package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Find_Doctor extends AppCompatActivity {

    CardView backBtn, familyPhysicianBtn, dietitianBtn, dentistBtn, cardiologistBtn, surgeonBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

        backBtn = findViewById(R.id.fdBackToHome);
        familyPhysicianBtn = findViewById(R.id.familyPhysician);
        dietitianBtn = findViewById(R.id.dietitian);
        dentistBtn = findViewById(R.id.dentist);
        cardiologistBtn = findViewById(R.id.cardiologist);
        surgeonBtn = findViewById(R.id.surgeon);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Find_Doctor.this, Home.class));
            }
        });

        familyPhysicianBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(Find_Doctor.this, DoctorsDetails.class);
               intent.putExtra("title","Family Physicians");
               startActivity(intent);
            }
        });

        dentistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Find_Doctor.this, DoctorsDetails.class);
                intent.putExtra("title","Dentist");
                startActivity(intent);
            }
        });

        dietitianBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Find_Doctor.this, DoctorsDetails.class);
                intent.putExtra("title","Dietitian");
                startActivity(intent);
            }
        });

        cardiologistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Find_Doctor.this, DoctorsDetails.class);
                intent.putExtra("title","Cardiologist");
                startActivity(intent);
            }
        });

        surgeonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Find_Doctor.this, DoctorsDetails.class);
                intent.putExtra("title","Surgeon");
                startActivity(intent);
            }
        });
    }
}