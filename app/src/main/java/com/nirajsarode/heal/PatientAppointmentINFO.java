package com.nirajsarode.heal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class PatientAppointmentINFO extends AppCompatActivity {

    String mDT;

    Button mCancel;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_appointment_info);


        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();

        mDT = getIntent().getExtras().get("AppID").toString();

        mCancel = findViewById(R.id.cancelapp);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFireStore.collection("appointments").document(mDT).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                        Intent info = new Intent(PatientAppointmentINFO.this,PatientAppointmentsActivity.class);
                        info.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(info);
                    }
                });
            }
        });

    }
}
