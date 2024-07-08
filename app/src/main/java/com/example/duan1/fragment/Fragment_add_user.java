package com.example.duan1.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duan1.DAO.ThanhVienDAO;
import com.example.duan1.DangKi;
import com.example.duan1.Login;
import com.example.duan1.Model.ThanhVien;
import com.example.duan1.R;

public class Fragment_add_user extends Fragment {
    EditText edtAvata_tnd, edtUser_tnd,edtPass_tnd,edthoten_tnd,edtEmail_tnd,edtsdt_tnd;
    Button btnTHEM;
    ThanhVienDAO tvDAO;
    public Fragment_add_user() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_add_user, container, false);
        edtAvata_tnd = view.findViewById(R.id.edtAvata_tnd);
        edtUser_tnd = view.findViewById(R.id.edtUser_tnd);
        edtPass_tnd = view.findViewById(R.id.edtPass_tnd);
        edthoten_tnd = view.findViewById(R.id.edthoten_tnd);
        edtEmail_tnd = view.findViewById(R.id.edtEmail_tnd);
        edtsdt_tnd = view.findViewById(R.id.edtsdt_tnd);
            btnTHEM =view.findViewById(R.id.btnTHEM);
            tvDAO = new ThanhVienDAO(getContext());



        btnTHEM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                ThanhVien kt = new ThanhVien();
//                int id = kt.getId();
                    String avatatnd =edtAvata_tnd.getText().toString();
                    String ma = edtUser_tnd.getText().toString();
                    String pass = edtPass_tnd.getText().toString();
                    String hten = edthoten_tnd.getText().toString();
                    String email = edtEmail_tnd.getText().toString();
                    String sdt = edtsdt_tnd.getText().toString();
                    String dc = "Hà Nội";
                    String loai ="Nhân Viên";
                    int scoins =0;
                    // Kiểm tra các trường dữ liệu có trống hay không
                    if (avatatnd.trim().length() < 2){
                        Toast.makeText(getContext(), "Nếu chưa có link ảnh vui lòng nhập ít nhất 2 ký tự", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (ma.isEmpty() || pass.isEmpty() || hten.isEmpty() || email.isEmpty() || sdt.isEmpty()) {
                        Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //Số điện thoại
                    if (!isValidPhoneNumber(sdt)){
                        Toast.makeText(getContext(), "Số điện thoại phải có từ 10 số hoặc 12 số", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!isValidEmail(email)){
                        Toast.makeText(getContext(), "E-mail sai định dạng", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ThanhVien kt = new ThanhVien(ma,avatatnd,hten,pass,sdt,email,scoins,dc,loai);
                    if(tvDAO.insert(kt)){
                        Toast.makeText(getContext(), "ĐĂNG KÝ THÀNH CÔNG", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getContext(), "ĐĂNG KÝ THẤT BẠI", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        return view;
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        // Kiểm tra số điện thoại có bắt đầu bằng số 0 và có từ 10 đến 12 chữ số
        return phoneNumber.matches("^0\\d{9,11}$");
    }
    public boolean isValidEmail(String email) {
        // Kiểm tra email có đúng định dạng
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailPattern);
    }
}