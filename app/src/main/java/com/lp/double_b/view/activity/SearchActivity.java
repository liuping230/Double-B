package com.lp.double_b.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.lp.double_b.R;
import com.lp.double_b.view.database.DataBaseDao;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/5/13.
 */
public class SearchActivity extends FragmentActivity implements View.OnClickListener {
    public static void startActivity(Context context) {
        Intent i = new Intent(context, SearchActivity.class);
        context.startActivity(i);
    }
    private ViewFlipper mainFlipper;
    private ListView listView;
    private InputMethodManager imm;
    public EditText searchEditText;
    public TextView searchClearButton;
    public boolean searchTextEmpty;
    private boolean isFirstNotEmpty = true;
    private boolean toSearch = false;
    DataBaseDao mDataBaseDao;
    private List<HashMap<String, Object>> book;
    @Override
    protected void onCreate( Bundle ag0) {
        super.onCreate(ag0);
        setTitle("搜索");
       mDataBaseDao=DataBaseDao.getInstance(this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);// 获得软键盘的对象
        setContentView(R.layout.activity_search);
        findViews();
        initViews();
        initSearchView();
    }
    private void findViews() {
        searchEditText = (EditText) findViewById(R.id.title_bar_search_edit);
        searchClearButton = (TextView) findViewById(R.id.title_bar_search_clear_button);// 清空搜索内容按钮

        mainFlipper = (ViewFlipper) findViewById(R.id.search_main_flipper);// 显示搜索到的小说的界面
        listView = (ListView) findViewById(R.id.listView_search);// 显示搜索到的小说的界面
    }

    public void initViews() {
        listView.setOnItemClickListener(listViewItemClickListener);
    }

    public void initSearchView() {
        searchEditText.addTextChangedListener(mTextWatcher);
        searchEditText.setOnClickListener(this);
        searchEditText.setOnEditorActionListener(enterKeyToSearch);
        setSearchTextEmpty(true);

        searchClearButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if (view == searchClearButton) {
            setSearchTextEmpty(true);
            showBooksSearched();
            imm.showSoftInput(searchEditText, 0); // 显示键盘
        } else if (view == searchEditText) {

            if (!toSearch) {
                showBooksSearched();
                mainFlipper.setDisplayedChild(0);
                if (isSearchTextEmpty()) {
                    searchEditText.setSelection(0);// 设置光标位置
                }
            } else
                toSearch = false;
        }
    }
    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            setSearchTextEmpty(searchEditText.getText().toString().length() == 0);
            String str = searchEditText.getText().toString();
            int strLength = str.length();
            if (strLength > 0) {
                showBooksSearched();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
        TextView.OnEditorActionListener enterKeyToSearch = new TextView.OnEditorActionListener() {
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            showBooks(searchEditText.getText().toString());
            toSearch = true;
        }
        return false;
    }
};

    private void showBooks(String s) {
    }

    AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final TextView tv = (TextView) view.findViewById(R.id.name);
            searchEditText.removeTextChangedListener(mTextWatcher);
            searchEditText.setText("");
            searchEditText.append(tv.getText().toString());
            searchEditText.addTextChangedListener(mTextWatcher);
            showBooks(tv.getText().toString());
        }
    };
    private boolean isSearchTextEmpty() {
        return searchTextEmpty;
    }

    private void showBooksSearched() {
        mainFlipper.setDisplayedChild(0);
        if (searchEditText.getText().toString().length() == 0) {
            listView.setAdapter(null);
            return;
        }
        //List<BookInfoBean> search =DataBaseDao.
        BaseAdapter mBaseAdapter = new BaseAdapter() {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = View.inflate(SearchActivity.this, R.layout.book_item, null);
                TextView title = (TextView) view.findViewById(R.id.name);
                TextView author = (TextView) view.findViewById(R.id.author);
                TextView keywords = (TextView) view.findViewById(R.id.keywords);
                title.setText(book.get(position).get("name") + "");
                author.setText(book.get(position).get("author") + "");
                keywords.setText(book.get(position).get("keywords") + "");
                return view;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public int getCount() {
                return book.size();
            }
        };
        listView.setAdapter(mBaseAdapter);
    }

    public void setSearchTextEmpty(boolean searchTextEmpty) {
        if (this.searchTextEmpty == searchTextEmpty)
            return;

        this.searchTextEmpty = searchTextEmpty;
        if (searchTextEmpty) {
            searchEditText.setText("");

            searchClearButton.setVisibility(View.INVISIBLE);
            isFirstNotEmpty = true;
        } else if (isFirstNotEmpty) {
            searchClearButton.setVisibility(View.VISIBLE);
            isFirstNotEmpty = false;
        }
    }
}
