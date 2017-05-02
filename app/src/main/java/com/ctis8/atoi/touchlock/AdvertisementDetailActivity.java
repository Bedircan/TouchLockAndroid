package com.ctis8.atoi.touchlock;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Atoi on 1.05.2017.
 */
public class AdvertisementDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_detail);

        Advertisement advertisement = (Advertisement) getIntent().getExtras().getSerializable("reservation");


    }
}
