package ir.alishi.vpn.view;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static AppCompatActivity appCompatActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }


}
