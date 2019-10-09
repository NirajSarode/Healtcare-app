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

public class DoctorList extends ArrayAdapter<Doctors>{
    private Activity context;
    private List<Doctors> doctorsList;

    public DoctorList(Activity context,List<Doctors> doctorsList){
        super(context,R.layout.doctor_list,doctorsList);
        this.context = context;
        this.doctorsList = doctorsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflator = context.getLayoutInflater();

        View listitem = inflator.inflate(R.layout.doctor_list,null,true);

        TextView docname = (TextView) listitem.findViewById(R.id.doctornameuser);
        TextView loc = (TextView) listitem.findViewById(R.id.location_uint);
        TextView speci = (TextView) listitem.findViewById(R.id.speciality_uint);

        Doctors doc = doctorsList.get(position);

        docname.setText(doc.getName());
        loc.setText(doc.getLocation());
        speci.setText(doc.getSpecialization());

        return listitem;
    }
}
