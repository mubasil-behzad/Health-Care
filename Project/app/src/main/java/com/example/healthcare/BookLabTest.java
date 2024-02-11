package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BookLabTest extends AppCompatActivity {

    EditText fullNameLB, addressLB,pinCodeLB, contactLB;
    Button bookingLB;
    ImageButton backLB;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_lab_test);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        fullNameLB = findViewById(R.id.editFullNameLTBook);
        addressLB = findViewById(R.id.editAddressLTBook);
        pinCodeLB = findViewById(R.id.editPinCodeLTBook);
        contactLB = findViewById(R.id.editContactNumberLTBook);
        bookingLB = findViewById(R.id.buttonLTBook);
        backLB = findViewById(R.id.backToLabActivity);

        Intent intent = getIntent();
        String[] price = intent.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":"));
        String date = intent.getStringExtra("date").toString();
        String time = intent.getStringExtra("time").toString();

        DocumentReference cartDocument = firestore.collection("cart").document(firebaseAuth.getCurrentUser().getUid());
        bookingLB.setOnClickListener(new View.OnClickListener() {
            Map<String,Object> order = new HashMap<>();
            @Override
            public void onClick(View view) {
                order.put("Full Name",fullNameLB.getText().toString());
                order.put("Address",addressLB.getText().toString());
                order.put("Pin Code",Integer.parseInt(pinCodeLB.getText().toString()));
                order.put("Contact No",contactLB.getText().toString());
                order.put("Date",date.toString());
                order.put("Time",time);
                order.put("Price",Float.parseFloat(price[1].toString()));

                firestore.collection("order").document(firebaseAuth.getCurrentUser().getUid())
                        .set(order)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent intent = new Intent(BookLabTest.this, Home.class);
                                Toast.makeText(BookLabTest.this, "Register Successful", Toast.LENGTH_SHORT).show();
                                cartDocument.delete();
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(BookLabTest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}