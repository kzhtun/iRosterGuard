package com.info121.iguard.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.info121.iguard.App;
import com.info121.iguard.R;
import com.info121.iguard.api.RestClient;
import com.info121.iguard.models.ObjectRes;
import com.info121.iguard.utils.Util;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerActivity extends AppCompatActivity {

    private Context mContext = ScannerActivity.this;

    @BindView(R.id.result)
    TextView mResult;

    @BindView(R.id.zxscan)
    ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        ButterKnife.bind(this);

        Dexter.withContext(mContext)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        mScannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
                            @Override
                            public void handleResult(Result rawResult) {
                              //  Toast.makeText(mContext, rawResult.getText(), Toast.LENGTH_SHORT).show();
                             //   mResult.setText(rawResult.getText() + "\n\n" + App.SiteAddress );

                                App.SiteInfo = rawResult.getText();

                            //  finish();

                                final Handler handler = new Handler(Looper.getMainLooper());
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                       // IN,SEH,Seahill,62,DAY,0800Hrs,08:00,20:00,21/09/2022,14:29
                                        String[] siteInfo = App.SiteInfo.split(",");

                                       // Log.e("Site Info",  siteInfo.toString());

                                        //Toast.makeText(mContext, App.LogType + "\n" + siteInfo[2] + "\n" + App.SiteAddress, Toast.LENGTH_LONG).show();
                                       showCompleteDialog(siteInfo);

                                    }
                                }, 1000);

                            }
                        });

                        mScannerView.startCamera();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(mContext, "You must accept this permission.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                })
                .check();
    }


    private void callSaveCheck(String[] siteInfo, String remarks){
        ProgressDialog progressDialog = new ProgressDialog(ScannerActivity.this);

        progressDialog.setMessage("Sending ...");
        progressDialog.show();

        Log.e("Mobile Key: " , Util.getMobileKey(mContext));

        String[] checkDate = siteInfo[8].split("/");
        String checkDateTime = checkDate[2] + "-" + checkDate[1] + "-" + checkDate[0] + " " + siteInfo[9];

        //@GET("savecheck/{guardcode},{guardname},{type},{sitecode},{checkdatetime},{location},{remarks},{secretkey},{mobilekey}")
        Call<ObjectRes> call = RestClient.METRO().getApiService().SaveCheck(
                App.currentUserProfile.getGuardcode(),
                App.currentUserProfile.getGuardname(),
                siteInfo[0],
                siteInfo[2],
                checkDateTime,
                 "test loc", //App.SiteAddress.replaceAll( ",", "#.#"),
                remarks ,
                Util.getSpecialKey(),
                Util.getMobileKey(mContext)

        );

        call.enqueue(new Callback<ObjectRes>() {
            @Override
            public void onResponse(Call<ObjectRes> call, Response<ObjectRes> response) {
                Log.e("Save Check : ", "Success");

                if(progressDialog.isShowing()) progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ObjectRes> call, Throwable t) {
                Log.e("Save Check : ", "Failed");

                if(progressDialog.isShowing()) progressDialog.dismiss();
            }
        });

    }


    private void showCompleteDialog(String[] siteInfo) {
        Dialog dialog;
        dialog = new Dialog(ScannerActivity.this);

        dialog.setContentView(R.layout.dialog_complete);
        dialog.setTitle("");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView sitename = dialog.findViewById(R.id.title);
        TextView name = dialog.findViewById(R.id.name);
        TextView date = dialog.findViewById(R.id.date);
        TextView time = dialog.findViewById(R.id.time);
        TextView location = dialog.findViewById(R.id.location);
        EditText remarks = dialog.findViewById(R.id.remarks);

        String[] checkDate = siteInfo[8].split("/");
        String checkDateTime = checkDate[2] + "-" + checkDate[1] + "-" + checkDate[0] + " " + siteInfo[9];

        sitename.setText(siteInfo[2]);
        name.setText(App.GuardName + "(" + App.GuardID + ")");
        date.setText(siteInfo[8]);
        time.setText(siteInfo[9]);
        location.setText(App.SiteAddress);

       // location.setText(App.fullAddress.replace("#.#", ","));

        Button complete = dialog.findViewById(R.id.complete);


        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSaveCheck(siteInfo, remarks.getText().toString());
                dialog.dismiss();
            }
        });

        // resize dialog
        Rect displayRectangle = new Rect();
        Window window = ScannerActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = (int) (displayRectangle.width() * 0.85f);

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


}