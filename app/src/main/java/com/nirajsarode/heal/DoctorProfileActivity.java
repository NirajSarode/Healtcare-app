package com.nirajsarode.heal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DoctorProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private Button submitbt;

    Spinner startspinner;
    Spinner endspinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        submitbt = findViewById(R.id.docsubmitbt);
        startspinner = (Spinner) findViewById(R.id.startspinner);
        endspinner = (Spinner) findViewById(R.id.endspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.start, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        startspinner.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.end, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        /* Apply the adapter to the spinner */
        endspinner.setAdapter(adapter1);


        String strtime = (String) startspinner.getSelectedItem();
        int start = Integer.valueOf(strtime);
        String endtime = (String) startspinner.getSelectedItem();
        int end = Integer.valueOf(endtime);
        if(start>end){
            submitbt.setEnabled(false);
        }


        submitbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText nameET = (EditText) findViewById(R.id.docname);
                String name = nameET.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    nameET.setError("Name Field Cannot be Empty");
                    return;
                }

                EditText contactEt = (EditText) findViewById(R.id.docdesc);
                String con = contactEt.getText().toString();
                if (TextUtils.isEmpty(con)) {
                    contactEt.setError("Contact Field Cannot be Empty");
                    return;
                }
                EditText bgEt = (EditText) findViewById(R.id.docspez);
                String spez = bgEt.getText().toString();
                if (TextUtils.isEmpty(spez)) {
                    bgEt.setError("BloodGrp Field Cannot be Empty");
                    return;
                }
                EditText genderEt = (EditText) findViewById(R.id.docgen);
                String gender = genderEt.getText().toString();
                if (TextUtils.isEmpty(gender)) {
                    genderEt.setError("Gender Field Cannot be Empty");
                    return;
                }

                String strtime = (String) startspinner.getSelectedItem();
                int start = Integer.valueOf(strtime);
                String endtime = (String) endspinner.getSelectedItem();
                int end = Integer.valueOf(endtime);

                EditText loc = (EditText) findViewById(R.id.docloc);
                String l = loc.getText().toString();
                if (TextUtils.isEmpty(l)) {
                    loc.setError("Height Field Cannot be Empty");
                    return;
                }
//                EditText wtEt = (EditText) findViewById(R.id.patientwtpro);
//                String wt = wtEt.getText().toString();
//                if (TextUtils.isEmpty(wt)) {
//                    wtEt.setError("Weight Field Cannot be Empty");
//                    return;
//                }
//


                EditText ageEt = (EditText) findViewById(R.id.docage);
                String a = ageEt.getText().toString();
                int age = Integer.valueOf(a);
                if (TextUtils.isEmpty(a)) {
                    ageEt.setError("Age Field Cannot be Empty");
                    return;
                }

                final String user_id = mAuth.getCurrentUser().getUid();
                String uEmail = mAuth.getCurrentUser().getEmail();


                    final Map<String, Object> data = new HashMap<>();
                    data.put("specialization", spez);
                    data.put("description", con);
                    data.put("email", uEmail);
                    data.put("location", l);
                    data.put("gender", gender);
                    data.put("start_time", start);
                    data.put("name", name);
                    data.put("age", age);
                    data.put("end_time", end);


                    final Map<String, Object> datat = new HashMap<>();
                    datat.put("name", name);
                    datat.put("email", uEmail);
                    datat.put("age", age);
                    datat.put("sex", gender);
                    data.put("location", l);
                    datat.put("category", "doctor");


                    mFirestore.collection("users").document(user_id).set(datat).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getBaseContext(), "User Profile Updated", Toast.LENGTH_LONG).show();
                        }
                    });

                    mFirestore.collection("doctors").document(user_id).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getBaseContext(), "Profile Updated", Toast.LENGTH_LONG).show();
                            finish();
                            Intent ad = new Intent(DoctorProfileActivity.this, SwitchActivity.class);
                            startActivity(ad);
                        }
                    });
            }
        });

    }
}
