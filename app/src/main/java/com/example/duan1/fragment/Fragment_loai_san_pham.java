package com.example.duan1.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import com.example.duan1.Adapter.Adapter_LoaiSanPham;
import com.example.duan1.DAO.LoaiSanPhamDAO;
import com.example.duan1.Model.LoaiSanPham;
import com.example.duan1.R;
public class Fragment_loai_san_pham extends Fragment {

    RecyclerView rcv_loaisanpham;
    FloatingActionButton fltadd;

    LoaiSanPhamDAO dao;
    private EditText tenlsp, avataSp;
    private Button LSP_add, LSP_cancel;
    public Fragment_loai_san_pham() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_san_pham, container, false);

        rcv_loaisanpham = view.findViewById(R.id.rcv_loaisanpham);
        fltadd = view.findViewById(R.id.fltadd);
        dao = new LoaiSanPhamDAO(getContext());
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
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_loaisp, null);

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        tenlsp = view.findViewById(R.id.tenlsp_add);
        avataSp = view.findViewById(R.id.avataSp_add);
        LSP_add = view.findViewById(R.id.LSP_Add);
        LSP_cancel = view.findViewById(R.id.LSP_Cancel);

        LSP_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String urlAvata = avataSp.getText().toString();
                String tlsp = tenlsp.getText().toString();

                if (urlAvata.isEmpty() || tlsp.isEmpty()){
                    Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                }else {
                    if (dao.insert(urlAvata, tlsp)){
                        loadData();
                        Toast.makeText(getActivity(), "Thêm loại sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(getActivity(), "Thêm loại sản phẩm thất bại!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    public void loadData(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv_loaisanpham.setLayoutManager(layoutManager);
        ArrayList<LoaiSanPham> list = dao.getDSLoaiSanPham();
        Adapter_LoaiSanPham adapter = new Adapter_LoaiSanPham(getContext(),list);
        rcv_loaisanpham.setAdapter(adapter);
    }
}