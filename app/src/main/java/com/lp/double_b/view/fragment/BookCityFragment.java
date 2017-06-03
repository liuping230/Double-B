package com.lp.double_b.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.util.IOUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lp.double_b.R;
import com.lp.double_b.view.activity.BookDetailActivity;
import com.lp.double_b.view.activity.SearchActivity;
import com.lp.double_b.view.adapter.BookListAdapter;
import com.lp.double_b.view.data.BookInfoBean;
import com.lp.double_b.view.util.FileUtils;
import com.lp.double_b.view.util.LogUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookCityFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG ="BookCityFragment";
    private LinearLayout searchLayout;
    BookListAdapter mAdapter;
    private ListView listView;
    public List<BookInfoBean> _listData;
    private LoadDataTask mLoadDataTask;

    Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.obj != null) {
                _listData = (List<BookInfoBean>) msg.obj;
            }


            return false;
        }
    });

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
        listView.setOnItemClickListener(this);
//        getData();

        //异步加载
        mLoadDataTask = new LoadDataTask();
        new Thread(mLoadDataTask).start();
       // ThreadPoolProxyFactory.createNormalThreadPoolProxy().submit(mLoadDataTask);

        return view;
    }

//    private void getData() {
//        _listData=new ArrayList<OneBook>();
//        OneBook b=new OneBook(R.drawable.cover,"婚然天成", "绿丸子", "总裁");
//       _listData.add(b);
//        mAdapter = new BookListAdapter(getActivity(), _listData);
//        listView.setAdapter(mAdapter);
//    }
    /**
     * 加载数据
     * 1.磁盘,磁盘有返回,存内存
     * 2.网络,网络有返回,存磁盘,存内存
     * @return
     * @throws Exception
     */
    public List<BookInfoBean> loadData(){

        List<BookInfoBean> result = null;


        /*--------------- 1.磁盘,磁盘有返回,存内存 ---------------*/
        //先本地,有返回
        result = loadDataFromLocal();
        if (result != null) {
            LogUtils.i(TAG, "从本地加载了数据-->" + getCacheFile().getAbsolutePath());
            return result;
        }
        /*--------------- 2.网络,网络有返回,存磁盘,存内存 ---------------*/
        //在网络,存本地
        return loadDataFromNet();
    }

    /**
     * 从本地加载数据
     *
     * @return
     */
    private List<BookInfoBean> loadDataFromLocal() {
        BufferedReader reader = null;
        try {
            File cacheFile = getCacheFile();
            if (cacheFile.exists()) {//有缓存
                //判断缓存是否过期-->读取缓存的生成时间

                reader = new BufferedReader(new FileReader(cacheFile));
                String insertTimeStr = reader.readLine();
                Long insertTime = Long.parseLong(insertTimeStr);

                if ((System.currentTimeMillis() - insertTime) < 5*60*1000) {
                    //有效的缓存-->读取缓存内容
                    String diskCacheJsonString = reader.readLine();

                    //解析返回
                    List<BookInfoBean> result = parseJsonString(diskCacheJsonString);
                    return result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(reader);
        }
        return null;
    }

    @NonNull
    private File getCacheFile() {
        String dir = FileUtils.getDir("json");//sdcard/Android/data/包目录/json
        String fileName = getInterfaceKey();
        return new File(dir, fileName);
    }


    /**
     * 从网络获取数据
     *
     * @return
     * @throws IOException
     */
    private List<BookInfoBean> loadDataFromNet() {
        //1.创建okHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建请求对象
        //http://localhost:8080/Double_B_Reader/home/female.json
        String url = "http://10.0.3.2:8080/Double_B_Reader/home/" + getInterfaceKey();

      LogUtils.s(url);

        Request request = new Request.Builder().get().url(url).build();

        //3.发起请求-->同步
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {//有响应
                String responseJsonString = response.body().string();

                Log.w(TAG,"responseJsonString = " + responseJsonString);

               /* BufferedWriter writer = null;
                try {
                    //保存数据到本地
                    File cacheFile = getCacheFile();

                    writer = new BufferedWriter(new FileWriter(cacheFile));
                    writer.write(System.currentTimeMillis() + "");
                    writer.newLine();//换行
                    writer.write(responseJsonString);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    IOUtils.close(writer);
                }*/

                //完成json的解析
                List<BookInfoBean> t = parseJsonString(responseJsonString);
                return t;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getInterfaceKey() {
        Bundle arguments = getArguments();
        //arguments.getString()
        return "female";
    }

    public List<BookInfoBean> parseJsonString(String resultJsonString) {
        List<BookInfoBean> result = new ArrayList<>();
        Gson gson = new Gson();
        //Type type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
       // result = gson.fromJson(resultJsonString, type);
        //result = GsonUtil.parseJsonArrayWithGson(resultJsonString, BookInfoBean.class);

        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(resultJsonString).getAsJsonArray();

        for (JsonElement jsonElement : jsonArray) {
            BookInfoBean bookInfoBean = gson.fromJson(jsonElement, BookInfoBean.class);
            Log.w(TAG,"BookInfoBean == " +bookInfoBean.toString());
            result.add(bookInfoBean);
        }
        return result;
    }

    class LoadDataTask implements Runnable {
        @Override
        public void run() {
            //真正在子线程中开始加载具体的数据了
            final List<BookInfoBean> bookInfoBeen = loadDataFromNet();
            Log.w(TAG,"bookInfoBeen.size() ++++++++++++++++++++++++++++++++++++ ");
            if (null != bookInfoBeen)
            {Log.w(TAG,"bookInfoBeen.size() ============================================================== ");
                Log.w(TAG,"bookInfoBeen.size() = " + bookInfoBeen.size());}

            Message message = Message.obtain();
            message.obj = bookInfoBeen;
            mHandler.sendMessage(message);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter = new BookListAdapter(getActivity(), bookInfoBeen);
                    listView.setAdapter(mAdapter);
                }
            });
            //run方法走到最后,置空任务
            mLoadDataTask = null;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (_listData != null) {

            BookInfoBean bookInfoBean = _listData.get(position);
            BookDetailActivity.startActivity(getActivity(),bookInfoBean);
        }
    }

}
