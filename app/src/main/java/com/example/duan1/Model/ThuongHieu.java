package com.example.duan1.Model;

public class ThuongHieu {
    int MaTH;
    private String Anh;
    String TenTH, SDT;

    public ThuongHieu() {
    }

    public ThuongHieu(int maTH, String anh, String tenTH, String SDT) {
        MaTH = maTH;
        Anh = anh;
        TenTH = tenTH;
        this.SDT = SDT;
    }

    public int getMaTH() {
        return MaTH;
    }

    public void setMaTH(int maTH) {
        MaTH = maTH;
    }

    public String getAnh() {
        return Anh;
    }

    public void setAnh(String anh) {
        Anh = anh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getTenTH() {
        return TenTH;
    }

    public void setTenTH(String tenTH) {
        TenTH = tenTH;
    }
}
