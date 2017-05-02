package com.ctis8.atoi.touchlock;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Atoi on 11.04.2017.
 */
public class CustomListAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<Reservation> reservationList = new ArrayList<>();
    private ArrayList<Property> propertyList = new ArrayList<>();
    private HashMap<ArrayList<Reservation>, ArrayList<Property>> tempList = new HashMap<>();
    private LayoutInflater inflater;
    private Handler handler;

    public CustomListAdapter(Activity context, ArrayList<Reservation> reservationList, ArrayList<Property> propertyList) {
        super();
        this.context = context;
        this.reservationList = reservationList;
        this.propertyList = propertyList;
        inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return reservationList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentView = convertView;

        if(currentView==null) {
            currentView = inflater.inflate(R.layout.list_row, parent, false);
        }

        TextView title = (TextView)currentView.findViewById(R.id.title);
        title.setText(propertyList.get(position).getTitle());

        ImageView image = (ImageView)currentView.findViewById(R.id.icon);
        Picasso.with(context).load(propertyList.get(position).getUrl()).resize(50, 50).into(image);

        return currentView;
    }

    public void refreshList() {
        Toast.makeText(context, propertyList.size()+"", Toast.LENGTH_LONG).show();
        handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        };
        new Thread(runnable).start();
    }
}
