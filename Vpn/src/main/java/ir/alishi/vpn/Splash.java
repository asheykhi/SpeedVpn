package ir.alishi.vpn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


import org.spongycastle.util.Constants;
import org.spongycastle.util.Utils;

import in.codeshuffle.typewriterview.TypeWriterView;

public class Splash extends AppCompatActivity {
    TypeWriterView typeWr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.requestFullScreenActivity(this);
        setContentView(R.layout.activity_splash);

        typeWr = findViewById(R.id.typeWriterView_desc);
        typeWr.setDelay(110);
        typeWr.setWithMusic(false);
        typeWr.animateText("Speed Vpn");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, Constants.SPLASH_TIME);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAndRemoveTask();
    }
}
