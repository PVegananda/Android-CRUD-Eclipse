package com.example.penyimpanansql;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseManager {
    private static final String ROW_ID = "_id";
    private static final String ROW_NAMA = "nama";
    private static final String ROW_HOBI = "alamat";
    private static final String NAMA_DB = "DatabaseAndroidDua";
    private static final String NAMA_TABEL = "datamemberbarumbs";
    private static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "create table " + NAMA_TABEL
            + " (" + ROW_ID + " integer PRIMARY KEY autoincrement," + ROW_NAMA
            + " text," + ROW_HOBI + " text)";

    private final Context context;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db;
    public DatabaseManager(Context ctx) {
        this.context = ctx;
        dbHelper = new DatabaseOpenHelper(ctx);
        db = dbHelper.getWritableDatabase();
    }
    public static class DatabaseOpenHelper extends SQLiteOpenHelper {
        public DatabaseOpenHelper(Context context) {
            super(context, NAMA_DB, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_DB);
            onCreate(db);
        }
    }
    public void close() {
        dbHelper.close();
    }

    public void addRow(String nama, String alamat) {

        ContentValues values = new ContentValues();
        values.put(ROW_NAMA, nama);
        values.put(ROW_HOBI, alamat);

        try {
            db.insert(NAMA_TABEL, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> ambilSemuaBaris() {
        ArrayList<ArrayList<Object>> dataArray = new ArrayList<ArrayList<Object>>();
        Cursor cur;
        try {
            cur = db.query(NAMA_TABEL, new String[] { ROW_ID, ROW_NAMA,
                    ROW_HOBI }, null, null, null, null, null);
            cur.moveToFirst();
            if (!cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getLong(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
        return dataArray;
    }

    public ArrayList<Object> ambilBaris(long rowId) {

        ArrayList<Object> arrbaris = new ArrayList<Object>();
        Cursor cursor;
        try {
            cursor = db.query(NAMA_TABEL, new String[] { ROW_ID, ROW_NAMA,
                    ROW_HOBI }, ROW_ID + "=" + rowId, null, null, null, null,
                    null);
            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    arrbaris.add(cursor.getLong(0));
                    arrbaris.add(cursor.getString(1));
                    arrbaris.add(cursor.getString(2));
                } while (cursor.moveToNext());
                String r = String.valueOf(arrbaris);
                Toast.makeText(context, "haha" + r, Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", e.toString());
            Toast.makeText(context, "hhii" + e.toString(), Toast.LENGTH_SHORT)
                    .show();
        }
        return arrbaris;
    }

    public void updateBaris(long rowId, String nama, String alamat) {

        ContentValues cv = new ContentValues();
        cv.put(ROW_NAMA, nama);
        cv.put(ROW_HOBI, alamat);

        try {
            db.update(NAMA_TABEL, cv, ROW_ID + "=" + rowId, null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Db Error", e.toString());
        }
    }

    public void deleteBaris(long idBaris) {
        try {
            db.delete(NAMA_TABEL, ROW_ID + "=" + idBaris, null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", e.toString());
        }
    }
    
}
