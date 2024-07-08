package com.example.duan1.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.duan1.Adapter.Adapter_SanPham;
import com.example.duan1.DAO.KichThuocDAO;
import com.example.duan1.DAO.LoaiSanPhamDAO;
import com.example.duan1.DAO.SanPhamDAO;
import com.example.duan1.DAO.ThuongHieuDAO;
import com.example.duan1.Model.KichThuoc;
import com.example.duan1.Model.LoaiSanPham;
import com.example.duan1.Model.SanPham;
import com.example.duan1.Model.ThuongHieu;
import com.example.duan1.R;
import com.example.duan1.sanphamchitiet;


public class Fragment_GianHang extends Fragment {
    SanPhamDAO spDAO;
    Adapter_SanPham adaptersp;
    RecyclerView rcv_sanpham;
    private ArrayList<SanPham> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__gian_hang, container, false);
        rcv_sanpham = view.findViewById(R.id.rcv_sanpham);
        loaddata();
        adaptersp.setClick(new Adapter_SanPham.click() {
            @Override
            public void click(int pos) {
                SanPham hienthi = list.get(pos);
                Intent intent = new Intent(getContext(), sanphamchitiet.class);
                intent.putExtra("sanphamct", hienthi);
                startActivity(intent);
            }
        });
        // phân quyền
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DANGNHAPTV", Context.MODE_PRIVATE);
        String Loai = sharedPreferences.getString("Loai", "");

        return view;
    }

    private void loaddata() {
        /// setlayout
        spDAO = new SanPhamDAO(getContext());
        list = spDAO.selectAllSanPham();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv_sanpham.setLayoutManager(linearLayoutManager);
        rcv_sanpham.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adaptersp = new Adapter_SanPham(getContext(), list);
        rcv_sanpham.setAdapter(adaptersp);

    }
}
