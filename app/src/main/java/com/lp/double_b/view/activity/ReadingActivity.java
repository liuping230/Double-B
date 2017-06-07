package com.lp.double_b.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lp.double_b.R;
import com.lp.double_b.view.fragment.ListChapterFragment;
import com.lp.double_b.view.util.MyWebView;

/**
 * Created by Administrator on 2017/5/24.
 */
public class ReadingActivity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = "ReadingActivity";
    private static final String EXTRA_BOOK_ID = "extra_book_id";

    private MyWebView mWebView;
    private ImageView preCh;
    private ImageView nextCh;
    private RelativeLayout controller;
    private RelativeLayout bookContents;
    private RelativeLayout bookFront;
    private RelativeLayout bookMode;

    private int index;
    private String id;
    public static Intent newIntent(Context context, String id){
        Intent intent = new Intent(context, ReadingActivity.class);
        intent.putExtra(EXTRA_BOOK_ID, id);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(params);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_read);
        //initImageLoader(getApplicationContext());
        index = 1;
        id = getIntent().getStringExtra(EXTRA_BOOK_ID);
        mWebView = (MyWebView) findViewById(R.id.activity_read_web_view);
        preCh = (ImageView) findViewById(R.id.pre_chapter);
        nextCh =  (ImageView) findViewById(R.id.next_chapter);
        controller = (RelativeLayout) findViewById(R.id.read_controller);
        bookContents = (RelativeLayout) findViewById(R.id.book_contents);
        bookFront = (RelativeLayout) findViewById(R.id.book_fonts);
        bookMode = (RelativeLayout) findViewById(R.id.book_mode);
        bookFront.setOnClickListener(this);
        bookMode.setOnClickListener(this);
        preCh.setOnClickListener(this);
        nextCh.setOnClickListener(this);
        bookContents.setOnClickListener(this);
        mWebView.setOnTouchScreenListener(new MyWebView.OnTouchScreenListener(){

            @Override
            public void onTouchScreen() {
                showhideController();
            }

            @Override
            public void onReleaseScreen() {
            }
        });
        updateCh();


    }


    private void updateCh(){
        mWebView.loadUrl("http://10.0.2.2:8080/Double_B_Reader/home/novel/"+id+".html#ch"+index);
    }

    private void showhideController(){
        if(controller.getVisibility()==View.GONE){
            controller.setVisibility(View.VISIBLE);
        }
        else{
            controller.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.pre_chapter:
                index--;
                updateCh();
                showhideController();
                break;
            case R.id.next_chapter:
                index++;
                updateCh();
                showhideController();
                break;
            case R.id.book_contents:
                Log.i(TAG, "onClick: ");
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().add(R.id.activity_read_container, ListChapterFragment.newInstance(id)).commit();

                break;
            case R.id.book_fonts:
                break;
            case R.id.book_mode:
                break;
            default:
                break;
        }
    }
}
