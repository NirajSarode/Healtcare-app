package com.nirajsarode.heal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class SelectDoctorActivity extends AppCompatActivity {

    ListView mDocListView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;

    List<Doctors> doclist;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_doctor);

        mDocListView = (ListView) findViewById(R.id.doctorslist);
        mAuth = FirebaseAuth.getInstance();
        doclist = new ArrayList<>();
        String user_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();

        mDocListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView dn = (TextView) view.findViewById(R.id.doctornameuser);
                final String text1 = dn.getText().toString();
                Log.d("S DOC DESC",text1);


                mFireStore.collection("doctors").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if(!documentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                            for (DocumentSnapshot d: list){
                                String mname = d.getString("name");
                                if(mname.equals(text1)){
                                    id = d.getId().toString();
                                    demom(id);
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    public void demom(String iid){
        Intent info = new Intent(SelectDoctorActivity.this, DoctorInfoActivity.class);
        info.putExtra("doctorid",iid);
        startActivity(info);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDocListView.setAdapter(null);
        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();
        doclist = new ArrayList<>();
        final String user_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mFireStore.collection("doctors").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if(!documentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                    for (DocumentSnapshot d: list){
                        Doctors f = d.toObject(Doctors.class);
                        doclist.add(f);
                    }
                    DoctorList adapter = new DoctorList(SelectDoctorActivity.this,doclist);
                    mDocListView.setAdapter(adapter);

                }
            }
        });

    }
}
