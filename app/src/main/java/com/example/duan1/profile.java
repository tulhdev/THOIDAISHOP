package com.example.duan1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.squareup.picasso.Picasso;

import com.example.duan1.databinding.ActivityProfileBinding;
import com.example.duan1.fragment.Fragment_TrangChu;

public class profile extends AppCompatActivity {
    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences preferences = getSharedPreferences("DANGNHAPTV", MODE_PRIVATE);
        int mand = preferences.getInt("id", 0);
        String tenDN = preferences.getString("MaTV", "");
        String hoten = preferences.getString("HoTen", "");
        String email = preferences.getString("Email", "");
        String sodienthoai = preferences.getString("SDT", "");
        String diachi = preferences.getString("DChi", "");
        int tien = preferences.getInt("SoTien", 0);
        String loaitaikhoan = preferences.getString("Loai", "");
        String urlAnh = preferences.getString("AvataTV","");
        binding.hiName.setText("Hi, " + hoten);
        binding.txtPMaNguoiDung.setText("Mã tài khoản: " + String.valueOf(mand));
        binding.txtPTenDangNhap.setText("Tên đăng nhập: " + tenDN);
        binding.txtPHoTen.setText("Họ tên: " + hoten);
        binding.txtPEmail.setText("Email: " + email);
        binding.txtPSoDienThoai.setText("Số điện thoại: " + sodienthoai);
        binding.txtPDiaChi.setText("Địa chỉ: " + diachi);
        binding.txtPSoTien.setText("Số tiền hiện có: " + String.valueOf(tien));
        binding.txtPLoaiTaiKhoan.setText("Loại tài khoản: " + loaitaikhoan);
//        load(list.get(position).getAnhnguoidung()).into(holder.binding.imgAnhQlNguoiDung);
        Picasso.get().load(urlAnh).into(binding.imgAvatarProfile);

        binding.imgBack.setOnClickListener(view ->
                finish()

        );

//        binding.btnhoso.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(profile.this,sua_Thong_tin_nguoi_dung.class));
//            }
//        });
    }


}
