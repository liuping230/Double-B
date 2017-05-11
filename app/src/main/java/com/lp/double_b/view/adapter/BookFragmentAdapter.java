package com.lp.double_b.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */
public class BookFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mDatas;

    public BookFragmentAdapter(FragmentManager fm, List<Fragment> datas) {
        super(fm);
        mDatas = datas;
    }

    @Override
    public Fragment getItem(int position) {
        if (null != mDatas) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (null != mDatas) {
            return mDatas.size();
        }
        return 0;
    }
}
