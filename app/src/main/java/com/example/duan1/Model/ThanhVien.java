package com.example.duan1.Model;

public class ThanhVien {
    private  int id;
    private String MaTV;
    private String AvataTV;
    private String HoTen;
    private String MatKhau;
    private String SDT;
    private String Email;
    private int sotien;
    private String DChi;
    private String loai;

    public ThanhVien() {
    }
/// add
    ///

    public ThanhVien(String maTV, String avataTV, String hoTen, String matKhau, String SDT, String email, int sotien, String DChi, String loai) {
        MaTV = maTV;
        AvataTV = avataTV;
        HoTen = hoTen;
        MatKhau = matKhau;
        this.SDT = SDT;
        Email = email;
        this.sotien = sotien;
        this.DChi = DChi;
        this.loai = loai;
    }

    public ThanhVien(String maTV, String avataTV, String hoTen, String matKhau, String SDT, String email, String DChi, String loai) {
        MaTV = maTV;
        AvataTV = avataTV;
        HoTen = hoTen;
        MatKhau = matKhau;
        this.SDT = SDT;
        Email = email;
        this.DChi = DChi;
        this.loai = loai;
    }

    public ThanhVien(int id, String maTV, String avataTV, String hoTen, String matKhau, String SDT, String email, String DChi, String loai) {
        this.id = id;
        MaTV = maTV;
        AvataTV = avataTV;
        HoTen = hoTen;
        MatKhau = matKhau;
        this.SDT = SDT;
        Email = email;
        this.DChi = DChi;
        this.loai = loai;
    }

    // hien thi

//    public ThanhVien(String maTV, String avataTV, String hoTen, String SDT, String email) {
//        MaTV = maTV;
//        AvataTV = avataTV;
//        HoTen = hoTen;
//        this.SDT = SDT;
//        Email = email;
//    }

    public ThanhVien(String maTV, String avataTV, String hoTen, String SDT, String email, int sotien, String DChi) {
        MaTV = maTV;
        AvataTV = avataTV;
        HoTen = hoTen;
        this.SDT = SDT;
        Email = email;
        this.sotien = sotien;
        this.DChi = DChi;
    }

    public int getSotien() {
        return sotien;
    }

    public void setSotien(int sotien) {
        this.sotien = sotien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaTV() {
        return MaTV;
    }

    public void setMaTV(String maTV) {
        MaTV = maTV;
    }

    public String getAvataTV() {
        return AvataTV;
    }

    public void setAvataTV(String avataTV) {
        AvataTV = avataTV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDChi() {
        return DChi;
    }

    public void setDChi(String DChi) {
        this.DChi = DChi;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }
}
