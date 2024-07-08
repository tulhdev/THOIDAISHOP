package com.example.duan1.Model;

public class LoaiSanPham {

    private int MaLSP;
    private String Avata;
    private String TenLSP;

    public LoaiSanPham() {
    }

    public LoaiSanPham(int maLSP, String avata, String tenLSP) {
        MaLSP = maLSP;
        Avata = avata;
        TenLSP = tenLSP;
    }

    public int getMaLSP() {
        return MaLSP;
    }

    public void setMaLSP(int maLSP) {
        MaLSP = maLSP;
    }

    public String getAvata() {
        return Avata;
    }

    public void setAvata(String avata) {
        Avata = avata;
    }

    public String getTenLSP() {
        return TenLSP;
    }

    public void setTenLSP(String tenLSP) {
        TenLSP = tenLSP;
    }
}
