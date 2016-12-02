package com.cisol.somenews.utils;

import android.os.Handler;
import android.os.Message;
import com.android.volley.Request;
import com.cisol.somenews.bean.ListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cisol on 16-11-28.
 */

public class GetNewsThread implements Runnable{

    private HttpUtils httpUtils;
    private String url;
    private Handler handler;
    private NetUtils netUtils;

    public GetNewsThread(HttpUtils httpUtils, String url ,Handler handler) {
        this.httpUtils = httpUtils;
        this.url = url;
        this.handler = handler;
        this.netUtils = new NetUtils();
    }

    @Override
    public void run() {
        httpUtils = new HttpUtils();
        httpUtils.volleyGet(Request.Method.GET, url, handler);
    }
}
