package com.studio.twin.daily;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import java.util.Locale;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.Spannable;
import android.text.method.KeyListener;
import android.text.style.BackgroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLngBounds;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;


public class AddPage extends Activity {

    ImageButton back, etiket, konum, share, galeri;
    RelativeLayout oky;
    LinearLayout linl;
    private Boolean exit = false;
    TextView tarih, val_konum;
    private KeyListener listener;
    public GoogleApiClient apiClient;
    EditText baslik;
    public static int mod = 0;
    ImageButton onceki, sonraki, emoji,italic,delete_konum,delete_emoji,kalem;
    ImageView kalin, birinci, ikinci, ucuncu, dorduncu, besinci, resim,etiket_ok;
    private KeyListener klavye;
    EditText val_etiket;
    HorizontalScrollView hsv;
    public static int bold_sayac = 1,italic_sayac=1,underline_sayac=1;
    public static int image_count = 0;
    private int PICK_IMAGE_REQUEST = 1;
    public Dialog dialog, dialog2,dialog3;
    Canvas canvas;
    ArrayList<Bitmap> bitmapArray;
    ArrayList<String> image_way;
    ArrayList<String> resim_tarihleri;
    public static int image_index = -1;
    Paint paint;
    LinearLayout layout;
    public static int image_count2 = 0;
    public static String yol = "";
    float downx = 0, downy = 0, upx = 0, upy = 0;
    private final static int REQUEST_SELECT_IMAGE = 100;
    private Animation animShow, animHide;
    public String konum_adresi;
    GPSTracker gps;
    Context context = null;
    ProgressDialog barProgressDialog;
    Handler updateBarHandler;
    public static  int proses_sayac=0;
    public String ozel_konum="";
    FrameLayout konum_frame;
   public static int PLACE_PICKER_REQUEST = 1;
    private GoogleApiClient mGoogleApiClient;
    public String metin_icerigi="";
    private boolean isReady = false;
    private RichEditor icerik;
    HorizontalScrollView renkler_kutusu;
    ImageButton kirmizi,sari,yesil,mavi,mor,pembe,turuncu,siyah,gri,underline;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        getWindow().setStatusBarColor(ContextCompat.getColor(AddPage.this, R.color.gri));


        setContentView(R.layout.activity_add_page);


        kalin = (ImageView) findViewById(R.id.kalin);
        back = (ImageButton) findViewById(R.id.btn_back);
        oky = (RelativeLayout) findViewById(R.id.r_ok);
        etiket = (ImageButton) findViewById(R.id.btn_etiket);
        konum = (ImageButton) findViewById(R.id.btn_local);
        galeri = (ImageButton) findViewById(R.id.btn_galery);
        share = (ImageButton) findViewById(R.id.btn_share);
        tarih = (TextView) findViewById(R.id.tarih);
        icerik = (RichEditor) findViewById(R.id.val_icerik);
        baslik = (EditText) findViewById(R.id.val_baslik);
        val_etiket = (EditText) findViewById(R.id.val_etiket);
        onceki = (ImageButton) findViewById(R.id.onceki);
        sonraki = (ImageButton) findViewById(R.id.sonraki);
        layout = (LinearLayout) findViewById(R.id.add_page_layout);
        resim = (ImageView) findViewById(R.id.add_page_resim);
        birinci = (ImageView) findViewById(R.id.birinci);
        ikinci = (ImageView) findViewById(R.id.ikinci);
        ucuncu = (ImageView) findViewById(R.id.ucuncu);
        dorduncu = (ImageView) findViewById(R.id.dorduncu);
        besinci = (ImageView) findViewById(R.id.besinci);
        hsv = (HorizontalScrollView) findViewById(R.id.show_hsv2);
        val_konum = (TextView) findViewById(R.id.val_konum);
        italic=(ImageButton)findViewById(R.id.italik);
        delete_konum=(ImageButton)findViewById(R.id.delete_konum);
        delete_emoji=(ImageButton)findViewById(R.id.delete_emoji);
        konum_frame=(FrameLayout)findViewById(R.id.konum_framelayout);
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.relative);
        etiket_ok=(ImageView)findViewById(R.id.etiket_ok);
        final FrameLayout eklentiler=(FrameLayout)findViewById(R.id.frameLayout2);
        kalem=(ImageButton)findViewById(R.id.kalem);
        kirmizi=(ImageButton)findViewById(R.id.kirmizi);
        sari=(ImageButton)findViewById(R.id.sari);
        yesil=(ImageButton)findViewById(R.id.yesil);
        mavi=(ImageButton)findViewById(R.id.mavi);
        mor=(ImageButton)findViewById(R.id.mor);
        pembe=(ImageButton)findViewById(R.id.pembe);
        turuncu=(ImageButton)findViewById(R.id.turuncu);
        gri=(ImageButton)findViewById(R.id.gri);
        siyah=(ImageButton)findViewById(R.id.siyah);
        renkler_kutusu=(HorizontalScrollView)findViewById(R.id.show_hsv3);
        ScrollView sw=(ScrollView)findViewById(R.id.scrollView);
        underline=(ImageButton)findViewById(R.id.underline);


        icerik.copyBackForwardList();
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renkler_kutusu.setVisibility(View.GONE);
            }
        });


        icerik.setPlaceholder("Bugün neler yaşadın..");
        //icerik.focusEditor();

        icerik.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                metin_icerigi = text;
            }
        });

        kalem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                renkler_kutusu.setVisibility(View.VISIBLE);
                icerik.focusEditor();

            }
        });

        kirmizi.setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                renkler_kutusu.setVisibility(View.GONE);
                icerik.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
                kalem.setBackgroundResource(R.drawable.text_kirmizi);
            }
        });
        sari.setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                renkler_kutusu.setVisibility(View.GONE);
                icerik.setTextColor(isChanged ? Color.BLACK : Color.YELLOW);
                isChanged = !isChanged;
                kalem.setBackgroundResource(R.drawable.text_sari);
            }
        });
        yesil.setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                renkler_kutusu.setVisibility(View.GONE);
                icerik.setTextColor(isChanged ? Color.BLACK : Color.parseColor("#069c01"));
                isChanged = !isChanged;
                kalem.setBackgroundResource(R.drawable.text_yesil);
            }
        });
        mavi.setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                renkler_kutusu.setVisibility(View.GONE);
                icerik.setTextColor(isChanged ? Color.BLACK : Color.BLUE);
                isChanged = !isChanged;
                kalem.setBackgroundResource(R.drawable.text_mavi);
            }
        });
        mor.setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                renkler_kutusu.setVisibility(View.GONE);
                icerik.setTextColor(isChanged ? Color.BLACK : Color.parseColor("#8002fe"));
                isChanged = !isChanged;
                kalem.setBackgroundResource(R.drawable.text_mor);

            }
        });
        pembe.setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                renkler_kutusu.setVisibility(View.GONE);
                icerik.setTextColor(isChanged ? Color.BLACK : Color.parseColor("#fe03e5"));
                isChanged = !isChanged;
                kalem.setBackgroundResource(R.drawable.text_pembe);
            }
        });
        turuncu.setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                renkler_kutusu.setVisibility(View.GONE);
                icerik.setTextColor(isChanged ? Color.BLACK : Color.parseColor("#fc4001"));
                isChanged = !isChanged;
                kalem.setBackgroundResource(R.drawable.text_turuncu);

            }
        });
        gri.setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                renkler_kutusu.setVisibility(View.GONE);
                icerik.setTextColor(isChanged ? Color.BLACK : Color.GRAY);
                isChanged = !isChanged;
                kalem.setBackgroundResource(R.drawable.text_gri);

            }
        });
        siyah.setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                renkler_kutusu.setVisibility(View.GONE);
                icerik.setTextColor(isChanged ? Color.BLACK : Color.BLACK);
                isChanged = !isChanged;
                kalem.setBackgroundResource(R.drawable.text_siyah);

            }
        });



        icerik.requestFocus();


        etiket_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val_etiket.setEnabled(false);
                icerik.requestFocus();
                etiket_ok.setVisibility(View.INVISIBLE);
            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icerik.requestFocus();
                icerik.focusEditor();
                renkler_kutusu.setVisibility(View.GONE);

            }
        });



        delete_konum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val_konum.setText("");
                konum_frame.setVisibility(View.INVISIBLE);

            }
        });

        delete_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mod = 0;
                layout.setVisibility(View.INVISIBLE);
                delete_emoji.setVisibility(View.INVISIBLE);
            }
        });


        icerik.animate();

        emoji = (ImageButton) findViewById(R.id.smile);


        Calendar c = Calendar.getInstance();
        int AY = c.get(Calendar.MONTH) + 1;
        int yil = c.get(Calendar.YEAR);
        int gun = c.get(Calendar.DAY_OF_MONTH);

        String ay = String.valueOf(AY);
        String day = String.valueOf(gun);
        if (AY < 10) {
            ay = "0" + AY;
        }
        if (gun < 10) {
            day = "0" + gun;
        }
        tarih.setText(day + "." + ay + "." + yil);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AddPage.this, R.anim.image_click));
                Intent intent = new Intent(AddPage.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        oky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AddPage.this, R.anim.image_click));

                if (baslik.getText().toString().matches("")) {
                    baslik.setError("Baslık Giriniz");
                }
                if (metin_icerigi.toString().matches("")) {

                } else if (baslik.getText() != null && metin_icerigi != null) {


                    Database db = new Database(getApplicationContext());

                    String a = metin_icerigi.toString();
                    String galeri = "";
                    int boyut = 0;
                    for (int i = 0; i < image_way.size(); i++) {
                        if (image_way.get(i) != null) {
                            boyut++;
                        }
                    }
                    String bir = "";
                    String iki = "";
                    String uc = "";
                    String dort = "";
                    String bes = "";
                    for (int i = 0; i < boyut; i++) {
                        if (i == 0) {
                            bir = bir + (image_way.get(0));
                        } else if (i == 1) {
                            iki = iki + (image_way.get(1));
                        } else if (i == 2) {
                            uc = uc + (image_way.get(2));
                        } else if (i == 3) {
                            dort = dort + (image_way.get(3));
                        } else if (i == 4) {
                            bes = bes + (image_way.get(4));
                        }
                    }
                    String new_galeri = galeri;
                    db.GunEkle(baslik.getText().toString(), metin_icerigi.toString(), val_etiket.getText().toString(), String.valueOf(val_konum.getText()), tarih.getText().toString(), String.valueOf(mod), bir, iki, uc, dort, bes);
                    db.close();
                    Toast.makeText(getApplicationContext(), "Kayıt Başarılı.", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(AddPage.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    image_count=0;
                    finish();
                    finishActivity(1);


                }
            }
        });


        italic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AddPage.this, R.anim.image_click));

                icerik.focusEditor();

                italic_sayac++;

                if (italic_sayac % 2 == 0) {

                    italic.setBackgroundResource(R.drawable.italikaktif);
                    icerik.setItalic();


                } else {
                    italic.setBackgroundResource(R.drawable.italik);
                    icerik.setItalic();
                }
            }
        });

        underline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AddPage.this, R.anim.image_click));
                icerik.focusEditor();
                underline_sayac++;

                if (underline_sayac % 2 == 0) {

                    underline.setBackgroundResource(R.drawable.underlineaktif);
                    icerik.setUnderline();


                } else {
                    underline.setBackgroundResource(R.drawable.underline);
                    icerik.setUnderline();
                }
            }
        });




        //emoji butonuna tıklandıgında...

        emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AddPage.this, R.anim.image_click));
                show_select_emoji();


            }
        });


        kalin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(AnimationUtils.loadAnimation(AddPage.this, R.anim.image_click));
                icerik.focusEditor();
                bold_sayac++;

                if (bold_sayac % 2 == 0) {

                    kalin.setBackgroundResource(R.drawable.tboldaktif);
                    icerik.setBold();


                } else {
                    kalin.setBackgroundResource(R.drawable.tbold);
                    icerik.setBold();
                }


            }
        });

        ///etiket butouna tıklandıgında...

        etiket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(AnimationUtils.loadAnimation(AddPage.this, R.anim.image_click));

                val_etiket.requestFocus();
                val_etiket.setEnabled(true);
                val_etiket.setFocusableInTouchMode(true);
                val_etiket.setClickable(true);
                String val = val_etiket.getText().toString();

                val = val + " #";
                val_etiket.setText(val);
                val_etiket.setSelection(val_etiket.getText().length());
                etiket_ok.setVisibility(View.VISIBLE);

            }
        });


        onceki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AddPage.this, R.anim.image_click));

                icerik.undo();
            }
        });
        sonraki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AddPage.this, R.anim.image_click));
              icerik.redo();
            }
        });


        ///konum butonuna tıklandıgında...

        konum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AddPage.this, R.anim.image_click));
              //  launchRingDialog(v);
               // KonumAl(v);
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent=builder.build(getApplicationContext());
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });


        galeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AddPage.this, R.anim.image_click));
                startActivityForResult(resimSecimiIntent(), REQUEST_SELECT_IMAGE);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(AddPage.this, R.anim.image_click));
            }
        });

        Bitmap bm = null;
        bitmapArray = new ArrayList<Bitmap>();
        bitmapArray.add(bm);
        bitmapArray.add(bm);
        bitmapArray.add(bm);
        bitmapArray.add(bm);
        bitmapArray.add(bm);
        resim_tarihleri = new ArrayList<>();
        resim_tarihleri.add(" ");
        resim_tarihleri.add(" ");
        resim_tarihleri.add(" ");
        resim_tarihleri.add(" ");
        resim_tarihleri.add(" ");

        image_way = new ArrayList<>();
        image_way.add("*");
        image_way.add("*");
        image_way.add("*");
        image_way.add("*");
        image_way.add("*");


        birinci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                image_show(Uri.parse(image_way.get(0)), "1 of 5", resim_tarihleri.get(0), 0);
            }
        });
        ikinci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(image_way.get(1)), "2 of 5", resim_tarihleri.get(1), 1);
            }
        });

        ucuncu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(image_way.get(2)), "3 of 5", resim_tarihleri.get(2), 2);
            }
        });

        dorduncu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(image_way.get(3)), "4 of 5", resim_tarihleri.get(3), 3);
            }
        });

        besinci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_show(Uri.parse(image_way.get(4)), "5 of 5", resim_tarihleri.get(4), 4);
            }
        });

    }


    public Intent resimSecimiIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, AddPage.this);
                String toastMsg = String.format("%s", place.getName());
                String addres = String.format("%s", place.getAddress());
                konum_frame.setVisibility(View.VISIBLE);
                String adres="<b>"+toastMsg+"</b> -"+addres;
                val_konum.setText(Html.fromHtml(adres));
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_SELECT_IMAGE) {
                Uri imageUri = getPickImageResultUri(data);
                startCropActivity(imageUri);
            }

            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Calendar now = Calendar.getInstance();
                yol = result.getUri().toString();
                int saat = now.get(Calendar.HOUR_OF_DAY);
                int dakika = now.get(Calendar.MINUTE);
                final Context context = null;
                if (result.getUri() != null) {

                    hsv.setVisibility(View.VISIBLE);
                    image_count++;
                    if (image_count == 1 || image_index == 1) {
                        birinci.setVisibility(View.VISIBLE);

                        // birinci.setImageURI(result.getUri());
                        Picasso.with(this).load(result.getUri()).into(birinci);
                        //  birinci.setImageURI(result.getUri());
                        image_way.set(0, yol);
                        resim_tarihleri.set(0, ("Bugün " + saat + ":" + dakika));


                    } else if (image_count == 2 || image_index == 2) {

                        ikinci.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(result.getUri()).into(ikinci);
                        //ikinci.setImageURI(result.getUri());
                        image_way.set(1, yol);
                        resim_tarihleri.set(1, ("Bugün " + saat + ":" + dakika));
                    } else if (image_count == 3 || image_index == 3) {

                        ucuncu.setVisibility(View.VISIBLE);
                        // ucuncu.setImageURI(result.getUri());
                        Picasso.with(this).load(result.getUri()).into(ucuncu);
                        image_way.set(2, yol);
                        resim_tarihleri.set(2, ("Bugün " + saat + ":" + dakika));
                    } else if (image_count == 4 || image_index == 4) {

                        dorduncu.setVisibility(View.VISIBLE);
                        //  dorduncu.setImageURI(result.getUri());
                        Picasso.with(this).load(result.getUri()).into(dorduncu);
                        image_way.set(3, yol);
                        resim_tarihleri.set(3, ("Bugün " + saat + ":" + dakika));
                    } else if (image_count == 5 || image_index == 5) {

                        besinci.setVisibility(View.VISIBLE);
                        //  besinci.setImageURI(result.getUri());
                        Picasso.with(this).load(result.getUri()).into(besinci);
                        image_way.set(4, yol);
                        resim_tarihleri.set(4, ("Bugün " + saat + ":" + dakika));
                    } else {
                        Toast.makeText(AddPage.this, "En fazla 5 tane resim ekleyebilirsiniz.!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(AddPage.this, "Fotoğraf seçilemedi!", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    private void startCropActivity(Uri imgUri) {
        CropImage.activity(imgUri)
                .setAutoZoomEnabled(true)
                .setAspectRatio(500, 500)
                .setFixAspectRatio(true)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    public void image_show(final Uri uri, String sira, String date, final int index) {
        dialog2 = new Dialog(AddPage.this, android.R.style.Theme_Light);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.image_show);

        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog2.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        dialog2.setCanceledOnTouchOutside(true);
        final View dis = (View) dialog2.findViewById(R.id.arka_layout2);
        final ImageView resim = (ImageView) dialog2.findViewById(R.id.view_resim);
        resim.setImageBitmap(convertBitmap(uri.toString()));
        final Button delete = (Button) dialog2.findViewById(R.id.delete);
        final Button update = (Button) dialog2.findViewById(R.id.update);
        final ImageButton close = (ImageButton) dialog2.findViewById(R.id.geri);
        final TextView image_baslik = (TextView) dialog2.findViewById(R.id.image_baslik);
        final TextView image_etiket = (TextView) dialog2.findViewById(R.id.image_etiket);
        final TextView number_page = (TextView) dialog2.findViewById(R.id.page_number);
        final TextView page_date = (TextView) dialog2.findViewById(R.id.image_create_date);
        final View cizgi = (View) dialog2.findViewById(R.id.golge);

        page_date.setText(date);
        number_page.setText(sira);

        image_baslik.setText(baslik.getText());
        image_etiket.setText(val_etiket.getText());

        resim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_count2++;
                if (image_count2 % 2 == 0) {
                    image_baslik.setVisibility(View.VISIBLE);
                    image_etiket.setVisibility(View.VISIBLE);
                    close.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.VISIBLE);
                    update.setVisibility(View.VISIBLE);
                    number_page.setVisibility(View.VISIBLE);
                    page_date.setVisibility(View.VISIBLE);
                    cizgi.setVisibility(View.VISIBLE);


                } else {
                    image_baslik.setVisibility(View.INVISIBLE);
                    image_etiket.setVisibility(View.INVISIBLE);
                    close.setVisibility(View.INVISIBLE);
                    delete.setVisibility(View.INVISIBLE);
                    update.setVisibility(View.INVISIBLE);
                    number_page.setVisibility(View.INVISIBLE);
                    page_date.setVisibility(View.INVISIBLE);
                    cizgi.setVisibility(View.INVISIBLE);

                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                image_index = index + 1;
                image_count--;
                resim_tarihleri.remove(index);
                image_way.remove(index);

                int boyut = 0;
                for (int i = 0; i < image_way.size(); i++) {
                    if (!image_way.get(i).equals("*")) {
                        boyut++;
                    }
                }
                if (boyut == 0) {
                    birinci.setVisibility(View.INVISIBLE);
                    ikinci.setVisibility(View.INVISIBLE);
                    ucuncu.setVisibility(View.INVISIBLE);
                    dorduncu.setVisibility(View.INVISIBLE);
                    besinci.setVisibility(View.INVISIBLE);
                } else if (boyut == 1) {
                    Picasso.with(AddPage.this).load(image_way.get(0)).into(birinci);
                    ikinci.setVisibility(View.INVISIBLE);
                    ucuncu.setVisibility(View.INVISIBLE);
                    dorduncu.setVisibility(View.INVISIBLE);
                    besinci.setVisibility(View.INVISIBLE);
                } else if (boyut == 2) {
                    Picasso.with(AddPage.this).load(image_way.get(0)).into(birinci);
                    Picasso.with(AddPage.this).load(image_way.get(1)).into(ikinci);
                    ucuncu.setVisibility(View.INVISIBLE);
                    dorduncu.setVisibility(View.INVISIBLE);
                    besinci.setVisibility(View.INVISIBLE);
                } else if (boyut == 3) {
                    Picasso.with(AddPage.this).load(image_way.get(0)).into(birinci);
                    Picasso.with(AddPage.this).load(image_way.get(1)).into(ikinci);
                    Picasso.with(AddPage.this).load(image_way.get(2)).into(ucuncu);
                    dorduncu.setVisibility(View.VISIBLE);
                    besinci.setVisibility(View.INVISIBLE);
                } else if (boyut == 4) {
                    Picasso.with(AddPage.this).load(image_way.get(0)).into(birinci);
                    Picasso.with(AddPage.this).load(image_way.get(1)).into(ikinci);
                    Picasso.with(AddPage.this).load(image_way.get(2)).into(ucuncu);
                    Picasso.with(AddPage.this).load(image_way.get(3)).into(dorduncu);
                    besinci.setVisibility(View.INVISIBLE);
                } else if (boyut == 5) {
                    Picasso.with(AddPage.this).load(image_way.get(0)).into(birinci);
                    Picasso.with(AddPage.this).load(image_way.get(1)).into(ikinci);
                    Picasso.with(AddPage.this).load(image_way.get(2)).into(ucuncu);
                    Picasso.with(AddPage.this).load(image_way.get(3)).into(dorduncu);
                    Picasso.with(AddPage.this).load(image_way.get(4)).into(besinci);
                }


                dialog2.dismiss();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_index = index + 1;
                startActivityForResult(resimSecimiIntent(), REQUEST_SELECT_IMAGE);
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



    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap convertBitmap(String url) {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }

    public void show_select_emoji() {
        dialog = new Dialog(AddPage.this, android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.emoji);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        final TextView mutlu = (TextView) dialog.findViewById(R.id.mutlu);
        final TextView normal = (TextView) dialog.findViewById(R.id.normal);
        final TextView asik = (TextView) dialog.findViewById(R.id.asik);
        final TextView kiskanc = (TextView) dialog.findViewById(R.id.kiskanc);
        final TextView moralsiz = (TextView) dialog.findViewById(R.id.moralsiz);
        final TextView yorgun = (TextView) dialog.findViewById(R.id.yorgun);
        final TextView uzgun = (TextView) dialog.findViewById(R.id.uzgun);
        final TextView sinirli = (TextView) dialog.findViewById(R.id.sinirli);
        final TextView havali = (TextView) dialog.findViewById(R.id.havali);
        final TextView eglenmis = (TextView) dialog.findViewById(R.id.eglenmis);
        final ImageView close = (ImageView) dialog.findViewById(R.id.imageView25);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


            }
        });


        mutlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mod = 1;
                layout.setVisibility(View.VISIBLE);
                delete_emoji.setVisibility(View.VISIBLE);
                resim.setImageResource(R.drawable.gulenyuz);
                dialog.dismiss();
            }
        });
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mod = 2;
                layout.setVisibility(View.VISIBLE);
                delete_emoji.setVisibility(View.VISIBLE);
                resim.setImageResource(R.drawable.normal);
                dialog.dismiss();
            }
        });
        asik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mod = 3;
                layout.setVisibility(View.VISIBLE);
                delete_emoji.setVisibility(View.VISIBLE);
                resim.setImageResource(R.drawable.asik);
                dialog.dismiss();
            }
        });
        kiskanc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mod = 4;
                layout.setVisibility(View.VISIBLE);
                delete_emoji.setVisibility(View.VISIBLE);
                resim.setImageResource(R.drawable.kiska);
                dialog.dismiss();
            }
        });
        moralsiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mod = 5;
                layout.setVisibility(View.VISIBLE);
                delete_emoji.setVisibility(View.VISIBLE);
                resim.setImageResource(R.drawable.moralsiz);
                dialog.dismiss();
            }
        });
        yorgun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mod = 6;
                layout.setVisibility(View.VISIBLE);
                delete_emoji.setVisibility(View.VISIBLE);
                resim.setImageResource(R.drawable.yorgun);
                dialog.dismiss();
            }
        });
        uzgun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mod = 7;
                layout.setVisibility(View.VISIBLE);
                delete_emoji.setVisibility(View.VISIBLE);
                resim.setImageResource(R.drawable.uzgun);
                dialog.dismiss();
            }
        });
        sinirli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mod = 8;
                layout.setVisibility(View.VISIBLE);
                delete_emoji.setVisibility(View.VISIBLE);
                resim.setImageResource(R.drawable.sinirli);
                dialog.dismiss();
            }
        });
        havali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mod = 9;
                layout.setVisibility(View.VISIBLE);
                delete_emoji.setVisibility(View.VISIBLE);
                resim.setImageResource(R.drawable.begenmis);
                dialog.dismiss();
            }
        });
        eglenmis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mod = 10;
                layout.setVisibility(View.VISIBLE);
                delete_emoji.setVisibility(View.VISIBLE);
                resim.setImageResource(R.drawable.eglenmis);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void initAnimation() {
        animShow = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        animHide = AnimationUtils.loadAnimation(this, R.anim.slide_down);
    }


        public void KonumAl(View v)
        {
            gps = new GPSTracker(AddPage.this);

            // check if GPS enabled
            if (gps.canGetLocation()) {



                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    Geocoder geocoder = new Geocoder(AddPage.this, Locale.getDefault());

                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        if (addresses != null && longitude > 0.00) {
                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            //  String postalCode = addresses.get(0).getPostalCode();
                            String neighborhood = addresses.get(0).getSubLocality();
                            String no = addresses.get(0).getFeatureName();
                            String tesis_adi = addresses.get(0).getPremises();
                            String ilce = addresses.get(0).getSubAdminArea();
                            String street = addresses.get(0).getThoroughfare();
                            String bulundugu_yer = addresses.get(0).getLocality();
                            String adres = "";

                            if (tesis_adi != null && bulundugu_yer != null) {
                                adres = tesis_adi.toUpperCase() + bulundugu_yer.toUpperCase() + street + " No:" + no + " " + neighborhood + " " + ilce + "/" + state;
                            } else {
                                adres = neighborhood + " " + street + " No:" + no + " " + ilce + "/" + state;
                            }

                            val_konum.setText(adres);
                            konum_frame.setVisibility(View.VISIBLE);
                        } else {
                            val_konum.setText("Konum Bilgisi Alınamadı.Internet erişimini kontrol ediniz.");
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        val_konum.setText("Canont get Address!");
                    }
                }

                // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
             else {
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();

              //

            }

        }

    public void launchRingDialog(View view) {
        final ProgressDialog ringProgressDialog =new  ProgressDialog(AddPage.this, ProgressDialog.THEME_HOLO_DARK);
        ringProgressDialog.setMessage("Konum bilgileri alınıyor ...");
        ringProgressDialog.setCancelable(true);
        ringProgressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Here you should write your time consuming task...
                    // Let the progress ring for 10 seconds...
                    Thread.sleep(1700);
                } catch (Exception e) {

                }
                ringProgressDialog.dismiss();
            }
        }).start();
    }



}