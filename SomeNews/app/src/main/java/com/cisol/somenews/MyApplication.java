package com.cisol.somenews;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by cisol on 16-11-23.
 */

public class MyApplication extends Application {

    private static RequestQueue queues;

    @Override
    public void onCreate() {
        queues = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getQueues() {
        return queues;
    }

}
