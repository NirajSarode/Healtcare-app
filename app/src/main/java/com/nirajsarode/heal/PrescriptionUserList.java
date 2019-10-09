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

public class PrescriptionUserList extends ArrayAdapter<PrescriptionInfo> {

    private Activity context;
    private List<PrescriptionInfo> presinfoList;

    public PrescriptionUserList(Activity context, List<PrescriptionInfo> presinfoList){
        super(context,R.layout.prescrtiptionuser_list,presinfoList);
        this.context = context;
        this.presinfoList= presinfoList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflator = context.getLayoutInflater();

        View listitem = inflator.inflate(R.layout.prescrtiptionuser_list,null,true);

        TextView docname = (TextView) listitem.findViewById(R.id.presdocname);
        TextView des = (TextView) listitem.findViewById(R.id.presdesc);
        TextView dat = (TextView) listitem.findViewById(R.id.presdate);

        PrescriptionInfo app = presinfoList.get(position);

        docname.setText(app.getDoctor());
        des.setText(app.getDescription());
        dat.setText(app.getDate());


        return listitem;
    }
}
