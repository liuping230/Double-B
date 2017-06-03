package com.lp.double_b.view.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lp.double_b.R;
import com.lp.double_b.view.adapter.ChapterListAdapter;
import com.lp.double_b.view.data.Chapter;
import com.lp.double_b.view.util.GsonUtil;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;


public class ListChapterFragment extends Fragment {
    private static final int MSG_SUCCESS = 1;
    private static final String BUNDLE_ID = "bundle_id";
    private static final String TAG = "ListChapterFragment";
    private ImageView backBtn;
    private TextView titleTextView;
    private List<Chapter> _list;
    private RecyclerView mRecyclerView;
    private OkHttpClient mOkHttpClient;
    private String id;
    private String json;

    public ListChapterFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String id){
        ListChapterFragment fragment = new ListChapterFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chapter_list, container, false);
        id = getArguments().getString(BUNDLE_ID);
        backBtn=(ImageView)view.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ReadingActivity.newIntent(getContext(),id);
            }
        });
        titleTextView=(TextView)view.findViewById(R.id.title_text_view);
        titleTextView.setText("章节列表");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.chapter_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ChapterListAdapter adapter = new ChapterListAdapter(getActivity(), _list);
        mRecyclerView.setAdapter(adapter);

        getData();
        return view;
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_SUCCESS:
                    List<Chapter> chapters = GsonUtil.parseJsonArrayWithGson(json, Chapter.class);
                    mRecyclerView.setAdapter(new ChapterListAdapter(getActivity(), chapters));
                    break;
                default:
            }
        }
    };

    private void getData() {
        mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.0.3.2:8080/Double_B_Reader/home/novel/" + id + ".txt")
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                json = response.body().string();
                mHandler.sendEmptyMessage(MSG_SUCCESS);
            }
        });
    }
}
