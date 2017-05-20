package com.lp.double_b.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lp.double_b.R;

/**
 * Created by Administrator on 2017/5/20.
 */
public class BookDetailActivity extends FragmentActivity implements View.OnClickListener{
    private static final String BUNDLE_KEY_BOOK_ID = "bundle_key_book_id";
    private ImageView cover;
    private TextView name;
    private TextView author;
    private TextView keywords;
    private TextView score;
    private TextView downNum;
    private TextView intro;
    private Button strbtn;
    private Button addbtn;
    public static void startActivity(Context context,int bookId){
        Intent i = new Intent(context, BookDetailActivity.class);
        i.putExtra(BUNDLE_KEY_BOOK_ID,bookId);
        context.startActivity(i);
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail);
        initView();
        initListener();
    }
    private void initView(){
        cover=(ImageView) findViewById(R.id.cover);
        name=(TextView) findViewById(R.id.name);
        author=(TextView) findViewById(R.id.author);
        keywords=(TextView)findViewById(R.id.keywords);
        score=(TextView) findViewById(R.id.score);
        downNum=(TextView) findViewById(R.id.downNum);
        intro=(TextView)findViewById(R.id.intro);
        strbtn=(Button)findViewById(R.id.strBtn);
        addbtn=(Button)findViewById(R.id.addBtn);
    }
    private void initListener(){
        strbtn.setOnClickListener(this);
        addbtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.strBtn://点击开始阅读
                break;
            case R.id.addBtn://点击加入书架
                break;
        }
    }
}

