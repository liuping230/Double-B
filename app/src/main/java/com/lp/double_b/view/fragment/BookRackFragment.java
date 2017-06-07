package com.lp.double_b.view.fragment;


import android.content.Context;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.lp.double_b.R;
import com.lp.double_b.view.activity.MainActivity;
import com.lp.double_b.view.adapter.BookRackAdapter;
import com.lp.double_b.view.data.BookInfoBean;
import com.lp.double_b.view.database.DataBaseDao;
import com.lp.double_b.view.provider.BookDataProvider;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookRackFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private static final int UPDATE_UI = 0;
    private static final String TAG = "BookRackFragment";
    private GridView mGridView;
    private ArrayList<BookInfoBean> mBooks;

    private final ArrayMap<String, BookInfoBean> mSelectedBooks = new ArrayMap<>();

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case UPDATE_UI:
                    updateData();
                    updateUI();
                    break;
                default:
                    Log.d(TAG,"no message to handler");
                    break;
            }
            return true;
        }
    });
    private DataBaseDao mDao;
    private BookRackAdapter mAdapter;
    private TextView mNoBookView;


    private BookRackObserver mObserver;
    private Context mContext;
    private MainActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_book_rack, container, false);
        mGridView = (GridView) view.findViewById(R.id.gv_books);
        mNoBookView = (TextView) view.findViewById(R.id.tv_no_book);

        mGridView.setOnItemClickListener(this);
        mGridView.setOnItemLongClickListener(this);

        mObserver = new BookRackObserver(mHandler);
        mContext.getContentResolver().registerContentObserver(BookDataProvider.CONTENT_URI,true,mObserver);
        initData();
        return view;
    }

    private void initData() {
        mDao = new DataBaseDao(mContext);
        mBooks = mDao.query();
        mAdapter = new BookRackAdapter(mBooks, mContext, this);
        mGridView.setAdapter(mAdapter);

        updateUI();
    }

    private void updateUI() {
        mGridView.setVisibility(mBooks.size() == 0 ? View.INVISIBLE : View.VISIBLE);
        mNoBookView.setVisibility(mBooks.size() == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    private void updateData() {
        mBooks = mDao.query();
        mAdapter.setData(mBooks);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        BookInfoBean bookInfoBean = mBooks.get(position);
//        BookDetailActivity.startActivity(getActivity(),bookInfoBean);
        if (mAdapter.isCheckBoxShow()) {
            //checkbox显示 为删除模式，点击该是就选中为要删除
            toggleSelect(bookInfoBean);
            mAdapter.notifyDataSetChanged();
        } else {
            //书架的正常显示模式，点击该书就进入阅读
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        if (!mAdapter.isCheckBoxShow()) {
            //长按的书目默认要被选中
            mActivity.showDeleteMode(true);
            mAdapter.setCheckBoxShow(true);
            onItemClick(parent,view,position,id);
        } else {
            //再次长按 退出删除模式
           exitMultiSelectState();
        }
        return true;
    }

    public void deleteBooks() {
        if (mSelectedBooks.size() != 0) {
            mulitDelete(mSelectedBooks.values());
        }
        exitMultiSelectState();
    }

    public void toggleSelect(BookInfoBean bookInfo) {

        String title = bookInfo.getTitle();
        if (mSelectedBooks.containsKey(title)) {
            mSelectedBooks.remove(title);
        } else {
            mSelectedBooks.put(title, bookInfo);
        }
    }

    public boolean isSelected(String title) {
        return mSelectedBooks.containsKey(title);
    }

    public void mulitDelete(Collection<BookInfoBean> books) {
        for (BookInfoBean book : books) {
            mDao.delete(book);
        }
    }

    public boolean onBackPressed() {
        if (mAdapter.isCheckBoxShow())  {
            Log.d(TAG,"onBackPressed() exit Multi Select State.");

            exitMultiSelectState();
            return true;
        }
        return false;
    }

    public void exitMultiSelectState() {
        mActivity.showDeleteMode(false);
        mSelectedBooks.clear();
        mAdapter.setCheckBoxShow(false);
        mAdapter.notifyDataSetChanged();
    }

    public boolean isDeleteMode() {
        return mAdapter.isCheckBoxShow();
    }

    public int getDeleteBooksCount() {
        return mSelectedBooks.size();
    }

    private class BookRackObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public BookRackObserver(Handler handler) {
            super(handler);
        }

        @Override
        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            mHandler.sendEmptyMessage(UPDATE_UI);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.getContentResolver().unregisterContentObserver(mObserver);
        mContext = null;
        mActivity = null;
    }
}
