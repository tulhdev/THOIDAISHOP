package com.example.duan1.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.example.duan1.DAO.DonHangChiTietDao;
import com.example.duan1.Model.DonHangChiTiet;
import com.example.duan1.databinding.ItemDonHangChiTietBinding;


public class adapter_don_hang_chi_tiet extends RecyclerView.Adapter<adapter_don_hang_chi_tiet.ViewHolder> {
    private ArrayList<DonHangChiTiet> list;
    private Context context;
    private DonHangChiTietDao dao;
    //private DanhGiaDao dao2;

    public adapter_don_hang_chi_tiet(ArrayList<DonHangChiTiet> list, Context context) {
        this.list = list;
        this.context = context;
        dao = new DonHangChiTietDao(context);
       // dao2=new DanhGiaDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDonHangChiTietBinding binding = ItemDonHangChiTietBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.txtDonGia.setText("Giá: " + String.valueOf(list.get(position).getDonGia()));
        holder.binding.txtmaChiTietDon.setText("Mã chi tiết đơn: " + String.valueOf(list.get(position).getMaChiTietDonHang()));
        holder.binding.txtMaDonHang.setText("Mã đơn hàng: " + String.valueOf(list.get(position).getMaDonHang()));
        holder.binding.txtMaSanPham.setText("Mã sản phẩm: " + String.valueOf(list.get(position).getMaSanPham()));
        holder.binding.txtThanhTien.setText("Thành tiền: " + String.valueOf(list.get(position).getThanhTien()));
        holder.binding.txtSoLuong.setText("Số lượng: " + String.valueOf(list.get(position).getSoLuong()));
        holder.binding.txttensanpham.setText("Tên sản phẩm: " + list.get(position).getTenSanPham());
        Picasso.get().load(list.get(position).getAnhsanpham()).into(holder.binding.imgAnhsp);
        DonHangChiTiet ct = list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemDonHangChiTietBinding binding;

        public ViewHolder(ItemDonHangChiTietBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
