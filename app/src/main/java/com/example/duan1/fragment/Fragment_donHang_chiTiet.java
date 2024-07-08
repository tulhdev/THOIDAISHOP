package com.example.duan1.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.example.duan1.Adapter.adapter_don_hang_chi_tiet;
import com.example.duan1.DAO.DonHangChiTietDao;
import com.example.duan1.Model.DonHangChiTiet;
import com.example.duan1.R;
import com.example.duan1.databinding.FragmentDonHangChiTietBinding;

public class Fragment_donHang_chiTiet extends Fragment {



    public Fragment_donHang_chiTiet() {
        // Required empty public constructor
    }
    FragmentDonHangChiTietBinding binding;
    private ArrayList<DonHangChiTiet> list = new ArrayList<>();

    private adapter_don_hang_chi_tiet adapterDonHangChiTiet;
    DonHangChiTietDao chiTietDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDonHangChiTietBinding.inflate(inflater, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcvDonHangChiTiet.setLayoutManager(layoutManager);
        chiTietDao = new DonHangChiTietDao(getContext());
        Bundle bundle = getArguments();
        if (bundle != null) {
            int maDonHang = bundle.getInt("maDonHang", 0);
            Log.d("Mã đơn hàng", String.valueOf(maDonHang));
            if (maDonHang != 0) {
                list = chiTietDao.getChiTietDonHangByMaDonHang(maDonHang);
                adapterDonHangChiTiet = new adapter_don_hang_chi_tiet(list, getContext());
                binding.rcvDonHangChiTiet.setAdapter(adapterDonHangChiTiet);

            }
        }

        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_DonHang frgQuanLyDonHang=new Fragment_DonHang();//fragment được chuyển đến sau khi ấn
                FragmentManager fragmentManager=getParentFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,frgQuanLyDonHang);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return binding.getRoot();
    }
}