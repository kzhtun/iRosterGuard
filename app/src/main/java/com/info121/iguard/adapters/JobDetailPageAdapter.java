package com.info121.iguard.adapters;


import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.info121.iguard.fragments.AvailableRemarkFragment;
import com.info121.iguard.fragments.ConfirmFragment;
import com.info121.iguard.fragments.SiteInfoFragment;


public class JobDetailPageAdapter extends FragmentStatePagerAdapter {

    public JobDetailPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: return SiteInfoFragment.newInstance("SITE INFO");
            case 1: return AvailableRemarkFragment.newInstance("AVAILABLE");
            case 2: return ConfirmFragment.newInstance("CONFIRM");
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Log.e("PageTitle", position + "");



        switch (position){
            case 0: return "SITE INFO";
            case 1: return "AVAILABLE";
            case 2: return "CONFIRM";
            default: return null;
        }


    }
}
