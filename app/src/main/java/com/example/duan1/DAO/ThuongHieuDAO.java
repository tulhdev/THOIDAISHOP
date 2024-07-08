package com.example.duan1.DAO;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import com.example.duan1.DataBase.Dbhelper;
import com.example.duan1.Model.KichThuoc;
import com.example.duan1.Model.ThuongHieu;

public class ThuongHieuDAO {

    Dbhelper dbhelper;

    public ThuongHieuDAO(Context context) {
        dbhelper = new Dbhelper(context);
    }

//    public ArrayList<ThuongHieu> getDSThuongHieuu(){
//        ArrayList<ThuongHieu> list = new ArrayList<>();
//        SQLiteDatabase db = dbhelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * from ThuongHieu", null);
//        if (cursor.getCount() != 0){
//            cursor.moveToFirst();
//                do {
//                    list.add(new ThuongHieu(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
//                } while (cursor.moveToNext());
//        }
//        return list;
//    }

    public ArrayList<ThuongHieu> getDSThuongHieu(){
        ArrayList<ThuongHieu> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("select * from ThuongHieu",null);
            if(cursor.getCount() > 0 ){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    ThuongHieu th = new ThuongHieu();
                    th.setMaTH(cursor.getInt(0));
                    th.setAnh(cursor.getString(1));
                    th.setSDT(cursor.getString(2));
                    th.setTenTH(cursor.getString(3));
                    list.add(th);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.e(TAG,"Lỗi",e);
        }
        return list;
    }

    public ThuongHieu selectThuongHieu(int MaTH){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ThuongHieu where MaTh = ?",new String[]{String.valueOf(MaTH)});

        ThuongHieu thuongHieu = null;
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            thuongHieu = new ThuongHieu(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }
        cursor.close();
        return thuongHieu;
    }


    public boolean insert (String SDT, String Anh, String TenTH){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Anh", Anh);
        values.put("SDT", SDT);
        values.put("TenTH", TenTH);
        long check = db.insert("ThuongHieu", null, values);
        if (check == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean update(ThuongHieu th){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Anh", th.getAnh());
        values.put("SDT", th.getSDT());
        values.put("TenTH", th.getTenTH());
        long check = db.update("ThuongHieu", values, "MaTH = ?", new String[]{String.valueOf(th.getMaTH())});
        if (check == -1){
            return false;
        }else {
            return true;
        }
    }

//    public boolean update(ThuongHieu th) {
//        if (th == null) {
//            return false;
//        }
//
//        try (SQLiteDatabase db = dbhelper.getWritableDatabase()) {
//            ContentValues values = new ContentValues();
//            values.put("Anh", th.getAnh());
//            values.put("SDT", th.getSDT());
//            values.put("TenTH", th.getTenTH());
//            long check = db.update("ThuongHieu", values, "MaTH = ?", new String[]{String.valueOf(th.getMaTH())});
//
//            return check != -1;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }


    // xóa loại sách
    // 1: xóa thành công, 0: xóa thất bại, -1 : có sách tồn tại trong loại đó
    public int delete(int MaTH){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SanPham WHERE MaTH = ?", new String[]{String.valueOf(MaTH)});
        if(cursor.getCount() != 0){
            return -1;
        }
        long check = db.delete("ThuongHieu","MaTH = ?", new String[]{String.valueOf(MaTH)});
        if(check == -1){
            return 0;
        }else {
            return 1;
        }
    }


}
