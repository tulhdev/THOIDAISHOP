package com.example.duan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import com.example.duan1.DAO.ThanhVienDAO;


import com.example.duan1.databinding.ActivityLayoutBinding;
import com.example.duan1.fragment.Fragment_DonHang;
import com.example.duan1.fragment.Fragment_GianHang;
import com.example.duan1.fragment.Fragment_NapTien;
import com.example.duan1.fragment.Fragment_Thongtin;
import com.example.duan1.fragment.Fragment_TrangChu;
import com.example.duan1.fragment.Fragment_add_user;
import com.example.duan1.fragment.Fragment_doanh_thu;
import com.example.duan1.fragment.Fragment_doi_mk;
import com.example.duan1.fragment.Fragment_gioHang;
import com.example.duan1.fragment.Fragment_kich_thuoc;
import com.example.duan1.fragment.Fragment_loai_san_pham;
import com.example.duan1.fragment.Fragment_ls_napTien;
import com.example.duan1.fragment.Fragment_san_pham;
import com.example.duan1.fragment.Fragment_thanh_vien;
import com.example.duan1.fragment.Fragment_thuong_hieu;

public class Layout extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;
    BottomNavigationView navigationView2;
    Context context = this;
    ThanhVienDAO dao;
    private ActivityLayoutBinding binding;
    //    Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        binding = ActivityLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        frameLayout = findViewById(R.id.frameLayout);
        navigationView = findViewById(R.id.navigationView);
        navigationView2 = findViewById(R.id.nav_bottom);
        View view = navigationView.getHeaderView(0);
        TextView txtloaitv = view.findViewById(R.id.txtloaitv_hd);
        TextView txtemailtv = view.findViewById(R.id.txtemailtv_hd);
        TextView txthotentv_hd = view.findViewById(R.id.txthotentv_hd);
        ImageView logo_user = view.findViewById(R.id.logo_user);
        dao = new ThanhVienDAO(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.syncState();
        // set drawer toggle
        handleBottomNavigationItemSelected();
        // Thêm mã để hiển thị Fragment_san_pham khi ứng dụng được khởi chạy
        Fragment_san_pham frgSP = new Fragment_san_pham();
        relaceFrg(frgSP);
        toolbar.setTitle("Quản lý sản phẩm");
        if (savedInstanceState == null) {
            relaceFrg(new Fragment_TrangChu());
            setTitle("Trang chủ");
        }
        binding.btnProFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout.this, profile.class);
                startActivity(intent);

            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.SanPham) {
                    Fragment_san_pham frgPM = new Fragment_san_pham();
                    relaceFrg(frgPM);
                    toolbar.setTitle("Quản lý sản phẩm");
                } else if (item.getItemId() == R.id.trangchu) {
                    Fragment_TrangChu ftc = new Fragment_TrangChu();
                    relaceFrg(ftc);
                    toolbar.setTitle("Trang chủ");
                } else if (item.getItemId() == R.id.LoaiSanPham) {
                    Fragment_loai_san_pham frgLS = new Fragment_loai_san_pham();
                    relaceFrg(frgLS);
                    toolbar.setTitle("Quản lý loại sản phẩm");
                } else if (item.getItemId() == R.id.KichThuoc) {
                    Fragment_kich_thuoc frgTV = new Fragment_kich_thuoc();
                    relaceFrg(frgTV);
                    toolbar.setTitle("Quản lý kích thước");
                } else if (item.getItemId() == R.id.ThuongHieu) {
                    Fragment_thuong_hieu frgT = new Fragment_thuong_hieu();
                    relaceFrg(frgT);
                    toolbar.setTitle("Quản lý thương hiệu");
                } else if (item.getItemId() == R.id.ThanhVien) {
                    Fragment_thanh_vien frgT = new Fragment_thanh_vien();
                    relaceFrg(frgT);
                    toolbar.setTitle("Quản lý thành viên");
                }else if (item.getItemId() == R.id.HoaDon){
                    Fragment_DonHang frgT = new Fragment_DonHang();
                    relaceFrg(frgT);
                    toolbar.setTitle("Quản lý hóa đơn");
                } else if (item.getItemId() == R.id.menuDT) {
                    Fragment_doanh_thu frgDT = new Fragment_doanh_thu();
                    relaceFrg(frgDT);
                    toolbar.setTitle("Quản lý doanh thu");
                } else if (item.getItemId() == R.id.menuTND) {
                    Fragment_add_user frgTND = new Fragment_add_user();
                    relaceFrg(frgTND);
                    toolbar.setTitle("Thêm nhân viên");
                } else if (item.getItemId() == R.id.menuthongtin) {
                    Fragment_Thongtin frgTND = new Fragment_Thongtin();
                    relaceFrg(frgTND);
                    toolbar.setTitle("Thông tin");
                }else if (item.getItemId() == R.id.menuDMK) {
                    Fragment_doi_mk frgDMK = new Fragment_doi_mk();
                    relaceFrg(frgDMK);
                    toolbar.setTitle("Đổi mật khẩu");
                } else if (item.getItemId() == R.id.lsnaptien) {
                    Fragment_ls_napTien frgDMK = new Fragment_ls_napTien();
                    relaceFrg(frgDMK);
                    toolbar.setTitle("Lich Su Nap Tien");
                } else if (item.getItemId() == R.id.menuDX) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Đăng Xuất");
                    builder.setMessage("Bạn chắc chắn muốn đăng xuất chứ!");
                    builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(Layout.this, Login.class));
                            finish();
                        }
                    });
                    builder.setNegativeButton("Hủy", null);
                    builder.create().show();
                }
                drawerLayout.closeDrawer(navigationView);
                return false;
            }
        });
        // phân quyền
        SharedPreferences sharedPreferences = getSharedPreferences("DANGNHAPTV", MODE_PRIVATE);
        String Loai = sharedPreferences.getString("Loai", "");
        if (Loai.equalsIgnoreCase("admin")) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.menuTND).setVisible(true);
            menu.findItem(R.id.menuthongtin).setVisible(false);
        }
        if (Loai.equalsIgnoreCase("Nhân Viên")) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.menuTND).setVisible(false);
            menu.findItem(R.id.menuthongtin).setVisible(false);


        }
        if (Loai.equalsIgnoreCase("Khách Hàng")) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.LoaiSanPham).setVisible(false);
            menu.findItem(R.id.SanPham).setVisible(false);
            menu.findItem(R.id.KichThuoc).setVisible(false);
            menu.findItem(R.id.ThanhVien).setVisible(false);
            menu.findItem(R.id.menuDT).setVisible(false);
            menu.findItem(R.id.menuDT).setVisible(false);
            menu.findItem(R.id.ThuongHieu).setVisible(false);
            menu.findItem(R.id.menuTND).setVisible(false);

        }
        int sotien = sharedPreferences.getInt("SoTien", 0);
        String urlAnh = sharedPreferences.getString("AvataTV", "");
        Picasso.get().load(urlAnh).into(binding.btnProFile);
        binding.txtSoTien.setText(String.valueOf(sotien));
        String loai = sharedPreferences.getString("Loai", "");
        txtloaitv.setText(loai);
        String email = sharedPreferences.getString("Email", "");
        txtemailtv.setText(email);
        String Hoten = sharedPreferences.getString("HoTen", "");
        txthotentv_hd.setText(Hoten);
        String avt = sharedPreferences.getString("AvataTV", "");
        Picasso.get().load(avt).into(logo_user);

    }

    public void relaceFrg(Fragment frg) {
        FragmentManager fg = getSupportFragmentManager();
        fg.beginTransaction().replace(R.id.frameLayout, frg).commit();
    }

    private void handleBottomNavigationItemSelected() {

        navigationView2.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_bot_giohang) {
                Fragment_gioHang frgPM = new Fragment_gioHang();
                relaceFrg(frgPM);
            } else if (item.getItemId() == R.id.nav_bot_home) {
                Fragment_TrangChu frgPM = new Fragment_TrangChu();
                relaceFrg(frgPM);

            } else if (item.getItemId() == R.id.nav_bot_sanpham) {
                Fragment_GianHang frgPM = new Fragment_GianHang();
                relaceFrg(frgPM);
            }  else if (item.getItemId() == R.id.nav_bot_naptien) {
                Fragment_NapTien frgPM = new Fragment_NapTien();
                relaceFrg(frgPM);
            }
            getSupportActionBar().setTitle(item.getTitle());
            return true;
        });
    }
}