package com.cisol.somenews;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by cisol on 16-11-23.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化XUtils
        x.Ext.init(this);
        //设置debug模式
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }

}
