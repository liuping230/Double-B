package com.lp.double_b.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lp.double_b.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookRackFragment extends Fragment {
    public BookRackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_book_rack, container, false);
        return view;
    }

}
