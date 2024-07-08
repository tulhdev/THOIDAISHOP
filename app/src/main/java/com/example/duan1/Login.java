package com.example.duan1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.example.duan1.DAO.ThanhVienDAO;
import com.example.duan1.Model.ThanhVien;
//import com.example.duan1.util.SendMail;

public class Login extends AppCompatActivity {
    TextInputEditText edtUser,edtPass;
    TextInputLayout in_User, in_Pass;
    AppCompatButton btnLOGIN;
    ThanhVienDAO tvdao;
    TextView txtDangki_dn, txtQuenMk;
//    private SendMail sendMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //anh xa
        txtDangki_dn = findViewById(R.id.txtDangki_dn);
        txtQuenMk = findViewById(R.id.txtQuenMk);
        in_User = findViewById(R.id.in_User);
        in_Pass = findViewById(R.id.in_Pass);
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnLOGIN = findViewById(R.id.btnLOGIN);
        CheckBox chkNho = findViewById(R.id.chkNho);
        // hiên thi tài khoản mật khẩu đã nhớ lêm edt
        SharedPreferences sharedPreferences = getSharedPreferences("DANGNHAPTV", MODE_PRIVATE);
        String saveduser = sharedPreferences.getString("MaTV", "");
        String savedPassword = sharedPreferences.getString("MatKhau", "");
        chkNho.setChecked(true);
        if (!savedPassword.isEmpty()) {
            edtUser.setText(saveduser);
            edtPass.setText(savedPassword);
        }
        //xử lí nút đăng nhập
        tvdao = new ThanhVienDAO(this);
//        sendMail = new SendMail();
        txtDangki_dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,DangKi.class);
                startActivity(intent);
                Toast.makeText(Login.this, "Đăng ký", Toast.LENGTH_SHORT).show();

            }
        });
        btnLOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(Login.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tvdao.checklogin(user,pass)){
                    /// nhớ tài khoan mật khẩu
                    if (chkNho.isChecked()==true) {
                        SharedPreferences sharedPreferences = getSharedPreferences("DANGNHAPTV", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("MaTV", user);
                        editor.putString("MatKhau", pass); // Lưu mật khẩu vào SharedPreferences
                        editor.apply();

                    }
                    Intent intent = new Intent(Login.this,Layout.class);
                    startActivity(intent);
                    Toast.makeText(Login.this, "Đăng nhập Thành công", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Login.this, "Dăng Nhập Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        txtQuenMk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDialogForgot();
//            }
//        });
    }

//    private void showDialogForgot(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.dialog_forgot, null);
//        builder.setView(view);
//
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.setCancelable(false);
//        alertDialog.show();
//
//        //Ánh xạ
//        EditText edtEmail = view.findViewById(R.id.edtEmail);
//        Button btnSend = view.findViewById(R.id.btnSend);
//        Button btnCancel = view.findViewById(R.id.btnCancel);
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//            }
//        });
//
//        btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String email = edtEmail.getText().toString();
//                String matkhau = tvdao.ForgotPassword(email);
////                Toast.makeText(Login.this, matkhau, Toast.LENGTH_SHORT).show();
//                if (matkhau.equals("")){
//                    Toast.makeText(Login.this, "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show();
//                }else {
//                    sendMail.Send(Login.this, email, "Muốn lấy lại mật khẩu không", "Mật khẩu của bạn không phải của tôi là: " + matkhau);
//                    alertDialog.dismiss();
//                }
//            }
//        });
//    }

}