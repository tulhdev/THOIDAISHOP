package com.example.duan1.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;

import com.example.duan1.DataBase.Dbhelper;
import com.example.duan1.Model.KichThuoc;
import com.example.duan1.Model.ThanhVien;

public class ThanhVienDAO {
    Dbhelper dbhelper;
    SharedPreferences sharedPreferences;

    public ThanhVienDAO(Context context) {
        dbhelper = new Dbhelper(context);
        sharedPreferences = context.getSharedPreferences("DANGNHAPTV", Context.MODE_PRIVATE);
    }

    public boolean checklogin(String MaTV, String MatKhau) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from ThanhVien where MaTV=? and  MatKhau=?", new String[]{MaTV, MatKhau});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("id", cursor.getInt(0));
            editor.putString("AvataTV", cursor.getString(2));
            editor.putString("HoTen", cursor.getString(3));
            editor.putString("DChi", cursor.getString(7));
            editor.putString("SDT", cursor.getString(5));
            editor.putString("Email", cursor.getString(6));
            editor.putInt("SoTien", cursor.getInt(8));
            editor.putString("Loai", cursor.getString(9));
            // Lưu mật khẩu vào SharedPreferences
           // editor.putString("Loai", cursor.getString(3));
            editor.commit();
            return true;
        } else {
            return false;
        }
    }
    public ArrayList<ThanhVien> selectAllthanhVien(){
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT *  FROM ThanhVien",null);
            if(cursor.getCount() >0 ){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    ThanhVien tv = new ThanhVien();
                    tv.setId(cursor.getInt(0));
                    tv.setMaTV(cursor.getString(1));
                    tv.setAvataTV(cursor.getString(2));
                    tv.setHoTen(cursor.getString(3));
                    tv.setMatKhau(cursor.getString(4));
                    tv.setSDT(cursor.getString(5));
                    tv.setEmail(cursor.getString(6));
                    tv.setDChi(cursor.getString(7));
                    tv.setSotien(cursor.getInt(8));
                    tv.setLoai(cursor.getString(9));
                    list.add(tv);
                    cursor.moveToNext();

                }
            }
        }catch (Exception e){

        }
        return list;
    }
    public int delete(int id) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT *  FROM HoaDon WHERE id=? ",new String[]{String.valueOf(id)});
        if (cursor.getCount()!=0){
            return -1;
        }else{
            long check = db.delete("ThanhVien", "id=?", new String[]{String.valueOf(id)});
            if (check ==-1){
                return 0;
            }else{
                return 1;
            }
        }

    }
    public boolean update(ThanhVien tv){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaTV",tv.getMaTV());
        values.put("AvataTV",tv.getAvataTV());
        values.put("HoTen",tv.getHoTen());
        values.put("MatKhau",tv.getMatKhau());
        values.put("SDT",tv.getSDT());
        values.put("Email",tv.getEmail());
        values.put("SoTien",tv.getSotien());
        values.put("DChi",tv.getDChi());
        long row = db.update("ThanhVien", values, "id=?", new String[]{String.valueOf(tv.getId())});
        return (row > 0);
    }
    public boolean updateSoTien(int maTaiKhoan, int soTienMoi) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("SoTien", soTienMoi);
        long result = db.update("ThanhVien", values, "id = ?", new String[]{String.valueOf(maTaiKhoan)});
        return result != -1;
    }
    public  boolean insert(ThanhVien tv) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaTV", tv.getMaTV());
        values.put("AvataTV",tv.getAvataTV());
        values.put("HoTen",tv.getHoTen());
        values.put("MatKhau", tv.getMatKhau());
        values.put("SDT",tv.getSDT());
        values.put("Email",tv.getEmail());
        values.put("SoTien",tv.getSotien());
        values.put("DChi",tv.getDChi());
        values.put("SoTien",tv.getSotien());
        values.put("Loai",tv.getLoai());
        long row = db.insert("ThanhVien", null, values);
        return (row > 0);
    }

    //forgot
    public String ForgotPassword(String email){
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT MatKhau FROM ThanhVien WHERE Email = ?", new String[]{email});
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            return  cursor.getString(0);
        }else {
            return "Thông tin sai";
        }
    }

    public boolean updateMK(String username, String oldPass, String newPass) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ThanhVien WHERE MaTV = ? AND MatKhau = ?", new String[]{username, oldPass});
        if (cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put("MatKhau", newPass);
            int check = db.update("ThanhVien", values, "MaTV = ?", new String[]{username});
            if (check > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

}
