package com.nirajsarode.heal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PatientProfileActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private Button submitbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        submitbt = findViewById(R.id.submitbt);


        submitbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText nameET = (EditText) findViewById(R.id.patientnamepro);
                String name = nameET.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    nameET.setError("Name Field Cannot be Empty");
                    return;
                }

                EditText contactEt = (EditText) findViewById(R.id.patientcontpro);
                String con = contactEt.getText().toString();
                if (TextUtils.isEmpty(con)) {
                    contactEt.setError("Contact Field Cannot be Empty");
                    return;
                }
                EditText bgEt = (EditText) findViewById(R.id.patientbgpro);
                String bt = bgEt.getText().toString();
                if (TextUtils.isEmpty(bt)) {
                    bgEt.setError("BloodGrp Field Cannot be Empty");
                    return;
                }
                EditText genderEt = (EditText) findViewById(R.id.patientgenderpro);
                String gender = genderEt.getText().toString();
                if (TextUtils.isEmpty(gender)) {
                    genderEt.setError("Gender Field Cannot be Empty");
                    return;
                }
                EditText heightEt = (EditText) findViewById(R.id.patienthtpro);
                String ht = heightEt.getText().toString();
                if (TextUtils.isEmpty(ht)) {
                    heightEt.setError("Height Field Cannot be Empty");
                    return;
                }
                EditText wtEt = (EditText) findViewById(R.id.patientwtpro);
                String wt = wtEt.getText().toString();
                if (TextUtils.isEmpty(wt)) {
                    wtEt.setError("Weight Field Cannot be Empty");
                    return;
                }

                EditText ageEt = (EditText) findViewById(R.id.patientagepro);
                String age = ageEt.getText().toString();
                if (TextUtils.isEmpty(age)) {
                    ageEt.setError("Age Field Cannot be Empty");
                    return;
                }

                final String user_id = mAuth.getCurrentUser().getUid();
                String uEmail = mAuth.getCurrentUser().getEmail();

                int c = Integer.valueOf(con);
                int a = Integer.valueOf(age);


                final Map<String,Object> data  = new HashMap<>();
                data.put("bloodgroup", bt);
                data.put("contact", c);
                data.put("email", uEmail);
                data.put("sex", gender);
                data.put("height", ht);
                data.put("name", name);
                data.put("age",a);
                data.put("weight", wt);


                final Map<String,Object> datat  = new HashMap<>();
                datat.put("name", name);
                datat.put("email", uEmail);
                datat.put("age",a);
                datat.put("sex", gender);
                datat.put("category", "patient");


                mFirestore.collection("users").document(user_id).set(datat).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getBaseContext(), "User Profile Updated", Toast.LENGTH_LONG).show();
                    }
                });
                mFirestore.collection("patients").document(user_id).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getBaseContext(), "Profile Updated", Toast.LENGTH_LONG).show();
                        finish();
                        Intent ad = new Intent(PatientProfileActivity.this,SwitchActivity.class);
                        startActivity(ad);
                    }
                });
            }
        });

    }
}
