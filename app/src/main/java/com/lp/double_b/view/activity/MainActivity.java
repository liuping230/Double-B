package com.lp.double_b.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
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
    private ImageView mIvDeleteBook;
    private BookRackFragment mBookRackFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //initImageLoader(getApplicationContext());
        initView();
        initListener();
        initData();
    }

    private void initData() {

        mFragments = new ArrayList<Fragment>();
        mFragments.add(new BookCityFragment());
        mBookRackFragment = new BookRackFragment();
        mFragments.add(mBookRackFragment);
        mFragmentAdapter = new BookFragmentAdapter(getSupportFragmentManager(),mFragments);
        mVpContent.setAdapter(mFragmentAdapter);
    }

    private void initListener() {
        mIvMine.setOnClickListener(this);
        mTvRecommend.setOnClickListener(this);
        mTvBookCity.setOnClickListener(this);
        mTvBookRack.setOnClickListener(this);
        mIvAddBook.setOnClickListener(this);
        mIvDeleteBook.setOnClickListener(this);
        mVpContent.setOnPageChangeListener(this);
    }

    private void initView() {
        mVpContent = (ViewPager) findViewById(R.id.vp_content);
        mIvMine = (ImageView) findViewById(R.id.iv_mine);
        mTvRecommend = (TextView) findViewById(R.id.tv_recommend);
        mTvBookCity = (TextView) findViewById(R.id.tv_book_city);
        mTvBookRack = (TextView)  findViewById(R.id.tv_book_rack);
        mIvAddBook = (ImageView)  findViewById(R.id.add_book);
        mIvDeleteBook = (ImageView)  findViewById(R.id.delete_book);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_mine: //点击“我的"

                break;
            case R.id.delete_book: //点击“删除"
                //mBookRackFragment.deleteBooks();
                showNormalDialog();
                break;
            case R.id.add_book: //点击“添加书籍"
                Intent intent=new Intent(MainActivity.this, com.lp.double_b.view.activity.SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_recommend://点击“推荐”
                break;
            case R.id.tv_book_city:
                if (mBookRackFragment.isDeleteMode()) {
                    mBookRackFragment.exitMultiSelectState();
                }
                mVpContent.setCurrentItem(0);
                setBookTitleColor(true);
                break;
            case R.id.tv_book_rack:
                mVpContent.setCurrentItem(1);
                setBookTitleColor(false);
                break;
        }
    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setIcon(R.drawable.cover);
        normalDialog.setTitle("删除书籍");

        int booksCount = mBookRackFragment.getDeleteBooksCount();
        if (booksCount == 0) {
            return;
        } else {
            normalDialog.setMessage("确定要删除所选" + booksCount + "本书籍");
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mBookRackFragment.deleteBooks();
                        }
                    });
            normalDialog.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //showDeleteMode(false);
                            mBookRackFragment.exitMultiSelectState();
                        }
                    });
        }

        // 显示
        normalDialog.show();
    }


    public void showDeleteMode(boolean deleteMode) {
        mIvAddBook.setVisibility(deleteMode ? View.INVISIBLE : View.VISIBLE);
        mIvDeleteBook.setVisibility(deleteMode ? View.VISIBLE : View.INVISIBLE);
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
        if (mBookRackFragment.isDeleteMode()) {
            mBookRackFragment.exitMultiSelectState();
        }
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
        mIvDeleteBook.setVisibility(View.INVISIBLE);

    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        if (mBookRackFragment != null) {
            if (mBookRackFragment.onBackPressed()) return;
        }
        super.onBackPressed();
    }

}
