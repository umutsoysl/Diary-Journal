package com.studio.twin.daily;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import java.util.Random;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class ShowPage extends Activity {
    ArrayList<HashMap<String, String>> Bilgiler;
    TextView baslik;
    TextView metin;
    TextView date;
    String date_title;
    TextView etiket,gun,resim_sayisi,location;
    Dialog dialog2;
    LinearLayout layout,temp_layout;
    ImageView resim,birinci,ikinci,ucuncu,dorduncu,besinci,resim2;
    HorizontalScrollView hsv;
    public int id=0,i=0,mod=0;
    ImageButton show_delete,show_edit,show_share;
    public String bir_resim,iki_resim,uc_resim,dort_resim,bes_resim;
    int no=0;
    FrameLayout frameLayout_resim_gostergesi,location_layout;
    String check_location,Silinecek_not;
    View v,golge;
    SeekBar metinBoyutu;
    TextView metinBoyutunuGoster;
    Context context;
    public static int silinecek_id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        getWindow().setStatusBarColor(ContextCompat.getColor(ShowPage.this, R.color.gri));

        Intent intent = getIntent();

        MainActivity m = new MainActivity();
        // String ID=intent.getStringExtra("id");

        id = Integer.parseInt(String.valueOf(m.posizyon));

        setContentView(R.layout.activity_show_page);
        baslik = (TextView) findViewById(R.id.show_add_baslik);
        metin = (TextView) findViewById(R.id.show_add_metin);
        etiket = (TextView) findViewById(R.id.show_add_etiket);
        date = (TextView) findViewById(R.id.show_add_date);
        ImageButton geri = (ImageButton) findViewById(R.id.back_goster);
        layout = (LinearLayout) findViewById(R.id.show_page_layout);
        resim = (ImageView) findViewById(R.id.show_page_resim);
        hsv = (HorizontalScrollView) findViewById(R.id.show_hsv2);
        birinci = (ImageView) findViewById(R.id.show_birinci);
        besinci = (ImageView) findViewById(R.id.show_besinci);
        ikinci = (ImageView) findViewById(R.id.show_ikinci);
        ucuncu = (ImageView) findViewById(R.id.show_ucuncu);
        dorduncu = (ImageView) findViewById(R.id.show_dorduncu);
        v = (View) findViewById(R.id.golge2);
        show_delete=(ImageButton)findViewById(R.id.show_delete);
        show_edit=(ImageButton)findViewById(R.id.show_edit);
        show_share=(ImageButton)findViewById(R.id.show_share);
        resim_sayisi = (TextView) findViewById(R.id.sayi_tresim);
        frameLayout_resim_gostergesi = (FrameLayout) findViewById(R.id.layout_resimSayisi);
        location_layout=(FrameLayout)findViewById(R.id.location_layout);
        location=(TextView)findViewById(R.id.show_location);
        metinBoyutu=(SeekBar)findViewById(R.id.seekBar);
        metinBoyutunuGoster=(TextView)findViewById(R.id.textSizeShow);


        show_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(getApplicationContext());
                Bilgiler = db.Gunler();

                id=db.Gunler().size()-id-1;

                Intent intent = new Intent(ShowPage.this, UpdatePage.class);
                intent.putExtra("id", id+"");
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        metinBoyutu.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                metinBoyutunuGoster.setText("Size: " + (progress + 16));
                metin.setTextSize(progress+16);
                etiket.setTextSize(progress+16);

            }
        });


        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowPage.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        show_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(ShowPage.this, R.anim.image_click));
                final Database db=new Database(getApplicationContext());
                Bilgiler = db.Gunler();
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        ShowPage.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                String not="Sil: "+Silinecek_not;
                alertDialogBuilder.setTitle(not);
                alertDialogBuilder.setIcon(R.drawable.delete_warning);
                // set title
                // set dialog message
                alertDialogBuilder
                        .setMessage("Bu Notunuzu Silmekten Emin misiniz?")
                        .setCancelable(false)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                final ProgressDialog ringProgressDialog =new  ProgressDialog(ShowPage.this, ProgressDialog.THEME_HOLO_DARK);
                                ringProgressDialog.setMessage(Silinecek_not+" siliniyor ...");
                                ringProgressDialog.setCancelable(true);
                                ringProgressDialog.show();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(1900);
                                        } catch (Exception e) {

                                        }
                                        ringProgressDialog.dismiss();
                                    }
                                }).start();

                                db.GunSil(silinecek_id);
                                Intent intent = new Intent(ShowPage.this, MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }
                        })
                        .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(true);
            }
        });






        birinci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(bir_resim));
            }
        });

        ikinci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(iki_resim));
            }
        });

        ucuncu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(uc_resim));
            }
        });

        dorduncu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(dort_resim));
            }
        });
        besinci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(bes_resim));
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        Database db = new Database(getApplicationContext()); // Db bağlantısı oluşturuyoruz. İlk seferde database oluşturulur.
        Bilgiler = db.Gunler();

        id=db.Gunler().size()-id-1;
        Silinecek_not=Bilgiler.get(id).get("baslik");
        silinecek_id=Integer.valueOf(Bilgiler.get(id).get("GUN_id"));
        baslik.setText(Bilgiler.get(id).get("baslik").toUpperCase());
        metin.setText(Html.fromHtml(Bilgiler.get(id).get("icerik")));
        date_title=Bilgiler.get(id).get("tarih");
        date.setText(Bilgiler.get(id).get("tarih"));
        etiket.setText(Bilgiler.get(id).get("etiket"));
        mod=Integer.valueOf(Bilgiler.get(id).get("emoji"));
        bir_resim=(Bilgiler.get(id).get("birinci"));
        iki_resim=(Bilgiler.get(id).get("ikinci"));
        uc_resim=(Bilgiler.get(id).get("ucuncu"));
        dort_resim=(Bilgiler.get(id).get("dorduncu"));
        bes_resim=(Bilgiler.get(id).get("besinci"));
        check_location=(Bilgiler.get(id).get("konum"));



        if(check_location.length()>2)
        {
            //v.setVisibility(View.INVISIBLE);
            location_layout.setVisibility(View.VISIBLE);
            location.setText(check_location);
        }


        if(bir_resim.length()<2&&iki_resim.length()<2&&uc_resim.length()<2&&dort_resim.length()<2&&bes_resim.length()<2)
        {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            birinci.setVisibility(View.VISIBLE);
            Random r = new Random();
            int i1 = r.nextInt(6) + 0;
            int[] myImageList = new int[]{R.drawable.manzara, R.drawable.manzara2,R.drawable.manzara6,R.drawable.manzara5,R.drawable.manzara4,R.drawable.manzara3};
            Picasso.with(this).load(myImageList[i1]).resize(width, 800).into(birinci);
        }
        else
        {

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            if(bir_resim.length()>2)
            {
                birinci.setVisibility(View.VISIBLE);
                try{
                    //birinci.setImageURI(Uri.parse(gun_resim1[position]));

                    no++;
                    Picasso.with(this).load(bir_resim).resize(width,height/3+height/5).into(birinci);
                }catch(Exception e){
                    e.getMessage();
                }

            }
            if(iki_resim.length()>2)
            {
                ikinci.setVisibility(View.VISIBLE);
                try{

                    //ikinci.setImageURI(Uri.parse(gun_resim2[position]));
                    no++;
                    Picasso.with(this).load(iki_resim).resize(width,height/3+height/5).into(ikinci);
                }catch(Exception e){
                    e.getMessage();
                }

            }
            if(uc_resim.length()>2)
            {
                ucuncu.setVisibility(View.VISIBLE);
                try{
                    no++;
                    //ucuncu.setImageURI(Uri.parse(gun_resim3[position]));
                    Picasso.with(this).load(uc_resim).resize(width,height/3+height/5).into(ucuncu);
                }catch(Exception e){
                    e.getMessage();
                }

            }
            if(dort_resim.length()>2)
            {
                dorduncu.setVisibility(View.VISIBLE);
                try {
                    no++;
                    //dorduncu.setImageURI(Uri.parse(gun_resim4[position]));
                    Picasso.with(this).load(dort_resim).resize(width, height/3+height/5).into(dorduncu);
                }catch(Exception e){
                    e.getMessage();
                }

            }
            if(bes_resim.length()>2)
            {
                besinci.setVisibility(View.VISIBLE);
                try {
                    no++;
                    // besinci.setImageURI(Uri.parse(gun_resim5[position]));
                    Picasso.with(this).load(bes_resim).resize(width,height/3+height/5).into(besinci);
                }catch(Exception e){
                    e.getMessage();
                }

            }
        }





        if(mod==0)
        {
            layout.setVisibility(View.INVISIBLE);
        }

        else  if(mod==1)
        {
            resim.setImageResource(R.drawable.gulenyuz);
        }

        else  if(mod==2)
        {
            resim.setImageResource(R.drawable.normal);
        }
        else  if(mod==3)
        {
            resim.setImageResource(R.drawable.asik);
        }
        else if(mod==4)
        {
            resim.setImageResource(R.drawable.kiska);
        }
        else  if(mod==5)
        {
            resim.setImageResource(R.drawable.moralsiz);
        }
        else if(mod==6)
        {
            resim.setImageResource(R.drawable.yorgun);
        }
        else   if(mod==7)
        {
            resim.setImageResource(R.drawable.uzgun);
        }
        else   if(mod==8)
        {
            resim.setImageResource(R.drawable.sinirli);
        }
        else   if(mod==9)
        {
            resim.setImageResource(R.drawable.begenmis);
        }
        else   if(mod==10)
        {
            resim.setImageResource(R.drawable.eglenmis);
        }


        resim_sayisi.setText(String.valueOf(no));
    }



    public Bitmap convertBitmap(String url)
    {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }

    public void image_show(final Uri uri)
    {


        dialog2 = new Dialog(this,android.R.style.Theme_Light);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.image_show);

        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog2.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        dialog2.setCanceledOnTouchOutside(true);
        final View dis =(View) dialog2.findViewById(R.id.arka_layout2);
        final ImageView resim=(ImageView)dialog2.findViewById(R.id.view_resim);
        resim.setImageBitmap(convertBitmap(uri.toString()));
        final Button delete=(Button)dialog2.findViewById(R.id.delete);
        final Button update=(Button)dialog2.findViewById(R.id.update);
        final ImageButton close=(ImageButton)dialog2.findViewById(R.id.geri);
        final TextView image_baslik=(TextView)dialog2.findViewById(R.id.image_baslik);
        final TextView image_etiket=(TextView)dialog2.findViewById(R.id.image_etiket);
        final TextView number_page=(TextView)dialog2.findViewById(R.id.page_number);
        final TextView page_date=(TextView)dialog2.findViewById(R.id.image_create_date);
        final View cizgi=(View)dialog2.findViewById(R.id.golge);

        page_date.setText((Bilgiler.get(id).get("tarih")));
        number_page.setVisibility(View.GONE);
        image_baslik.setText((Bilgiler.get(id).get("baslik")));
        image_etiket.setText((Bilgiler.get(id).get("etiket")));
       // delete.setVisibility(View.GONE);
       // update.setVisibility(View.GONE);
       // cizgi.setVisibility(View.GONE);
        delete.setText("Kaydet");
        update.setText("Paylaş");


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        dialog2.show();
    }


}
