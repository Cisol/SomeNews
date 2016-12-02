package com.cisol.somenews.utils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.cisol.somenews.MainActivity;
import com.cisol.somenews.MyApplication;
import com.cisol.somenews.R;
import com.cisol.somenews.bean.JsonBean;
import com.cisol.somenews.bean.ListBean;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by cisol on 16-11-24.
 */

public class HttpUtils {
    private Gson gson;
    private ListBean.NewsItem news;
    private List<ListBean.NewsItem> newsList;
    private NetUtils netUtils;

    public HttpUtils() {
        gson = new Gson();
        newsList = new ArrayList<>();
        netUtils = new NetUtils();
    }

    public void volleyGet(int method, String url, final Handler handler) {
//        String url = "http://c.m.163.com/nc/article/headline/T1348647853363/0-20.html";
//        String url1 = "http://m.weather.com.cn/data/101010100.html";
        JsonObjectRequest request = new JsonObjectRequest(method, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String result = null;
                try {
                    result = response.getString("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ListBean listBean = gson.fromJson(result, ListBean.class);
                for (ListBean.NewsItem newsItem : listBean.data) {
                    news = new ListBean.NewsItem();
                    news.title = newsItem.title;
                    news.date = newsItem.date;
                    news.thumbnail_pic_s = newsItem.thumbnail_pic_s;
                    news.url = newsItem.url;
                    newsList.add(news);
                }
                netUtils.addAll(newsList);
                Message message = handler.obtainMessage();
                List<ListBean.NewsItem> newsItemList = netUtils.getNewsList();
                message.what = newsItemList.size();
                message.obj = netUtils.getNewsList();
                handler.sendMessage(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("请求失败", error.toString());
            }
        });
        request.setTag("abcGet");
        MyApplication.getQueues().add(request);
    }

    /**
     * 获取网络图片资源
     * @param url
     */
    public void getImage(String url, ImageView imageView) {

        ImageLoader imageLoader = new ImageLoader(MyApplication.getQueues(), new BitmapCache());

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.mipmap.fish, R.mipmap.fish2);

        imageLoader.get(url, listener);
    }

    /**
     * 图片缓存
     */
    public class BitmapCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }
    }
}
