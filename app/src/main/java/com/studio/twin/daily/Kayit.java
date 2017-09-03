package com.studio.twin.daily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Kayit extends AppCompatActivity {
    int row=0;
    EditText name,email,password,password2;
    Button create;
    String names,emails,passwords,password2s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_kayit);

        name=(EditText)findViewById(R.id.input_name);
        email=(EditText)findViewById(R.id.input_email);
        password=(EditText)findViewById(R.id.input_password);
        password2=(EditText)findViewById(R.id.input_password2);
        create=(Button)findViewById(R.id.btn_signup);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                names=name.getText().toString();
                if( name.getText().toString().length() == 0 )
                    name.setError( "Name is required!" );
                emails=email.getText().toString();
                if( email.getText().toString().length() == 0 )
                    email.setError( "Email is required!" );
                passwords=password.getText().toString();
                if( password.getText().toString().length() == 0 )
                    password.setError( "Password is required!" );
                password2s=password2.getText().toString();
                if( password2.getText().toString().length() == 0 )
                    password2.setError( "Repeat password!" );

                if(passwords.equals(password2s)&&passwords!=null&&!passwords.equals(""))
                {
                    Database db = new Database(getApplicationContext());
                    db.islemEkle(names,emails,passwords);//kullanıcıyı ekledik..
                    Toast.makeText(getApplicationContext(), "\n" +
                            "Nice!!Registration succeeded.", Toast.LENGTH_LONG).show();
                    db.close();
                    Intent intent = new Intent(Kayit.this, Giris.class);
                    startActivity(intent);

                }
                else
                {
                    password2.setError( "Repeat password! error " );
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Database db = new Database(getApplicationContext());
        row=db.getRowCount();
        if(row>0)
        {
            Intent intent = new Intent(Kayit.this, Giris.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Database db = new Database(getApplicationContext());
        row=db.getRowCount();
        if(row>0)
        {
            Intent intent = new Intent(Kayit.this, Giris.class);
            startActivity(intent);
        }
    }
}
