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

public class PrescriptionItemList  extends ArrayAdapter<PrescriptionItem> {

    private Activity context;
    private List<PrescriptionItem> prescitemList;

    public PrescriptionItemList(Activity context, List<PrescriptionItem> prescitemList){
        super(context,R.layout.prescription_list,prescitemList);
        this.context = context;
        this.prescitemList= prescitemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflator = context.getLayoutInflater();

        View listitem = inflator.inflate(R.layout.prescription_list,null,true);

        TextView itemname = (TextView) listitem.findViewById(R.id.presitemname);
        TextView qua = (TextView) listitem.findViewById(R.id.presquantityofitem);
       //TextView desc = (TextView) listitem.findViewById(R.id.prespriceofitem);

        PrescriptionItem app = prescitemList.get(position);

        itemname.setText(app.getMedicine());
        qua.setText(String.valueOf(app.getQuantity()));

        return listitem;
    }
}
