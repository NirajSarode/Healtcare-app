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

public class CartItemList extends ArrayAdapter<CartItem> {
private Activity context;
private List<CartItem> cartItemList;

public CartItemList(Activity context,List<CartItem> cartItemList){
        super(context,R.layout.cartitem_list,cartItemList);
        this.context = context;
        this.cartItemList = cartItemList;
        }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflator = context.getLayoutInflater();
        View listitem = inflator.inflate(R.layout.cartitem_list,null,true);

        TextView itemname = (TextView) listitem.findViewById(R.id.itemname);
        TextView quan = (TextView) listitem.findViewById(R.id.quantityofitem);
        TextView price = (TextView) listitem.findViewById(R.id.priceofitem);

        CartItem car = cartItemList.get(position);

        itemname.setText(car.getMedicines());
        quan.setText(String.valueOf(car.getQuantity()));
        price.setText(String.valueOf(car.getPrice()));

    return listitem;

    }
}
