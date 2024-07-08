package com.example.duan1.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.example.duan1.DAO.GioHangDAO;
import com.example.duan1.DAO.SanPhamDAO;
import com.example.duan1.Model.GioHang;
import com.example.duan1.Model.SanPham;
import com.example.duan1.R;

public class Adapter_GioHang extends RecyclerView.Adapter<Adapter_GioHang.ViewHolder> {
    private Context context;
    private ArrayList<GioHang> List;
    GioHangDAO dao;
    //
    //new
    private TotalPriceListener listener;

    public Adapter_GioHang(Context context, ArrayList<GioHang> List) {
        this.context = context;
        this.List = List;
        dao = new GioHangDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_gio_hang2, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Lấy thông tin từ danh sách gioHangList
        GioHang gioHangItem = List.get(position);

        // Hiển thị thông tin trong View
        holder.item_giohang_tensp.setText(gioHangItem.getTenSanPham());
//        holder.item_giohang_gia.setText(String.valueOf(gioHangItem.getGiaSanPham()));
        holder.item_giohang_soluong.setText(String.valueOf(gioHangItem.getSoLuongMua()));
        Picasso.get().load(gioHangItem.getAvataSP()).into(holder.item_giohang_image);
        Picasso.get().load(List.get(position).getAvataSP()).into(holder.item_giohang_image);
        // Giả sử 'item_giohang_giasp2' là một TextView khác để hiển thị tổng giá
        holder.item_giohang_giasp2.setText(String.valueOf(gioHangItem.getGiaSanPham() * gioHangItem.getSoLuongMua()));
        holder.chkChonSanPham.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                gioHangItem.setSelected(b);
                holder.chkChonSanPham.setChecked(b);
                notifyDataSetChanged();
                updateTotalPrice();

            }
        });

        holder.item_giohang_cong.setOnClickListener(view -> {
            if (gioHangItem.getSoLuongMua() < gioHangItem.getSoLuongspcl()) {
                gioHangItem.setSoLuongMua(gioHangItem.getSoLuongMua() + 1);
                dao.updateGioHang(gioHangItem);
                notifyDataSetChanged();
                updateTotalPrice();
            } else {
                Toast.makeText(context, "Không thể mua thêm, số lượng trong kho đã đạt tối đa", Toast.LENGTH_SHORT).show();
            }
        });
        holder.item_giohang_tru.setOnClickListener(view -> {
            if (gioHangItem.getSoLuongMua() > 1) {
                gioHangItem.setSoLuongMua(gioHangItem.getSoLuongMua() - 1);

                dao.updateGioHang(gioHangItem);
                notifyDataSetChanged();
                updateTotalPrice();
            } else {

                removeItem(gioHangItem);

            }
        });
//        holder.item_giohang_tru.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int soLuong = gioHangItem.getSoLuongMua();
//                if (soLuong > 1) {
//                    soLuong--; // Giảm số lượng đi 1 đơn vị
//                    gioHangItem.setSoLuongMua(soLuong); // Cập nhật số lượng mới cho sản phẩm
//                    holder.item_giohang_soluong.setText(String.valueOf(soLuong)); // Hiển thị số lượng mới
//
//                    // Cập nhật số lượng trong cơ sở dữ liệu
//                    dao.updateQuantity(gioHangItem.getMaSP(), soLuong);
//                    // Thông báo cho RecyclerView cập nhật lại
//                    notifyItemChanged(position);
//
//                }
//            }
//        });
//
//        holder.item_giohang_cong.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int soLuong = gioHangItem.getSoLuongMua();
//                soLuong++; // Tăng số lượng lên 1 đơn vị
//                gioHangItem.setSoLuongMua(soLuong); // Cập nhật số lượng mới cho sản phẩm
//                holder.item_giohang_soluong.setText(String.valueOf(soLuong)); // Hiển thị số lượng mới
//
//                // Cập nhật số lượng trong cơ sở dữ liệu
//                dao.updateQuantity(gioHangItem.getMaSP(), soLuong);
//                // Thông báo cho RecyclerView cập nhật lại
//                notifyItemChanged(position);
//            }
//        });

//        // Trong Adapter_GioHang
//        holder.item_giohang_cong.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int soLuongTrongGioHang = gioHangItem.getSoLuong();
//                int remainingQuantity = getRemainingQuantity(gioHangItem);
//
//                if (soLuongTrongGioHang < remainingQuantity) {
//                    // Nếu số lượng trong giỏ hàng chưa vượt quá tồn kho
//                    soLuongTrongGioHang++;
//                    gioHangItem.setSoLuong(soLuongTrongGioHang);
//                    holder.item_giohang_soluong.setText(String.valueOf(soLuongTrongGioHang));
//
//                    // Cập nhật số lượng trong cơ sở dữ liệu
//                    dao.updateQuantity(gioHangItem.getMaSP(), soLuongTrongGioHang);
//
//                    // Cập nhật lại giá trị remainingQuantity
//                    remainingQuantity = getRemainingQuantity(gioHangItem);
//
//                    // Thông báo cho RecyclerView cập nhật lại
//                    notifyItemChanged(position);
//                } else {
//                    // Nếu số lượng trong giỏ hàng đã vượt quá tồn kho
//                    Toast.makeText(context, "Số lượng sản phẩm đã quá tồn kho", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//// Trong Adapter_GioHang
//        holder.item_giohang_tru.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int soLuongTrongGioHang = gioHangItem.getSoLuong();
//                if (soLuongTrongGioHang > 0) {
//                    soLuongTrongGioHang--;
//                    gioHangItem.setSoLuong(soLuongTrongGioHang);
//                    holder.item_giohang_soluong.setText(String.valueOf(soLuongTrongGioHang));
//
//                    // Cập nhật số lượng trong cơ sở dữ liệu
//                    dao.updateQuantity(gioHangItem.getMaSP(), soLuongTrongGioHang);
//
//                    // Cập nhật lại giá trị remainingQuantity
//                    int remainingQuantity = getRemainingQuantity(gioHangItem);
//
//                    // Thông báo cho RecyclerView cập nhật lại
//                    notifyItemChanged(position);
//                }
//            }
//        });


    }
    private void updateTotalPrice() {
        if (listener != null) {
            int totalAmount = 0;

            for (GioHang gioHang : List) {
                if (gioHang.isSelected()) {
                    totalAmount += gioHang.getSoLuongMua() * gioHang.getGiaSanPham();
                }
            }
            listener.onTotalPriceUpdated(totalAmount);
        }
    }
    public void setTotalPriceListener(TotalPriceListener listener) {
        this.listener = listener;
    }
    public Context getContext() {
        return context;
    }
    public interface TotalPriceListener {
        void onTotalPriceUpdated(int totalAmount);
    }

    public void updateCartList(ArrayList<GioHang> updatedList) {
        List.clear();
        List.addAll(updatedList);
//        this.list = updatedList;
        notifyDataSetChanged();

    }

    private void removeItem(GioHang gioHang) {
        if (dao.deleteGioHang(gioHang)) {
            List.remove(gioHang);

            notifyDataSetChanged();
            updateTotalPrice();
        } else {
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }
    }
    public void removeItem2(int pos) {
        GioHang gioHang1=List.get(pos);
        if (dao.deleteGioHang(gioHang1)) {
            List.remove(gioHang1);

            notifyDataSetChanged();
            updateTotalPrice();
        } else {
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public int getItemCount() {
        return List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item_giohang_image, ImgDeleteGH, item_giohang_tru, item_giohang_cong;
        TextView item_giohang_tensp, item_giohang_gia, item_giohang_soluong, item_giohang_giasp2;
        CheckBox chkChonSanPham;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
//            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_giasp2 = itemView.findViewById(R.id.item_giohang_giasp2);
//            ImgDeleteGH = itemView.findViewById(R.id.ImgDeleteGH);
            item_giohang_tru = itemView.findViewById(R.id.item_giohang_tru);
            item_giohang_cong = itemView.findViewById(R.id.item_giohang_cong);
            chkChonSanPham = itemView.findViewById(R.id.chkChonSanPham);
        }
    }

    // Trong Adapter_GioHang
    private int getRemainingQuantity(SanPham gioHangItem) {
        int soLuongSanPham = gioHangItem.getSoLuong();
        int soLuongTrongGioHang = gioHangItem.getSoLuong();
        return soLuongSanPham - soLuongTrongGioHang;
    }

}
