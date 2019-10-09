package com.nirajsarode.heal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorProfileUpdateActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private Button updatebt;

    Spinner startspinner;
    Spinner endspinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile_update);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        final String user_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

//        updatebt = findViewById(R.id.docsubmitbt);
        startspinner = (Spinner) findViewById(R.id.startspinnerup);
        endspinner = (Spinner) findViewById(R.id.endspinnerup);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.start, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
//            submitbt.setEnabled(false);
        }

        final TextView nameET = (TextView) findViewById(R.id.docnameup);

        final EditText locEt = (EditText) findViewById(R.id.doclocup);

        final EditText genderEt = (EditText) findViewById(R.id.docgenup);

        final EditText spezET = (EditText) findViewById(R.id.docspezup);

        final EditText descEt = (EditText) findViewById(R.id.docdescup);

        final EditText ageEt = (EditText) findViewById(R.id.docageup);




        mFirestore.collection("doctors").document(user_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String pname = (String) documentSnapshot.get("name");
                String gen = (String) documentSnapshot.get("gender");
                String loc = (String) documentSnapshot.get("location");
                int age = documentSnapshot.getLong("age").intValue();
                String spez = (String) documentSnapshot.get("specialization");
                String des = (String) documentSnapshot.get("description");
                String ags = String.valueOf(age);
                nameET.setText(pname);
                genderEt.setText(gen);
                locEt.setText(loc);
                spezET.setText(spez);
                descEt.setText(des);
                ageEt.setText(ags);
            }
        });



    }
}
