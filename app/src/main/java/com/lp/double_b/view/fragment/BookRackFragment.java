package com.lp.double_b.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lp.double_b.R;
import com.lp.double_b.view.activity.SearchActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookRackFragment extends Fragment {


    private ImageView add_book;

    public BookRackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_book_rack, container, false);
        add_book=(ImageView)view.findViewById(R.id.add_book);
        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.startActivity(getActivity());
            }
        });
        return view;
    }

}
