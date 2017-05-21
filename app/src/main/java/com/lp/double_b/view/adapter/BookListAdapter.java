package com.lp.double_b.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lp.double_b.R;
import com.lp.double_b.view.data.BookInfoBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20.
 */
public class BookListAdapter extends BaseAdapter{
    private LayoutInflater mInflater;

    public List<BookInfoBean> _listData=null;
    private Context mContext;

    public BookListAdapter (Context context,List<BookInfoBean> list){
        _listData=list;
        mContext=context;
        mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        获取ListView中的view对象
        Holder holder;
       if (convertView==null){
           convertView=mInflater.inflate(R.layout.book_item,null);
            holder=new Holder();
//           获取自定义布局中的每一个控件的对象
           holder.cover=(ImageView)convertView.findViewById(R.id.cover);
           holder.name=(TextView)convertView.findViewById(R.id.name);
           holder.author=(TextView)convertView.findViewById(R.id.author);
           holder.keyword=(TextView)convertView.findViewById(R.id.keywords);
           convertView.setTag(holder);
//           convertView=view;
       }
        else{  holder = (Holder) convertView.getTag();}
        final BookInfoBean book=_listData.get(position);
//        将数据添加到自定义的布局中
//        holder.cover.setImageResource(R.drawable.cover);
        ImageLoader.getInstance().displayImage(book.getImage(),holder.cover);
        holder.name.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        holder.keyword.setText(book.getCat());

        return convertView;
    }

    public class Holder {
        public TextView name;
        public TextView author;
        public TextView keyword;
        public ImageView cover;
    }
    @Override
    public int getCount() {
        return _listData == null ? 0 : _listData.size();
    }

    @Override
    public Object getItem(int position) {
        return _listData == null ? null : _listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return _listData == null ? 0 : position;
    }

}
