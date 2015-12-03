package cs258.emerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JoniMarie on 10/25/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "database1";

    public static final String TABLE_LOCATION = "location_table";
    public static final String LOC_ID = "LOC_ID";
    public static final String LOC_NAME = "LOC_NAME";
    public static final String LOC_LONGITUDE = "LOC_LONGITUDE";
    public static final String LOC_LATITUDE = "LOC_LATITUDE";
    public static final String LOC_CONTACT = "LOC_CONTACT";

    public static final String TABLE_CONTACT = "contact_table";
    public static final String CON_ID = "CON_ID";
    public static final String CON_NAME = "CON_NAME";
    public static final String CON_NUMBER = "CON_NUMBER";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_LOCATION + "( LOC_ID INTEGER PRIMARY KEY AUTOINCREMENT, LOC_NAME TEXT, LOC_LONGITUDE REAL, LOC_LATITUDE REAL, LOC_CONTACT INTEGER )");
        db.execSQL("create table " + TABLE_CONTACT + "( CON_ID INTEGER PRIMARY KEY AUTOINCREMENT, CON_NAME TEXT, CON_NUMBER INTEGER )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        onCreate(db);
    }

    public boolean insertLocation(String name, float longitude, float lat, int cont){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOC_NAME, name);
        cv.put(LOC_LONGITUDE, longitude);
        cv.put(LOC_LATITUDE, lat);
        cv.put(LOC_CONTACT, cont);

        long res = db.insert(TABLE_LOCATION, null, cv);

        if(res == -1){
            return false;
        }
        return true;
    }

    public boolean insertContact(String name, int num){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CON_NAME, name);
        cv.put(CON_NUMBER, num);

        long res = db.insert(TABLE_CONTACT, null, cv);

        if(res == -1){
            return false;
        }
        return true;
    }

    public Cursor getAllLocation(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_LOCATION, null);
        return result;
    }

    public Cursor getAllContact(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_CONTACT, null);
        return result;
    }

   /* public boolean updateLocation(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(LOC_NAME, name);
        cv.put(LOC_LONGITUDE, longitude);
        cv.put(LOC_LATITUDE, lat);
        cv.put(LOC_CONTACT, cont);

        db.update(TABLE_CONTACT, cv, "ID = ?", new String[]{id});
        return true;

    }*/

    public boolean updateContact(String id, String name, int num) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(CON_ID, Integer.parseInt(id));
        cv.put(CON_NAME, name);
        cv.put(CON_NUMBER, num);

        db.update(TABLE_CONTACT, cv, "CON_ID = ?", new String[]{id});
        return true;
    }

    public int deleteContact(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CONTACT, "CON_ID = ?", new String[]{id});
    }
}
