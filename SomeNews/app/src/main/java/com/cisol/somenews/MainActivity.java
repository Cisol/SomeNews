package com.cisol.somenews;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.HorizontalScrollView;
import com.cisol.somenews.bean.ListBean;
import com.cisol.somenews.utils.HttpUtils;
import com.cisol.somenews.utils.NetUtils;
import com.cisol.somenews.view.SimpleFragment;
import com.cisol.somenews.view.ViewPagerIndicator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.LogRecord;

public class MainActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;
    private HorizontalScrollView mScrollView;

    private List<String> mTitles = Arrays.asList("推荐", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚");
    private List<SimpleFragment> mContents = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;
    private HttpUtils httpUtils = new HttpUtils();
    private List<ListBean.NewsItem> newsList;
    private NetUtils netUtils = new NetUtils();
    private Handler handler;
    private boolean isFirstFlush = true;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        init_views();
        init_Datas();

        netUtils.getNews(httpUtils, "http://v.juhe.cn/toutiao/index?type=top&key=MyAppKey", handler);

        mIndicator.setVisibleTabCount(4);  //设置Tab个数
        mIndicator.setTabItemTitles(mTitles);  //设置title
        mIndicator.setScrollView(mScrollView);
        mViewPager.setAdapter(mAdapter);  //设置ViewPager的适配器
        mIndicator.setViewPager(mViewPager, 0);

        mIndicator.setOnPageChangedListener(new ViewPagerIndicator.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position) {
                    case 0:
                        netUtils.getNews(httpUtils, "http://v.juhe.cn/toutiao/index?type=top&key=MyAppKey", handler);
                        break;
                    case 1:
                        netUtils.getNews(httpUtils, "http://v.juhe.cn/toutiao/index?type=shehui&key=MyAppKey", handler);
                        break;
                    case 2:
                        netUtils.getNews(httpUtils, "http://v.juhe.cn/toutiao/index?type=guonei&key=MyAppKey", handler);
                            break;
                    case 3:
                        netUtils.getNews(httpUtils, "http://v.juhe.cn/toutiao/index?type=guoji&key=MyAppKey", handler);
                            break;
                    case 4:
                        netUtils.getNews(httpUtils, "http://v.juhe.cn/toutiao/index?type=yule&key=MyAppKey", handler);
                            break;
                    case 5:
                        netUtils.getNews(httpUtils, "http://v.juhe.cn/toutiao/index?type=tiyu&key=MyAppKey", handler);
                            break;
                    case 6:
                        netUtils.getNews(httpUtils, "http://v.juhe.cn/toutiao/index?type=junshi&key=MyAppKey", handler);
                            break;
                    case 7:
                        netUtils.getNews(httpUtils, "http://v.juhe.cn/toutiao/index?type=keji&key=MyAppKey", handler);
                            break;
                    case 8:
                        netUtils.getNews(httpUtils, "http://v.juhe.cn/toutiao/index?type=caijing&key=MyAppKey", handler);
                            break;
                    case 9:
                        netUtils.getNews(httpUtils, "http://v.juhe.cn/toutiao/index?type=shishang&key=MyAppKey", handler);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    // Example of a call to a native method
//    tv.setText(stringFromJNI());
    }

    private void init_Datas() {
        newsList = new ArrayList<>();
//        ListBean.NewsItem news = new ListBean.NewsItem();
//        news.title = "测试";
//        news.thumbnail_pic_s = "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2713426324,2635858931&fm=58";
//        news.date = "2016.11.30";
//        news.url = "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2713426324,2635858931&fm=58";
//        newsList.add(news);

        for (String title : mTitles) {
            SimpleFragment fragment = SimpleFragment.newInstance(title, newsList);
            mContents.add(fragment);
        }


        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private  int mChildCount = 0;

            @Override
            public void notifyDataSetChanged() {
                mChildCount = getCount();
                super.notifyDataSetChanged();
            }

            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }

            @Override
            public int getItemPosition(Object object) {
                if (mChildCount > 0) {
                    mChildCount--;
                    return POSITION_NONE;
                }
                return super.getItemPosition(object);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                System.out.println("handleMessage方法被调用!");
                super.handleMessage(msg);
                List<ListBean.NewsItem> list;
                list = (List<ListBean.NewsItem>) msg.obj;
//                for(ListBean.NewsItem news : newsList) {
//                    System.out.println(news.title + "\n"
//                            + news.url + "\n" + news.imgsrc + "\n"
//                            + news.lmodify + "\n");
//                }

                newsList.clear();
                newsList.addAll(list);
//                mViewPager.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                System.out.println("当前线程:" +  Thread.currentThread().getName());
            }
        };
    }

    private void init_views() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        mScrollView = (HorizontalScrollView) findViewById(R.id.idScroll);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getQueues().cancelAll("abcGet");
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
