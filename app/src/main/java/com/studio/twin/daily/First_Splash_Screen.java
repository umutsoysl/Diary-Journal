package com.studio.twin.daily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Calendar;

public class First_Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first__splash__screen);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        // resim=(ImageView)findViewById(R.id.screen);
        Thread background = new Thread() {
            public void run() {

                try {

                    sleep(1 * 900);

                    // After 5 seconds redirect to another intent
                    Intent intent = new Intent(First_Splash_Screen.this, SplashScreen.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();
    }
}
