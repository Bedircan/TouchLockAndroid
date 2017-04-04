package com.ctis8.atoi.touchlock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Atoi on 30.03.2017.
 */
public class GiveAdvertisement extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static GiveAdvertisement newInstance() {
        GiveAdvertisement fragment = new GiveAdvertisement();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.give_advertisement, container, false);
        return view;
    }
}
