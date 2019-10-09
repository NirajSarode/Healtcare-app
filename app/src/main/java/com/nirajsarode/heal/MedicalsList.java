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

public class MedicalsList extends ArrayAdapter<Medicals> {

private Activity context;
private List<Medicals> medicalsList;
public String idfromlist;


public MedicalsList(Activity context, List<Medicals> medicalsList){
    super(context,R.layout.medicals_list,medicalsList);
        this.context = context;
        this.medicalsList= medicalsList;
        }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflator = context.getLayoutInflater();

        View listitem = inflator.inflate(R.layout.medicals_list,null,true);

        TextView medname = (TextView) listitem.findViewById(R.id.mednameuser);
        TextView loc = (TextView) listitem.findViewById(R.id.medlocuser);

        Medicals app = medicalsList.get(position);

        medname.setText(app.getName());
        loc.setText(app.getLocation());


    return listitem;
    }
}
