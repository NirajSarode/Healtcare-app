package com.nirajsarode.heal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class PatientAppointmentsActivity extends AppCompatActivity {

    ListView mappoListView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;

    private String Did;

    List<Appointments> appolist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFireStore = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_patient_appointments);
        mappoListView = (ListView) findViewById(R.id.appolist);
        mAuth = FirebaseAuth.getInstance();
        appolist = new ArrayList<Appointments>();
        String user_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();

        mappoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView apt = (TextView) view.findViewById(R.id.timeofapp);
                String text1 = apt.getText().toString();
                int num1 = Integer.parseInt(text1);

                TextView apd = (TextView) view.findViewById(R.id.dateofapp);
                String text2 = apd.getText().toString();

                final String text3 = text2+text1;

                mFireStore.collection("appointments").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if(!documentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                            for (DocumentSnapshot d: list){
                                String dt = d.getString("date_time");
                                if(dt.equals(text3)){
                                    Did = d.getId().toString();
                                    demo(Did);
                                }
                            }
                        }
                    }
                });
            }
        });

    }

    public void demo(String iid){
        Intent info = new Intent(PatientAppointmentsActivity.this,PatientAppointmentINFO.class);
        info.putExtra("AppID",iid);
        startActivity(info);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mappoListView.setAdapter(null);
        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();
        appolist = new ArrayList<Appointments>();
        final String user_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mFireStore.collection("appointments").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if(!documentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                    for (DocumentSnapshot d: list){

                        String usid = d.getString("userid");
                        if(user_id.equals(usid)) {
                            Appointments a = d.toObject(Appointments.class);
                            appolist.add(a);
                        }
                    }

                    AppointmentsList adapter = new AppointmentsList(PatientAppointmentsActivity.this,appolist);
                    mappoListView.setAdapter(adapter);

                }
            }
        });

    }
}
