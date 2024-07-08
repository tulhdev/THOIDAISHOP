package com.example.duan1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.example.duan1.DAO.SanPhamDAO;
import com.example.duan1.Model.SanPham;
import com.example.duan1.R;
import com.example.duan1.sanphamchitiet;

public class Adapter_trangchu extends RecyclerView.Adapter<Adapter_trangchu.ViewHolder>{
    private Context context;
    private ArrayList<SanPham> list;
    SanPhamDAO dao;
    public interface click{
        void click (int pos);
    }
    private Adapter_SanPham.click click;
    public void setClick(Adapter_SanPham.click click1){
        click = click1;
    }
    public Adapter_trangchu(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
        dao = new SanPhamDAO(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sanpham_tt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txttensp_tt.setText(list.get(position).getTenSP());
        holder.txtgiasp_tt.setText(String.valueOf(list.get(position).getGia()));
        // Load ảnh vào ImageView
        Picasso.get().load(list.get(position).getAvataSP()).into(holder.ImgAnhth);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(click != null){
                    click.click(holder.getAdapterPosition());
                    Intent intent = new Intent(context, sanphamchitiet.class);
                    intent.putExtra("sanphamct", list.get(holder.getAdapterPosition()));
                    intent.putExtra("anhsp", list.get(holder.getAdapterPosition()).getAvataSP());
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtgiasp_tt,txttensp_tt;
        ImageView ImgAnhth;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ImgAnhth = itemView.findViewById(R.id.ImgAnhth);
            txtgiasp_tt = itemView.findViewById(R.id.txtgiasp_tt);
            txttensp_tt = itemView.findViewById(R.id.txttensp_tt);
        }
    }
}
