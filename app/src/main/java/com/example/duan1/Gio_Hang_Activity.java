package com.example.duan1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.duan1.Adapter.Adapter_GioHang;
import com.example.duan1.Adapter.swipe;
import com.example.duan1.DAO.GioHangDAO;
import com.example.duan1.DAO.SanPhamDAO;
import com.example.duan1.Model.GioHang;
import com.example.duan1.Model.SanPham;
import com.example.duan1.databinding.ActivityGioHangBinding;

public class Gio_Hang_Activity extends AppCompatActivity implements Adapter_GioHang.TotalPriceListener {
    TextView giohangtrong, tongtien;
    private ActivityGioHangBinding binding;
    View gView;
    Toolbar toolbar;
    RecyclerView recycleView;
    Button btnmuahang;
    //    Adapter_GioHang adapter;
    GioHangDAO ghDAO;
    private Adapter_GioHang adapter;
    SanPhamDAO dao;
    /// new
    private ArrayList<GioHang> list = new ArrayList<>();
    private void displayCart(ArrayList<GioHang> cartList) {
        recycleView = findViewById(R.id.recycleviewgiohang);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);

        if (adapter == null) {
            adapter = new Adapter_GioHang(this, cartList);
            recycleView.setAdapter(adapter);

        } else {
            adapter.updateCartList(cartList);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGioHangBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_gio_hang);
        gView = binding.getRoot();
        recycleView = findViewById(R.id.recycleviewgiohang);
        toolbar = findViewById(R.id.toolbar);
        btnmuahang = findViewById(R.id.btnmuahang);
        tongtien = findViewById(R.id.txttongtien);
        adapter = new Adapter_GioHang(this, list);
        recycleView.setAdapter(adapter);
        ghDAO = new GioHangDAO(this);
        ItemTouchHelper sw = new ItemTouchHelper(new swipe(adapter));
        sw.attachToRecyclerView(recycleView);
        adapter.setTotalPriceListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences("DANGNHAPTV", MODE_PRIVATE);
        int mand = sharedPreferences.getInt("id", 0);
//        chiTietDao = new DonHangChiTietDao(this);
//        donHangDao = new DonHangDao(this);
//
        dao = new SanPhamDAO(this);
//        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
//        sharedViewModel.getMasp().observe(getViewLifecycleOwner(), masp -> {
//
//            if (isAdded() && isVisible()) {
//                if (sharedViewModel.getAddToCartClicked().getValue() != null && sharedViewModel.getAddToCartClicked().getValue()) {
//                    updateGioHangByMaSp(masp);
//                    sharedViewModel.setAddToCartClicked(true); // Đặt lại trạng thái
//                }
//            }
//        });
        list = ghDAO.getDanhSachGioHangByMaNguoiDung(mand);
        displayCart(list);

//        binding.btnThanhToan.setOnClickListener(view -> {
//            showDialogThanhToan();
//
//        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onTotalPriceUpdated(int totalAmount) {
        if (binding != null && binding.txttongtien != null) {
            binding.txttongtien.setText(String.valueOf(totalAmount));
            tongtien.setText(String.valueOf(totalAmount));
        }

    }
}
