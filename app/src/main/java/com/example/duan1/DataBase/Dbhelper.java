package com.example.duan1.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "THOIDAISHOP";
    private static final int DB_VERSION= 53;

    public Dbhelper(Context context) {
        super(context, DB_NAME,null , DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ThanhVien(\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    MaTV     TEXT NOT NULL,\n" +
                "    AvataTV  TEXT NOT NULL,\n" +
                "    HoTen    TEXT NOT NULL,\n" +
                "    MatKhau  TEXT NOT NULL,\n" +
                "    SDT      TEXT NOT NULL,\n" +
                "    Email    TEXT NOT NULL,\n" +
                "    DChi     TEXT NOT NULL,\n" +
                "    SoTien   INTEGER NOT NULL,\n" +
                "    Loai     TEXT NOT NULL\n" +
                ");\n");
        db.execSQL("CREATE TABLE ThuongHieu(\n" +
                "    MaTH    INTEGER PRIMARY KEY  AUTOINCREMENT,\n" +
                "    Anh     TEXT NOT NULL,\n" +
                "    SDT     TEXT NOT NULL,\n" +
                "    TenTH   TEXT NOT NULL\n" +
                ");\n");
        db.execSQL("CREATE TABLE LoaiSanPham(\n" +
                "    MaLSP    INTEGER PRIMARY KEY  AUTOINCREMENT,\n" +
                "    Avata    TEXT NOT NULL,\n" +
                "    TenLSP   TEXT NOT NULL\n" +
                ");\n");
        db.execSQL("CREATE TABLE KichThuoc(\n" +
                "    id       INTEGER PRIMARY KEY  AUTOINCREMENT,\n" +
                "    AvataKT  TEXT NOT NULL,\n" +
                "    MaKT     TEXT NOT NULL,\n" +
                "    Size     INTEGER NOT NULL,\n" +
                "    SoLuong  INTEGER NOT NULL\n" +
                ");\n");
        db.execSQL("CREATE TABLE GioHang(\n" +
                "    MAGH       INTEGER PRIMARY KEY  AUTOINCREMENT,\n" +
                "    id        INTEGER REFERENCES ThanhVien (id), \n" +
                "    MaSP      INTEGER REFERENCES SanPham (MaSP), \n" +
                "    SoLuong  INTEGER NOT NULL\n" +
                ");\n");
        db.execSQL("CREATE TABLE DONHANG(\n" +
                "    madonhang       INTEGER PRIMARY KEY  AUTOINCREMENT,\n" +
                "    id               INTEGER REFERENCES ThanhVien (id), \n" +
                "    ngaydathang      TEXT NOT NULL, \n" +
                "    tongtien          INTEGER NOT NULL,\n" +
                "    trangthai          TEXT NOT NULL\n" +
                ");\n");
        db.execSQL("CREATE TABLE CHITIETDONHANG(\n" +
                "    machitietdonhang   INTEGER PRIMARY KEY  AUTOINCREMENT,\n" +
                "    madonhang          INTEGER REFERENCES DONHANG (madonhang), \n" +
                "    MaSP               INTEGER REFERENCES SanPham (MaSP), \n" +
                "    soluong            INTEGER NOT NULL,\n" +
                "    dongia             INTEGER NOT NULL,\n" +
                "    thanhtien          INTEGER NOT NULL\n" +
                ");\n");
        db.execSQL("CREATE TABLE SanPham(\n" +
                "    MaSP     INTEGER PRIMARY KEY  AUTOINCREMENT,\n" +
                "    AvataSP  TEXT NOT NULL,\n" +
                "    TenSP    TEXT NOT NULL,\n" +
                "    Gia      INTEGER NOT NULL,\n" +
                "    SoLuong  INTEGER NOT NULL,\n" +
                "    id       INTEGER REFERENCES KichThuoc (id), \n" +
                "    MaTH     INTEGER REFERENCES ThuongHieu (MaTH), \n" +
                "    MaLSP    INTEGER REFERENCES LoaiSanPham (MaLSP) \n" +
                ");\n");
        db.execSQL("CREATE TABLE NAPTIEN(\n" +
                "    MaNT     INTEGER PRIMARY KEY  AUTOINCREMENT,\n" +
                "    SoTien      INTEGER NOT NULL,\n" +
                "    ngayNT      TEXT NOT NULL, \n" +
                "    TenNXN    TEXT NOT NULL,\n" +
                "    TrangThai    INTEGER NOT NULL,\n" +
                "    id        INTEGER REFERENCES ThanhVien (id)\n" +
                ");\n");


        //INSERT
        db.execSQL("INSERT INTO  ThanhVien VALUES(1,'admin', 'https://i.pinimg.com/736x/26/80/b2/2680b245cf06bcd901518a53a02e6c9a.jpg','Lê Hoàng Tú','admin',0982322079,'Lehoangtu56@gmail.com','hà nội',10000000,'ADMIN')," +
                "(2,'Giangvu', 'https://i.pinimg.com/736x/66/d1/4a/66d14aec6cd11d59264dd58525fdb449.jpg','Vũ Hoàng Giang','2042044',0763754415,'Vuhoanggiang2042k4@gmail.com','Thái bình',10000000,'ADMIN')," +
                "(3,'nhanvien', 'https://alolimo.com/wp-content/uploads/2021/05/ga-da-lat-7.jpg','Nguyễn Văn A','nhanvien',0982322079,'Nhanvien56@gmail.com','hà nội',100000,'Nhân Viên')," +
                "(4,'khachhang', 'https://baoduyenbabyhouse.com/wp-content/uploads/2022/01/cach-tao-dang-khi-chup-anh-25.jpg','Sùng A Pháo','khachhang',0982322079,'Khachhang56@gmail.com','hà nội',1000000,'Khách Hàng')");
        db.execSQL("INSERT INTO  ThuongHieu VALUES(1, 'https://i.pinimg.com/736x/62/b0/13/62b0137789418bf9a4c66b31dd23cdd7.jpg',0982322079, 'Nike')" +
                ", (2, 'https://i.pinimg.com/736x/37/ff/8b/37ff8bd1f97eb08224129ef9ab5ae2d6.jpg',0868761723, 'Adidas'), " +
                "(3, 'https://i.pinimg.com/736x/81/db/6c/81db6c6aa703368c07733e03584b8309.jpg',0734423235, 'Converse')");
       db.execSQL("INSERT INTO  LoaiSanPham VALUES(1, 'https://salt.tikicdn.com/ts/tmp/3e/00/2d/84c50775b583026c21edd0c4aad35dbc.jpg', 'Giày thể thao')," +
               " (2, 'https://zocker.vn/pic/Product/Zocker-inspire-pro-xanh-chuoi_8274_HasThumb.webp', 'Giày đá bóng'), " +
               "(3, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTWF-eud_KXyMaPwB9dEbokTPmza7QBeVClRQ&usqp=CAU', 'Giày leo núi')");
      db.execSQL("INSERT INTO  KichThuoc VALUES(1,'https://laforce.vn/wp-content/uploads/2022/04/bang-size-giay.jpg','CS39', 39, 3)," +
              " (2,'https://file.hstatic.net/200000382351/file/bang_size_giay-01_e688b8db320547e99a8aefd54da4aa41_grande.jpg', 'AD40',40, 6), " +
              "(3,'https://4men.com.vn/images/thumbs/2017/08/cach-do-size-giay-nam-chuan-100-news-234.jpg','NK41', 41, 8)");
      db.execSQL("INSERT INTO  SanPham VALUES(0,'https://shopvnb.com//uploads/san_pham/giay-cau-long-kumpoo-kh-e55-trang-chinh-hang-1.webp','Giày Kumpoo', 150000,3,2,1,3)," +
              "(1,'https://cdn.shopify.com/s/files/1/0456/5070/6581/files/cach-phan-biet-giay-sneaker-chinh-hang_600x600.jpg?v=1663556399','Giày Sneaker', 200000,3,3,2,1)," +
              "(2,'https://i.pinimg.com/736x/97/1c/34/971c349ce0121fd47b3b1bfaa6f6d4fc.jpg','Giày Superstar', 356000,3,1,3,1)," +
              "(3,'https://i.pinimg.com/736x/5c/d1/77/5cd1778d4e4fc2a4a945f33c601f1f53.jpg','Giày Balance 574', 278000,3,1,2,2)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            // Drop các bảng cũ (nếu tang version)
            db.execSQL("DROP TABLE IF EXISTS ThanhVien");
            db.execSQL("DROP TABLE IF EXISTS ThuongHieu");
            db.execSQL("DROP TABLE IF EXISTS LoaiSanPham");
            db.execSQL("DROP TABLE IF EXISTS KichThuoc");
            db.execSQL("DROP TABLE IF EXISTS SanPham");
            db.execSQL("DROP TABLE IF EXISTS DONHANG");
            db.execSQL("DROP TABLE IF EXISTS CHITIETDONHANG");
            db.execSQL("DROP TABLE IF EXISTS GioHang");
            db.execSQL("DROP TABLE IF EXISTS NAPTIEN");
            // tạo bảng mới
            onCreate(db);
        }
    }
}
