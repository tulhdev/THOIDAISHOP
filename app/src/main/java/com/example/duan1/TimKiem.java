package com.example.duan1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;

import com.example.duan1.Adapter.Adapter_SanPham;
import com.example.duan1.DAO.SanPhamDAO;
import com.example.duan1.Model.SanPham;

public class TimKiem extends AppCompatActivity {
    SanPhamDAO spDAO;
    Adapter_SanPham adaptersp;
    RecyclerView rcv_sanpham;
    EditText edttimkiem_tk;
    private ArrayList<SanPham> list = new ArrayList<>();
    private ArrayList<SanPham> filteredList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        rcv_sanpham = findViewById(R.id.rclsanpham);
        edttimkiem_tk = findViewById(R.id.edttimkiem_tk);

        spDAO= new SanPhamDAO(this);
        list = spDAO.selectAllSanPham();
        filteredList.addAll(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_sanpham.setLayoutManager(linearLayoutManager);
        rcv_sanpham.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adaptersp = new Adapter_SanPham(this, list);
        rcv_sanpham.setAdapter(adaptersp);
        // timkiem
        edttimkiem_tk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list.clear();
                for (SanPham sp:filteredList) {
                    if (sp.getTenthuonghieu().contains(s.toString())){
                        list.add(sp);
                    }
                }
                adaptersp.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        adaptersp.setClick(new Adapter_SanPham.click() {
            @Override
            public void click(int pos) {
                SanPham hienthi = list.get(pos);
                Intent intent = new Intent(TimKiem.this, sanphamchitiet.class);
                intent.putExtra("sanphamct",hienthi);
                startActivity(intent);
            }
        });
    }
    // timkiem
    private void filterLoaiSach(String keyword) {
        filteredList.clear();
        for (SanPham sp : list) {
            // Nếu tên loại sách chứa từ khóa tìm kiếm, thêm vào danh sách lọc
            if (sp.getTenthuonghieu().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(sp);
            }
        }
        adaptersp.notifyDataSetChanged();
    }
    }
