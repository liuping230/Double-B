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
import com.lp.double_b.view.data.BookInfoBean;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2017/5/20.
 */
public class BookDetailActivity extends FragmentActivity implements View.OnClickListener{
    private static final String BUNDLE_KEY_BOOK_ID = "bundle_key_book_id";
    private static final String BUNDLE_KEY_BOOK_TITLE = "bundle_key_book_title";
    private static final String BUNDLE_KEY_BOOK_AUTHOR = "bundle_key_book_author";
    private static final String BUNDLE_KEY_BOOK_IMAGE = "bundle_key_book_image";
    private static final String BUNDLE_KEY_BOOK_CAT = "bundle_key_book_cat";
    private static final String BUNDLE_KEY_BOOK_SHORTINTRO = "bundle_key_book_shortIntro";
    private ImageView cover;
    private TextView name;
    private TextView mAuthor;
    private TextView keywords;
    private TextView score;
    private TextView downNum;
    private TextView intro;
    private Button strbtn;
    private Button addbtn;
    public static void startActivity(Context context,BookInfoBean book){
        Intent i = new Intent(context, BookDetailActivity.class);
        i.putExtra(BUNDLE_KEY_BOOK_ID,book.get_id());
        i.putExtra(BUNDLE_KEY_BOOK_TITLE,book.getTitle());
        i.putExtra(BUNDLE_KEY_BOOK_AUTHOR,book.getAuthor());
        i.putExtra(BUNDLE_KEY_BOOK_IMAGE,book.getImage());
        i.putExtra(BUNDLE_KEY_BOOK_CAT,book.getCat());
        i.putExtra(BUNDLE_KEY_BOOK_SHORTINTRO,book.getShortIntro());

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
        Intent intent = getIntent();
        String ID = intent.getStringExtra(BUNDLE_KEY_BOOK_ID);
        String title = intent.getStringExtra(BUNDLE_KEY_BOOK_TITLE);
        String author = intent.getStringExtra(BUNDLE_KEY_BOOK_AUTHOR);
        String image = intent.getStringExtra(BUNDLE_KEY_BOOK_IMAGE);
        String cat = intent.getStringExtra(BUNDLE_KEY_BOOK_CAT);
        String shortIntro = intent.getStringExtra(BUNDLE_KEY_BOOK_SHORTINTRO);

        cover=(ImageView) findViewById(R.id.cover);
        name=(TextView) findViewById(R.id.name);
        mAuthor =(TextView) findViewById(R.id.author);
        keywords=(TextView)findViewById(R.id.keywords);
        score=(TextView) findViewById(R.id.score);
        downNum=(TextView) findViewById(R.id.downNum);
        intro=(TextView)findViewById(R.id.intro);
        strbtn=(Button)findViewById(R.id.strBtn);
        addbtn=(Button)findViewById(R.id.addBtn);
        name.setText(title);
        mAuthor.setText(author);
        keywords.setText(cat);
        intro.setText(shortIntro);
        ImageLoader.getInstance().displayImage(image,cover);
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

