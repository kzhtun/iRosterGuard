package com.info121.iguard.activities;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Handler;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.info121.iguard.App;
import com.info121.iguard.R;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.info121.iguard.api.RestClient;
import com.info121.iguard.models.ObjectRes;
import com.info121.iguard.utils.PrefDB;
import com.info121.iguard.utils.Util;

public class LoginActivity extends AppCompatActivity {
    String TAG = this.getClass().getSimpleName();


    Tag myTag;
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    Boolean NFCSupport = false;
    boolean writeMode;

    // -- NFC End --//

    PrefDB prefDB;


    @BindView(R.id.ver)
    TextView mUiVersion;

    @BindView(R.id.date)
    TextView mDate;

    @BindView(R.id.time)
    TextView mTime;

    @BindView(R.id.user_name)
    EditText mUserName;

    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.remember_me)
    CheckBox mRemember;

    Context mContext = LoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        prefDB = new PrefDB(getApplicationContext());

        String dateString = Util.convertDateToString(Calendar.getInstance().getTime(), "EEE dd MMM yyyy");
        String timeString = Util.convertDateToString(Calendar.getInstance().getTime(), "hh:mm a");

        mDate.setText(dateString);
        mTime.setText(timeString);

//        final Handler timer = new Handler(getMainLooper());
//
//        timer.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                showTime();
//                timer.postDelayed(this, 60000);
//            }
//        }, 60000);

        mUiVersion.setText("Ver " + Util.getVersionName(mContext));
        initializeNFC();


        if (prefDB.getBoolean(App.CONST_REMEMBER_ME)) {
            mUserName.setText(prefDB.getString(App.GuardID));
            mRemember.setChecked(true);
        }

        Log.e("CODE : ", Util.getSpecialKey() + "," +
                Util.getMobileKey(mContext));


//        mUserName.setText("M000764");
//        mPassword.setText("metropolis");

        mUserName.setText("M000764");
        mPassword.setText("test");

    }

    private void showTime() {
        String dateString = Util.convertDateToString(Calendar.getInstance().getTime(), "hh:mm a");
        mTime.setText(dateString.toString());
    }

    @OnClick(R.id.login)
    public void loginOnClick() {
        mProgressBar.setVisibility(View.VISIBLE);

        App.GuardID = mUserName.getText().toString();
        App.LastLogin = "Last Login : " + Util.convertDateToString(Calendar.getInstance().getTime(), "EEE, dd MMM yyyy, hh:mm a");


        if (mPassword.getText().toString().length() == 0) {
            mPassword.setError("Password can not left blank.");
            mPassword.setFocusable(true);
            mPassword.requestFocus();

        }

        if (mRemember.isChecked()) {
            prefDB.putBoolean(App.CONST_REMEMBER_ME, true);
           // prefDB.putString(App.GuardID, mUserName.getText().toString());
        }else {
            prefDB.putBoolean(App.CONST_REMEMBER_ME, false);
        }


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    App.FCNToken = task.getResult().getToken();
                    Log.e("NEW FCM Token: ", App.FCNToken);

                    callValidateUser();
                });

       // callValidateUser();


    }

    public void callValidateUser() {
        Call<ObjectRes> call = RestClient.METRO().getApiService().ValidateUser(
                mUserName.getText().toString().trim(),
                mPassword.getText().toString().trim(),
                "N",
                App.FCNToken,
                Util.getSpecialKey(),
                Util.getMobileKey(mContext)
        );

        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                mProgressBar.setVisibility(View.GONE);
                if (response.body().getResponsemessage().equalsIgnoreCase("Valid")) {

                    App.GuardID = mUserName.getText().toString().trim();
                    App.AutToken = response.body().getToken();
                    App.LastLogin = response.body().getLastlogin();

                    loginSuccessful();

                } else {
                    mUserName.setError("Invalid user name or password.");
                    mUserName.requestFocus();
                }
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                Log.e("Failed", t.getMessage());
            }
        });
    }

    private void loginSuccessful() {
        mProgressBar.setVisibility(View.GONE);


        // instantiate wiht new Token
        //RestClient.Dismiss();

        prefDB.putString(App.CONST_USER_NAME, App.GuardID);
//        prefDB.putString(App.CONST_DEVICE_ID, App.deviceID);
//        prefDB.putLong(App.CONST_TIMER_DELAY, App.timerDelay);

        // location
        //startLocationService();

        if (mRemember.isChecked())
            prefDB.putBoolean(App.CONST_REMEMBER_ME, true);
        else
            prefDB.putBoolean(App.CONST_REMEMBER_ME, false);

        Log.e(TAG, "Login Successful");

        callGetUserProfile();

    }

    @OnClick(R.id.forgot_password)
    public void forgotPasswordOnClick() {
        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
    }


    // --- NFC Related ---//


    private void initializeNFC() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            //finish();
            NFCSupport = false;
        } else {
            NFCSupport = true;
            readFromIntent(getIntent());

            pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
            tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
            writeTagFilters = new IntentFilter[]{tagDetected};
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setMessage("Identifying ...");
        dialog.show();

        setIntent(intent);
        readFromIntent(intent);

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            final Handler timer = new Handler(getMainLooper());
            timer.postDelayed(() -> {
                loginOnClick();
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }, 500);


        }

        Log.e("NFC :", "onNewIntent");


    }

    private void readFromIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }

        Log.e("NFC :", "readFromIntent");

        // loginOnClick();
    }

    private void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) return;

        String text = "";
//        String tagId = new String(msgs[0].getRecords()[0].getType());
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; // Get the Text Encoding
        int languageCodeLength = payload[0] & 0063; // Get the Language Code, e.g. "en"
        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");

        try {
            // Get the Text
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
        }

        //  tvNFCContent.setText("NFC Content: " + text);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (NFCSupport)
            WriteModeOff();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NFCSupport)
            WriteModeOn();
    }

    private void WriteModeOn() {
        writeMode = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
    }

    /******************************************************************************
     **********************************Disable Write*******************************
     ******************************************************************************/
    private void WriteModeOff() {
        writeMode = false;
        nfcAdapter.disableForegroundDispatch(this);
    }


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

                App.currentUserProfile = response.body().getProfileDetails().get(0);

                // login successful
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                Log.e("Get User Profile : ", "Failed");
            }
        });
    }

}
