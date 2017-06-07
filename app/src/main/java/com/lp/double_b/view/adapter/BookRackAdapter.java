package com.lp.double_b.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lp.double_b.R;
import com.lp.double_b.view.activity.BookDetailActivity;
import com.lp.double_b.view.data.BookInfoBean;
import com.lp.double_b.view.fragment.BookRackFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */
public class BookRackAdapter extends BaseAdapter  {
    private final BookRackFragment mFragment;
    public List<BookInfoBean> mBookInfoBeanList;
    public Context mContext;
    private boolean mIsCheckBoxShow = false;

    public BookRackAdapter(List<BookInfoBean> book, Context context,BookRackFragment fragment){
        mBookInfoBeanList=book;
        mContext=context;
        mFragment = fragment;
    }
    @Override
    public int getCount() {
        return mBookInfoBeanList == null ? 0: mBookInfoBeanList.size();
    }
    @Override
    public Object getItem(int position) {
        return mBookInfoBeanList == null ? null : mBookInfoBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mBookInfoBeanList == null ? 0 : position;
    }

    public void setData(List<BookInfoBean> books) {
        mBookInfoBeanList=books;
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView==null){
            holder =new Holder();
            convertView=LayoutInflater.from(mContext).inflate(R.layout.add_book_item,null);
            holder.cover= (ImageView) convertView.findViewById(R.id.cover);
            holder.cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookDetailActivity.startActivity(mContext,mBookInfoBeanList.get(position));

                }
            });
            holder.checkBox= (ImageView) convertView.findViewById(R.id.iv_check_box);
            holder.name=(TextView)convertView.findViewById(R.id.name);
            holder.author=(TextView)convertView.findViewById(R.id.author);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }

        BookInfoBean book = mBookInfoBeanList.get(position);

        holder.name.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        ImageLoader.getInstance().displayImage(book.getImage(),holder.cover);
        holder.checkBox.setVisibility(mIsCheckBoxShow ? View.VISIBLE : View.INVISIBLE);
        holder.checkBox.setSelected(mIsCheckBoxShow && isBookSelected(book.getTitle()));
        return convertView;
    }

    private boolean isBookSelected(String title) {
        return mFragment.isSelected(title);
    }

    public class Holder{
        public ImageView cover;
        public ImageView checkBox;
        public TextView name;
        public TextView author;
    }


    public void setCheckBoxShow(boolean show) {
        mIsCheckBoxShow = show;
    }

    public boolean isCheckBoxShow() {
        return mIsCheckBoxShow;
    }
}
