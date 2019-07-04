package com.library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.library.R;

/**
 * @author zcj
 * @date 2019/7/4
 */
public class MyLogDialog extends Dialog {

    private TextView tv;

    public MyLogDialog(Context context) {
        super(context);
    }

    public MyLogDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_log);
        tv = findViewById(R.id.tv);
    }

    public void setText(String text){
        tv.setText(text);
    }
}
