package com.example.a21962.cookrecipes;

import java.io.Serializable;

/**
 * Created by 21962 on 2018/4/13.
 */

public class cookie implements Serializable{
    int id;
    String name;
    String type;
    int cookingTime;
    int preparationTime;
    String ingredients;
    String prepationsSteps;
    float rate;
    String picture;
    byte[] picture1;
    public String getType(){
        return type;
    }
    public  int getPreparationTime(){
        return preparationTime;
    }
    public int getCookingTime(){
        return cookingTime;
    }
    public int getId(){
        return id;
    }
    public String getIngredients(){
        return ingredients;
    }
    public String getPrepationsSteps(){
        return prepationsSteps;
    }
    public String getPicture(){
        return picture;
    }
    public float getRate(){
        return rate;
    }
    public String getName(){
        return name;
    }
    public cookie(int id, String name,float rate,int cootime,int ptime,String ingredients,String pSteps,String picture,String type){
        this.id=id;
        this.name=name;
        this.rate=rate;
        this.cookingTime=cootime;
        this.preparationTime=ptime;
        this.ingredients=ingredients;
        this.prepationsSteps=pSteps;
        this.picture=picture;
        this.type=type;
    }
    public cookie(int id, String name,int rate,int cootime,int ptime,String ingredients,String pSteps,byte[] picture,String type){
        this.id=id;
        this.name=name;
        this.rate=rate;
        this.cookingTime=cootime;
        this.preparationTime=ptime;
        this.ingredients=ingredients;
        this.prepationsSteps=pSteps;
        this.picture1=picture;
        this.type=type;
    }
}
