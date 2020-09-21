package com.info121.iguard;

import android.content.Context;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;


/**
 * Created by KZHTUN on 3/8/2016.
 */
public class AbstractActivity extends AppCompatActivity {
    final String TAG = AbstractActivity.class.getSimpleName();

//    Toolbar mToolbar;
//    DrawerLayout mDrawerLayout;
//    NavigationView mNavigationView;

    @Override
    protected void onStart() {
        super.onStart();

        // event bus register
        EventBus.getDefault().register(this);
        Log.e(TAG, "EventBus Registered on Activity ... " + this.getLocalClassName());
    }

    @Override
    protected void onStop() {
        // event bus unregister
        EventBus.getDefault().unregister(this);
        Log.e(TAG, "EventBus Un-Registered on Activity ... " + this.getLocalClassName());

        super.onStop();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Subscribe
    public void onEvent(Throwable t) {

    }
}
