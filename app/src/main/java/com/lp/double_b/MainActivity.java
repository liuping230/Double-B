package com.lp.double_b;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ImageView mIvMine;
    private TextView mTvRecommend;
    private TextView mTvBookCity;
    private TextView mTvBookRack;
    private ViewPager mVpContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initListener() {
        mIvMine.setOnClickListener(this);
        mTvRecommend.setOnClickListener(this);
        mTvBookCity.setOnClickListener(this);
        mTvBookRack.setOnClickListener(this);

        mVpContent.setOnPageChangeListener(this);
    }

    private void initView() {
        mVpContent = (ViewPager) findViewById(R.id.vp_content);
        mIvMine = (ImageView) findViewById(R.id.iv_mine);
        mTvRecommend = (TextView) findViewById(R.id.tv_recommend);
        mTvBookCity = (TextView) findViewById(R.id.tv_book_city);
        mTvBookRack = (TextView)  findViewById(R.id.tv_book_rack);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_mine:
                break;
            case R.id.tv_recommend:
                break;
            case R.id.tv_book_city:
                break;
            case R.id.tv_book_rack:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
