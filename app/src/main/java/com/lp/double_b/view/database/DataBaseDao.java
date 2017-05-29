package com.lp.double_b.view.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.lp.double_b.view.data.BookInfoBean;
import com.lp.double_b.view.provider.BookDataProvider;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/26.
 */
public class DataBaseDao {

    private final Context mContext;
    public static DataBaseDao instance;

    public DataBaseDao (Context context){
       mContext = context;
    }
    public synchronized static DataBaseDao getInstance(Context context){
        if (instance==null){
            instance=new DataBaseDao(context);
        }
        return  instance;
    }
    //添加数据
    public void add(BookInfoBean book){

    //bookId text, author text, cat text, image text, shortIntro text
        ContentValues values = new ContentValues();
        values.put("title", book.getTitle());
        values.put("bookId", book.get_id());
        values.put("author", book.getAuthor());
        values.put("cat", book.getCat());
        values.put("image", book.getImage());
        values.put("shortIntro", book.getShortIntro());

        mContext.getContentResolver().insert(BookDataProvider.CONTENT_URI, values);
    }
    public void delete(BookInfoBean book){

        //mDatabase.delete(DataBaseHelper.TABLE_NAME, "title = ?",new String[]{book.getTitle()} );
       mContext.getContentResolver().delete(BookDataProvider.CONTENT_URI, "title = ?", new String[]{book.getTitle()});
    }
    public ArrayList<BookInfoBean> query(){

        ArrayList<BookInfoBean> list = new ArrayList<>();
        //Cursor cursor = mDatabase.query(DataBaseHelper.TABLE_NAME, new String[]{"title", "bookId", "author", "cat", "image", "shortIntro"}, null, null, null, null, null);
        Cursor cursor = mContext.getContentResolver().query(BookDataProvider.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String bookId = cursor.getString(cursor.getColumnIndex("bookId"));
            String author = cursor.getString(cursor.getColumnIndex("author"));
            String cat = cursor.getString(cursor.getColumnIndex("cat"));
            String image = cursor.getString(cursor.getColumnIndex("image"));
            String shortIntro = cursor.getString(cursor.getColumnIndex("shortIntro"));

            Log.w("DataBaseDao", "title ============ " + title);
            BookInfoBean book = new BookInfoBean();
            book.setTitle(title);
            book.set_id(bookId);
            book.setAuthor(author);
            book.setCat(cat);
            book.setImage(image);
            book.setShortIntro(shortIntro);
            list.add(book);
        }

        cursor.close();
        return list;
    }


    public boolean query(String title){

        //Cursor cursor = mDatabase.query(DataBaseHelper.TABLE_NAME, new String[]{"title", "bookId", "author", "cat", "image", "shortIntro"}, null, null, null, null, null);
        Cursor cursor = mContext.getContentResolver().query(BookDataProvider.CONTENT_URI, null, "title = ?", new String[]{title}, null);
        boolean hasExist;
        if (cursor.moveToNext()) {
            hasExist = true;
        } else {
            hasExist = false;
        }

        cursor.close();
       return hasExist;
    }
}
