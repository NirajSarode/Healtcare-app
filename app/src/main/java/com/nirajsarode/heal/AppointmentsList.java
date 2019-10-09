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

public class AppointmentsList  extends ArrayAdapter<Appointments> {

    private Activity context;
    private List<Appointments> appoList;


    public AppointmentsList(Activity context, List<Appointments> appoList){
        super(context,R.layout.appointments_list,appoList);
        this.context = context;
        this.appoList = appoList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflator = context.getLayoutInflater();

        View listitem = inflator.inflate(R.layout.appointments_list,null,true);

        TextView docname = (TextView) listitem.findViewById(R.id.doctornameapp);
        TextView time = (TextView) listitem.findViewById(R.id.timeofapp);
        TextView date = (TextView) listitem.findViewById(R.id.dateofapp);
       // TextView id = (TextView) listitem.findViewById(R.id.appoid);

        Appointments app = appoList.get(position);

        docname.setText(app.getDoctor());
        time.setText(String.valueOf(app.getTime()));
        date.setText(app.getDate());
        //id.setText(app.getDocid());

        return listitem;
    }
}
