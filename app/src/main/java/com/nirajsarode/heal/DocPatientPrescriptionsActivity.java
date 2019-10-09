package com.nirajsarode.heal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class DocPatientPrescriptionsActivity extends AppCompatActivity {



    ListView mPrescListView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;

    List<PrescriptionInfo> prescinfolist;
    static String docna;
    static String daa;
    static String dess;
    private String DDDid;
    private String userID;

    String appID;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_patient_prescriptions);

        mFireStore = FirebaseFirestore.getInstance();
        mPrescListView = (ListView) findViewById(R.id.presshowuserdoc);
        mAuth = FirebaseAuth.getInstance();
        prescinfolist = new ArrayList<PrescriptionInfo>();
        final String user_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();
        userID = getIntent().getExtras().get("UID").toString();
        appID = getIntent().getExtras().get("AppID").toString();

        Button AddP = findViewById(R.id.addpresdoc);
        AddP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info = new Intent(DocPatientPrescriptionsActivity.this,DocAddPrescriptionActivity.class);
                Bundle extras = new Bundle();
                extras.putString("UId",userID);
                extras.putString("AppId",appID);
                info.putExtras(extras);
                startActivity(info);
            }
        });



//        TextView na = (TextView) findViewById(R.id.presdocname);
//        docna = na.getText().toString();
//
//        TextView da = (TextView) findViewById(R.id.presdate);
//        daa = da.getText().toString();
//        TextView des = (TextView) findViewById(R.id.presdesc);
//        dess = des.getText().toString();


        mPrescListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {


                final TextView na = (TextView) view.findViewById(R.id.presdocname);
                docna = na.getText().toString();
                Log.d("S DOC NAME",docna);

                TextView da = (TextView) view.findViewById(R.id.presdate);
                daa = da.getText().toString();

                Log.d("S DOC NAME",daa);
                TextView des = (TextView) view.findViewById(R.id.presdesc);
                dess = des.getText().toString();
                Log.d("S DOC NAME",dess);


                mFireStore.collection("patients").document(userID).collection("prescriptions").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d: list){

                                String uid = d.getString("docid");
                                String useid = d.getString("userid");
;
                                String dt = d.getString("date");
                                String name = d.getString("doctor");
                                String desc = d.getString("description");
                                Log.d("R DOC DATE",dt);
                                Log.d("R DOC NAME",name);

                                Log.d("R DOC DESC",desc);

                                if(dt.equals(daa) && desc.equals(dess) && docna.equals(name)){
                                    String dd = d.getId().toString();
                                    Intent info = new Intent(DocPatientPrescriptionsActivity.this,DocPatientPrescriptionsItemsActivity.class);
                                    Bundle extras = new Bundle();
                                    extras.putString("PresId",dd);
                                    extras.putString("UId",userID);
                                    info.putExtras(extras);
                                    startActivity(info);
                                }
                            }

                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPrescListView.setAdapter(null);
        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();
        prescinfolist = new ArrayList<PrescriptionInfo>();
        final String user_id = mAuth.getCurrentUser().getUid();
        String uEmail = mAuth.getCurrentUser().getEmail();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mFireStore.collection("patients").document(userID).collection("prescriptions").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if(!documentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                    for (DocumentSnapshot d: list){

                        String date = d.getString("date");
                        String doctor = d.getString("doctor");
                        String desc = d.getString("description");
                        String uid = d.getString("doctorid");

                        if(uid.equals(user_id)){
                        PrescriptionInfo a = d.toObject(PrescriptionInfo.class);
                        prescinfolist.add(a);
                        }
                    }

                    PrescriptionUserList adapter = new PrescriptionUserList(DocPatientPrescriptionsActivity.this,prescinfolist);
                    mPrescListView.setAdapter(adapter);

                }
            }
        });

    }
}
