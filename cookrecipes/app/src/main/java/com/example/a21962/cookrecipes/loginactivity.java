package com.example.a21962.cookrecipes;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class loginactivity extends AppCompatActivity {
    private ArrayList<cookie> Cookies=new ArrayList<cookie>();
    static cookiedatebase help;
    static SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        help=new cookiedatebase(getApplication());
        db=help.getWritableDatabase();
       final EditText name=(EditText)findViewById(R.id.name);
       final EditText password=(EditText)findViewById(R.id.pass);
       final Button login=(Button)findViewById(R.id.button1);

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
            }
        });
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setText("");
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals("admin")&&password.getText().toString().equals("admin")){
                    Log.d("name",name.getText().toString());

                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

}
