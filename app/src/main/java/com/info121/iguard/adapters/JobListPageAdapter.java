package com.info121.iguard.adapters;


import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.info121.iguard.fragments.AvailableFragment;
import com.info121.iguard.fragments.ShortageFragment;


public class JobListPageAdapter extends FragmentStatePagerAdapter {

    public JobListPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: return ShortageFragment.newInstance("SHORTAGE");
            case 1: return AvailableFragment.newInstance("AVAILABLE");
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
            case 0: return "SHORTAGE";
            case 1: return "AVAILABLE";
            default: return null;
        }


    }
}
