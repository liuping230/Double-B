package com.lp.double_b.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lp.double_b.R;
import com.lp.double_b.view.data.Chapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20.
 */
public class ChapterListAdapter extends RecyclerView.Adapter<ChapterListAdapter.Holder>{
    private List<Chapter> mChapters;
    private Context mContext;

    public ChapterListAdapter(Context context, List<Chapter>  chapters){
        mChapters = chapters;
        mContext = context;
    }

    class Holder extends RecyclerView.ViewHolder{
        public TextView name;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.chapter_title);
        }
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder holder = new Holder(LayoutInflater.from(mContext).inflate(R.layout.chapter_list_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Chapter chapter = mChapters.get(position);
        holder.name.setText(chapter.getName());
    }


    @Override
    public int getItemCount() {
        return (this.mChapters != null) ? this .mChapters. size() : 0 ;
    }

}
