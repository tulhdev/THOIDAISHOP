package com.example.duan1.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import com.example.duan1.DAO.LoaiSanPhamDAO;
import com.example.duan1.DAO.ThuongHieuDAO;
import com.example.duan1.Model.KichThuoc;
import com.example.duan1.Model.LoaiSanPham;
import com.example.duan1.R;

public class Adapter_LoaiSanPham extends RecyclerView.Adapter<Adapter_LoaiSanPham.ViewHolder> {
    private Context context;
    private ArrayList<LoaiSanPham> list;
    LoaiSanPhamDAO dao;
    private EditText avataLsp, edt_updateTenLoaisp;
    Button LSP_Update, LSP_Cancel;

    public Adapter_LoaiSanPham(Context context, ArrayList<LoaiSanPham> list) {
        this.context = context;
        this.list = list;
        dao = new LoaiSanPhamDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loai_san_pham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiSanPham lsp = list.get(position);
        holder.MaLSP.setText(String.valueOf(list.get(position).getMaLSP()));
        holder.TenLSP.setText(list.get(position).getTenLSP());
        // Load ảnh từ đường dẫn và hiển thị trong ImageView
//        Glide.with(context).load(list.get(position).getAvata()).into(holder.Avata);
        Picasso.get().load(lsp.getAvata()).into(holder.Avata);
//        Picasso.get().load(lsp.getAvata()).placeholder(R.drawable.san_pham).into(holder.Avata);
//        Picasso.get().load(list.get(position).getAvata()).into(holder.Avata);


        holder.LSP_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Warning!!!");
                builder.setMessage("Bạn có chắc là muốn xóa loại sản phẩm này chứ!!!");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoaiSanPhamDAO dao = new LoaiSanPhamDAO(context);
                        int check = dao.delete(list.get(holder.getAdapterPosition()).getMaLSP());
                        switch (check) {
                            case 1:
                                list.clear();
                                list = dao.getDSLoaiSanPham();
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa loại sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Không thể xóa vì đang có loại sản phẩm thuộc sản phẩm", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa loại sản phẩm không thành công", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create().show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                opendialogsua(list.get(holder.getAdapterPosition()));
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView MaLSP, TenLSP;
        ImageView Avata, LSP_Delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            MaLSP = itemView.findViewById(R.id.MaLSP);
            TenLSP = itemView.findViewById(R.id.TenLSP);
            Avata = itemView.findViewById(R.id.Avata);
            LSP_Delete = itemView.findViewById(R.id.LSP_Delete);
        }
    }

    public void opendialogsua(LoaiSanPham lsp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_loai_san_pham, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        avataLsp = view.findViewById(R.id.avataLsp_up);
        edt_updateTenLoaisp = view.findViewById(R.id.edt_updateTenLoaisp);
        LSP_Update = view.findViewById(R.id.LSP_Update);
        LSP_Cancel = view.findViewById(R.id.LSP_Cancel_up);

        // Lưu giữ giá trị hiện tại
        avataLsp.setText(lsp.getAvata());
        edt_updateTenLoaisp.setText(lsp.getTenLSP());

        LSP_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String avata = avataLsp.getText().toString();
                String tenlsp = edt_updateTenLoaisp.getText().toString();

                if (avata.equals("")) {
                    Toast.makeText(context, "Vui lòng không để trống Link ảnh loại sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tenlsp.equals("")) {
                    Toast.makeText(context, "Vui lòng không để trống Tên loại sản phẩm", Toast.LENGTH_SHORT).show();
                } else {
                    lsp.setAvata(avata);
                    lsp.setTenLSP(tenlsp);
                    if (dao.update(lsp)) {
                        list.clear();
                        list.addAll(dao.getDSLoaiSanPham());
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Thêm loại sản phẩm thành công!", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Thêm loại sản phẩm thất bại!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        LSP_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avataLsp.setText("");
                edt_updateTenLoaisp.setText("");
            }
        });
    }
}
