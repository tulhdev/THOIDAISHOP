package com.example.duan1.Model;

public class KichThuoc {

    private int id;
    private String AvataKT;
    private String MaKT;
    private int Size;
    private int SoLuong;

    public KichThuoc() {
    }

    public KichThuoc(String avataKT, String maKT, int size, int soLuong) {
        AvataKT = avataKT;
        MaKT = maKT;
        Size = size;
        SoLuong = soLuong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaKT() {
        return MaKT;
    }

    public void setMaKT(String maKT) {
        MaKT = maKT;
    }

    public String getAvataKT() {
        return AvataKT;
    }

    public void setAvataKT(String avataKT) {
        AvataKT = avataKT;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }
}