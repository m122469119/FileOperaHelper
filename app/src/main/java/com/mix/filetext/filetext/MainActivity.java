package com.mix.filetext.filetext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppFileOperateHelper.init(getApplication());

        Log.e(MainActivity.class.getName(), AppFileOperateHelper.getAppFilesDirPath());

        Log.e(MainActivity.class.getName(), AppFileOperateHelper.getAppMediaDirPath());

        AppFileOperateHelper.writeStringToFilesDirFile("Hello World", "hello.java");

        Log.e(MainActivity.class.getName(), AppFileOperateHelper.getStringFromFilesDirFile("hello.java"));

        AppFileOperateHelper.copyFileFromAssetsToFilesDirFile("text", "text");

        Log.e(MainActivity.class.getName(), AppFileOperateHelper.getStringFromFilesDirFile("text"));

    }


}
