package com.nirajsarode.heal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SelectCategoryUserActivity extends AppCompatActivity {

    private Button mPtbt;
    private Button mdocbt;
    private Button mMedicalbt;
    private Button mLabbt;
    private Button mlogout;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category_user);

        mAuth = FirebaseAuth.getInstance();
        mPtbt = findViewById(R.id.patientsel);
        mdocbt = findViewById(R.id.docsel);
//        mMedicalbt = findViewById(R.id.medsel);
//        mLabbt = findViewById(R.id.labsel);


        mPtbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ad = new Intent(SelectCategoryUserActivity.this,PatientProfileActivity.class);
                startActivity(ad);
            }
        });

        mdocbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ad = new Intent(SelectCategoryUserActivity.this,DoctorProfileActivity.class);
                startActivity(ad);
            }
        });
//        mlogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mAuth.signOut();
//                finish();
//                Intent ad = new Intent(SelectCategoryUserActivity.this,MainActivity.class);
//                startActivity(ad);
//            }
//        });
//
//        mMedicalbt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent ad = new Intent(SelectCategoryUserActivity.this,PatientProfileActivity.class);
////                startActivity(ad);
//            }
//        });
//
//        mLabbt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent ad = new Intent(SelectCategoryUserActivity.this,PatientProfileActivity.class);
////                startActivity(ad);
//            }
//        });
    }
}
