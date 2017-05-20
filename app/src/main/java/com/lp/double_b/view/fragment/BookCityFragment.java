package com.lp.double_b.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lp.double_b.R;
import com.lp.double_b.view.activity.SearchActivity;
import com.lp.double_b.view.adapter.BookListAdapter;
import com.lp.double_b.view.data.OneBook;

import java.util.ArrayList;
import java.util.List;

public class BookCityFragment extends Fragment {

    private LinearLayout searchLayout;
    BookListAdapter mAdapter;
    private ListView listView;
    public List<OneBook> _listData;
    public BookCityFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_city, container, false);
        searchLayout = (LinearLayout) view.findViewById(R.id.search_layout);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.startActivity(getActivity());
            }
        });

        listView=(ListView)view.findViewById(R.id.listView);
        getData();

        return view;
    }

    private void getData() {
        _listData=new ArrayList<OneBook>();
        OneBook b=new OneBook(R.drawable.cover,"婚然天成", "绿丸子", "总裁");
       _listData.add(b);
        mAdapter = new BookListAdapter(getActivity(), _listData);
        listView.setAdapter(mAdapter);
    }
}
