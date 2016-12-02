package com.cisol.somenews.utils;

import android.os.Handler;
import android.support.v4.view.NestedScrollingChild;

import com.cisol.somenews.bean.ListBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cisol on 16-11-28.
 */

public class NetUtils {

    private List<ListBean.NewsItem> newsList;

    public NetUtils() {
        this.newsList = new ArrayList<>();
    }

    public void getNews(HttpUtils httpUtils, String url, Handler handler) {
        GetNewsThread getNewsThread = new GetNewsThread(httpUtils, url, handler);
        getNewsThread.run();
//        return newsList;
    }

    public void addAll(List<ListBean.NewsItem> newsList) {
        this.newsList.addAll(newsList);
    }

    public List<ListBean.NewsItem> getNewsList() {
        return newsList;
    }
}
