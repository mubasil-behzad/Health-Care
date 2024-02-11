package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorsDetails extends AppCompatActivity {

    private String[][] doctor_Details_1 = {
            {"Doctor Name : Khizar Abbas","Hospital Address : Sabaz Koat","Experience : 5 years","Mobile # : 123456789","600"},
            {"Doctor Name : Arooj Fatima","Hospital Address : Ugoki","Experience : 8 years","Mobile # : 123456789","900"},
            {"Doctor Name : Arshia Butt","Hospital Address : Chawinda","Experience : 6 years","Mobile # : 123456789","300"},
            {"Doctor Name : Amara Batool","Hospital Address : Zafarwal","Experience : 7 years","Mobile # : 123456789","500"},
            {"Doctor Name : Mubasil Behzad","Hospital Address : Daska","Experience : 7 years","Mobile # : 123456789","1000"},
    };
    private String[][] doctor_Details_2 = {
            {"Doctor Name : Mubasil Behzad","Hospital Address : Daska","Experience : 7 years","Mobile# : 123456789","1000"},
            {"Doctor Name : Khizar Abbas","Hospital Address : Sabaz Koat","Experience : 5 years","Mobile # : 123456789","600"},
            {"Doctor Name : Arooj Fatima","Hospital Address : Ugoki","Experience : 8 years","Mobile # : 123456789","900"},
            {"Doctor Name : Arshia Butt","Hospital Address : Chawinda","Experience : 6 years","Mobile # : 123456789","300"},
            {"Doctor Name : Amara Batool","Hospital Address : Zafarwal","Experience : 7 years","Mobile # : 123456789","500"},
    };
    private String[][] doctor_Details_3 = {
            {"Doctor Name : Arooj Fatima","Hospital Address : Ugoki","Experience : 8 years","Mobile # : 123456789","900"},
            {"Doctor Name : Khizar Abbas","Hospital Address : Sabaz Koat","Experience : 5 years","Mobile # : 123456789","600"},
            {"Doctor Name : Arshia Butt","Hospital Address : Chawinda","Experience : 6 years","Mobile # : 123456789","300"},
            {"Doctor Name : Amara Batool","Hospital Address : Zafarwal","Experience : 7 years","Mobile # : 123456789","500"},
            {"Doctor Name : Mubasil Behzad","Hospital Address : Daska","Experience : 7 years","Mobile # : 123456789","1000"},
    };
    private String[][] doctor_Details_4 = {
            {"Doctor Name : Arshia Butt","Hospital Address : Chawinda","Experience : 6 years","Mobile # : 123456789","300"},
            {"Doctor Name : Khizar Abbas","Hospital Address : Sabaz Koat","Experience : 5 years","Mobile # : 123456789","600"},
            {"Doctor Name : Arooj Fatima","Hospital Address : Ugoki","Experience : 8 years","Mobile # : 123456789","900"},
            {"Doctor Name : Amara Batool","Hospital Address : Zafarwal","Experience : 7 years","Mobile # : 123456789","500"},
            {"Doctor Name : Mubasil Behzad","Hospital Address : Daska","Experience : 7 years","Mobile # : 123456789","1000"},
    };

    private String[][] doctor_Details_5 = {
            {"Doctor Name : Amara Batool","Hospital Address : Zafarwal","Experience : 7 years","Mobile # : 123456789","500"},
            {"Doctor Name : Arshia Butt","Hospital Address : Chawinda","Experience : 6 years","Mobile # : 123456789","300"},
            {"Doctor Name : Khizar Abbas","Hospital Address : Sabaz Koat","Experience : 5 years","Mobile # : 123456789","600"},
            {"Doctor Name : Arooj Fatima","Hospital Address : Ugoki","Experience : 8 years","Mobile # : 123456789","900"},
            {"Doctor Name : Mubasil Behzad","Hospital Address : Daska","Experience : 7 years","Mobile # : 123456789","1000"},
    };
    ImageButton backBtn;
    TextView title;

    ListView listView;
    String[][] doctor_Details = {};
    ArrayList arrayList;

    HashMap<String,String> hashMap;
    SimpleAdapter simpleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_details);

        backBtn = findViewById(R.id.backToFindDoctor);
        title = findViewById(R.id.doctorDetailTitle);
        listView = findViewById(R.id.lv_doctorDetails);

        Intent intent = getIntent();
        String docTitle = intent.getStringExtra("title");
        title.setText(docTitle);
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        if (docTitle.compareTo("Family Physicians") == 0){
            doctor_Details = doctor_Details_1;
        } else if (docTitle.compareTo("Dietitian") == 0) {
            doctor_Details = doctor_Details_2;
        }else if (docTitle.compareTo("Dentist") == 0) {
            doctor_Details = doctor_Details_3;
        }else if (docTitle.compareTo("Surgeon") == 0) {
            doctor_Details = doctor_Details_4;
        }else {
            doctor_Details = doctor_Details_5;
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorsDetails.this, Find_Doctor.class));
            }
        });

        arrayList = new ArrayList();
        for (int i=0;i< doctor_Details.length;i++){
            hashMap = new HashMap<String,String>();
            hashMap.put("line1",doctor_Details[i][0]);
            hashMap.put("line2",doctor_Details[i][1]);
            hashMap.put("line3",doctor_Details[i][2]);
            hashMap.put("line4",doctor_Details[i][3]);
            hashMap.put("line5","Cons Fees:"+doctor_Details[i][4]+"/-");
            arrayList.add(hashMap);
        }
        simpleAdapter = new SimpleAdapter(DoctorsDetails.this,
                arrayList,
                R.layout.multiples_lines,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e}
        );
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(DoctorsDetails.this,Appointment.class);
                intent1.putExtra("text1",docTitle);
                intent1.putExtra("text2",doctor_Details[i][0]);
                intent1.putExtra("text3",doctor_Details[i][1]);
                intent1.putExtra("text4",doctor_Details[i][3]);
                intent1.putExtra("text5",doctor_Details[i][4]);
                startActivity(intent1);
            }
        });
    }
}