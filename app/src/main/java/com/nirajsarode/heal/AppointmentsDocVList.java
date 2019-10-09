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

public class AppointmentsDocVList extends ArrayAdapter<AppointmentsDocV> {

private Activity context;
private List<AppointmentsDocV> appodvList;


public AppointmentsDocVList(Activity context, List<AppointmentsDocV> appodvList){
        super(context,R.layout.appointmentsdv_list,appodvList);
        this.context = context;
        this.appodvList = appodvList;
        }

@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflator = context.getLayoutInflater();

        View listitem = inflator.inflate(R.layout.appointmentsdv_list,null,true);

        TextView pname = (TextView) listitem.findViewById(R.id.patientnamenameapp);
        TextView time = (TextView) listitem.findViewById(R.id.timeofappdv);
        TextView date = (TextView) listitem.findViewById(R.id.dateofappdv);
        //TextView id = (TextView) listitem.findViewById(R.id.appoiddv);

        AppointmentsDocV app = appodvList.get(position);

        pname.setText(app.getUser());
        time.setText(String.valueOf(app.getTime()));
        date.setText(app.getDate());
        //id.setText(app.getDocid());

        return listitem;
        }
}