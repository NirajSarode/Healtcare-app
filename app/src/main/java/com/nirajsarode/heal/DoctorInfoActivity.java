package com.nirajsarode.heal;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class DoctorInfoActivity  extends AppCompatActivity  {
    private String mDocId;
    private String mDocname;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    private TextView mDocName;
    private TextView mDocSpe;
    private TextView mDocLoc;


    EditText mSelectDate;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    TextView mSelectTime;

    String docId;
    String datime;

    Spinner slotspinner;

    Button mBookapp;

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);


        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        slotspinner = (Spinner) findViewById(R.id.timespinner);


        final String user_id = mAuth.getCurrentUser().getUid();

        final List<Integer> timess = new ArrayList<Integer>();
        timess.add(Integer.valueOf("0000"));


        mDocLoc = (TextView)findViewById(R.id.doclocinfou);
        mDocName = (TextView)findViewById(R.id.docnameinfou);
        mDocSpe = (TextView)findViewById(R.id.docspecinfou);
        mBookapp = (Button)findViewById(R.id.boapp);
        mSelectDate = (EditText) findViewById(R.id.selectdate);
        //mSelectTime = (EditText) findViewById(R.id.selecttime);

        mDocId = getIntent().getExtras().get("doctorid").toString();




        mSelectDate.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int date = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(DoctorInfoActivity.this, android.R.style.Theme_Holo_Light,
                        mDateSetListener, year, month, date);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        final int y = Calendar.getInstance().get(Calendar.YEAR);
        final int m = Calendar.getInstance().get(Calendar.MONTH);
        final int d = Calendar.getInstance().get(Calendar.DATE);

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                month = month + 1;
                if(month < 10){
                    String mo = String.valueOf(month);
                    mo = "0" + mo;
                    month = Integer.parseInt(mo);
                }
                if(date < 10){

                    String da = String.valueOf(date);
                    da = "0" + da;
                    date = Integer.parseInt(da);
                }
                // Create SimpleDateFormat object
                SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd");
                Date d1 = null;
                try {

                    d1 = sdfo.parse(y+"-"+m+"-"+d);
                    Log.v("D1", String.valueOf(d1));

                    Date d2 = sdfo.parse(year+"-"+month+"-"+date);
                    Log.v("D2", String.valueOf(d2));
                    String DateFrom =  year+ "-" + month + "-" + date;
                    if (d2.compareTo(d1) > 0) {
                        Log.v("Compare", "BOOM");
                        mSelectDate.setText(DateFrom);
                    }else{
                        mBookapp.setEnabled(false);
                        Toast.makeText(getApplicationContext(), "Select Proper Date ", Toast.LENGTH_SHORT).show();

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        };

        mFirestore.collection("doctors").document(mDocId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                int start =  documentSnapshot.getLong("start_time").intValue();
                int end = documentSnapshot.getLong("end_time").intValue();
                Log.v(TAG, String.valueOf(start));
                Log.v(TAG, String.valueOf(end));

                while (start<end) {
                    if(start%100 ==0)
                        start= start +30;
                    else
                        start= start +70;

                    Log.v(TAG, String.valueOf(start));
                    timess.add(start);
                    ArrayAdapter<Integer> timessAdapter = new ArrayAdapter<Integer>(DoctorInfoActivity.this, android.R.layout.simple_spinner_item, timess);
                    timessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    slotspinner.setAdapter(timessAdapter);
                }
            }
        });


        mFirestore.collection("doctors").document(mDocId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String ddnn = (String) documentSnapshot.get("name");
                String sp = (String) documentSnapshot.get("specialization");
                String loc = (String) documentSnapshot.get("location");
                mDocName.setText(ddnn);
                mDocLoc.setText(loc);
                mDocSpe.setText(sp);
            }
        });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uEmail = user.getEmail();


        mFirestore.collection("appointments").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {

                if(!documentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                    for (DocumentSnapshot d: list){
                        String did = d.getString("docid");
                        String ui = d.getString("userid");
                        String datim = d.getString("date_time");

                        Integer toime = (Integer) slotspinner.getSelectedItem();
                        String t = String.valueOf(toime);

                        String dt = mSelectDate.getText().toString();
//                        String tm = mSelectTime.getText().toString();

                        datime = dt+t;
                        if(ui.equals(user_id) && did.equals(mDocId)){
                            mBookapp.setEnabled(false);
                            Toast.makeText(getApplicationContext(), "YOU ALREADY HAVE APPOINTMENT", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent ad = new Intent(DoctorInfoActivity.this,SelectDoctorActivity.class);
                            ad.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(ad);

                        }
                        if(datim.equals(datime) && did.equals(mDocId)) {
                            mBookapp.setEnabled(false);
                            Toast.makeText(getApplicationContext(), "Take a Chill pill boi, You ain;t that sick", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


        mFirestore.collection("patients").document(user_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name  = (String) documentSnapshot.get("name");
                boo(name);
            }
        });

    }

    public void boo(final String name){


        mBookapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirestore = FirebaseFirestore.getInstance();
                String user_id = mAuth.getCurrentUser().getUid();
                String uEmail = mAuth.getCurrentUser().getEmail();
//                String timeap = mSelectTime.getText().toString();
                String dateap = mSelectDate.getText().toString();
                String docname = mDocName.getText().toString();
                Integer toime = (Integer) slotspinner.getSelectedItem();
                String t = String.valueOf(toime);


                boolean Stat = false;

                Log.v(TAG, "INSIDE");


                Map<String,Object> data  = new HashMap<>();
                data.put("date", dateap);
                data.put("doctor", docname);
                data.put("time", toime);
                data.put("date_time", dateap+t);
                data.put("userid", user_id);
                data.put("docid", mDocId);
                data.put("user", name);
                data.put("status", Stat);

                mFirestore.collection("appointments").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getBaseContext(), "Appointment Booked", Toast.LENGTH_LONG).show();
                        finish();
                        Intent ad = new Intent(DoctorInfoActivity.this,DashboardActivity.class);
                        startActivity(ad);
                    }
                });
            }
        });
    }
    }