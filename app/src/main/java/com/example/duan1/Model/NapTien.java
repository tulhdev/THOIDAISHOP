package com.example.duan1.Model;

public class NapTien {
    private int MaNT ;
    private int sotien;
    private String ngayNT;
    private String TenNXN;
    private  int  trangthai;
    private  int id ;
    private int sotientrcdo;
    private String tenNNT;//ten tai khoan

    public NapTien() {
    }

    public int getSotientrcdo() {
        return sotientrcdo;
    }

    public void setSotientrcdo(int sotientrcdo) {
        this.sotientrcdo = sotientrcdo;
    }

    public NapTien(int sotien, String ngayNT, String tenNXN, int trangthai, int id) {
        this.sotien = sotien;
        this.ngayNT = ngayNT;
        TenNXN = tenNXN;
        this.trangthai = trangthai;
        this.id = id;
    }

    public NapTien(int maNT, int sotien, String ngayNT, String tenNXN, int trangthai, String tenNNT) {
        MaNT = maNT;
        this.sotien = sotien;
        this.ngayNT = ngayNT;
        TenNXN = tenNXN;
        this.trangthai = trangthai;
        this.tenNNT = tenNNT;
    }

    public NapTien(int maNT, int sotien, String ngayNT, String tenNXN, int trangthai, int id, String tenNNT) {
        MaNT = maNT;
        this.sotien = sotien;
        this.ngayNT = ngayNT;
        TenNXN = tenNXN;
        this.trangthai = trangthai;
        this.id = id;
        this.tenNNT = tenNNT;
    }

    public int getMaNT() {
        return MaNT;
    }

    public void setMaNT(int maNT) {
        MaNT = maNT;
    }

    public int getSotien() {
        return sotien;
    }

    public void setSotien(int sotien) {
        this.sotien = sotien;
    }

    public String getNgayNT() {
        return ngayNT;
    }

    public void setNgayNT(String ngayNT) {
        this.ngayNT = ngayNT;
    }

    public String getTenNXN() {
        return TenNXN;
    }

    public void setTenNXN(String tenNXN) {
        TenNXN = tenNXN;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNNT() {
        return tenNNT;
    }

    public void setTenNNT(String tenNNT) {
        this.tenNNT = tenNNT;
    }
}
