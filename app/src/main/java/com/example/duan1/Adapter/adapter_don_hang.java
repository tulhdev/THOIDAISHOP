package com.example.duan1.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.duan1.DAO.DonHangDao;
import com.example.duan1.Model.DonHang;
import com.example.duan1.Model.SanPham;
import com.example.duan1.Model.ThanhVien;
import com.example.duan1.databinding.DialogUpdateTrangThaiDonhangBinding;
import com.example.duan1.databinding.DialogXoaDonHangBinding;
import com.example.duan1.databinding.ItemQlDonHangBinding;
import com.example.duan1.R;

public class adapter_don_hang extends RecyclerView.Adapter<adapter_don_hang.Viewholder> {
    protected ArrayList<DonHang> list;
    protected ArrayList<SanPham> listsp;
    protected DonHangDao dao;
    private Context context;

    public adapter_don_hang(ArrayList<DonHang> list, Context context) {
        this.list = list;
        this.context = context;
        dao = new DonHangDao(context);
    }

    public interface OnItemClick {
        void onItemClick(int position);
    }

    private OnItemClick mListener;

    public void setOnItemClick(OnItemClick listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemQlDonHangBinding binding = ItemQlDonHangBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        DonHang donHang = list.get(position);

        SharedPreferences sharedPreferences = context.getSharedPreferences("DANGNHAPTV", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("id",0);
        String loai =sharedPreferences.getString("Loai","");
        if (loai.equalsIgnoreCase("admin")){
            holder.binding.txtMdonhang.setText("Mã đơn hàng: " + String.valueOf(donHang.getMaDonHang()));
            holder.binding.txtMnguoidung.setText("Mã người dung: " + String.valueOf(donHang.getMaTaiKhoan()));
            holder.binding.txtDHTennguoidung.setText("Tên người dùng: " + donHang.getTenTaiKhoan());
            holder.binding.txtNgayDat.setText("Ngày đặt hàng: " + donHang.getNgayDatHang());
            holder.binding.txtTrangThai.setText("Trạng thái: " + donHang.getTrangthai());
            holder.binding.txtTongTien.setText("Tổng tiền: " + String.valueOf(donHang.getTongTien()));
        }
        if (loai.equalsIgnoreCase("Nhân Viên")){
            holder.binding.txtMdonhang.setText("Mã đơn hàng: " + String.valueOf(donHang.getMaDonHang()));
            holder.binding.txtMnguoidung.setText("Mã người dung: " + String.valueOf(donHang.getMaTaiKhoan()));
            holder.binding.txtDHTennguoidung.setText("Tên người dùng: " + donHang.getTenTaiKhoan());
            holder.binding.txtNgayDat.setText("Ngày đặt hàng: " + donHang.getNgayDatHang());
            holder.binding.txtTrangThai.setText("Trạng thái: " + donHang.getTrangthai());
            holder.binding.txtTongTien.setText("Tổng tiền: " + String.valueOf(donHang.getTongTien()));
        }
        if (loai.equalsIgnoreCase("Khách Hàng")){
            if(id==donHang.getMaTaiKhoan()){
                holder.binding.txtMdonhang.setText("Mã đơn hàng: " + String.valueOf(donHang.getMaDonHang()));
                holder.binding.txtMnguoidung.setText("Mã người dung: " + String.valueOf(donHang.getMaTaiKhoan()));
                holder.binding.txtDHTennguoidung.setText("Tên người dùng: " + donHang.getTenTaiKhoan());
                holder.binding.txtNgayDat.setText("Ngày đặt hàng: " + donHang.getNgayDatHang());
                holder.binding.txtTrangThai.setText("Trạng thái: " + donHang.getTrangthai());
                holder.binding.txtTongTien.setText("Tổng tiền: " + String.valueOf(donHang.getTongTien()));

            }else{
                holder.itemView.setVisibility(View.GONE);
            }
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(holder.getAdapterPosition());

                }
            }
        });
        holder.binding.btnchinhsuaTrangThai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialogsua(donHang);
            }
        });

        holder.binding.btnXoaDonHang.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            DialogXoaDonHangBinding dialogXoaDonHangBinding = DialogXoaDonHangBinding.inflate(inflater);
            builder.setView(dialogXoaDonHangBinding.getRoot());

            Dialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.nen_dialog_doan);
            dialog.show();
            dialogXoaDonHangBinding.btnOutXoaDonHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialog.dismiss();
                }
            });
            dialogXoaDonHangBinding.btnConfilmXoaDonHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int check = dao.xoaDonHang(list.get(holder.getAdapterPosition()).getMaDonHang());
                    switch (check) {
                        case 1:
                            list.clear();
                            list = dao.getDsDonHang();
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công Đơn hàng", Toast.LENGTH_SHORT).show();
                            break;
                        case 0:
                            Toast.makeText(context, "Xóa không thành công Đơn hàng", Toast.LENGTH_SHORT).show();
                            break;
                        case -1:
                            Toast.makeText(context, "Không xóa được Đơn hàng này vì đang còn tồn tại trong chi tiết hóa đơn", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ItemQlDonHangBinding binding;

        public Viewholder(ItemQlDonHangBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void opendialogsua(DonHang tv){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_trang_thai_donhang, null);

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        EditText tt = view.findViewById(R.id.txt_trang_thai);
        Button btnHUy =  view.findViewById(R.id.btnhuy_trangthai);
        Button btnUPdate =  view.findViewById(R.id.btnxacnhan_trangthai);
        btnUPdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int madh = tv.getMaDonHang();
                String trangt= tt.getText().toString();
                    tv.setMaDonHang(madh);
                    tv.setTrangthai(trangt);
                    if (dao.updateDonHang(tv)){
                        list.clear();
                        list.addAll(dao.getDsDonHang());
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Update thành viên thành công!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Update thành viên thất bại!!!", Toast.LENGTH_SHORT).show();
                    }
                }

        });
        btnHUy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ed_updateMaTV.setText("");
//                ed_updateAvata.setText("");
//                ed_updateName.setText("");
//                ed_updateMk.setText("");
//                ed_updateSDT.setText("");
//                ed_updateEmail.setText("");
//                ed_updateDiachi.setText("");
            }
        });
    }
}
