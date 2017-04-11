package com.ctis8.atoi.touchlock;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Atoi on 30.03.2017.
 */
public class AdvertisementList extends Fragment {

    private ListView list;

    String[] itemname ={
            "Ankara",
            "İstanbul",
            "Adana",
            "Antalya",
            "İzmir",
            "Denizli",
            "Bursa",
            "Eskisehir"
    };

    Integer[] imgid={
            R.drawable.house,
            R.drawable.house,
            R.drawable.house,
            R.drawable.house,
            R.drawable.house,
            R.drawable.house,
            R.drawable.house,
            R.drawable.house    ,
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static AdvertisementList newInstance() {
        AdvertisementList fragment = new AdvertisementList();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.advertisement_list, container, false);
        CustomListAdapter adapter=new CustomListAdapter(getActivity(), itemname, imgid);
        final Advertisement advertisement = new Advertisement("Ankara", true, "private room", 5, "Güzel ev", "13/04/2017", "18/04/2017");
        list = (ListView)view.findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                messageDialog(advertisement);
            }
        });
        return view;
    }

    public void messageDialog(Advertisement advertisement) {

        final Dialog myDialog = new Dialog(getActivity());
        myDialog.setContentView(R.layout.detail_advertisement);
        myDialog.setTitle("My Advertisement");
        myDialog.setCancelable(true);

        TextView tvCity = (TextView) myDialog.findViewById(R.id.tvCity);
        tvCity.setText(advertisement.getCity());

        TextView tvPet = (TextView) myDialog.findViewById(R.id.tvPet);
        tvPet.setText(advertisement.isPetAllowed()+"");

        TextView tvType = (TextView) myDialog.findViewById(R.id.tvType);
        tvType.setText(advertisement.getType());

        TextView tvGuest = (TextView) myDialog.findViewById(R.id.tvNumGuest);
        tvType.setText(advertisement.getNumGuest()+"");

        TextView tvDesc = (TextView) myDialog.findViewById(R.id.tvDesc);
        tvDesc.setText(advertisement.getDescription());

        TextView tvDate = (TextView) myDialog.findViewById(R.id.tvDate);
        tvDate.setText(advertisement.getCheckInDate()+" --- "+advertisement.getCheckOutDate());

        myDialog.show();

    }

}
