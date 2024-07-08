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
import com.example.duan1.Model.NapTien;
import com.example.duan1.Model.ThanhVien;

public class NapTienDAO {
    Dbhelper dbhelper;


    public NapTienDAO(Context context) {
        dbhelper = new Dbhelper(context);
    }

    public ArrayList<NapTien> selectAllNapTien(){
        ArrayList<NapTien> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("SELECT nt.MaNT,nt.SoTien,nt.ngayNT,nt.TenNXN,nt.TrangThai,nt.id , tv.MaTV,tv.HoTen,tv.SoTien\n" +
                    "FROM NAPTIEN nt, ThanhVien tv\n" +
                    "Where nt.id = tv.id ",null);
            if(cursor.getCount() >0 ){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    NapTien nt = new NapTien();
                    nt.setMaNT(cursor.getInt(0));
                    nt.setSotien(cursor.getInt(1));
                    nt.setNgayNT(cursor.getString(2));
                    nt.setTrangthai(cursor.getInt(4));
                    nt.setTenNXN(cursor.getString(3));
                    nt.setTenNNT(cursor.getString(6));
                    nt.setSotientrcdo(cursor.getInt(8));
                    list.add(nt);
                    cursor.moveToNext();

                }
            }
        }catch (Exception e){
            Log.i(TAG,"Lỗi",e);
        }
        return list;
    }
    public  boolean insert(NapTien nt) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("MaNT", nt.getMaNT());
        values.put("SoTien",nt.getSotien());
        values.put("ngayNT",nt.getNgayNT());
        values.put("TenNXN", nt.getTenNXN());
        values.put("TrangThai",nt.getTrangthai());
        values.put("id",nt.getId());
        long row = db.insert("NAPTIEN", null, values);
        return (row > 0);
    }
    public  boolean thaydoitrangthai(int MaPM){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TrangThai",1);
        long row = db.update("NAPTIEN", values, "MaNT=?", new String[]{String.valueOf(MaPM)});
        return (row > 0);
    }
}
