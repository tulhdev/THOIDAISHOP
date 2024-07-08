package com.example.duan1.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import com.example.duan1.Adapter.Adapter_KichThuoc;
import com.example.duan1.Adapter.Adapter_LoaiSanPham;
import com.example.duan1.DAO.KichThuocDAO;
import com.example.duan1.Model.KichThuoc;
import com.example.duan1.Model.LoaiSanPham;
import com.example.duan1.R;

public class Fragment_kich_thuoc extends Fragment {
    KichThuocDAO ktDAO;
    Adapter_KichThuoc adapterkt;
    RecyclerView rcv_kichthuoc;
    FloatingActionButton fltadd;

    private ArrayList<KichThuoc> list = new ArrayList<>();


    public Fragment_kich_thuoc() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kich_thuoc, container, false);
        // ánh xạ
        rcv_kichthuoc = view.findViewById(R.id.rcv_kichthuoc);
        fltadd = view.findViewById(R.id.fltadd);
        ktDAO = new KichThuocDAO(getContext());
        loadData();

        fltadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAdd();
            }
        });

        return view;
    }

    private void dialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Tạo giao diện của Dialog từ layout XML
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.item_kich_thuoc_add, null);
        builder.setView(dialogView);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputLayout in_ma = dialogView.findViewById(R.id.in_ma);
        TextInputLayout in_anh = dialogView.findViewById(R.id.in_anh);
        TextInputLayout in_size = dialogView.findViewById(R.id.in_size);
        TextInputLayout in_soluong = dialogView.findViewById(R.id.in_soluong);

        TextInputEditText edtma_kt = dialogView.findViewById(R.id.edtma_kt);
        TextInputEditText edtanh_kt = dialogView.findViewById(R.id.edtanh_kt);
        TextInputEditText edtsize_kt = dialogView.findViewById(R.id.edtsize_kt);
        TextInputEditText edtSoluong_kt = dialogView.findViewById(R.id.edtSoluong_kt);

        Button KT_add = dialogView.findViewById(R.id.KT_Add);
        Button KT_cancel = dialogView.findViewById(R.id.KT_Cancel);

        KT_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String makt = edtma_kt.getText().toString();
                String Anhkt = edtanh_kt.getText().toString();
                String size = edtsize_kt.getText().toString();
                String Soluong = edtSoluong_kt.getText().toString();

                // MaKT
                if (makt.isEmpty()) {
                    in_ma.setError("Vui lòng không để trống mã kích thước ");
                    return;
                } else {
                    in_ma.setError(null);
                }
                if (makt.trim().length() < 4) {
                    in_ma.setError("Mã Kích thước phải có ít nhất 4 ký tự");
                    return;
                } else {
                    in_ma.setError(null);
                }
                // Link ảnh
                if (Anhkt.isEmpty()) {
                    in_anh.setError("Vui lòng không để trống link ảnh kích thước");
                    return;
                } else {
                    in_anh.setError(null);
                }
                // Size
                if (size.isEmpty()) {
                    in_size.setError("Vui lòng không để trống Size!");
                    return;
                } else {
                    in_size.setError(null);
                }
                // Số lượng
                if (Soluong.isEmpty()) {
                    in_soluong.setError("Vui lòng không để trống Số lượng!");
                    return;
                } else {
                    in_soluong.setError(null);
                }
                if (Soluong.equals("")) {
                    Toast.makeText(getContext(), "Vui lòng không để trống Số lượng!", Toast.LENGTH_SHORT).show();
                } else {
                    if (ktDAO.insert(makt, Anhkt, size, Soluong)) {
                        loadData();
                        Toast.makeText(getActivity(), "Thêm kích thước thành công!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Thêm kíck thước thất bại!!!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public void loadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv_kichthuoc.setLayoutManager(layoutManager);
        ArrayList<KichThuoc> list = ktDAO.selectAllKichThuoc();
        Adapter_KichThuoc adapter = new Adapter_KichThuoc(getContext(), list);
        rcv_kichthuoc.setAdapter(adapter);
    }
}