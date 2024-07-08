package com.example.duan1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.example.duan1.DAO.DonHangChiTietDao;
import com.example.duan1.Model.DonHangChiTiet;
import com.example.duan1.databinding.ItemConfilmThanhToanBinding;


public class adapter_thanh_toan extends RecyclerView.Adapter<adapter_thanh_toan.ViewHolder> {
    private ArrayList<DonHangChiTiet> list;
    private Context context;
    private DonHangChiTietDao dao;

    public adapter_thanh_toan(ArrayList<DonHangChiTiet> list, Context context) {
        this.list = list;
        this.context = context;
        dao = new DonHangChiTietDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemConfilmThanhToanBinding binding = ItemConfilmThanhToanBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.txtTenSanPham.setText("Tên sản phẩm: " + list.get(position).getTenSanPham());
        holder.binding.txtMaSanPham.setText("Mã sản phẩm: " + String.valueOf(list.get(position).getMaSanPham()));
        holder.binding.txtMaDonHang.setText("Mã đơn hàng: " + String.valueOf(list.get(position).getMaDonHang()));
        holder.binding.txtSoLuong.setText("Số lượng: " + String.valueOf(list.get(position).getSoLuong()));
        holder.binding.txtDonGia.setText("Giá: " + String.valueOf(list.get(position).getDonGia()));
        holder.binding.txtThanhTien.setText("Thành tiền: " + String.valueOf(list.get(position).getThanhTien()));
        Picasso.get().load(list.get(position).getAnhsanpham()).into(holder.binding.imgAnhSp);
        DonHangChiTiet ct=list.get(position);
//        holder.binding.btnthemdanhgia.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
//                DialogDanhGiaBinding dialogDanhGiaBinding = DialogDanhGiaBinding.inflate(layoutInflater);
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setView(dialogDanhGiaBinding.getRoot());
//                AlertDialog dialog = builder.create();
//                dialog.show();
//                dialogDanhGiaBinding.btnthembinhluan.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        String danhgia = dialogDanhGiaBinding.eddanhgia.getText().toString();
//                        String nhanxet = dialogDanhGiaBinding.ednhanxet.getText().toString();
//                        LocalDate currentDate = LocalDate.now();
//                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//                        String ngayHienTai = currentDate.format(formatter);
//                        if (danhgia.isEmpty()) {
//                            dialogDanhGiaBinding.eddanhgia.setError("Vui lòng không để trống");
//                        } else {
//                            dialogDanhGiaBinding.eddanhgia.setError(null);
//                        }
//                        if (nhanxet.isEmpty()) {
//                            dialogDanhGiaBinding.ednhanxet.setError("Vui lòng không để trống");
//                        } else {
//                            dialogDanhGiaBinding.ednhanxet.setError(null);
//                        }
//                        if (dialogDanhGiaBinding.eddanhgia.getError() == null && dialogDanhGiaBinding.ednhanxet.getError() == null) {
//                            boolean check=dao2.addDanhGia(ct.getMaSanPham(), danhgia, nhanxet, ngayHienTai);
//                            if (check) {
//                               ArrayList<DanhGia>list1=new ArrayList<>();
//                                list1 =dao2.getDanhGiaByMaSanPham(ct.getMaSanPham());
//                                Toast.makeText(context, "Thêm  thành công", Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            } else {
//                                Toast.makeText(context, "Thêm không thành công", Toast.LENGTH_SHORT).show();
//                            }
//                        }else {
//                            Toast.makeText(context, "truong lol", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemConfilmThanhToanBinding binding;

        public ViewHolder(ItemConfilmThanhToanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
