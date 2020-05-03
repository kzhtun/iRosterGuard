package com.info121.iroster.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.info121.iroster.fragments.ShiftFragment;


public class SummaryPageAdapter extends FragmentStatePagerAdapter {

    public SummaryPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: return ShiftFragment.newInstance("DAY");
            case 1: return ShiftFragment.newInstance("NIGHT");
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Log.e("PageTitle", position + "");



        switch (position){
            case 0: return "DAY";
            case 1: return "NIGHT";
            default: return null;
        }


    }
}
