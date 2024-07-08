package com.example.duan1.DAO;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import com.example.duan1.DataBase.Dbhelper;
import com.example.duan1.Model.KichThuoc;
import com.example.duan1.Model.ThanhVien;

public class KichThuocDAO {
    Dbhelper dbhelper;


    public KichThuocDAO(Context context) {
        dbhelper = new Dbhelper(context);
    }

    public ArrayList<KichThuoc> selectAllKichThuoc(){
        ArrayList<KichThuoc> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("select * from KichThuoc",null);
            if(cursor.getCount() >0 ){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    KichThuoc kt = new KichThuoc();
                    kt.setId(cursor.getInt(0));
                    kt.setAvataKT(cursor.getString(1));
                    kt.setMaKT(cursor.getString(2));
                    kt.setSize(cursor.getInt(3));
                    kt.setSoLuong(cursor.getInt(4));
                    list.add(kt);
                    cursor.moveToNext();

                }
            }
        }catch (Exception e){
            Log.i(TAG,"Lỗi",e);
        }
        return list;
    }
    public int delete(int MaKT) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT *  FROM SanPham WHERE id=? ",new String[]{String.valueOf(MaKT)});
        if (cursor.getCount()!=0){
            return -1;
        }else{
            long check = db.delete("KichThuoc", "id=?", new String[]{String.valueOf(MaKT)});
            if (check ==-1){
                return 0;
            }else{
                return 1;
            }
        }

    }
    public  boolean update(KichThuoc kt){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaKT",kt.getMaKT());
        values.put("AvataKT",kt.getAvataKT());
        values.put("Size",kt.getSize());
        values.put("SoLuong",kt.getSoLuong());
        long check = db.update("KichThuoc", values, "id=?", new String[]{String.valueOf(kt.getId())});
        if (check == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean insert (String MaKT, String AvataKT, String Size, String SoLuong ){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaKT", MaKT);
        values.put("AvataKT", AvataKT);
        values.put("Size", Size);
        values.put("SoLuong", SoLuong);
        long check = db.insert("KichThuoc", null, values);
        if (check == -1){
            return false;
        }else {
            return true;
        }
    }
}
