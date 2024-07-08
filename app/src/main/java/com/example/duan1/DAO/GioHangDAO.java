package com.example.duan1.DAO;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import com.example.duan1.DataBase.Dbhelper;
import com.example.duan1.Model.GioHang;
import com.example.duan1.Model.SanPham;

public class GioHangDAO {
    static Dbhelper dbhelper;
    public GioHangDAO (Context context){
        dbhelper = new Dbhelper(context);
    }
    public static ArrayList<GioHang> selectAllGioHang(){
        ArrayList<GioHang> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT gh.MAGH,gh.id, gh.MaSP, gh.SoLuong ,sp.AvataSP ,sp.TenSP, sp.Gia, sp.SoLuong\n" +
                    "FROM GioHang gh , SanPham sp\n",null);
            if(cursor.getCount() >0 ){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    GioHang sp = new GioHang();
                    sp.setMAGH(cursor.getInt(0));
                    sp.setId(cursor.getInt(1));
                    sp.setMaSP(cursor.getInt(2));
                    sp.setSoLuongMua(cursor.getInt(3));
                    sp.setAvataSP(cursor.getString(4));
                    sp.setTenSanPham(cursor.getString(5));
                    sp.setGiaSanPham(cursor.getInt(6));
                    sp.setSoLuongspcl(cursor.getInt(7));
                    list.add(sp);
                    cursor.moveToNext();

                }
            }
        }catch (Exception e){
            Log.i(TAG,"Lỗi",e);
        }
        return list;
    }
    public ArrayList<GioHang> getDanhSachGioHangByMaNguoiDung(int maNguoiDung) {
        ArrayList<GioHang> list = new ArrayList<>();
        SQLiteDatabase database = dbhelper.getReadableDatabase();
        try {
            // Thêm điều kiện WHERE cho mã người dùng
            String query = "SELECT gh.MAGH,gh.id, gh.MaSP, gh.SoLuong ,sp.AvataSP ,sp.TenSP, sp.Gia, sp.SoLuong\n" +
                    "FROM GioHang gh , SanPham sp\n" +
                    "WHERE gh.MaSP = sp.MaSP AND gh.id = ?";

            Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(maNguoiDung)});
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    GioHang sp = new GioHang();
                    sp.setMAGH(cursor.getInt(0));
                    sp.setId(cursor.getInt(1));
                    sp.setMaSP(cursor.getInt(2));
                    sp.setSoLuongMua(cursor.getInt(3));
                    sp.setAvataSP(cursor.getString(4));
                    sp.setTenSanPham(cursor.getString(5));
                    sp.setGiaSanPham(cursor.getInt(6));
                    sp.setSoLuongspcl(cursor.getInt(7));
                    list.add(sp);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.i(ContentValues.TAG, "Lỗiiii", e);
        }
        return list;
    }
    // Phương thức thêm sản phẩm vào giỏ hàng

    public boolean insertGioHang(GioHang gh) {
        SQLiteDatabase da = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaSP", gh.getMaSP());
        values.put("id", gh.getId());
        values.put("SoLuong", gh.getSoLuongMua());
        long check = da.insert("GioHang", null, values);
        return check > 0;
    }
    public boolean updateGioHang(GioHang gioHang) {
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("SoLuong", gioHang.getSoLuongMua());
        int rowsAffected = database.update("GioHang", values, "MAGH = ?", new String[]{String.valueOf(gioHang.getMAGH())});
        return rowsAffected > 0;
    }

    public boolean deleteGioHang(GioHang gioHang) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("GioHang", "MAGH = ?", new String[]{String.valueOf(gioHang.getMAGH())});
        return check > 0;
    }

    public boolean updateQuantity(int maSP, int newQuantity) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("SoLuong", newQuantity);

        // Update số lượng sản phẩm trong giỏ hàng dựa trên MaSP
        long row = db.update("SanPham", values, "MaSP=?", new String[]{String.valueOf(maSP)});
        return (row > 0);
    }
    public GioHang getGioHangByMasp(int masp, int mand) {
        GioHang gioHang = null;
        SQLiteDatabase database = dbhelper.getReadableDatabase();
        try {
            Cursor cursor = database.rawQuery("SELECT gh.MAGH,gh.id, gh.MaSP, gh.SoLuong " +
                    "FROM GIOHANG gh WHERE MaSP = ? AND id = ?", new String[]{String.valueOf(masp), String.valueOf(mand)});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                gioHang = new GioHang();
                gioHang.setMAGH(cursor.getInt(0));
                gioHang.setMaSP(cursor.getInt(2));
                gioHang.setId(cursor.getInt(1));
                gioHang.setSoLuongMua(cursor.getInt(3));
            }
            cursor.moveToNext();
        } catch (Exception e) {
            Log.e(ContentValues.TAG, "Error", e);
        }
        return gioHang;
    }

    // Hàm lấy số lượng của một sản phẩm trong giỏ hàng dựa trên MaSP
    public int getQuantity(int maSP) {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        int quantity = 0;

        Cursor cursor = db.rawQuery("SELECT SoLuong FROM SanPham WHERE MaSP = ?", new String[]{String.valueOf(maSP)});
        if (cursor.moveToFirst()) {
            // In log để xem thông tin chi tiết của Cursor
            Log.d("CursorInfo", "ColumnIndex for SoLuong: " + cursor.getColumnIndex("SoLuong"));
            // Sử dụng chỉ mục cố định của cột "SoLuong" (ví dụ 0 là chỉ mục đầu tiên)
            quantity = cursor.getInt(0);
        }

        cursor.close();
        return quantity;
    }
}
