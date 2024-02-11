package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Appointment extends AppCompatActivity {

    EditText fullName, address, contactNumber, fees;
    TextView appTitle, dateTxt, timeTxt;
    Button bookAppointmentBtn;
    private ImageButton btnDate, btnTime, backD_DBtn;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        backD_DBtn = findViewById(R.id.backToD_D);
        fullName = findViewById(R.id.editFullName);
        address = findViewById(R.id.editAddress);
        contactNumber = findViewById(R.id.editContactNumber);
        fees = findViewById(R.id.editFees);
        appTitle = findViewById(R.id.textTitle);
        btnDate= findViewById(R.id.dateBtn);
        btnTime = findViewById(R.id.TimeBtn);
        dateTxt = findViewById(R.id.txtDate);
        timeTxt = findViewById(R.id.txtTime);
        bookAppointmentBtn = findViewById(R.id.buttonBookAppointment);

        fullName.setKeyListener(null);
        address.setKeyListener(null);
        contactNumber.setKeyListener(null);
        fees.setKeyListener(null);

        Intent intent = getIntent();
        String Title = intent.getStringExtra("text1");
        String FullName = intent.getStringExtra("text2");
        String Address = intent.getStringExtra("text3");
        String ContactNumber = intent.getStringExtra("text4");
        String Fees = intent.getStringExtra("text5");

        appTitle.setText(Title);
        fullName.setText(FullName);
        address.setText(Address);
        contactNumber.setText(ContactNumber);
        fees.setText("Cons Fees : "+Fees+"/-");

        initDatePicker();
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        initTimePicker();
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });

        backD_DBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Appointment.this, Find_Doctor.class));
            }
        });

        bookAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Appointment.this, "Booked", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1= i1+1;
                dateTxt.setText(i2+"/"+i1+"/"+i);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis()+86400000);
    }
    private void initTimePicker(){
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeTxt.setText(i+":"+i1);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this,style,timeSetListener,hours,minutes,true);
    }

}