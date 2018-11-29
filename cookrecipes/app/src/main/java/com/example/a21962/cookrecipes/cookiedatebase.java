package com.example.a21962.cookrecipes;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 21962 on 2018/4/17.
 */

public class cookiedatebase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="cook.db";
    private static final int DATABASE_VERSION=2;
    private static final String TABLE_NAME="mycookie";
    private static final String TABLE_CREATE=
            "CREATE TABLE "+TABLE_NAME+" ("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "name TEXT, "+
            "type TEXT, "+
            "cookingtime INTEGER, "+
            "preparationttime INTEGER, "+
            "ingredients TEXT, "+
            "preparationSteps TEXT, "+
            "rate decimal, "+
            "picture TEXT);";

    public cookiedatebase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("database///","creating!!!!!!!!1");
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.w("Example:","Upgrading database, this will drop "+
                "tables and recreate");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
public static final String getname(){return TABLE_NAME;}


}
