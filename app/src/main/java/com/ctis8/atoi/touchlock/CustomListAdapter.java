package com.ctis8.atoi.touchlock;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Atoi on 11.04.2017.
 */
public class CustomListAdapter extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<Advertisement> advertisementList;

    public CustomListAdapter(Activity context, ArrayList<Advertisement> advertisementList) {
        super(context, R.layout.list_row);

        this.context = context;
        this.advertisementList = advertisementList;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_row, null, true);
        
        TextView txtTitle = (TextView) rowView.findViewById(R.id.ItemName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        txtTitle.setText(advertisementList.get(position).getTitle());
        imageView.setImageBitmap(advertisementList.get(position).getIcon());

        return rowView;
    };
}
