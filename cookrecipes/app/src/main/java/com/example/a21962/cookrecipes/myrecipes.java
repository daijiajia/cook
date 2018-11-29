package com.example.a21962.cookrecipes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class myrecipes extends AppCompatActivity {
    Button add;

    static ArrayList<cookie> COOKIES=new ArrayList<cookie>();
    ListView listView;
    MyAdapter yourAdapter;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(android.os.Message msg){

            initView();
            init_clist();

        };
    };
    private void initView(){
        listView=(ListView)findViewById(R.id.my_coo_list);

    }
    private void init_clist(){
        yourAdapter = new MyAdapter(getApplicationContext(),COOKIES);
        listView.setAdapter(yourAdapter);
    }
    public void mydatabase(){
        Cursor cursor = loginactivity.db.rawQuery("Select * from mycookie",null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String cookingtime = cursor.getString(cursor.getColumnIndex("cookingtime"));
            String preparationttime = cursor.getString(cursor.getColumnIndex("preparationttime"));
            String ingredients = cursor.getString(cursor.getColumnIndex("ingredients"));
            String preparationSteps = cursor.getString(cursor.getColumnIndex("preparationSteps"));
            String rate = cursor.getString(cursor.getColumnIndex("rate"));
            String picture = cursor.getString(cursor.getColumnIndex("picture"));
            cookie Cookie=new cookie(Integer.valueOf(id),name,Float.parseFloat(rate),Integer.valueOf(cookingtime),Integer.valueOf(preparationttime),ingredients,preparationSteps,picture,type);
            COOKIES.add(Cookie);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myrecipes);
        COOKIES =new ArrayList<cookie>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mydatabase();
                Message message=new Message();
                message.what=0;
                message.obj=COOKIES;
                handler.sendMessage(message);
            }
        }).start();


    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        add=(Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),addActivity.class);
                startActivity(intent);
            }
        });
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
}
