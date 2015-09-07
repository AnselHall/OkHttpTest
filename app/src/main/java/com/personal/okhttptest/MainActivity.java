package com.personal.okhttptest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.personal.okhttptest.constant.Url;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.main_iv)
    ImageView main_iv;
    @InjectView(R.id.main_tv)
    TextView main_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initData();
    }

    private void initData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder().url(Url.imageUrls[10]).build();
        Call call = okHttpClient.newCall(build);
        call.enqueue(new Callback() {

            private Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            private String string = "HelloWorld";

            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("Log", "FAILURE");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

                /*string = response.body().string();
                Log.e("Log", string + "SUCCESS");*/
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        main_iv.setImageBitmap(bitmap);
                        main_tv.setText(string);
                    }
                });
            }
        });
    }
}
