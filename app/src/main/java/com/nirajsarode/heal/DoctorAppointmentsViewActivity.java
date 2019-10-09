package com.nirajsarode.heal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DoctorAppointmentsViewActivity extends AppCompatActivity {

    ListView mappodvListView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;
    private String Diddv;

    List<AppointmentsDocV> appodvlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointments_view);

        mappodvListView = (ListView) findViewById(R.id.docappolist);
        mAuth = FirebaseAuth.getInstance();
        appodvlist = new ArrayList<AppointmentsDocV>();
        String user_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();
        mFireStore = FirebaseFirestore.getInstance();

        mappodvListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView aptdv = (TextView) view.findViewById(R.id.timeofappdv);
                String text1 = aptdv.getText().toString();
                int num1 = Integer.parseInt(text1);

                TextView apddv = (TextView) view.findViewById(R.id.dateofappdv);
                String text2 = apddv.getText().toString();

                final String text3 = text2+text1;

                mFireStore.collection("appointments").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if(!documentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                            for (DocumentSnapshot d: list){
                                String dt = d.getString("date_time");
                                if(dt.equals(text3)){
                                    Diddv = d.getId().toString();
                                    demo(Diddv);
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    public void demo(String iid){
        Intent info = new Intent(DoctorAppointmentsViewActivity.this,DoctorPatientInfoViewActivity.class);
        info.putExtra("AppIDdv",iid);
        startActivity(info);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mappodvListView.setAdapter(null);
        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();
        appodvlist = new ArrayList<AppointmentsDocV>();
        final String user_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mFireStore.collection("appointments").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if(!documentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                    for (DocumentSnapshot d: list){

                        String usid = d.getString("docid");
                        if(user_id.equals(usid)) {
                            AppointmentsDocV a = d.toObject(AppointmentsDocV.class);
                            appodvlist.add(a);
                        }
                    }

                    AppointmentsDocVList adapter = new AppointmentsDocVList(DoctorAppointmentsViewActivity.this,appodvlist);
                    mappodvListView.setAdapter(adapter);

                }
            }
        });

    }
}
