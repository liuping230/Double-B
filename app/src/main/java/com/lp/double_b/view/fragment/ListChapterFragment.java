package com.lp.double_b.view.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lp.double_b.R;
import com.lp.double_b.view.activity.ReadingActivity;
import com.lp.double_b.view.adapter.ChapterListAdapter;
import com.lp.double_b.view.data.Chapter;
import com.lp.double_b.view.util.LogUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ListChapterFragment extends Fragment implements AdapterView.OnItemClickListener{
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
    private ChapterListAdapter mAdapter;
    private LoadDataTask mLoadDataTask;

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
              Log.w("Onclick","++++++++++++++++++");
                FragmentManager fm = getFragmentManager();
//XXXX是当前类的名称
                fm.beginTransaction().remove(ListChapterFragment.this).commit();
//                getActivity().finish();
//                new Thread() {
//                    public void run() {
//                        try {
//                            Instrumentation inst = new Instrumentation();
//                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
//                        } catch (Exception e) {
//                        }
//                    }
//                }.start();
            }
        });
        //异步加载
        mLoadDataTask = new LoadDataTask();
        new Thread(mLoadDataTask).start();
        titleTextView=(TextView)view.findViewById(R.id.title_text_view);
        titleTextView.setText("章节列表");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.chapter_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ChapterListAdapter adapter = new ChapterListAdapter(getActivity(), _list);
        mRecyclerView.setAdapter(adapter);

//        getData();
        return view;
    }
    Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.obj != null) {
                _list = (List<Chapter>) msg.obj;
            }


            return false;
        }
    });
    private List<Chapter> loadDataFromNet() {
        //1.创建okHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建请求对象
        //http://localhost:8080/Double_B_Reader/home/female.json
        String url = "http://10.0.3.2:8080/Double_B_Reader/home/novel/" + id + ".txt" ;

        LogUtils.s(url);

        Request request = new Request.Builder().get().url(url).build();

        //3.发起请求-->同步
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {//有响应
                String responseJsonString = response.body().string();

                Log.w(TAG,"responseJsonString = " + responseJsonString);

                //完成json的解析
                List<Chapter> t = parseJsonString(responseJsonString);
                return t;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Chapter> parseJsonString(String resultJsonString) {
        List<Chapter> result = new ArrayList<>();
        Gson gson = new Gson();

        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(resultJsonString).getAsJsonArray();

        for (JsonElement jsonElement : jsonArray) {
            Chapter chapter = gson.fromJson(jsonElement, Chapter.class);
            Log.w(TAG,"BookInfoBean == " +chapter.toString());
            result.add(chapter);
        }
        return result;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (_list != null) {

            Chapter chapter = _list.get(position);
           ReadingActivity.newIntent(getActivity(),chapter.getName());
        }
    }


    class LoadDataTask implements Runnable {
        @Override
        public void run() {
            //真正在子线程中开始加载具体的数据了
            final List<Chapter> chapter = loadDataFromNet();
            Log.w(TAG,"chapter.size() ++++++++++++++++++++++++++++++++++++ ");
            if (null != chapter)
            {Log.w(TAG,"chapter.size() ============================================================== ");
                Log.w(TAG,"chapter.size() = " + chapter.size());}

            Message message = Message.obtain();
            message.obj = chapter;
            mHandler.sendMessage(message);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter = new ChapterListAdapter(getActivity(), chapter);
                    mRecyclerView.setAdapter(mAdapter);
                }
            });
            //run方法走到最后,置空任务
            mLoadDataTask = null;
        }
    }
//    private Handler mHandler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            switch (msg.what){
//                case MSG_SUCCESS:
//                    List<Chapter> chapters = GsonUtil.parseJsonArrayWithGson(json, Chapter.class);
//                    mRecyclerView.setAdapter(new ChapterListAdapter(getActivity(), chapters));
//                    break;
//                default:
//            }
//            return false;
//        }
//    });
////        @Override
////        public void handleMessage(Message msg) {
////            switch (msg.what){
////                case MSG_SUCCESS:
////                    List<Chapter> chapters = GsonUtil.parseJsonArrayWithGson(json, Chapter.class);
////                    mRecyclerView.setAdapter(new ChapterListAdapter(getActivity(), chapters));
////                    break;
////                default:
////            }
////        }
////};
//
//    private void getData() {
//        mOkHttpClient = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("http://10.0.3.2:8080/Double_B_Reader/home/novel/" + id + ".txt")
//                .build();
//        Call call = mOkHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                json = response.body().string();
//                mHandler.sendEmptyMessage(MSG_SUCCESS);
//            }
//        });
//    }
}
