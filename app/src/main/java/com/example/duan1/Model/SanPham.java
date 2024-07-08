package com.example.duan1.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SanPham implements Parcelable {
    private int MaSP;
    private String AvataSP = null;
    private String TenSP;
    private  int Gia;
    private  int SoLuong;
    private  int id;
    private  int MaTH;
    private  int MaLSP;
/// thêm
    private String size;
    private String tenthuonghieu;
    private String tenlsp;
    public SanPham() {
    }
// them

    public SanPham(int maSP, String avataSP, String tenSP, int gia, int soLuong, int id, int maTH, int maLSP, String size, String tenthuonghieu, String tenlsp) {
        MaSP = maSP;
        AvataSP = avataSP;
        TenSP = tenSP;
        Gia = gia;
        SoLuong = soLuong;
        this.id = id;
        MaTH = maTH;
        MaLSP = maLSP;
        this.size = size;
        this.tenthuonghieu = tenthuonghieu;
        this.tenlsp = tenlsp;
    }

    public SanPham(int maSP, String avataSP, String tenSP, int gia, int soLuong, int id, int maTH, int maLSP) {
        MaSP = maSP;
        AvataSP = avataSP;
        TenSP = tenSP;
        Gia = gia;
        SoLuong = soLuong;
        this.id = id;
        MaTH = maTH;
        MaLSP = maLSP;
    }

    public SanPham(String avataSP, String tenSP, int gia, int soLuong, int id, int maTH, int maLSP) {
        AvataSP = avataSP;
        TenSP = tenSP;
        Gia = gia;
        SoLuong = soLuong;
        this.id = id;
        MaTH = maTH;
        MaLSP = maLSP;
    }

    public SanPham(int maSP, String tenSP, int gia, int soLuong, int id, int maTH, int maLSP) {
        MaSP = maSP;
        TenSP = tenSP;
        Gia = gia;
        SoLuong = soLuong;
        this.id = id;
        MaTH = maTH;
        MaLSP = maLSP;
    }

    public SanPham(int maSP, String avataSP, String tenSP, int gia, int soLuong) {
        MaSP = maSP;
        AvataSP = avataSP;
        TenSP = tenSP;
        Gia = gia;
        SoLuong = soLuong;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int maSP) {
        MaSP = maSP;
    }

    public String getAvataSP() {
        return AvataSP;
    }

    public void setAvataSP(String avataSP) {
        AvataSP = avataSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int gia) {
        Gia = gia;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaTH() {
        return MaTH;
    }

    public void setMaTH(int maTH) {
        MaTH = maTH;
    }

    public int getMaLSP() {
        return MaLSP;
    }

    public void setMaLSP(int maLSP) {
        MaLSP = maLSP;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTenthuonghieu() {
        return tenthuonghieu;
    }

    public void setTenthuonghieu(String tenthuonghieu) {
        this.tenthuonghieu = tenthuonghieu;
    }

    public String getTenlsp() {
        return tenlsp;
    }

    public void setTenlsp(String tenlsp) {
        this.tenlsp = tenlsp;
    }

    public static final  Creator<SanPham> CREATOR = new Creator<SanPham>() {
        @Override
        public SanPham createFromParcel(Parcel parcel) {
            return new SanPham(parcel);
        }

        @Override
        public SanPham[] newArray(int i) {
            return new SanPham[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    public SanPham(Parcel in ){
        MaSP = in.readInt();
        TenSP = in.readString();
        Gia = in.readInt();
        SoLuong = in.readInt();
        this.size = in.readString();
        this.tenthuonghieu = in.readString();
        this.tenlsp = in.readString();

    }
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(MaSP);
        parcel.writeString(TenSP);
        parcel.writeInt(Gia);
        parcel.writeInt(SoLuong);
        parcel.writeString( this.size);
        parcel.writeString(this.tenthuonghieu);
        parcel.writeString(this.tenlsp);
//        parcel.writeString(AvataSP);

    }
}
