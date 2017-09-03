package com.studio.twin.daily;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Boolean exit = false;
    final Context context = this;
    ListView lv;
    public  static int posizyon=0;
    ArrayList<HashMap<String, String>> gunler;
    TextView mesaj;
    String gun_baslik[];
    int gun_id[];
    String gun_metin[];
    String gun_tarih[];
    String gun_konum[];
    String gun_etiket[];
    String gun_emoji[];
    String gun_resim1[];
    String gun_resim2[];
    String gun_resim3[];
    String gun_resim4[];
    String gun_resim5[];

    ListviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddPage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        lv = (ListView) findViewById(R.id.daily_list);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.image_click));
                Intent intent = new Intent(MainActivity.this, ShowPage.class);
                startActivity(intent);
                intent.putExtra("id", position);
                posizyon=position;
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
     //   this.registerForContextMenu(lv);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (exit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            System.exit(0);}
        // finish activity
        else {
            Snackbar.make(drawer, " Çıkış İçin Tekrar Basınız!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onResume()
    {
        super.onResume();

        Database db = new Database(getApplicationContext());


        gunler = db.Gunler();
        mesaj = (TextView) findViewById(R.id.mesaj);
        if (gunler.size() != 0) {
            mesaj.setVisibility(View.INVISIBLE);

        }

            gun_baslik = new String[gunler.size()];
            gun_metin = new String[gunler.size()];
            gun_tarih = new String[gunler.size()];
            gun_etiket = new String[gunler.size()];
            gun_id = new int[gunler.size()];
            gun_emoji = new String[gunler.size()];
            gun_resim1 = new String[gunler.size()];
            gun_resim2 = new String[gunler.size()];
            gun_resim3 = new String[gunler.size()];
            gun_resim4 = new String[gunler.size()];
            gun_resim5 = new String[gunler.size()];
            gun_konum= new String[gunler.size()];




            int a=gunler.size();
            for (int i = 0; i < gunler.size(); i++) {
                gun_baslik[i] = gunler.get(a-i-1).get("baslik");
                gun_metin[i] = gunler.get(a-i-1).get("icerik");
                gun_tarih[i] = gunler.get(a-i-1).get("tarih");
                gun_etiket[i] = gunler.get(a-i-1).get("etiket");
                gun_emoji[i] = gunler.get(a-i-1).get("emoji");
                gun_resim1[i]=gunler.get(a-i-1).get("birinci");
                gun_resim2[i]=gunler.get(a-i-1).get("ikinci");
                gun_resim3[i]=gunler.get(a-i-1).get("ucuncu");
                gun_resim4[i]=gunler.get(a-i-1).get("dorduncu");
                gun_resim5[i]=gunler.get(a-i-1).get("besinci");
                gun_konum[i]=gunler.get(a-i-1).get("konum");

                gun_id[i] = Integer.parseInt(gunler.get(a-i-1).get("GUN_id"));
                //Yukarıdaki ile aynı tek farkı değerleri integer a çevirdik.
            }



            adapter = new ListviewAdapter(this, gun_baslik, gun_metin, gun_tarih, gun_etiket, gun_emoji,gun_resim1,gun_resim2,gun_resim3,gun_resim4,gun_resim5,gun_konum);
            lv.setAdapter(adapter);






    }
}
