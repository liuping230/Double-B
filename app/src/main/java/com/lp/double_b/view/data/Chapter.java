package com.lp.double_b.view.data;

/**
 * Created by Administrator on 2017/6/3.
 */
public class Chapter {
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Chapter{" +
              "name" + name + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }
}
