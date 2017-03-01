package com.example.shankarpentyala.wearassignment;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {

    private TextView mTextView;
    private GoogleApiClient client;
    EditText searchtext;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_main);

        Button Image_search1 = (Button) findViewById (R.id.clickbtn);
        searchtext  = (EditText) findViewById(R.id.searchtext);
       final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

    }

    public void images(View V) {

        String SearchQuery =searchtext.getText().toString();
        String Url = "https://kgsearch.googleapis.com/v1/entities:search?query=" + searchtext.getText().toString() +
                "&key=AIzaSyB82KHibkqFCPiYbTs-G_Z60Y-YjM2dEU8&limit=1&indent=True";
        OkHttpClient Client = new OkHttpClient ();
        Request request = new Request.Builder()
                .url(Url)
                .build();
        Client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final JSONObject jsonResult;
                final String result =response.body().string();
                image = (ImageView) findViewById (R.id.imageView);

                try {
                    jsonResult = new JSONObject(result);
                    JSONArray convertedTextArray = jsonResult.getJSONArray("itemListElement");
                    final String cText;
                    cText=convertedTextArray.getJSONObject(0).getJSONObject ("result").getJSONObject ("image").get ("contentUrl").toString ();
                    runOnUiThread (new Runnable ( ) {
                        @Override
                        public void run() {
                            Picasso.with (getApplicationContext ())
                                    .load(cText)
                                    .into(image);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace ( );
                }
            }
        });


    }

}
