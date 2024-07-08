package com.example.duan1.DAO;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import com.example.duan1.DataBase.Dbhelper;
import com.example.duan1.Model.DonHang;


public class DonHangDao {
    private Dbhelper dbHelper;

    public DonHangDao(Context context) {
        this.dbHelper = new Dbhelper(context);
    }

    public ArrayList<DonHang> getDsDonHang() {
        ArrayList<DonHang> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            Cursor cursor = database.rawQuery("SELECT DONHANG.madonhang, ThanhVien.id, ThanhVien.HoTen, DONHANG.ngaydathang, DONHANG.tongtien, DONHANG.trangthai " +
                    "FROM DONHANG,ThanhVien WHERE DONHANG.id = ThanhVien.id", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    DonHang donHang = new DonHang();
                    donHang.setMaDonHang(cursor.getInt(0));
                    donHang.setMaTaiKhoan(cursor.getInt(1));
                    donHang.setTenTaiKhoan(cursor.getString(2));
                    donHang.setNgayDatHang(cursor.getString(3));
                    donHang.setTongTien(cursor.getInt(4));
                    donHang.setTrangthai(cursor.getString(5));

                    list.add(donHang);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Lỗi", e);
        }
        return list;
    }

//    public boolean xoaDonHang(DonHang donHang) {
//        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
//        long check = sqLiteDatabase.delete("DONHANG", "madonhang = ?", new String[]{String.valueOf(donHang.getMaDonHang())});
//        return check > 0;
//
//    }
public int xoaDonHang(int madonhang) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    Cursor cursor = db.rawQuery("select * from CHITIETDONHANG where madonhang = ?", new String[]{String.valueOf(madonhang)});
    if (cursor.getCount() != 0) {
        return -1;
    }
    long check = db.delete("DONHANG", "madonhang = ?", new String[]{String.valueOf(madonhang)});
    if (check == -1) {
        return 0;
    } else {
        return 1;
    }
}
    public boolean updateDonHang(DonHang donHang) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("madonhang",donHang.getMaDonHang());
        values.put("trangthai", donHang.getTrangthai());
        long check = sqLiteDatabase.update("DONHANG", values, "madonhang = ?", new String[]{String.valueOf(donHang.getMaDonHang())});
        return check > 0;

    }

    public int insertDonHang(DonHang donHang) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", donHang.getMaTaiKhoan());
        values.put("ngaydathang", donHang.getNgayDatHang());
        values.put("tongtien", donHang.getTongTien());
        values.put("trangthai", donHang.getTrangthai());

        try {
            long insertedId = db.insert("DONHANG", null, values);
            db.close();

            // Kiểm tra xem đơn hàng đã được chèn thành công hay không
            if (insertedId > 0) {
                return (int) insertedId; // Trả về ID của đơn hàng nếu thành công
            } else {
                return -1; // Trả về -1 nếu có lỗi khi chèn đơn hàng
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi chèn đơn hàng", e);
            return -1; // Trả về -1 nếu có lỗi khi chèn đơn hàng
        }
    }

    public ArrayList<DonHang> getDonHangByMaTaiKhoan(int maTaiKhoan) {
        ArrayList<DonHang> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            String query = "SELECT DONHANG.madonhang, ThanhVien.id, ThanhVien.HoTen, DONHANG.ngaydathang, DONHANG.tongtien, DONHANG.trangthai FROM DONHANG JOIN ThanhVien ON DONHANG.id = ThanhVien.id WHERE ThanhVien.id = ?";

            Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(maTaiKhoan)});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    DonHang donHang = new DonHang();
                    donHang.setMaDonHang(cursor.getInt(0));
                    donHang.setMaTaiKhoan(cursor.getInt(1));
                    donHang.setTenTaiKhoan(cursor.getString(2));
                    donHang.setNgayDatHang(cursor.getString(3));
                    donHang.setTongTien(cursor.getInt(4));
                    donHang.setTrangthai(cursor.getString(5));
                    list.add(donHang);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Lỗi", e);
        }
        return list;
    }
}
