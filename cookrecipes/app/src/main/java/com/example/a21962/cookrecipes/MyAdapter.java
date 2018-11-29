package com.example.a21962.cookrecipes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 21962 on 2018/4/14.
 */

public class MyAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<cookie> my_cookies;
    private Context context;
    public MyAdapter(){

    }

    public MyAdapter(Context context,List<cookie> my_cookie){
        this.mInflater = LayoutInflater.from(context);
        this.my_cookies=my_cookie;
        this.context=context;
    }

    @Override
    public int getCount() {
        return my_cookies.size();
    }

    @Override
    public cookie getItem(int i) {
        return my_cookies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view  == null){
            view=mInflater.inflate(R.layout.tools,viewGroup,false);
            holder=new ViewHolder();
            holder.myname=(TextView)view.findViewById(R.id.co_name);
            holder.myrate=(RatingBar)view.findViewById(R.id.rate);

            view.setTag(holder);


        }else {
            holder = (ViewHolder) view.getTag();
        }
        final cookie myc=my_cookies.get(i);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(context, detail_activity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("this_cookie",myc);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    context.startActivity(intent);

                }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(context, "long click", Toast.LENGTH_LONG).show();
                try {
                    Intent intent = new Intent(context, delete.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("dele_cookie",myc);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    context.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }

                return true;
            }
        });

        holder.myname.setText(myc.getName());
        holder.myrate.setRating(myc.getRate());
        return view;
    }
    private class ViewHolder{
        public  TextView myname;
        public RatingBar myrate;
    }
}
