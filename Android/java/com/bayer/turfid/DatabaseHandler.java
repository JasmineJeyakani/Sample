package com.bayer.turfid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jasmine Jeyakani on 29-Nov-16.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TurfGolf";
    private static final String TABLE_NAME = "GolfTable";
    private static final String GOLF_ID = "id";
    private static final String GOLF_NAME = "name";
    private static final String GOLF_ADDRESS = "address";
    private static final String GOLF_EMAIL = "email";
    private static final String GOLF_SPRAYMAKE = "spraymake";
    private static final String GOLF_SPRAYMODEL = "spraymodel";
    private static final String AREA_TABLE = "areatable";
    private static final String AREA_ID = "areaid";
    private static final String AREA_Name = "areaname";
    private static final String AREA_VALUE = "areavalue";
    private static final String SPRAYTABLE = "spraytable";
    private static final String SPRAYNO = "sprayno";
    private static final String DATE = "date";
    private static final String DATEFINAL = "datefinal";
    private static final String AREANAME = "areanamefinal";
    private static final String TQPESTICIDE = "tqpesti";
    private static final String TQWATER = "tqwater";
    private static final String TQTOTVOL = "tqtotvol";
    private static final String TQAREA = "tqarea";
    private static final String PTTANKFIl = "pttankfill";
    private static final String PTPESTICIDE = "ptpesti";
    private static final String PTWATER = "ptwater";
    private static final String PTAREA = "ptarea";
    private static final String QPPESTICIDE = "qppesti";
    private static final String QPWATER = "qpwater";
    private static final String QPTOTVOL = "qptotval";
    private static final String QPAREA = "qparea";
    private static final String NOOFTANKS = "nooftanks";
    private static final String CLUBNAME = "clubname";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String MAP_TABLE = "lastname";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + GOLF_ID + " INTEGER PRIMARY KEY," + GOLF_NAME + " TEXT," + GOLF_ADDRESS + " TEXT," +
                GOLF_EMAIL + " TEXT," + FIRSTNAME + " TEXT ," + LASTNAME + " TEXT ," + GOLF_SPRAYMAKE + " TEXT," + GOLF_SPRAYMODEL + " TEXT)";
        String CREATE_AREA_TABLE = "CREATE TABLE " + AREA_TABLE + "(" + AREA_ID + " INTEGER PRIMARY KEY," + AREA_Name + " TEXT," + AREA_VALUE + " TEXT," +
                GOLF_NAME + " TEXT)";

        String CREATE_MAP_TABLE = "CREATE TABLE " + MAP_TABLE + "(" + AREA_ID + " INTEGER PRIMARY KEY," + GOLF_NAME + " TEXT," + AREA_Name + " TEXT," +
                AREA_VALUE + " TEXT)";
        String CREATE_SPRAY_TABLE = "CREATE TABLE " + SPRAYTABLE + "(" + SPRAYNO + " INTEGER PRIMARY KEY," + DATE + " TEXT," + DATEFINAL + " TEXT," + AREANAME + " TEXT," + CLUBNAME + " TEXT," +
                TQPESTICIDE + " TEXT," + TQWATER + " TEXT," + TQTOTVOL + " TEXT," + TQAREA + " TEXT," + PTTANKFIl + " TEXT," + PTPESTICIDE + " TEXT," +
                PTWATER + " TEXT," + PTAREA + " TEXT," + QPPESTICIDE + " TEXT," + QPWATER + " TEXT," + QPTOTVOL + " TEXT," + QPAREA + " TEXT," +
                NOOFTANKS + " TEXT)";

        db.execSQL(CREATE_ITEM_TABLE);
        db.execSQL(CREATE_AREA_TABLE);
        db.execSQL(CREATE_SPRAY_TABLE);
        db.execSQL(CREATE_MAP_TABLE);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AREA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SPRAYTABLE);

        onCreate(db);
    }

    public void insertTable(String name, String address, String email, String firstName, String lastName, String sprayMake, String sprayModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GOLF_NAME, name);
        contentValues.put(GOLF_ADDRESS, address);
        contentValues.put(GOLF_EMAIL, email);
        contentValues.put(FIRSTNAME, firstName);
        contentValues.put(LASTNAME, lastName);
        contentValues.put(GOLF_SPRAYMAKE, sprayMake);
        contentValues.put(GOLF_SPRAYMODEL, sprayModel);
        db.insert(TABLE_NAME, null, contentValues);
        db.close();


    }

    public void insertAreaTable(String clubname, String clubArea, String clubValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GOLF_NAME, clubname);
        contentValues.put(AREA_Name, clubArea);
        contentValues.put(AREA_VALUE, clubValue);
        db.insert(AREA_TABLE, null, contentValues);
        Log.d("Inserted", String.valueOf(contentValues));
        db.close();


    }

    public void insertMapTable(String clubname, String clubArea, String clubValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GOLF_NAME, clubname);
        contentValues.put(AREA_Name, clubArea);
        contentValues.put(AREA_VALUE, clubValue);
        db.insert(MAP_TABLE, null, contentValues);
        Log.d("Inserted", String.valueOf(contentValues));
        db.close();


    }


    public List<String> selectAreaValue(String golfName, String areaName) {

        List<String> lis = new ArrayList<>();
        String selectQury = "SELECT " + AREA_VALUE + " FROM " + AREA_TABLE + " WHERE " + GOLF_NAME + " =\"" + golfName + "\" AND " + AREA_Name + " =\"" + areaName + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQury, null);
        int contentval = cursor.getColumnIndex(AREA_VALUE);

        if (cursor.moveToFirst()) {
            do {
                lis.add(cursor.getString(contentval));//adding 2nd column data
            } while (cursor.moveToNext());

        }
        // closing connection
        cursor.close();
        db.close();

        return lis;
    }


    public void insertSprayTablle(String datefinal, String date, String area, String clubname, String tqpesti, String tqwater, String tqtot, String tqarea, String pttank,
                                  String ptpesti, String ptwater, String ptarea,
                                  String nooftanks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATEFINAL, datefinal);
        contentValues.put(DATE, date);
        contentValues.put(AREANAME, area);
        contentValues.put(CLUBNAME, clubname);
        contentValues.put(TQPESTICIDE, tqpesti);
        contentValues.put(TQWATER, tqwater);
        contentValues.put(TQTOTVOL, tqtot);
        contentValues.put(TQAREA, tqarea);
        contentValues.put(PTTANKFIl, pttank);
        contentValues.put(PTPESTICIDE, ptpesti);
        contentValues.put(PTWATER, ptwater);
        contentValues.put(PTAREA, ptarea);
        contentValues.put(NOOFTANKS, nooftanks);
        db.insert(SPRAYTABLE, null, contentValues);
        Log.d("Inserted", String.valueOf(contentValues));
        db.close();
    }

    public List<String> selectGolfName() {

        List<String> list = new ArrayList<String>();
        String selectQuery = "SELECT " + GOLF_NAME + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int contentval = cursor.getColumnIndex(GOLF_NAME);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(contentval));//adding 2nd column data
            } while (cursor.moveToNext());

        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return list;
    }

    public List<String> selectGolfNamecreate() {

        List<String> list = new ArrayList<String>();
        String selectQuery = "SELECT " + GOLF_NAME + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int contentval = cursor.getColumnIndex(GOLF_NAME);
        list.add(0, "Create a new Golf Club");
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(contentval));//adding 2nd column data
            } while (cursor.moveToNext());

        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return list;
    }

    public List<String> selectAreaName(String gfName) {

        List<String> list = new ArrayList<String>();
        String selectQuery = "SELECT " + AREA_Name + " FROM " + AREA_TABLE + " WHERE " + GOLF_NAME + "=\"" + gfName + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int contentval = cursor.getColumnIndex(AREA_Name);

        list.add(0, "Create a New Area");
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(contentval));//adding 2nd column data
            } while (cursor.moveToNext());

        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return list;
    }


    public List<String> selectSprayerMake() {

        List<String> list = new ArrayList<String>();
        String selectQuery = "SELECT " + GOLF_SPRAYMAKE + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int contentval = cursor.getColumnIndex(GOLF_SPRAYMAKE);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(contentval));//adding 2nd column data
            } while (cursor.moveToNext());

        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return list;
    }

    public List<String> selectSprayerModel() {

        List<String> list = new ArrayList<String>();
        String selectQuery = "SELECT " + GOLF_SPRAYMODEL + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int contentval = cursor.getColumnIndex(GOLF_SPRAYMODEL);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(contentval));//adding 2nd column data

            } while (cursor.moveToNext());

        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return list;
    }

    public List<String> selectTable() {
        List<String> list = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(5));//adding 2nd column data
            } while (cursor.moveToNext());

        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return list;


    }

    public List<String> selectClubName() {
        List<String> lis = new ArrayList<>();
        String selectQury = "SELECT " + GOLF_NAME + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQury, null);
        int contentval = cursor.getColumnIndex(GOLF_NAME);

        if (cursor.moveToFirst()) {
            do {
                lis.add(cursor.getString(contentval));//adding 2nd column data
            } while (cursor.moveToNext());

        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return lis;
    }

    public List<String> selectAddressName() {
        List<String> lis = new ArrayList<>();
        String selectQury = "SELECT " + GOLF_ADDRESS + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQury, null);
        int contentval = cursor.getColumnIndex(GOLF_ADDRESS);

        if (cursor.moveToFirst()) {
            do {
                lis.add(cursor.getString(contentval));//adding 2nd column data
            } while (cursor.moveToNext());

        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return lis;
    }

    public void updateSprayTablle(String name, String address, String email, String sprayMake, String sprayModel, String cname) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GOLF_NAME, name);
        contentValues.put(GOLF_ADDRESS, address);
        contentValues.put(GOLF_EMAIL, email);
        contentValues.put(GOLF_SPRAYMAKE, sprayMake);
        contentValues.put(GOLF_SPRAYMODEL, sprayModel);
        db.update(TABLE_NAME, contentValues, GOLF_NAME + "= \"" + cname + "\"", null);
        db.close();

    }

    public List<String> selectEmail(String golfName) {

        List<String> lis = new ArrayList<>();
        String selectQury = "SELECT " + GOLF_EMAIL + " FROM " + TABLE_NAME + " WHERE " + GOLF_NAME + " =\"" + golfName + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQury, null);
        int contentval = cursor.getColumnIndex(GOLF_EMAIL);

        if (cursor.moveToFirst()) {
            do {
                lis.add(cursor.getString(contentval));//adding 2nd column data
            } while (cursor.moveToNext());

        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return lis;
    }

    public List<String> selectFirstName(String golfName) {

        List<String> lis = new ArrayList<>();
        String selectQury = "SELECT " + FIRSTNAME + " FROM " + TABLE_NAME + " WHERE " + GOLF_NAME + " =\"" + golfName + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQury, null);
        int contentval = cursor.getColumnIndex(FIRSTNAME);

        if (cursor.moveToFirst()) {
            do {
                lis.add(cursor.getString(contentval));//adding 2nd column data
            } while (cursor.moveToNext());

        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return lis;
    }

    public List<String> selectLastName(String golfName) {

        List<String> lis = new ArrayList<>();
        String selectQury = "SELECT " + LASTNAME + " FROM " + TABLE_NAME + " WHERE " + GOLF_NAME + " =\"" + golfName + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQury, null);
        int contentval = cursor.getColumnIndex(LASTNAME);

        if (cursor.moveToFirst()) {
            do {
                lis.add(cursor.getString(contentval));//adding 2nd column data
            } while (cursor.moveToNext());

        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return lis;
    }

    public List<String> selectGolfSprayMake() {
        List<String> lis = new ArrayList<>();
        String selectQury = "SELECT " + GOLF_SPRAYMAKE + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQury, null);
        int contentval = cursor.getColumnIndex(GOLF_SPRAYMAKE);

        if (cursor.moveToFirst()) {
            do {
                lis.add(cursor.getString(contentval));//adding 2nd column data
            } while (cursor.moveToNext());

        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return lis;
    }

    public List<String> golfSprayMake(String clubName) {
        List<String> lis = new ArrayList<>();
        String selectQury = "SELECT " + GOLF_SPRAYMAKE + " FROM " + TABLE_NAME + " WHERE " + GOLF_NAME + " =\"" + clubName + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQury, null);
        int contentval = cursor.getColumnIndex(GOLF_SPRAYMAKE);

        if (cursor.moveToFirst()) {
            do {
                lis.add(cursor.getString(contentval));//adding 2nd column data
            } while (cursor.moveToNext());

        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return lis;
    }

    public List<String> golfSprayModel(String clubName) {
        List<String> lis = new ArrayList<>();
        String selectQury = "SELECT " + GOLF_SPRAYMODEL + " FROM " + TABLE_NAME + " WHERE " + GOLF_NAME + " =\"" + clubName + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQury, null);
        int contentval = cursor.getColumnIndex(GOLF_SPRAYMODEL);

        if (cursor.moveToFirst()) {
            do {
                lis.add(cursor.getString(contentval));//adding 2nd column data
            } while (cursor.moveToNext());

        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return lis;
    }



    public ArrayList<HashMap<String, String>> samp() {
        ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
        // List<String> lis=new ArrayList<>();

        // String selectQury="SELECT "+DATE+","+AREANAME+" FROM "+ SPRAYTABLE;
        String selectQury = "SELECT * FROM " + SPRAYTABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQury, null);
        int contentvalfinal = cursor.getColumnIndex(DATEFINAL);
        int contentval = cursor.getColumnIndex(DATE);
        int contentVal1 = cursor.getColumnIndex(AREANAME);
        int contentval2 = cursor.getColumnIndex(TQPESTICIDE);
        int contentval3 = cursor.getColumnIndex(TQWATER);
        int contentval4 = cursor.getColumnIndex(TQTOTVOL);
        int contentval5 = cursor.getColumnIndex(TQAREA);
        int contentval6 = cursor.getColumnIndex(PTTANKFIl);
        int contentval7 = cursor.getColumnIndex(PTPESTICIDE);
        int contentval8 = cursor.getColumnIndex(PTWATER);
        int contentval9 = cursor.getColumnIndex(PTAREA);
        int contentval10 = cursor.getColumnIndex(QPPESTICIDE);
        int contentval11 = cursor.getColumnIndex(QPWATER);
        int contentval12 = cursor.getColumnIndex(QPTOTVOL);
        int contentval13 = cursor.getColumnIndex(QPAREA);
        int contentval14 = cursor.getColumnIndex(NOOFTANKS);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> lis = new HashMap<>();
                lis.put("datefinal", cursor.getString(contentvalfinal));
                lis.put("date", cursor.getString(contentval));
                lis.put("area", cursor.getString(contentVal1));
                lis.put("tqp", cursor.getString(contentval2));
                lis.put("tqw", cursor.getString(contentval3));
                lis.put("tqt", cursor.getString(contentval4));
                lis.put("tqa", cursor.getString(contentval5));
                lis.put("ptf", cursor.getString(contentval6));
                lis.put("ptp", cursor.getString(contentval7));
                lis.put("ptw", cursor.getString(contentval8));
                lis.put("pta", cursor.getString(contentval9));
                lis.put("qpp", cursor.getString(contentval10));
                lis.put("qpw", cursor.getString(contentval11));
                lis.put("qpt", cursor.getString(contentval12));
                lis.put("qpa", cursor.getString(contentval13));
                lis.put("nots", cursor.getString(contentval14));
                oslist.add(lis);
                // lis.add(cursor.getString(contentval));//adding 2nd column data
            } while (cursor.moveToNext());

        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return oslist;
    }


    public ArrayList<HashMap<String, String>> selectSprayReport(String date, String area) {
        ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
        // List<String> lis=new ArrayList<>();
        HashMap<String, String> lis = new HashMap<>();
        String selectQury = "SELECT * FROM " + SPRAYTABLE + " WHERE " + AREANAME + "=\" " + area + "\" AND " + DATE + " =\"" + date + "\"";
        String selectQury1 = "SELECT " + AREANAME + " FROM " + SPRAYTABLE + " WHERE " + AREANAME + "=\" " + area + "\" AND " + DATE + " =\"" + date + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQury, null);
        Cursor cursor1 = db.rawQuery(selectQury1, null);
        int contentval = cursor.getColumnIndex(DATE);
        int contentval1 = cursor.getColumnIndex(AREANAME);
        int contentval2 = cursor.getColumnIndex(TQPESTICIDE);
        int contentval3 = cursor.getColumnIndex(TQWATER);
        int contentval4 = cursor.getColumnIndex(TQTOTVOL);
        int contentval5 = cursor.getColumnIndex(TQAREA);
        int contentval6 = cursor.getColumnIndex(PTTANKFIl);
        int contentval7 = cursor.getColumnIndex(PTPESTICIDE);
        int contentval8 = cursor.getColumnIndex(PTWATER);
        int contentval9 = cursor.getColumnIndex(PTAREA);
        int contentval10 = cursor.getColumnIndex(QPPESTICIDE);
        int contentval11 = cursor.getColumnIndex(QPWATER);
        int contentval12 = cursor.getColumnIndex(QPTOTVOL);
        int contentval13 = cursor.getColumnIndex(QPAREA);
        int contentval14 = cursor.getColumnIndex(NOOFTANKS);


        if (cursor.moveToFirst()) {
            do {
                String val = cursor1.getString(contentval1);

                // lis.add(cursor.getString(contentval));//adding 2nd column data
                lis.put("date1", cursor.getString(contentval));
                lis.put("area1", cursor.getString(contentval1));
                lis.put("tqp", cursor.getString(contentval2));
                lis.put("tqw", cursor.getString(contentval3));
                lis.put("tqt", cursor.getString(contentval4));
                lis.put("tqa", cursor.getString(contentval5));
                lis.put("ptf", cursor.getString(contentval6));
                lis.put("ptp", cursor.getString(contentval7));
                lis.put("ptw", cursor.getString(contentval8));
                lis.put("pta", cursor.getString(contentval9));
                lis.put("qpp", cursor.getString(contentval10));
                lis.put("qpw", cursor.getString(contentval11));
                lis.put("qpt", cursor.getString(contentval12));
                lis.put("qpa", cursor.getString(contentval13));
                lis.put("nots", cursor.getString(contentval14));
                oslist.add(lis);
                Log.d("oslist", oslist.get(1).get("nots"));

            } while (cursor.moveToNext());

        }
        // closing connection
        cursor.close();
        db.close();
        Log.d("cursor", String.valueOf(cursor));

        // returning lables
        return oslist;
    }

}
