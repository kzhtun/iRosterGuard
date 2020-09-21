package com.info121.iguard.adapters;


import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.info121.iguard.fragments.ShiftFragment;


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
