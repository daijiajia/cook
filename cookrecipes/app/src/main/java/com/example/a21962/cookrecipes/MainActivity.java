package com.example.a21962.cookrecipes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Button my;
    private ArrayList<cookie> Cookies=new ArrayList<cookie>();
    private MyAdapter myAdapter;
    private ACache mACache;
    EditText editText;
    JSONObject jsonObject;
    JSONArray jsonArray;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
    @Override
        public void handleMessage(android.os.Message msg){

        initView();
        init_clist();

    };
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.se_cook_edit);
        mACache=ACache.get(this);
        my=(Button)findViewById(R.id.my) ;
        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),myrecipes.class);
                startActivity(intent);
            }
        });
        Button search=(Button) findViewById(R.id.sea_cook);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ArrayList<cookie> serchlist=new ArrayList<cookie>();
               for(int i=0;i<Cookies.size();i++){
                    if(Cookies.get(i).getName().equals(editText.getText().toString())){
                        serchlist.add(Cookies.get(i));
                    }
               }
               try {
                   for (int l = 0; l < myrecipes.COOKIES.size(); l++) {
                       if (myrecipes.COOKIES.get(l).getName().equals(editText.getText().toString())) {
                           serchlist.add(myrecipes.COOKIES.get(l));
                       }
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }
                myAdapter = new MyAdapter(getApplicationContext(),serchlist);
                listView.setAdapter(myAdapter);
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {

               sendGetRequest("http://supinfo.steve-colinet.fr/supcooking?action=getCooking&login=admin&password=admin");
                Message message=new Message();
                message.what=0;
                message.obj=Cookies;
                handler.sendMessage(message);
            }
        }).start();
    }

    private void initView(){
        listView=(ListView)findViewById(R.id.cook_list);

    }
    private void init_clist(){
        myAdapter = new MyAdapter(getApplicationContext(),Cookies);
        listView.setAdapter(myAdapter);
    }
@Override
    public boolean onCreateOptionsMenu(Menu  menu){
        getMenuInflater().inflate(R.menu.menu_res_file,menu);
        return true;
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.toall:
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
            case R.id.tomy:
                Intent lntent=new Intent(getApplicationContext(),myrecipes.class);
                startActivity(lntent);
                break;
            default:
        }
        return true;
}
     public  String sendGetRequest(String address) {

        String result = null;
      //   mACache.put("myresult",result);
        try {
            result=mACache.getAsString("myresult");
            if(result == null) {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet();
                URI uri = new URI(address);
                httpGet.setURI(uri);
                HttpResponse response = httpClient.execute(httpGet);
                result = EntityUtils.toString(response.getEntity());
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("recipes");
                mACache.put("myresult",result);
                Log.d("myresult",mACache.getAsString("myresult"));
            }
            else{
                result = mACache.getAsString("myresult");
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("recipes");
            }

            Cookies = new ArrayList<cookie>();

            for (int i = 0; i < jsonArray.length(); i++) {
                cookie Cookie = new cookie(
                        jsonArray.getJSONObject(i).getInt("id"),
                        jsonArray.getJSONObject(i).getString("name"),
                        jsonArray.getJSONObject(i).getInt("rate"),
                        jsonArray.getJSONObject(i).getInt("cookingTime"),
                        jsonArray.getJSONObject(i).getInt("preparationtTime"),
                        jsonArray.getJSONObject(i).getString("ingredients"),
                        jsonArray.getJSONObject(i).getString("preparationSteps"),
                        jsonArray.getJSONObject(i).getString("picture"),
                        jsonArray.getJSONObject(i).getString("type")
                );

                Cookies.add(Cookie);
            }
        } catch (Exception e) {
            Log.e("error", e.getMessage(), e);
        }
        return result;

    }
}
