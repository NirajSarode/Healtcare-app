package com.nirajsarode.heal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DoctorDashBoardActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;
    private Button mMyApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dash_board);

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();
        mMyApp = findViewById(R.id.manageappdoc);
//        mMyApp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent ad = new Intent(DoctorDashBoardActivity.this,DoctorAppointmentsViewActivity.class);
//            }
//        });

    }

    public void Managaapp(View view){
        manageapp();
    }

    private void manageapp() {
        Intent ad = new Intent(DoctorDashBoardActivity.this,DoctorAppointmentsViewActivity.class);
        startActivity(ad);
    }

    public void DoctorProfile(View view){
        docpro();
    }

    private void docpro() {
        Intent ad = new Intent(DoctorDashBoardActivity.this,DoctorProfileUpdateActivity.class);
        startActivity(ad);
    }

    public void LogoutUser(View view) {
        logout();
    }

    private void logout() {
        mAuth.signOut();
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }


}



