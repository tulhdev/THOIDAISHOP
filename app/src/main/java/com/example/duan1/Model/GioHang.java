package com.example.duan1.Model;

public class GioHang {
    private int MAGH;
    private int MaSP;
    private int id;

    private int soLuongMua;
    private String tenSanPham;
    private int giaSanPham;
    private boolean isSelected;
    private String AvataSP;
    private int soLuongspcl;


    public GioHang() {

    }

    public GioHang(int maSP, int id, int soLuongMua) {
        MaSP = maSP;
        this.id = id;
        this.soLuongMua = soLuongMua;
    }

    public GioHang(int MAGH, int maSP, int id, int soLuongMua, String tenSanPham, int giaSanPham) {
        this.MAGH = MAGH;
        MaSP = maSP;
        this.id = id;
        this.soLuongMua = soLuongMua;
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
    }

    public int getMAGH() {
        return MAGH;
    }

    public void setMAGH(int MAGH) {
        this.MAGH = MAGH;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int maSP) {
        MaSP = maSP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(int giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getAvataSP() {
        return AvataSP;
    }

    public void setAvataSP(String avataSP) {
        AvataSP = avataSP;
    }

    public int getSoLuongspcl() {
        return soLuongspcl;
    }

    public void setSoLuongspcl(int soLuongspcl) {
        this.soLuongspcl = soLuongspcl;
    }
}
