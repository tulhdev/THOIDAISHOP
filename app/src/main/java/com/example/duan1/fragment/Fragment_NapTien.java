package com.example.duan1.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.example.duan1.Adapter.Adapter_ThanhVien;
import com.example.duan1.Adapter.Adapter_ThuongHieu;
import com.example.duan1.DAO.NapTienDAO;
import com.example.duan1.DAO.ThanhVienDAO;
import com.example.duan1.Model.NapTien;
import com.example.duan1.Model.ThanhVien;
import com.example.duan1.Model.ThuongHieu;
import com.example.duan1.R;

public class Fragment_NapTien extends Fragment {



    public Fragment_NapTien() {
        // Required empty public constructor
    }
    NapTienDAO ntdao;
    Button fltadd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__nap_tien, container, false);
        fltadd = view.findViewById(R.id.fltadd);
        ntdao = new NapTienDAO(getContext());
        fltadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiaLogAddTH();
            }
        });
        return view;
    }
    public void DiaLogAddTH() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_naptien, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        TextInputEditText ed_sotien = view.findViewById(R.id.ed_sotien);
        Button AddTH = view.findViewById(R.id.TH_Add);
        Button CancelTH = view.findViewById(R.id.TH_Canceladd);

        AddTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("DANGNHAPTV", Context.MODE_PRIVATE);
                int id = sharedPreferences.getInt("id", 0);
                int sotin = Integer.parseInt(ed_sotien.getText().toString());
                String TenNXN = " Chua xac nhan";
                LocalDate currentDate = LocalDate.now();
                int TrangThai = 0;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String ngayHienTai = currentDate.format(formatter);
                NapTien nt = new NapTien(sotin, ngayHienTai, TenNXN, TrangThai, id);
                if (ntdao.insert(nt)) {
                    Toast.makeText(getActivity(), "Gui phieu nap thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "Gui phieu nap thất bại!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        CancelTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ed_addAvataTH.setText("");
//                ed_SDT.setText("");
//                ed_TenTH.setText("");
            }
        });
    }

    }



