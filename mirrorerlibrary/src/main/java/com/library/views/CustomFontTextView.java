package com.library.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.library.R;
import com.library.utils.FontUtils;

/**
 * 参考使用，需要重写。
 */
public class CustomFontTextView extends AppCompatTextView {
    public CustomFontTextView(Context context) {
        this(context, null);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FontView);
        int font = typedArray.getInt(R.styleable.FontView_textFont, 0);
        typedArray.recycle();
        if (font == 3) {
            setTypeface(FontUtils.getInstance().getW3());
        } else if (font == 5) {
            setTypeface(FontUtils.getInstance().getW5());
        } else if (font == 7) {
            setTypeface(FontUtils.getInstance().getW7());
        } else if (font == 9) {
            setTypeface(FontUtils.getInstance().getW9());
        }
    }
}
