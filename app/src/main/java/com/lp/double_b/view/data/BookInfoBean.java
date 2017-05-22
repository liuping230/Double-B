package com.lp.double_b.view.data;

/**
 * Created by Administrator on 2017/5/20.
 */
public class BookInfoBean {
   public String _id;
   public String author;
   public String cat;
   public String image;
   public String shortIntro;
   public String title;
    @Override
    public String toString() {
        return "BookInfoBean{" +
                "_id=" + _id +
                ", author='" + author + '\'' +
                ", cat='" + cat + '\'' +
                ", image='" + image + '\'' +
                ", shortIntro='" + shortIntro + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShortIntro() {
        return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
