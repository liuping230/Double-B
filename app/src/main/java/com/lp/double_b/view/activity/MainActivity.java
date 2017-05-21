package com.lp.double_b.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.lp.double_b.R;
import com.lp.double_b.view.adapter.BookFragmentAdapter;
import com.lp.double_b.view.fragment.BookCityFragment;
import com.lp.double_b.view.fragment.BookRackFragment;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ImageView mIvMine;
    private TextView mTvRecommend;
    private TextView mTvBookCity;
    private TextView mTvBookRack;
    private ViewPager mVpContent;
    private ArrayList<Fragment> mFragments;
    private BookFragmentAdapter mFragmentAdapter;
    private ImageView mIvAddBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initImageLoader(getApplicationContext());
        initView();
        initListener();
        initData();
    }

    private void initData() {

        mFragments = new ArrayList<Fragment>();
        mFragments.add(new BookCityFragment());
        mFragments.add(new BookRackFragment());
        mFragmentAdapter = new BookFragmentAdapter(getSupportFragmentManager(),mFragments);
        mVpContent.setAdapter(mFragmentAdapter);
    }

    private void initListener() {
        mIvMine.setOnClickListener(this);
        mTvRecommend.setOnClickListener(this);
        mTvBookCity.setOnClickListener(this);
        mTvBookRack.setOnClickListener(this);
        mIvAddBook.setOnClickListener(this);

        mVpContent.setOnPageChangeListener(this);
    }

    private void initView() {
        mVpContent = (ViewPager) findViewById(R.id.vp_content);
        mIvMine = (ImageView) findViewById(R.id.iv_mine);
        mTvRecommend = (TextView) findViewById(R.id.tv_recommend);
        mTvBookCity = (TextView) findViewById(R.id.tv_book_city);
        mTvBookRack = (TextView)  findViewById(R.id.tv_book_rack);
        mIvAddBook = (ImageView)  findViewById(R.id.add_book);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_mine: //点击“我的"

                break;
            case R.id.add_book: //点击“添加书籍"
                Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_recommend://点击“推荐”
                break;
            case R.id.tv_book_city:
                mVpContent.setCurrentItem(0);
                setBookTitleColor(true);
                break;
            case R.id.tv_book_rack:
                mVpContent.setCurrentItem(1);
                setBookTitleColor(false);
                break;
        }
    }

    private void setBookTitleColor(boolean isBookCitySelected) {
        mTvBookCity.setTextColor(isBookCitySelected ? Color.RED : Color.GRAY);
        mTvBookRack.setTextColor(isBookCitySelected ? Color.GRAY : Color.RED);
    }

    /**
     * 页面滑动时调用
     * @param position 页面位置是哪一页
     * @param positionOffset 滑动了多少距离
     * @param positionOffsetPixels 滑动了多少距离（转换成了px）
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        Log.d("onPageScrolled: ","position = " + position + "");
    }

    /**
     * 页面选中时调用此方法
     * @param position 当前页面的位置
     */
    @Override
    public void onPageSelected(int position) {
        /*if (0 == position) {
            setBookTitleColor(true);

        } else {
            setBookTitleColor(false);
        }*/
        setBookTitleColor(0 == position);
        mTvRecommend.setVisibility(0 == position ? View.VISIBLE : View.INVISIBLE);
        mIvMine.setVisibility(0 == position ? View.VISIBLE : View.INVISIBLE);
        mIvAddBook.setVisibility(0 == position ? View.INVISIBLE : View.VISIBLE);

    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)//
                .threadPriority(Thread.NORM_PRIORITY - 2)//
                .denyCacheImageMultipleSizesInMemory()//
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .memoryCache(new LruMemoryCache(4 * 1024 * 1024)).tasksProcessingOrder(QueueProcessingType.LIFO)//
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}
