package com.example.duan1.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.DataBase.Dbhelper;

public class ThongKeDAO {
    Dbhelper dbhelper;
    public ThongKeDAO(Context context) {
        dbhelper = new Dbhelper(context);
    }


//    public int getDoanhThu(String start, String end) {
//        start = start.replace("/", "");
//        end = end.replace("/", "");
//        SQLiteDatabase db = dbhelper.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT SUM(Gia) FROM HoaDon WHERE substr(NgayDH, 7) || substr(NgayDH, 4,2) || substr(NgayDH,1,2) BETWEEN ? AND ?", new String[]{start, end});
//        if (cursor.getCount() != 0) {
//            cursor.moveToFirst();
//            return cursor.getInt(0);
//        }
//        return 0;
//    }

    public int getDoanhThu(String start, String end) {
        start = start.replace("/", "");
        end = end.replace("/", "");
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(tongtien) FROM DONHANG WHERE substr(ngaydathang, 7) || substr(ngaydathang, 4,2) || substr(ngaydathang,1,2) BETWEEN ? AND ?", new String[]{start, end});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }


}
