package com.example.a21962.cookrecipes;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class detail_activity extends AppCompatActivity {
    cookie this_cookie;
    ImageView imageView;
    TextView nametext;
    TextView typetext;
    TextView cotime;
    TextView preptime;
    TextView ingre;
    TextView prestep;
    private String url=null;
    String path = Environment.getExternalStorageDirectory().getPath() + "mycache";
    File file = new File(path);

    DisplayImageOptions imageOptions;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
//                    System.out.println("111");
//                    Bitmap bitmap=(Bitmap)msg.obj;
//                    imageView.setImageBitmap(bitmap);
                    ImageLoader.getInstance().displayImage(url,imageView,imageOptions);
                    break;
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
        .diskCache(new UnlimitedDiskCache(file)).build();
        ImageLoader.getInstance().init(configuration);
        imageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).build();
        nametext=(TextView)findViewById(R.id.co_name);
        typetext=(TextView)findViewById(R.id.type);
        cotime=(TextView)findViewById(R.id.cookingtime);
        preptime=(TextView)findViewById(R.id.prepartime);
        ingre=(TextView)findViewById(R.id.ingredients);
        prestep=(TextView)findViewById(R.id.preparsteps);
        this_cookie=(cookie) getIntent().getSerializableExtra("this_cookie");
        imageView=(ImageView) findViewById(R.id.imageofcookie);
        nametext.setText("name: "+this_cookie.getName());
        typetext.setText("type: "+this_cookie.getType());
        cotime.setText("cooktime: "+Integer.toString(this_cookie.getCookingTime()));
        preptime.setText("preparetime: "+Integer.toString(this_cookie.getPreparationTime()));
        ingre.setText(this_cookie.getIngredients());
        ingre.setMovementMethod(ScrollingMovementMethod.getInstance());
        prestep.setText(this_cookie.getPrepationsSteps());
        prestep.setMovementMethod(ScrollingMovementMethod.getInstance());
        url=this_cookie.getPicture();
        Uri uri=Uri.parse((String) this_cookie.getPicture());
         new Thread(new Runnable() {
             @Override
             public void run() {
                 //Bitmap bitmap=getURLimage(url);
                 try {

//                     Bitmap bitmap = BitmapFactory.decodeByteArray(this_cookie.picture1, 0, this_cookie.picture1.length);
                         Message msg = new Message();
                         msg.what = 0;
                        msg.obj = imageOptions;
//                     System.out.println("000");
                        handler.sendMessage(msg);
                 }catch (Exception e){
                     e.printStackTrace();
                 }finally {
//                     Bitmap bitmap=getURLimage(url);
//                     Message msg = new Message();
//                     msg.what = 0;
//                     msg.obj = bitmap;
//                     System.out.println("000");
//                     handler.sendMessage(msg);
                 }

             }
         }).start();

    }
    public Bitmap getURLimage(String url){
        Bitmap bitmap =null;
        try {
            URL myurl=new URL(url);
            HttpURLConnection coon =(HttpURLConnection)myurl.openConnection();
            coon.setConnectTimeout(6000);
            coon.setUseCaches(true);
                InputStream inputStream = coon.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.eval,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.eval:
                Bundle bundle=new Bundle();
                bundle.putSerializable("eval",this_cookie);
                Intent intent=new Intent(getApplicationContext(),evaluate.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            default:
        }
        return true;
    }
}
