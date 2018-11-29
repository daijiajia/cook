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
import android.widget.TextView;
import android.widget.Toast;

public class delete extends AppCompatActivity {
    TextView delete_view;
    Button delet_button;
    cookie delete_cookie;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
                Toast.makeText(getApplicationContext(), "you have delete it -.-", Toast.LENGTH_LONG).show();
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        delete_cookie=(cookie) getIntent().getSerializableExtra("dele_cookie");
        delete_view=(TextView)findViewById(R.id.dele_textview);
        delete_view.setText(delete_cookie.getName()+"("+delete_cookie.getId()+")");
        delet_button=(Button)findViewById(R.id.de_bu);
        delet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            loginactivity.db.execSQL("DELETE FROM mycookie WHERE id = '"+delete_cookie.getId()+"' AND" +
                                    "  name= '"+delete_cookie.getName()+"';");
                            Message msg = new Message();
                            msg.what = 0;
                            msg.obj = 0;
                            handler.sendMessage(msg);
                            finish();
                            Intent intent = new Intent(getApplicationContext(),myrecipes.class);
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }
}
