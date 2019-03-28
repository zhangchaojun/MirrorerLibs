package com.library.utils;

import android.graphics.Typeface;



public class FontUtils {
    private static FontUtils instance;

    public static FontUtils getInstance() {
        if (instance == null) {
            synchronized (FontUtils.class) {
                if (instance == null) {
                    instance = new FontUtils();
                }
            }
        }
        return instance;
    }

    private Typeface w3;
    private Typeface w5;
    private Typeface w7;
    private Typeface w9;


    public FontUtils() {
//        this.w3 = Typeface.createFromAsset(JoyoungBaseApplication.getInstance().getAssets(), "fonts/W3");
//        this.w5 = Typeface.createFromAsset(JoyoungBaseApplication.getInstance().getAssets(), "fonts/W5");
//        this.w7 = Typeface.createFromAsset(JoyoungBaseApplication.getInstance().getAssets(), "fonts/W7");
//        this.w9 = Typeface.createFromAsset(JoyoungBaseApplication.getInstance().getAssets(), "fonts/W9");
    }

    public Typeface getW3() {
        return w3;
    }

    public Typeface getW5() {
        return w5;
    }

    public Typeface getW7() {
        return w7;
    }

    public Typeface getW9() {
        return w9;
    }
}
