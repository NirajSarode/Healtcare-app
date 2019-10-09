package com.nirajsarode.heal;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MedicinesList extends ArrayAdapter<Medicines> {

    private Activity context;
    private List<Medicines> medicalmedList;
    public String idfromlist;


    public MedicinesList(Activity context, List<Medicines> medicalmedList){
        super(context,R.layout.medicines_list,medicalmedList);
        this.context = context;
        this.medicalmedList= medicalmedList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflator = context.getLayoutInflater();

        View listitem = inflator.inflate(R.layout.medicines_list,null,true);

        TextView medname = (TextView) listitem.findViewById(R.id.mednamestock);
        //TextView loc = (TextView) listitem.findViewById(R.id.medlocuser);

        Medicines app = medicalmedList.get(position);

        medname.setText(app.getName());
        //loc.setText(app.getLocation());



        return listitem;
    }
}
