package com.example.duan1.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.example.duan1.DAO.ThanhVienDAO;
import com.example.duan1.Login;
import com.example.duan1.R;

public class Fragment_doi_mk extends Fragment {

    TextInputLayout in_CurrentPassword, in_NewPass, in_Repass;
    TextInputEditText ed_txtCurrentPassword, ed_txtNewPass, ed_txtRepass;
    Button btnSaveMK, btnCancelSaveMK;
    private Context context ;
    public Fragment_doi_mk() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doi_mk, container, false);
        in_CurrentPassword = view.findViewById(R.id.in_Pass);
        in_NewPass = view.findViewById(R.id.in_NewPass);
        in_Repass = view.findViewById(R.id.in_NlPass);
        ed_txtCurrentPassword = view.findViewById(R.id.edtPass);
        ed_txtNewPass = view.findViewById(R.id.edtNewPass);
        ed_txtRepass = view.findViewById(R.id.edt_NlPass);
        btnSaveMK = view.findViewById(R.id.btnDoimk);

        btnSaveMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoiMK();
            }
        });

        return view;
    }

    public void DoiMK() {
        String OldPass = ed_txtCurrentPassword.getText().toString();
        String newPass = ed_txtNewPass.getText().toString();
        String rePass = ed_txtRepass.getText().toString();

        if (OldPass.isEmpty() || newPass.isEmpty() || rePass.isEmpty()) {
            if (OldPass.isEmpty()) {
                in_CurrentPassword.setError("Vui lòng không để trống Mật khẩu cũ");
            } else {
                in_CurrentPassword.setError(null);
            }
            if (newPass.isEmpty()) {
                in_NewPass.setError("Vui lòng không để trống Mật khẩu mới");
            } else {
                in_NewPass.setError(null);
            }
            if (rePass.isEmpty()) {
                in_Repass.setError("Vui lòng không để trống Nhập lại mật khẩu");
            } else {
                in_Repass.setError(null);
            }
            return;
        }

        // Kiểm tra xem mật khẩu mới có ít nhất 5 ký tự
        if (newPass.length() < 5) {
            in_NewPass.setError("Mật khẩu mới phải có ít nhất 5 ký tự");
            return;
        }

        if (newPass.equals(rePass)) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("DANGNHAPTV", getContext().MODE_PRIVATE);
            String matt = sharedPreferences.getString("MaTV","");
            String mk = sharedPreferences.getString("MatKhau","");

            //Cập nhật lại
            ThanhVienDAO tvDao = new ThanhVienDAO(getContext());
            boolean check = tvDao.updateMK(matt, OldPass, newPass);
            if (OldPass.equals(mk)) {
                if (check) {
                    Toast.makeText(getContext(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), Login.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Đổi mật khẩu thất bại!!!", Toast.LENGTH_SHORT).show();
                }
            } else {
                in_CurrentPassword.setError("Mật khẩu hiện tại không đúng");
            }
        } else {
            in_NewPass.setError("Mật khẩu không khớp");
            in_Repass.setError("Mật khẩu không khớp");
        }
    }


}