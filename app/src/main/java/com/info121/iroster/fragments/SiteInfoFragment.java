package com.info121.iroster.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.info121.iroster.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SiteInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SiteInfoFragment extends Fragment {

    Context mContext = getActivity();
    String mCurrentTab = "";

    public SiteInfoFragment() {
    }

    public static SiteInfoFragment newInstance(String param1) {
        SiteInfoFragment fragment = new SiteInfoFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        fragment.mCurrentTab = param1;

        return fragment;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_site_info, container, false);

        ButterKnife.bind(this, view);

        return view;
    }
}
