package com.lp.double_b.view.data;

/**
 * Created by Administrator on 2017/5/19.
 */
public class OneBook {
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    private int bookId;
    private String name;
    private String author;
    private String keywords;
    private int cover;

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public OneBook(int cover, String name, String author, String keywords){
        super();
        this.name=name;
        this.author=author;
        this.keywords=keywords;
        this.cover=cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
