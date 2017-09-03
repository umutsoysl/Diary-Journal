package com.studio.twin.daily;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Calendar;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SplashScreen extends Activity {
    ImageView resim;
    LinearLayout li;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

       // resim=(ImageView)findViewById(R.id.screen);
        li=(LinearLayout)findViewById(R.id.lineer);

        Calendar c = Calendar.getInstance();
        int saat = c.get(Calendar.HOUR_OF_DAY);
        if(saat>=21||saat<=5)
        {
            // resim.setImageResource(R.drawable.iyigeceler);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            li.setBackgroundResource(R.drawable.iyigeceler);
        }
        else if(saat<12)
        {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            li.setBackgroundResource(R.drawable.gunaydin);
        }
        else if(saat<=21&&saat>=17)
        {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            li.setBackgroundResource(R.drawable.iyiaksamlar);
        }
        else
        {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            li.setBackgroundResource(R.drawable.iyigunler);
        }
        Thread background = new Thread() {
            public void run() {

                try {

                    sleep(3 * 900);
                    Intent i=new Intent(getBaseContext(),Kayit.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();
    }
}
