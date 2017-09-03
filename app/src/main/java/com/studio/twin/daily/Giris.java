package com.studio.twin.daily;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Giris extends Activity {
    Button giris,help;
    EditText kod;
    TextView temp;
    String[] name;
    String[] password;
    ImageButton eye;
    int sayac=0;
    private Boolean exit = false;
    ArrayList<HashMap<String, String>> user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_giris);
        giris=(Button)findViewById(R.id.giris_devamBtn);
        kod=(EditText)findViewById(R.id.editText);
        help=(Button)findViewById(R.id.button);
        eye=(ImageButton)findViewById(R.id.goz);




        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sayac++;
                String y="";
                if(sayac%2==0)
                {

                }
                else{
                    if(kod.getText().toString().length()>=1) {
                        kod.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    } else {
                        kod.setInputType(129);
                    }
                }

            }
        });



        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text=kod.getText().toString();

                if(text.equals(password[0]))
                {
                    Intent intent = new Intent(Giris.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
                else
                {
                    kod.setError("Kod Hatalı.");
                }


            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Giris.this, KodHelp.class);
                overridePendingTransition(R.anim.left, R.anim.right);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            System.exit(0);

    }

    @Override
    protected void onResume() {
        super.onResume();


        Database db = new Database(getApplicationContext());

        user=db.oturum();
        name=new String[user.size()];
        password=new String[user.size()];

        for (int i = 0; i < user.size(); i++) {
            name[i]=user.get(0).get("name");
            password[i]=user.get(0).get("password");
        }

        //temp.setText("Merhaba  " + name[0]);
    }

    @Override
    protected void onStart() {
        super.onStart();


        Database db = new Database(getApplicationContext());

        user=db.oturum();
        name=new String[user.size()];
        password=new String[user.size()];

        for (int i = 0; i < user.size(); i++) {
            name[i]=user.get(0).get("name");
            password[i]=user.get(0).get("password");
        }

       // temp.setText("Merhaba  " + name[0]);
    }
}
