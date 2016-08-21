package com.bignerdranch.android.newcrimeintent;

/**
 * Created by leehyuntae on 8/20/16.
 */
public class MyItem {
    static public final int TYPE_TEXT = 0;
    static public final int TYPE_IMAGE = 1;


    public int type;
    public final long id;
    public final String text;

    public MyItem(long id, String text, int type) {
        this.id = id;
        this.text = text;
        this.type = type;
    }
}
