package com.nirajsarode.heal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class DoctorPatientInfoViewActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;
    private TextView mU;
    private Button mCancelApp;
    private String uid;
    private Button mPresc;
    private Button mProfile;

    private  String mDtdv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_info_view);
        mCancelApp = findViewById(R.id.cancelapp);
        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();
        mPresc = findViewById(R.id.patientpresc);
        mProfile = findViewById(R.id.patientinfoview);
        mDtdv = getIntent().getExtras().get("AppIDdv").toString();

        mCancelApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFireStore.collection("appointments").document(mDtdv).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                        Intent info = new Intent(DoctorPatientInfoViewActivity.this,DoctorAppointmentsViewActivity.class);
                        startActivity(info);
                    }
                });
            }
        });

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFireStore.collection("appointments").document(mDtdv).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String userID = (String) documentSnapshot.get("userid");

                        Intent info = new Intent(DoctorPatientInfoViewActivity.this,DoctorViewPatientInfoActivity.class);
                        info.putExtra("UID",userID);
                        startActivity(info);
                    }
                });
            }
        });
        mPresc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFireStore.collection("appointments").document(mDtdv).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String userID = (String) documentSnapshot.get("userid");

                        Intent info = new Intent(DoctorPatientInfoViewActivity.this,DocPatientPrescriptionsActivity.class);
                        Bundle extras = new Bundle();
                        extras.putString("UID",userID);
                        extras.putString("AppID",mDtdv);
                        info.putExtras(extras);
                        startActivity(info);
                    }
                });
            }
        });

    }

}
