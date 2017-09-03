package com.studio.twin.daily;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "sqllite_database";

    private static final String TABLE_NAME = "Users";
    private static String NAME = "name";
    private static String ID = "id";
    private static String EMAİL = "email";
    private static String PASSWORD = "password";


    private static final String TABLE = "Daily";
    private static String GUN_ID = "GUN_id";
    private static String baslik = "baslik";
    private static String etiket = "etiket";
    private static String tarih = "tarih";
    private static String emoji = "emoji";
    private static String konum = "konum";
    private static String icerik = "icerik";
    private static String birinci = "birinci";
    private static String ikinci = "ikinci";
    private static String ucuncu = "ucuncu";
    private static String dorduncu = "dorduncu";
    private static String besinci = "besinci";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {  // Databesi oluşturuyoruz.Bu methodu biz çağırmıyoruz. Databese de obje oluşturduğumuzda otamatik çağırılıyor.
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME + " TEXT,"
                + EMAİL + " TEXT,"
                + PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);


        String DailyTable = "CREATE TABLE " + TABLE + "("
                + GUN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + baslik + " TEXT,"
                + konum + " TEXT,"
                + tarih + " TEXT,"
                + emoji + " TEXT,"
                + etiket + " TEXT,"
                + icerik + " TEXT,"
                + birinci + " TEXT,"
                + ikinci + " TEXT,"
                + ucuncu + " TEXT,"
                + dorduncu + " TEXT,"
                + besinci + " TEXT" + ")";
        db.execSQL(DailyTable);

    }

    public void GunSil(int id){ //id si belli olan row u silmek için

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, GUN_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void GunEkle(String basl, String metin,String eti,String konu,String gun,String emo,String birinc,String ikinc,String uc,String dordunc,String besinc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(baslik, basl);
        values.put(icerik, metin);
        values.put(etiket, eti);
        values.put(konum, konu);
        values.put(tarih, gun);
        values.put(emoji,emo);
        values.put(birinci,birinc);
        values.put(ikinci,ikinc);
        values.put(ucuncu,uc);
        values.put(dorduncu,dordunc);
        values.put(besinci,besinc);
        db.insert(TABLE, null, values);
        db.close(); //Database Bağlantısını kapattık*/
    }

    public void GunGuncelle(String basl, String metin,String eti,String loaction,String gun,String birinc,String ikinc,String ucunc,String dordunc,String besinc,String emo,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Bu methodda ise var olan veriyi güncelliyoruz(update)
        ContentValues values = new ContentValues();
        values.put(baslik, basl);
        values.put(icerik, metin);
        values.put(etiket, eti);
        values.put(konum, loaction);
        values.put(tarih, gun);
        values.put(birinci,birinc);
        values.put(ikinci,ikinc);
        values.put(ucuncu,ucunc);
        values.put(dorduncu,dordunc);
        values.put(besinci,besinc);
        values.put(emoji,emo);

        db.update(TABLE, values, GUN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
    public void islemEkle(String name, String email,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(EMAİL, email);
        values.put(PASSWORD, password);
        db.insert(TABLE_NAME, null, values);
        db.close(); //Database Bağlantısını kapattık*/
    }

    public  ArrayList<HashMap<String, String>> Gunler(){

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> islemlist = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                islemlist.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        return islemlist;
    }

    public int gunSayisi() {
        // Bu method bu uygulamada kullanılmıyor ama her zaman lazım olabilir.Tablodaki row sayısını geri döner.
        String countQuery = "SELECT  * FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }


    public int getRowCount() {
        // Bu method bu uygulamada kullanılmıyor ama her zaman lazım olabilir.Tablodaki row sayısını geri döner.
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }

    public  ArrayList<HashMap<String, String>> oturum(){

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> oturumlist = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                oturumlist.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        return oturumlist;
    }

    public void resetTables(){
        //Bunuda uygulamada kullanmıyoruz. Tüm verileri siler. tabloyu resetler.
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}
