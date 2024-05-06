package com.code.mysocial;

import android.content.Context;
import android.content.SharedPreferences;

public class MysharedPreferences {
    private Context context;

    public MysharedPreferences(Context context) {
        this.context = context;
    }
    public void setMyData(String number){
        SharedPreferences.Editor editor= context.getSharedPreferences("mydata",Context.MODE_PRIVATE).edit();
        editor.putString("number",number);
        editor.apply();
    }
    public String getMyNumber(String name, String number){
        SharedPreferences editor= context.getSharedPreferences("mydata",Context.MODE_PRIVATE);
        return editor.getString("number",null);
    }
}
