package com.info121.iguard.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;


import com.info121.iguard.AbstractActivity;
import com.info121.iguard.App;
import com.info121.iguard.R;

import com.info121.iguard.api.RestClient;
import com.info121.iguard.models.ObjectRes;
import com.info121.iguard.models.ProfileDetails;
import com.info121.iguard.utils.Util;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends AbstractActivity {
    Context mContext = ProfileActivity.this;

    @BindView(R.id.home)
    ImageView home;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

//    @BindView(R.id.profile_layout)
//    LinearLayout profileLayout;

    @BindView(R.id.name)
    TextView userName;

    @BindView(R.id.guard_id)
    TextView guardId;

    @BindView(R.id.phone_no)
    TextView phoneNo;

    @BindView(R.id.submit)
    Button mSubmit;

    @BindView(R.id.new_password)
    EditText mNewPassword;

    @BindView(R.id.confirm_password)
    EditText mConfirmPassword;


//    @BindView(R.id.list_device)
//    ListView listDevice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        //profileLayout.bringToFront();

        callGetUserProfile();

    }


//    private void callUnRegisterDevice(String DeviceID) {
//        Call<ObjectRes> call = RestClient.METRO().getApiService().UnRegisterDevice(
//                DeviceID,
//                App.SecretKey,
//                App.MobileKey
//        );
//
//        call.enqueue(new Callback<ObjectRes>() {
//            @Override
//            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
//                Log.e("UnRegister Device : ", "Success");
//
//                if (DeviceID.equals(App.DeviceID)) {
//                    System.exit(1);
//                }else {
//                    Toast.makeText(mContext, "Device is successfully remove from the list", Toast.LENGTH_SHORT).show();
//                    callGetUserProfile();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ObjectRes> call, Throwable t) {
//                Log.e("UnRegister Device : ", "Failed");
//            }
//        });
//    }

    private void callGetUserProfile() {
        Call<ObjectRes> call = RestClient.METRO().getApiService().GetUserProfile(
                App.GuardID,
                Util.getSpecialKey(),
                Util.getMobileKey(mContext)
        );

        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                Log.e("Get User Profile : ", "Success");

                displayUserInfo(response.body().getProfileDetails());

            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                Log.e("Get User Profile : ", "Failed");
            }
        });
    }

    private void displayUserInfo(List<ProfileDetails> profileDetails) {

        guardId.setText(App.GuardID);

        userName.setText(profileDetails.get(0).getGuardname());
        phoneNo.setText(profileDetails.get(0).gethPNo());

//        for (ProfileDetails p : profileDetails) {
//            if (p.getGuardcode().equals(App.DeviceID)) {
//
//            }
//        }

//        DeviceListAdapter deviceListAdapter = new DeviceListAdapter(profileDetails, mContext);
//        listDevice.setAdapter(deviceListAdapter);
//        setListViewHeightBasedOnItems(listDevice);

    }

    @OnClick(R.id.home)
    public void homeOnClick() {
        finish();
    }

    @OnClick(R.id.submit)
    public void submitOnClick() {
        if (validPassword()) {
            callUpdatePassword();
        }
    }

    private void callUpdatePassword() {
        Call<ObjectRes> call = RestClient.METRO().getApiService().UpdatePassword(
                App.GuardID,
                mNewPassword.getText().toString(),
                Util.getSpecialKey(),
                Util.getMobileKey(mContext)
        );

        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                Log.e("Update Password : ", "Success");

                if (response.body().getResponsemessage().equalsIgnoreCase("Valid")) {
                    showSuccessfulMessage();
                }

            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                Log.e("Update Password : ", "Failed");
            }
        });

    }

    private boolean validPassword() {
        if (mNewPassword.getText().length() == 0) {
            mNewPassword.setError("New password should not be left blank");
            mNewPassword.setFocusable(true);
            return false;
        }

        if (mConfirmPassword.getText().length() == 0) {
            mConfirmPassword.setError("Confirm password should not be left blank");
            mConfirmPassword.setFocusable(true);
            return false;
        }

        if (!mNewPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {
            mConfirmPassword.setError("New and confirm password does not match");
            mConfirmPassword.setFocusable(true);
            return false;
        }

        return true;
    }


        private void showSuccessfulMessage() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

            alertDialog.setMessage("Your password has been changed successfully. Do you want to logout now.");


        // On pressing Settings button
        alertDialog.setPositiveButton("Yes", (dialog, which) -> {
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());

        // Showing Alert Message
        alertDialog.show();
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

//    @Subscribe
//    public void onEvent(String deviceID) {
//        callUnRegisterDevice(deviceID);
//    }

}