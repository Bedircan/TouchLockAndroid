package com.ctis8.atoi.touchlock;

/**
 * Created by Atoi on 30.03.2017.
 */
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.Locale;

/**
 * Created by Atoi on 27.11.2016.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context _context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        _context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return  SearchAdvertisement.newInstance();
            case 1:
                return  AdvertisementList.newInstance();
            case 2:
                return  GiveAdvertisement.newInstance();
        }
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "Search Advertisement";
            case 1:
                return "Advertisements";

            case 2:
                return "Give Advertisement";
        }
        return null;
    }
}
