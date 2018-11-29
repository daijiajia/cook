package com.example.a21962.cookrecipes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class evaluate extends AppCompatActivity {
    cookie Cookie;
    TextView textView;
    Button evaluate;
    RatingBar ratingBar;
    float rate;
    String address;
    JSONObject jsonObject;
    JSONArray jsonArray;
    boolean a;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
                    if(a){
                        Log.d("sadas","asdasd");
                        Toast.makeText(getApplicationContext(), "thanks for your evaluate", Toast.LENGTH_LONG).show();
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        Cookie=(cookie) getIntent().getSerializableExtra("eval");
        textView=(TextView)findViewById(R.id.ev_name);
        ratingBar=(RatingBar)findViewById(R.id.ev_star);
        evaluate=(Button)findViewById(R.id.ev_b);
        textView.setText(Cookie.getName());

        evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "thanks for your evaluate", Toast.LENGTH_LONG).show();
                rate=ratingBar.getRating();
                Log.d("id",Integer.toString(Cookie.getId()));
                rate=ratingBar.getRating();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        address="http://supinfo.steve-colinet.fr/supcooking?action=evaluate&login=admin&password=admin&cookingId="+Cookie.getId()+
                                "&rate="+rate;
                        Log.d("url",address);
                        sendGetRequest(address);
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = a;
                        handler.sendMessage(msg);
                        finish();
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                }).start();

            }
        });

    }
    public  String sendGetRequest(String address) {
        String result=null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet();
            URI uri = new URI(address);
            httpGet.setURI(uri);
            HttpResponse response = httpClient.execute(httpGet);
            result = EntityUtils.toString(response.getEntity());
            jsonObject = new JSONObject(result);

            boolean a = jsonObject.getBoolean("success");
            if(a){

                Log.d("daijaijia","asdssssssssss");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;
    }
}
