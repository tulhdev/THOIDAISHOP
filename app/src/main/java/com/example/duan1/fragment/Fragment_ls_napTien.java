package com.example.duan1.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.example.duan1.Adapter.Adapter_Nap_Tien;
import com.example.duan1.DAO.NapTienDAO;
import com.example.duan1.DAO.ThanhVienDAO;
import com.example.duan1.Model.NapTien;
import com.example.duan1.R;

public class Fragment_ls_napTien extends Fragment {

    public Fragment_ls_napTien() {
        // Required empty public constructor
    }
    NapTienDAO tvDAO;

    RecyclerView rcv_naptien;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ls_nap_tien, container, false);
        rcv_naptien = view.findViewById(R.id.rcv_naptien);
        tvDAO = new NapTienDAO(getContext());
        loadData();
        return view;
    }
    public void loadData(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv_naptien.setLayoutManager(layoutManager);
        ArrayList<NapTien> list = tvDAO.selectAllNapTien();
        Adapter_Nap_Tien adapter = new Adapter_Nap_Tien(getContext(),list);
        rcv_naptien.setAdapter(adapter);
    }
}