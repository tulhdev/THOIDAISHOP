package com.example.duan1.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import com.example.duan1.Adapter.adapter_don_hang;
import com.example.duan1.DAO.DonHangChiTietDao;
import com.example.duan1.DAO.DonHangDao;
import com.example.duan1.Model.DonHang;
import com.example.duan1.R;
import com.example.duan1.databinding.FragmentDonHangBinding;


public class Fragment_DonHang extends Fragment {


    public Fragment_DonHang() {
        // Required empty public constructor
    }

    FragmentDonHangBinding binding;
    private ArrayList<DonHang> list = new ArrayList<>();
    private DonHangDao dao;
    private adapter_don_hang adapterDonHang;
    DonHangChiTietDao chiTietDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDonHangBinding.inflate(inflater, container, false);
        dao = new DonHangDao(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcvDonHang.setLayoutManager(layoutManager);
        list = dao.getDsDonHang();
        // Đảo ngược danh sách đơn hàng
        Collections.reverse(list);
        adapterDonHang = new adapter_don_hang(list, getContext());
        binding.rcvDonHang.setAdapter(adapterDonHang);
        chiTietDao = new DonHangChiTietDao(getContext());
        adapterDonHang.setOnItemClick(new adapter_don_hang.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                DonHang donHang = list.get(position);
                int maDonHang = donHang.getMaDonHang();

                Bundle bundle = new Bundle();
                bundle.putInt("maDonHang", maDonHang);
                Fragment_donHang_chiTiet frgDonHangChiTiet = new Fragment_donHang_chiTiet();
                frgDonHangChiTiet.setArguments(bundle);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, frgDonHangChiTiet);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return binding.getRoot();
    }
}