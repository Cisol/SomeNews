package com.cisol.somenews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cisol.somenews.view.SimpleFragment;
import com.cisol.somenews.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;
    private HorizontalScrollView mScrollView;

    private List<String> mTitles = Arrays.asList("推荐", "热点", "国际", "科技", "视频", "社会", "图片", "数码", "动漫");
    private List<SimpleFragment> mContents = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        init_views();
        init_Datas();

        mIndicator.setVisibleTabCount(4);  //设置Tab个数
        mIndicator.setTabItemTitles(mTitles);  //设置title
        mIndicator.setScrollView(mScrollView);
        mViewPager.setAdapter(mAdapter);  //设置ViewPager的适配器
        mIndicator.setViewPager(mViewPager, 0);

    // Example of a call to a native method
//    tv.setText(stringFromJNI());
    }

    private void init_Datas() {
        for (String title : mTitles) {
            SimpleFragment fragment = SimpleFragment.newInstance(title);
            mContents.add(fragment);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };

    }

    private void init_views() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        mScrollView = (HorizontalScrollView) findViewById(R.id.idScroll);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
