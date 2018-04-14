package com.cl.popularmovies.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static String compress(String input){
        if (input==null||input.isEmpty()) {
            return "";
        }
        StringBuffer stringBuffer=new StringBuffer();
        char countChar=0;
        int count=0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == countChar) {
                count++;
            } else {
                if (countChar!=0) {
                    stringBuffer.append(countChar);
                    stringBuffer.append(count);
                }
                countChar=input.charAt(i);
                count=1;
            }
        }
        if (countChar!=0) {
            stringBuffer.append(countChar);
            stringBuffer.append(count);
        }
        return stringBuffer.length()<input.length()?stringBuffer.toString():input;
    }
}
