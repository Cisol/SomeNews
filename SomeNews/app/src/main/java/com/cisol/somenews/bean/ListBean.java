package com.cisol.somenews.bean;

import java.util.List;

/**
 * Created by cisol on 16-11-23.
 */

public class ListBean {

    public List<NewsItem> data;

    public static class NewsItem {
        public String title;
        public String date;
        public String thumbnail_pic_s;
        public String url;
    }
}
