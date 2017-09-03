package com.studio.twin.daily;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import com.squareup.picasso.Picasso;

public class ListviewAdapter extends BaseAdapter {
    String ay;
    // Declare Variables
    Dialog dialog2;
    Context context;
    String[] gun_baslik;
    String[] gun_metin;
    String[] gun_tarih;
    String[] gun_konum;
    String[] gun_etiket;
    String[] gun_emoji;
    String[] gun_resim1;
    String[] gun_resim2;
    String[] gun_resim3;
    String[] gun_resim4;
    String[] gun_resim5;
    String[] ozel_konum;
    ArrayList<Bitmap> bitmapArray;
    public static int i=0;
    public static int image_counter=0;
    public static int image_count2 = 0;
    LayoutInflater inflater;
    public static String zaman="";
    public static String color="";
    ArrayList<HashMap<String, String>> Bilgiler;
    //ListviewAdapter constructor
    //Gelen değerleri set ediyor
    public ListviewAdapter(Context context, String[] baslik, String[] metin, String[] tarih, String[] etiket,String[] emoji,String[] birinc,String[] ikinc,String[] ucunc,String[] dordunc,String[] besinc,String[] konum) {
        this.context = context;
        this.gun_baslik = baslik;
        this.gun_metin = metin;
        this.gun_tarih = tarih;
        this.gun_etiket = etiket;
        this.gun_emoji=emoji;
        this.gun_resim1=birinc;
        this.gun_resim2=ikinc;
        this.gun_resim3=ucunc;
        this.gun_resim4=dordunc;
        this.gun_resim5=besinc;
        this.gun_konum=konum;



    }

    @Override
    public int getCount() {
        return gun_tarih.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {

        i=position;
        // Declare Variables
        TextView baslik;
        final TextView icerik;
        TextView gun,tarih;
        TextView emoji,konum;
        LinearLayout arkaplan,layout,item_emoji_layout;
        RelativeLayout item_delete,item_update,item_share;
        ImageView resim,birinci,ikinci,ucuncu,dorduncu,besinci,image_konum,vi,emoji_image,resim2,resim3,resim4,resim5;
        final TextView devammi;
        HorizontalScrollView hsv;
        FrameLayout date_frame,item_frame;
        TextView etiket;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View itemView = inflater.inflate(R.layout.list_item, parent, false);//list_item_row dan yeni bir view oluşturuyoruz

        // oluşan itemviewin içindeki alanları Anasayfadan gelen değerler ile set ediyoruz
        baslik = (TextView) itemView.findViewById(R.id.header);
        icerik = (TextView) itemView.findViewById(R.id.metin);
        gun = (TextView) itemView.findViewById(R.id.date_day);
        tarih = (TextView) itemView.findViewById(R.id.date_string);
        date_frame=(FrameLayout)itemView.findViewById(R.id.date_frame);
        item_frame=(FrameLayout)itemView.findViewById(R.id.item_frame);
        arkaplan=(LinearLayout)itemView.findViewById(R.id.list_resim);
        item_emoji_layout=(LinearLayout)itemView.findViewById(R.id.item_emoji_layout);
        resim=(ImageView)itemView.findViewById(R.id.imageView2);
        resim2=(ImageView)itemView.findViewById(R.id.imageView27);
        resim3=(ImageView)itemView.findViewById(R.id.imageView28);
        resim4=(ImageView)itemView.findViewById(R.id.imageView29);
        resim5=(ImageView)itemView.findViewById(R.id.imageView18);
        emoji_image=(ImageView)itemView.findViewById(R.id.emoji_image);
        konum=(TextView)itemView.findViewById(R.id.li_konum);
        image_konum=(ImageView)itemView.findViewById(R.id.konum_image);
        item_delete=(RelativeLayout)itemView.findViewById(R.id.r_item_delete);
        item_update=(RelativeLayout)itemView.findViewById(R.id.r_item_update);
        item_share=(RelativeLayout)itemView.findViewById(R.id.r_item_share);

        final Database db = new Database(context); // Db bağlantısı oluşturuyoruz. İlk seferde database oluşturulur.
        Bilgiler = db.Gunler();
        final int  gelen_id = Integer.valueOf(Bilgiler.get(db.Gunler().size()-position-1).get("GUN_id"));


        item_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, UpdatePage.class);
                intent.putExtra("id", position+"");
                context.startActivity(intent);
            }
        });


        item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                //String not="Sil: "+Silinecek_not;
              //  alertDialogBuilder.setTitle(not);
                alertDialogBuilder.setIcon(R.drawable.delete_warning);
                // set title
                // set dialog message
                alertDialogBuilder
                        .setMessage("Bu Notunuzu Silmekten Emin misiniz?")
                        .setCancelable(false)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                final ProgressDialog ringProgressDialog =new  ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
                                // ringProgressDialog.setMessage(Silinecek_not+" siliniyor ...");
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
                                db.GunSil(gelen_id);
                                Intent intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);
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



        int width= context.getResources().getDisplayMetrics().widthPixels;
        int height= context.getResources().getDisplayMetrics().heightPixels;

        if(gun_resim1[position].length()<2)
        {
            arkaplan.setVisibility(View.GONE);
        }
        else if(gun_resim2[position].length()<2){
            resim2.setVisibility(View.GONE);
            resim3.setVisibility(View.GONE);
            resim4.setVisibility(View.GONE);
            Picasso.with(context).load(gun_resim1[position]).centerCrop().resize((width / 2 + width / 4), (height/8 + height/10)).into(resim);
        }
        else if(gun_resim1[position].length()>2&&gun_resim2[position].length()>2&&gun_resim3[position].length()<2&&gun_resim4[position].length()<2)
        {
            resim3.setVisibility(View.GONE);
            resim4.setVisibility(View.GONE);
            Picasso.with(context).load(gun_resim1[position]).centerCrop().centerCrop().resize(((width / 2 + width / 4-width/40) ) / 2, ((height / 8 + height / 10))).into(resim);
            Picasso.with(context).load(gun_resim2[position]).centerCrop().centerCrop().resize(((width / 2 + width / 4-width/40) ) / 2, ((height / 8 + height / 10)) ).into(resim2);
        }
        else if(gun_resim1[position].length()>2&&gun_resim2[position].length()>2&&gun_resim3[position].length()>2&&gun_resim4[position].length()<2)
        {
            resim3.setVisibility(View.GONE);
            resim4.setVisibility(View.GONE);
            Picasso.with(context).load(gun_resim1[position]).centerCrop().centerCrop().resize(((width / 2 + width / 4-width/40) ) / 2, ((height / 8 + height / 10)/2)).into(resim);
            Picasso.with(context).load(gun_resim2[position]).centerCrop().centerCrop().resize(((width / 2 + width / 4-width/40) ) / 2, ((height / 8 + height / 10)/2) ).into(resim2);
            Picasso.with(context).load(gun_resim3[position]).centerCrop().centerCrop().centerCrop().resize(((width / 2 + width/4 ) ), ((height / 8 + height / 10)/2) ).into(resim5);
        }
        else if(gun_resim1[position].length()>2&&gun_resim2[position].length()>2&&gun_resim3[position].length()>2&&gun_resim4[position].length()>2)
        {
            item_frame.setMinimumHeight((height/8 + height/10+height/100));
            Picasso.with(context).load(gun_resim1[position]).centerCrop().centerCrop().resize(((width / 2 + width / 4 -width/40 )) / 2, ((height / 8 + height / 10)/2)).into(resim);
            Picasso.with(context).load(gun_resim2[position]).centerCrop().centerCrop().resize(((width / 2 + width /4-width/40) ) / 2, ((height / 8 + height / 10)/2) ).into(resim2);
            Picasso.with(context).load(gun_resim3[position]).centerCrop().centerCrop().resize(((width / 2 + width / 4-width/40) ) / 2, ((height / 8+ height / 10)/2)).into(resim3);
            Picasso.with(context).load(gun_resim4[position]).centerCrop().centerCrop().resize(((width / 2 + width / 4-width/40) ) / 2, ((height / 8 + height / 10)/2) ).into(resim4);
        }

        if(Integer.parseInt(gun_emoji[position])==0)
        {
            item_emoji_layout.setVisibility(View.INVISIBLE);
        }

        else  if(Integer.parseInt(gun_emoji[position])==1)
        {
            emoji_image.setImageResource(R.drawable.gulenyuz);
            //icerik.setPadding(0, 8, 0, 0);
        }

        else  if(Integer.parseInt(gun_emoji[position])==2)
        {
            emoji_image.setImageResource(R.drawable.normal);
           // icerik.setPadding(0, 8, 0, 0);
        }
        else  if(Integer.parseInt(gun_emoji[position])==3)
        {
            emoji_image.setImageResource(R.drawable.asik);
          //  icerik.setPadding(0, 8, 0, 0);
        }
        else if(Integer.parseInt(gun_emoji[position])==4)
        {
            emoji_image.setImageResource(R.drawable.kiska);
            //icerik.setPadding(0, 8, 0, 0);
        }
        else  if(Integer.parseInt(gun_emoji[position])==5)
        {
            emoji_image.setImageResource(R.drawable.moralsiz);
           // icerik.setPadding(0, 8, 0, 0);
        }
        else if(Integer.parseInt(gun_emoji[position])==6)
        {
            emoji_image.setImageResource(R.drawable.yorgun);
           // icerik.setPadding(0, 8, 0, 0);
        }
        else   if(Integer.parseInt(gun_emoji[position])==7)
        {
            emoji_image.setImageResource(R.drawable.uzgun);
            //icerik.setPadding(0, 8, 0, 0);
        }
        else   if(Integer.parseInt(gun_emoji[position])==8)
        {
            emoji_image.setImageResource(R.drawable.sinirli);
            //icerik.setPadding(0, 8, 0, 0);
        }
        else   if(Integer.parseInt(gun_emoji[position])==9)
        {
            emoji_image.setImageResource(R.drawable.begenmis);
           // icerik.setPadding(0, 8, 0, 0);
        }
        else   if(Integer.parseInt(gun_emoji[position])==10)
        {
            emoji_image.setImageResource(R.drawable.eglenmis);
           // icerik.setPadding(0, 8, 0, 0);
        }




        Random r = new Random();
        int i1 = r.nextInt(22) + 0;
        String[] mycolor = new String[]{"#2e8fff","#069c01","#8002fe","#fe03e5","#fc4001","#03324a","#12a4fa","#00a5c1","#02849a","#FF9B0621","#1f6818"
        ,"#616161","#5D4037","#607D8B","#00796B","#F50057","#C51162","#B71C1C","#3F51B5","#006064","#E65100","#424242"
        };
      //  date_frame.setBackgroundResource(R.drawable.circle_layout);
      //  date_frame.setBackgroundColor(Color.parseColor(mycolor[i1]));

        String temp=gun_tarih[position].toString();
        String parca[]=temp.split("\\.");
        if(parca[1].equals("01"))
        {
            ay="Ock";
        }
        if(parca[1].equals("02"))
        {
            ay="Şbt";
        }
        if(parca[1].equals("03"))
        {
            ay="Mrt";
        }
        if(parca[1].equals("04"))
        {
            ay="Nsn";
        }
        if(parca[1].equals("05"))
        {
            ay="May";
        }
        if(parca[1].equals("06"))
        {
            ay="Haz";
        }
        if(parca[1].equals("07"))
        {
            ay="Tem";
        }
        if(parca[1].equals("08"))
        {
            ay="Ağu";
        }
        if(parca[1].equals("09"))
        {
            ay="Eyl";
        }
        if(parca[1].equals("10"))
        {
            ay="Eki";
        }
        if(parca[1].equals("11"))
        {
            ay="Kas";
        }
        if(parca[1].equals("12"))
        {
            ay="Ara";
        }

        gun.setText(parca[0]);
        tarih.setText(ay + "," + parca[2]);

        int day_count=Integer.valueOf(parca[0].toString());

        Calendar c = Calendar.getInstance();
        int AY = c.get(Calendar.MONTH) + 1;
        int yil = c.get(Calendar.YEAR);
        int gun1 = c.get(Calendar.DAY_OF_MONTH);
        String day1 = String.valueOf(gun);


        if(gun1-day_count<=0)
        {
            zaman=" Bugün";
        }
       else if(gun1-day_count==1)
        {
            zaman=" Dün";
        }
        else if(gun1-day_count==2)
        {
            zaman=" 2d";
        }
        else if(gun1-day_count==3)
        {
            zaman=" 3d";
        }
        else if(gun1-day_count==4)
        {
            zaman=" 4d";
        }
        else if(gun1-day_count==5)
        {
            zaman=" 5d";
        }
        else if(gun1-day_count==6)
        {
            zaman=" 6d";
        }
        else if(7<=gun1-day_count&&gun1-day_count<=14)
        {
            zaman=" 1h";
        }
        else if(15<=gun1-day_count&&gun1-day_count<=21)
        {
            zaman=" 2h";
        }
        else if(22<=gun1-day_count&&gun1-day_count<=28)
        {
            zaman="  3h";
        }
        else if(29<=gun1-day_count&&gun1-day_count<=31)
        {
            zaman=" 1ay";
        }

        String html="<a style=\"font-size:11px;\"><font color='#657786'>"+"&#x25AA"+zaman+"</font><a>";


        String temp2=gun_konum[position].toString();
        String yer[]=temp2.split("\\-");

        String real_header="";
        String kelime[]=gun_baslik[position].toString().split(" ");
        for(int i=0;i<kelime.length;i++)
        {
            int a=kelime[i].length();
            String tem=kelime[i].substring(0,1);
            real_header=real_header+tem.toUpperCase()+kelime[i].substring(1,a).toLowerCase()+" ";
        }


        baslik.setText(Html.fromHtml("<b>"+real_header+"</b>"+html));
        if(gun_metin[position].length()>100)
        {
            if(gun_etiket[position].length()>2)
            {
                icerik.setText(Html.fromHtml(gun_metin[position].substring(0, 100) + " ..." +"<br><font size='11' color='#159dd1'>"+gun_etiket[position]+"</font></br>"));

            }
            else if(gun_etiket[position].length()<2)
            {
                icerik.setText(Html.fromHtml(gun_metin[position].substring(0, 100) + " ..."));

            }
            if(gun_konum[position].length()>42)
            {

                konum.setText(gun_konum[position].substring(0, 42) + " ...");
            }
            else if (gun_konum[position].length()>5)
            {
                konum.setText(gun_konum[position]);
            }
            else {
                image_konum.setVisibility(View.GONE);
                konum.setText(gun_konum[position]);
                konum.setVisibility(View.GONE);

            }


        } else {

            if(gun_etiket[position].length()>2)
            {
                icerik.setText(Html.fromHtml(gun_metin[position]  +"<br><font size='11' color='#159dd1'>"+gun_etiket[position]+"</font></br>"));

            }
            else if(gun_etiket[position].length()<2)
            {
                icerik.setText(Html.fromHtml(gun_metin[position]));

            }

            if(gun_konum[position].length()>42)
            {

                konum.setText(gun_konum[position].substring(0, 42) + " ...");
            }
            else if (gun_konum[position].length()>5)
            {
                konum.setText(gun_konum[position]);
            }
            else {
                image_konum.setVisibility(View.GONE);
                konum.setText(gun_konum[position]);
                konum.setVisibility(View.GONE);

            }
        }

        resim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(gun_resim1[position]),position);
            }
        });

        resim2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(gun_resim2[position]),position);
            }
        });

        resim3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(gun_resim3[position]),position);
            }
        });

        resim4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(gun_resim4[position]), position);
            }
        });
        resim5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(gun_resim3[position]), position);
            }
        });



       /* birinci=(ImageView)itemView.findViewById(R.id.birinci_list);
        ikinci=(ImageView)itemView.findViewById(R.id.ikinci_list);
        ucuncu=(ImageView)itemView.findViewById(R.id.ucuncu_list);
        dorduncu=(ImageView)itemView.findViewById(R.id.dorduncu_list);
         etiket = (TextView) itemView.findViewById(R.id.etiket);
        besinci=(ImageView)itemView.findViewById(R.id.besinci_list);
        View hs=itemView.findViewById(R.id.hsv_list);
        konum=(TextView)itemView.findViewById(R.id.list_konum);
        image_konum=(ImageView)itemView.findViewById(R.id.image_konum);
        vi=(ImageView)itemView.findViewById(R.id.vi_li);




       if(gun_resim1[position].length()<2&&gun_resim2[position].length()<2&&gun_resim3[position].length()<2&&gun_resim4[position].length()<2&&gun_resim5[position].length()<2)
        {
            hs.setVisibility(View.GONE);
        }
        else
        {

            if(gun_resim1[position].length()>2)
            {
                birinci.setVisibility(View.VISIBLE);
                try{
                    //birinci.setImageURI(Uri.parse(gun_resim1[position]));


                    Picasso.with(context).load(gun_resim1[position]).resize(100,100).into(birinci);
                }catch(Exception e){
                    e.getMessage();
                }

            }
            if(gun_resim2[position].length()>2)
            {
                ikinci.setVisibility(View.VISIBLE);
                try{

                    //ikinci.setImageURI(Uri.parse(gun_resim2[position]));
                    Picasso.with(context).load(gun_resim2[position]).resize(100, 100).into(ikinci);
                }catch(Exception e){
                    e.getMessage();
                }

            }
            if(gun_resim3[position].length()>2)
            {
                ucuncu.setVisibility(View.VISIBLE);
                try{

                    //ucuncu.setImageURI(Uri.parse(gun_resim3[position]));
                    Picasso.with(context).load(gun_resim3[position]).resize(100, 100).into(ucuncu);
                }catch(Exception e){
                    e.getMessage();
                }

            }
            if(gun_resim4[position].length()>2)
            {
                dorduncu.setVisibility(View.VISIBLE);
                try{

                    //dorduncu.setImageURI(Uri.parse(gun_resim4[position]));
                    Picasso.with(context).load(gun_resim4[position]).resize(100, 100).into(dorduncu);
                }catch(Exception e){
                    e.getMessage();
                }

            }
            if(gun_resim5[position].length()>2)
            {
                besinci.setVisibility(View.VISIBLE);
                try{

                   // besinci.setImageURI(Uri.parse(gun_resim5[position]));
                    Picasso.with(context).load(gun_resim5[position]).resize(100,100).into(besinci);
                }catch(Exception e){
                    e.getMessage();
                }

            }
        }

        if(Integer.parseInt(gun_emoji[position])==0)
        {
            arkaplan.setVisibility(View.INVISIBLE);
        }

      else  if(Integer.parseInt(gun_emoji[position])==1)
        {
           resim.setImageResource(R.drawable.gulenyuz);
        }

        else  if(Integer.parseInt(gun_emoji[position])==2)
        {
            resim.setImageResource(R.drawable.normal);
        }
        else  if(Integer.parseInt(gun_emoji[position])==3)
        {
            resim.setImageResource(R.drawable.asik);
        }
        else if(Integer.parseInt(gun_emoji[position])==4)
        {
            resim.setImageResource(R.drawable.kiska);
        }
        else  if(Integer.parseInt(gun_emoji[position])==5)
        {
            resim.setImageResource(R.drawable.moralsiz);
        }
        else if(Integer.parseInt(gun_emoji[position])==6)
        {
            resim.setImageResource(R.drawable.yorgun);
        }
        else   if(Integer.parseInt(gun_emoji[position])==7)
        {
            resim.setImageResource(R.drawable.uzgun);
        }
        else   if(Integer.parseInt(gun_emoji[position])==8)
        {
            resim.setImageResource(R.drawable.sinirli);
        }
        else   if(Integer.parseInt(gun_emoji[position])==9)
        {
            resim.setImageResource(R.drawable.begenmis);
        }
        else   if(Integer.parseInt(gun_emoji[position])==10)
        {
            resim.setImageResource(R.drawable.eglenmis);
        }



        String temp=gun_tarih[position].toString();
        String parca[]=temp.split("\\.");
        if(parca[1].equals("01"))
        {
            ay="Ocak";
        }
        if(parca[1].equals("02"))
        {
            ay="Şubat";
        }
        if(parca[1].equals("03"))
        {
            ay="Mart";
        }
        if(parca[1].equals("04"))
        {
            ay="Nisan";
        }
        if(parca[1].equals("05"))
        {
            ay="Mayıs";
        }
        if(parca[1].equals("06"))
        {
            ay="Haziran";
        }
        if(parca[1].equals("07"))
        {
            ay="Temmuz";
        }
        if(parca[1].equals("08"))
        {
            ay="Ağustos";
        }
        if(parca[1].equals("09"))
        {
            ay="Eylül";
        }
        if(parca[1].equals("10"))
        {
            ay="Ekim";
        }
        if(parca[1].equals("11"))
        {
            ay="Kasım";
        }
        if(parca[1].equals("12"))
        {
            ay="Aralık";
        }

        tarih.setText(ay+" "+parca[0]+", "+parca[2]);




        if(gun_metin[position].length()>100)
        {
                baslik.setText(gun_baslik[position]);
                etiket.setText(gun_etiket[position]);
                icerik.setText(Html.fromHtml(gun_metin[position].substring(0, 100) + " ..."));
            if(gun_konum[position].length()>50)
            {
                vi.setVisibility(View.VISIBLE);
                konum.setText(gun_konum[position].substring(0, 50) + " ...");
            }
           else if (gun_konum[position].length()>5)
            {    vi.setVisibility(View.VISIBLE);
               // image_konum.setVisibility(View.GONE);
                konum.setText(gun_konum[position]);
            }
            else {
                vi.setVisibility(View.INVISIBLE);
                image_konum.setVisibility(View.GONE);
                konum.setText(gun_konum[position]);
            }

        } else {

            baslik.setText(gun_baslik[position]);
           // tarih.setText(gun_tarih[position]);
            etiket.setText(gun_etiket[position]);
            icerik.setText(Html.fromHtml(gun_metin[position]));
            if(gun_konum[position].length()>50)
            {
                vi.setVisibility(View.VISIBLE);
                konum.setText(gun_konum[position].substring(0, 50) + " ...");
            }
            else if (gun_konum[position].length()>5)
            {
                vi.setVisibility(View.VISIBLE);
                // image_konum.setVisibility(View.GONE);
                konum.setText(gun_konum[position]);
            }
            else {
                vi.setVisibility(View.INVISIBLE);
                image_konum.setVisibility(View.GONE);
                konum.setText(gun_konum[position]);
            }

        }



        birinci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(gun_resim1[position]),position);
            }
        });

        ikinci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(gun_resim2[position]),position);
            }
        });

        ucuncu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(gun_resim3[position]),position);
            }
        });

        dorduncu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(gun_resim4[position]),position);
            }
        });
        besinci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(gun_resim5[position]), position);
            }
        });
*/

        return itemView;

    }

    public void image_show(final Uri uri,final int index)
    {
        i=index;
        dialog2 = new Dialog(context,android.R.style.Theme_Light);
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

        page_date.setText(gun_tarih[i]);
        number_page.setVisibility(View.GONE);
        image_baslik.setText(gun_baslik[i]);
        image_etiket.setText(gun_etiket[i]);
        delete.setVisibility(View.GONE);
        update.setVisibility(View.GONE);
        cizgi.setVisibility(View.GONE);

        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        dialog2.show();
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



}