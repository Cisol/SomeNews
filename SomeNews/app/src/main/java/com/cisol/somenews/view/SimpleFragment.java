package com.cisol.somenews.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cisol.somenews.R;
import com.cisol.somenews.bean.ListBean;
import com.cisol.somenews.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by cisol on 16-11-19.
 */

public class SimpleFragment extends Fragment {
    private View view;
    private String mTitle;
    private List<ListBean.NewsItem> newsList;
    private static final String BUNDLE_TITLE = "title";
    private static final String BUNDLE_CONTENT= "content";
    private HttpUtils httpUtils = new HttpUtils();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment, container, false);
        ListView listView = (ListView) view.findViewById(R.id.newsList);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(BUNDLE_TITLE);
            newsList = (List<ListBean.NewsItem>) bundle.getParcelableArrayList(BUNDLE_CONTENT).get(0);
        }
        System.out.println("newsList长度:" + newsList.size());
        listView.setAdapter(new ContentAdapter(this.getActivity(), newsList, R.layout.fragment_list_item));
        listView.setEmptyView(view.findViewById(R.id.emptyView));
        return view;
    }

    public static SimpleFragment newInstance(String title, List<ListBean.NewsItem> newsList) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        ArrayList list = new ArrayList();
        list.add(newsList);
        bundle.putParcelableArrayList(BUNDLE_CONTENT, list);
        SimpleFragment fragment = new SimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    class ContentAdapter extends BaseAdapter {

        private List<ListBean.NewsItem> newsList;
        private int resource;
        private LayoutInflater mInflater;

        public ContentAdapter(Context context, List<ListBean.NewsItem> newsList, int resource) {
            this.newsList = newsList;
            this.resource = resource;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public Object getItem(int i) {
            return newsList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            System.out.println("getView方法执行!");
            ViewHolder viewHolder = null;
            //如果缓存view为空,则需要创建view
            if (view == null) {
                viewHolder = new ViewHolder();
                //根据自定义的Item布局加载布局
                view = mInflater.inflate(resource, viewGroup, false);
                viewHolder.img = (ImageView) view.findViewById(R.id.newsIcon);
                viewHolder.title = (TextView) view.findViewById(R.id.newsTitle);
                viewHolder.content = (TextView) view.findViewById(R.id.newsTime);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            httpUtils.getImage(newsList.get(i).thumbnail_pic_s, viewHolder.img);
            viewHolder.title.setText(newsList.get(i).title);
            viewHolder.content.setText(newsList.get(i).date);
            return view;
        }
    }

    static class ViewHolder {
        public ImageView img;
        public TextView title;
        public TextView content;
    }

}
