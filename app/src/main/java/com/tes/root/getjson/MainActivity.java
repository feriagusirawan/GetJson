package com.tes.root.getjson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
//http://square.github.io/okhttp/

public class MainActivity extends AppCompatActivity {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    final String strURL = "https://sandbox.savanna.zebra.com/v2/relativelocationing/calculaterelativelocation";
    final String strBodyJson = "{\"scan\":\"049200045701\",\"content\":{ \"BarcodeScanUtc\":\"2018-10-07T23:58:25.039Z\",\"Device\":\"TC75\",\"ScanResults\":[{\"BSSID\":\"20:4E:7F:AC:8E:9F\",\"RecUtc\":\"2018-10-07T23:46:28.937Z\",\"SignalStrength\":-65,\"SignalType\":1}],\"Symbology\":\"UPCA\",\"Value\":\"049200045701\",\"Version\":\"1.0.0.38-master.54d32d3\",\"Latitude\":42.1866674,\"Longitude\":-117.31623414323549}}";
    final String strApikey = "replace me with your apikey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        final TextView textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody body = RequestBody.create(JSON, strBodyJson);

                        Request request = new Request.Builder()
                                .url(strURL)
                                .addHeader("Content_Type","application/json")
                                .addHeader("apikey",strApikey)
                                .post(body)
                                .build();

                        Response response = null;

                        try{
                            response = client.newCall(request).execute();
                            return response.body().string();

                        } catch (IOException e){
                            e.printStackTrace();
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        textView.setText(o.toString());
                    }
                }.execute();
            }
        });
    }
}
//https://github.com/dinogregorich/Android_GetJsonDataFromRestAPI
