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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowMedicalsActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";


    ListView mMedicalListView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;
    List<Medicals> medicallist;
    private CollectionReference dbref;


    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_medicals);

        mMedicalListView = (ListView) findViewById(R.id.medicallist);
        mAuth = FirebaseAuth.getInstance();
        medicallist = new ArrayList<Medicals>();
        String user_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();
        mFireStore = FirebaseFirestore.getInstance();


        mMedicalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView mn = (TextView) view.findViewById(R.id.mednameuser);
                final String text1 = mn.getText().toString();

                mFireStore.collection("medicals").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if(!documentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                            for (DocumentSnapshot d: list){
                                String mname = d.getString("name");
                                Log.v(TAG, mname);
                                if(mname.equals(text1)){
                                    id = d.getId().toString();
                                    demo(id);
                                }
                            }
                        }
                    }
                });
            }
        });

    }

    public void demo(String iid){
        Intent info = new Intent(ShowMedicalsActivity.this,SelectMedsActivity.class);
        info.putExtra("MedicalID",iid);
        startActivity(info);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMedicalListView.setAdapter(null);
        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();
        medicallist = new ArrayList<Medicals>();
        final String user_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mFireStore.collection("medicals").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if(!documentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                    for (DocumentSnapshot d: list){

                        String usid = d.getString("userid");
                            Medicals a = d.toObject(Medicals.class);
                            medicallist.add(a);
                    }

                    MedicalsList adapter = new MedicalsList(ShowMedicalsActivity.this,medicallist);
                    mMedicalListView.setAdapter(adapter);
                }
            }
        });

    }
}
