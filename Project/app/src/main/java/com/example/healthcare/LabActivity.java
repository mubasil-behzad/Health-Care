package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class LabActivity extends AppCompatActivity {

    HashMap<String,String> hashMap;
    ArrayList arrayList;
    TextView labTotalTV, dateLab, timeLab;
    ListView labCartLV;
    private ImageButton selectDateLab, selectTimeLab, backBtn, checkOutBtn;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        selectDateLab =findViewById(R.id.LabdateBtn);
        selectTimeLab =findViewById(R.id.LabTimeBtn);
        backBtn =findViewById(R.id.labCartBackBtn);
        checkOutBtn = findViewById(R.id.LabPay);
        labTotalTV = findViewById(R.id.LabTotalCost);
        dateLab = findViewById(R.id.LabtxtDate);
        timeLab = findViewById(R.id.LabtxtTime);
        labCartLV = findViewById(R.id.lv_labCart);

        fetchCartData();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabActivity.this, LabTest.class));
            }
        });

        initDatePicker();
        selectDateLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        initTimePicker();
        selectTimeLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });

        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LabActivity.this,BookLabTest.class);
                intent.putExtra("price",labTotalTV.getText());
                intent.putExtra("date",dateLab.getText());
                intent.putExtra("time",timeLab.getText());
                startActivity(intent);
            }
        });

    }
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1= i1+1;
                dateLab.setText(i2+"/"+i1+"/"+i);
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
                timeLab.setText(i+":"+i1);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this,style,timeSetListener,hours,minutes,true);
    }

    private void fetchCartData() {
        firestore.collection("cart")
                .document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Cart document exists, handle the data
                        List<String> packages = (List<String>) documentSnapshot.get("Packages");
                        List<String> costs = (List<String>) documentSnapshot.get("Costs");
                        float totalCost = 0;
                        // Now you can use the 'packages' and 'costs' lists as needed
                        if (packages != null && costs != null) {
                            arrayList = new ArrayList();
                            for (int i = 0; i < packages.size(); i++) {
                                String pkg = packages.get(i);
                                String cost = costs.get(i);
                                totalCost = totalCost + Float.parseFloat(cost);
                                hashMap = new HashMap<>();
                                hashMap.put("line1", pkg);  // Assuming package is a String
                                hashMap.put("line2", "");   // Add additional lines if needed
                                hashMap.put("line3", "");
                                hashMap.put("line4", "");
                                hashMap.put("line5", "Total Cost: " + cost + "/-");
                                arrayList.add(hashMap);
                            }
                            labTotalTV.setText("Total Cost : "+totalCost);
                            simpleAdapter = new SimpleAdapter(LabActivity.this,
                                    arrayList,
                                    R.layout.multiples_lines,
                                    new String[]{"line1","line2","line3","line4","line5"},
                                    new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e}
                            );
                            labCartLV.setAdapter(simpleAdapter);

                        }
                    } else {
                        // Cart document does not exist
                        Toast.makeText(LabActivity.this, "Cart is empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(LabActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }

}