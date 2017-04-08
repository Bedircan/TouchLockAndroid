package com.ctis8.atoi.touchlock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

/**
 * Created by Atoi on 30.03.2017.
 */
public class AdvertisementList extends Fragment {

    SparseArray<MyExpandableListAdapter.Group> groups = new SparseArray<MyExpandableListAdapter.Group>();

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
        ExpandableListView advertisementList = (ExpandableListView) view.findViewById(R.id.advertisementList);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(getActivity(), groups);
        advertisementList.setAdapter(adapter);
        createData();
        return view;
    }
    public void createData() {
        for (int j = 0; j < 5; j++) {
            MyExpandableListAdapter.Group group = new MyExpandableListAdapter.Group ("Test " + j);
            for (int i = 0; i < 5; i++) {
                group.children.add("Sub Item" + i);
            }
            groups.append(j, group);
        }
    }

}
