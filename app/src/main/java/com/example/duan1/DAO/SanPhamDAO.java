package com.example.duan1.DAO;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import com.example.duan1.DataBase.Dbhelper;
import com.example.duan1.Model.SanPham;
import com.example.duan1.Model.ThanhVien;

public class SanPhamDAO {
    static Dbhelper dbhelper;
    public SanPhamDAO (Context context){
        dbhelper = new Dbhelper(context);
    }

    public static ArrayList<SanPham> selectAllSanPham(){
        ArrayList<SanPham> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT sp.MaSP, sp.AvataSP ,sp.TenSP, sp.Gia,sp.SoLuong,sp.id,kt.MaKT,kt.Size,kt.SoLuong,sp.MaTH,th.SDT,th.TenTH,sp.MaLSP,lsp.TenLSP\n" +
                    "FROM SanPham sp, KichThuoc kt,ThuongHieu th, LoaiSanPham lsp\n" +
                    "Where sp.id = kt.id and sp.MaTH = th.MaTH and  sp.MaLSP = lsp.MaLSP",null);
            if(cursor.getCount() >0 ){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    SanPham sp = new SanPham();
                    sp.setMaSP(cursor.getInt(0));
                    sp.setAvataSP(cursor.getString(1));
                    sp.setTenSP(cursor.getString(2));
                    sp.setGia(cursor.getInt(3));
                    sp.setSoLuong(cursor.getInt(4));
                    sp.setSize(cursor.getString(7));
                    sp.setTenthuonghieu(cursor.getString(11));
                    sp.setTenlsp(cursor.getString(13));
                    list.add(sp);
                    cursor.moveToNext();

                }
            }
        }catch (Exception e){
            Log.i(TAG,"Lỗi",e);
        }
        return list;
    }
    public static ArrayList<SanPham> selectAllGioHang(){
        ArrayList<SanPham> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT sp.MaSP, sp.AvataSP ,sp.TenSP, sp.Gia, sp.SoLuong\n" +
                    "FROM SanPham sp\n",null);
            if(cursor.getCount() >0 ){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    SanPham sp = new SanPham();
                    sp.setMaSP(cursor.getInt(0));
                    sp.setAvataSP(cursor.getString(1));
                    sp.setTenSP(cursor.getString(2));
                    sp.setGia(cursor.getInt(3));
                    sp.setSoLuong(cursor.getInt(4));
                    list.add(sp);
                    cursor.moveToNext();

                }
            }
        }catch (Exception e){
            Log.i(TAG,"Lỗi",e);
        }
        return list;
    }

    // Phương thức thêm sản phẩm vào giỏ hàng
    public long themSanPhamVaoGioHang(String TenSP, int SoLuong, double Gia, String AvataSP) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("AvataSP", AvataSP); // Thêm giá trị cho trường AvataSP
        values.put("TenSP", TenSP);
        values.put("SoLuong", SoLuong);
        values.put("Gia", Gia);

        return db.insert("SanPham", null, values);
    }



    public int delete(int MaSP){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DONHANG WHERE madonhang = ?", new String[]{String.valueOf(MaSP)});
        if (cursor.getCount() != 0){
            return -1;
        }
        long check = db.delete("SanPham", "MaSP = ?", new String[]{String.valueOf(MaSP)});
        if (check == -1){
            return 0;
        }else {
            return 1;
        }
    }
    public boolean deletee(int masp) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        long row = db.delete("SanPham", "MaSP=?", new String[]{String.valueOf(masp)});
        return (row > 0);
    }
    public boolean insert(SanPham pm) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("MaSP", pm.getMaLSP());
        values.put("AvataSP",pm.getAvataSP());
        values.put("TenSP", pm.getTenSP());
        values.put("Gia",pm.getGia());
        values.put("SoLuong", pm.getSoLuong());
        values.put("id", pm.getId());
        values.put("MaTH", pm.getMaTH());
        values.put("MaLSP", pm.getMaLSP());
        long row = db.insert("SanPham", null, values);
        return (row > 0);
    }

    public boolean update(SanPham pm){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("MaSP", pm.getMaLSP());
        values.put("AvataSP",pm.getAvataSP());
        values.put("TenSP", pm.getTenSP());
        values.put("Gia",pm.getGia());
        values.put("SoLuong", pm.getSoLuong());
        values.put("id", pm.getId());
        values.put("MaTH", pm.getMaTH());
        values.put("MaLSP", pm.getMaLSP());
        long row = db.update("SanPham", values, "MaSP=?", new String[]{String.valueOf(pm.getMaSP())});
        return (row > 0);
    }

    // Hàm cập nhật số lượng sản phẩm trong giỏ hàng
    public boolean updateQuantity(int maSP, int newQuantity) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("SoLuong", newQuantity);

        // Update số lượng sản phẩm trong giỏ hàng dựa trên MaSP
        long row = db.update("SanPham", values, "MaSP=?", new String[]{String.valueOf(maSP)});
        return (row > 0);
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
    private static final String COL_MASP = "MaSP";
    private static final String COL_ANHSP = "AvataSP";
    private static final String COL_TENSP = "TenSP";
    private static final String COL_GIA = "Gia";
    private static final String COL_SOLUONG = "SoLuong";
    private static final String COL_MATV= "id";
    private static final String COL_MATH= "MaTH";
    private static final String COL_MALSP = "MaLSP";



    // ... các phương thức khác
    @SuppressLint("Range")
    public SanPham getSanPhamById(int masanpham) {
        SQLiteDatabase database = dbhelper.getReadableDatabase();
        SanPham sanPham = null;

        String[] columns = {COL_MASP, COL_ANHSP, COL_TENSP,  COL_GIA, COL_SOLUONG, COL_MATV,COL_MATH,COL_MALSP};
        String selection = COL_MASP + "=?";
        String[] selectionArgs = {String.valueOf(masanpham)};

        Cursor cursor = database.query("SanPham", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int maSanPham = cursor.getInt(cursor.getColumnIndex(COL_MASP));
            String anhSanPham = cursor.getString(cursor.getColumnIndex(COL_ANHSP));
            String tenSanPham = cursor.getString(cursor.getColumnIndex(COL_TENSP));
            int gia = cursor.getInt(cursor.getColumnIndex(COL_GIA));
            int slsp = cursor.getInt(cursor.getColumnIndex(COL_SOLUONG));
            int matv = cursor.getInt(cursor.getColumnIndex(COL_MATV));
            int math = cursor.getInt(cursor.getColumnIndex(COL_MATH));
            int maLoaiSanPham = cursor.getInt(cursor.getColumnIndex(COL_MALSP));


            int sl = cursor.getInt(cursor.getColumnIndex(COL_SOLUONG));
            sanPham = new SanPham(maSanPham, anhSanPham, tenSanPham, gia, slsp, matv, math,maLoaiSanPham);
        }

        cursor.close();
        return sanPham;
    }

    public boolean updateSlSanPham(int maSanPham, int newQuantity) {
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("SoLuong", newQuantity);

        // Đảm bảo rằng điều kiện WHERE sử dụng mã sản phẩm đúng
        String whereClause = "MaSP = ?";
        String[] whereArgs = {String.valueOf(maSanPham)};

        // Thực hiện cập nhật
        int rowsAffected = database.update("SanPham", values, whereClause, whereArgs);

        // Trả về true nếu có ít nhất một hàng bị ảnh hưởng
        return rowsAffected > 0;
    }

}
