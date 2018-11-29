package com.example.a21962.cookrecipes;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class addActivity extends AppCompatActivity {
    EditText add_NAME;
    EditText add_TYPE;
    EditText add_PRETIME;
    EditText add_COOKTIME;
    EditText add_INGR;
    EditText add_PRESTEPS;
    RatingBar add_RATE;
    ImageView photo;
    static String mFilePath;
    static int REQUEST_CAMERA=2;
    static int CHOOSE_FILE=1;
    static Uri photoUri=null;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private void showAlertDialog(){
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(LayoutInflater.from(this).inflate(R.layout.file_or_camera,null));
        dialog.show();
        dialog.getWindow().setContentView(R.layout.file_or_camera);
        Button cemera=(Button) dialog.findViewById(R.id.camera);
        Button file=(Button) dialog.findViewById(R.id.file);
        Button cancle=(Button)dialog.findViewById(R.id.cancle);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        cemera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                    Calendar calendar = Calendar.getInstance();
                    String date = calendar.get(Calendar.YEAR)
                            + (calendar.get(Calendar.MONTH)+1)//从0计算
                            + calendar.get(Calendar.DAY_OF_MONTH)
                            + calendar.get(Calendar.HOUR_OF_DAY)
                            + calendar.get(Calendar.MINUTE) +calendar.get(Calendar.SECOND)+"s";
                    Log.e("msg", date);

                mFilePath= Environment.getExternalStorageDirectory().getPath();
                mFilePath=mFilePath+"/" + date+".png";

                Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 photoUri =Uri.fromFile(new File(mFilePath));

                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(intent,REQUEST_CAMERA);
            }
        });
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,CHOOSE_FILE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            if(requestCode == REQUEST_CAMERA){

                FileInputStream fis =null;
                try {

                    fis = new FileInputStream(mFilePath);
                    Bitmap bitmap= BitmapFactory.decodeStream(fis);

                    photo.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }finally {
                    try {
                        fis.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }else if (requestCode==CHOOSE_FILE){
                Uri uri=data.getData();
                photoUri=uri;
                if(uri != null){
                    try{
                        Bitmap bitmap =MediaStore.Images.Media.getBitmap(getContentResolver(),uri);

                        photo.setImageBitmap(bitmap);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }else{
                    Bundle bundleE=data.getExtras();
                    if(bundleE != null){
                        Bitmap bitmap =bundleE.getParcelable("data");

                        photo.setImageBitmap(bitmap);
                    }
                }
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        verifyStoragePermissions(this);
        add_NAME=(EditText)findViewById(R.id.add_name);
        add_TYPE=(EditText)findViewById(R.id.add_type);
        add_PRETIME=(EditText)findViewById(R.id.addpretime);
        add_COOKTIME=(EditText)findViewById(R.id.addcooktime);
        add_INGR=(EditText)findViewById(R.id.addingre);
        add_PRESTEPS=(EditText)findViewById(R.id.addpresteps);
        add_RATE=(RatingBar)findViewById(R.id.addrate);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        photo=(ImageView)findViewById(R.id.addphoto);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAlertDialog();
            }
        });
        Button add=(Button)findViewById(R.id.edit_finish);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   String sql="INSERT INTO mycookie (id, name, type,cookingtime,preparationttime,ingredients,preparationSteps,rate,picture) VALUES (,,)";
                ContentValues values = new ContentValues();
                values.put("name",add_NAME.getText().toString());
                values.put("type", add_TYPE.getText().toString());
                values.put("cookingtime",String.valueOf(add_COOKTIME.getText()));
                values.put("preparationttime", String.valueOf(add_PRETIME.getText().toString()));
                values.put("ingredients", add_INGR.getText().toString());
                values.put("preparationSteps", add_PRESTEPS.getText().toString());
                values.put("rate", add_RATE.getRating());
                values.put("picture",photoUri.toString());

                loginactivity.db.insert("mycookie",null,values);
                Intent intent=new Intent(getApplication(),myrecipes.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
