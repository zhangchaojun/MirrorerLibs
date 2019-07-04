package com.example.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.library.dialog.MyLogDialog;
import com.library.utils.MyUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MyLogDialog dialog = new MyLogDialog(this);
        dialog.show();
        dialog.setText("我时");
    }
}
