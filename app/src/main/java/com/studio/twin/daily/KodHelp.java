package com.studio.twin.daily;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Random;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class KodHelp extends Activity {

    EditText email,code;
    Button get_code,set_code;
    String[] mail;
    String guvenlik_kodu;
    String[] password;
    private Boolean exit = false;
    ArrayList<HashMap<String, String>> user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_kod_help);

        email=(EditText)findViewById(R.id.email);
        code=(EditText)findViewById(R.id.code);
        get_code=(Button)findViewById(R.id.get_kod);
        set_code=(Button)findViewById(R.id.reset_password);

        set_code.setVisibility(View.INVISIBLE);
        code.setVisibility(View.INVISIBLE);

        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(4);
        char tempChar;
        for (int i = 0; i < 4; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        guvenlik_kodu=randomStringBuilder.toString();

        get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp=email.getText().toString();
                if(temp.equals(mail[0].toString()))
                {

                    try {
                        GMailSender sender = new GMailSender("mzlm.sysl@gmail.com", "1311050853");
                        sender.sendMail("This is Subject",
                               "Güvenlik Kodu : "+guvenlik_kodu,
                                "mzlm.sysl@gmail.com",
                               temp);
                        set_code.setVisibility(View.VISIBLE);
                        code.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }
                }
                else
                {
                    email.setError("Lütfen Hesabınıza kayıtlı email giriniz.");
                }
            }
        });



        set_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a=code.getText().toString();

                if(a.equals(guvenlik_kodu))
                {
                    Toast.makeText(getBaseContext(),"Başarılı",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    code.setError("güvenlik kodu hatalı!");
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Database db = new Database(getApplicationContext());

        user=db.oturum();
        mail=new String[user.size()];
        password=new String[user.size()];

        for (int i = 0; i < user.size(); i++) {
            mail[i]=user.get(0).get("email");
            password[i]=user.get(0).get("password");
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        Database db = new Database(getApplicationContext());

        user=db.oturum();
        mail=new String[user.size()];
        password=new String[user.size()];

        for (int i = 0; i < user.size(); i++) {
            mail[i]=user.get(0).get("email");
            password[i]=user.get(0).get("password");
        }


    }
}
