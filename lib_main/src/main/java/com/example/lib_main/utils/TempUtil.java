package com.example.lib_main.utils;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class TempUtil {

    public static float getDensity(Context context){
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.density;
    }
}
