package com.example.duan1.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.example.duan1.DAO.ThanhVienDAO;
import com.example.duan1.Model.LoaiSanPham;
import com.example.duan1.Model.ThanhVien;
import com.example.duan1.R;

public class Adapter_ThanhVien  extends RecyclerView.Adapter<Adapter_ThanhVien.ViewHolder>{
    private Context context;
    private ArrayList<ThanhVien> list;
    ThanhVienDAO dao;
    public Adapter_ThanhVien(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
        dao = new ThanhVienDAO(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thanh_vien, parent, false);

        return new  ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThanhVien tv = list.get(position);
        holder.txtMaTV_tv.setText(list.get(position).getMaTV());
        holder.txtHoten_tv.setText(list.get(position).getHoTen());
        holder.txtSDT_tv.setText(list.get(position).getSDT());
        holder.txtEmail_tv.setText(list.get(position).getEmail());
        holder.txtDchi_tv.setText(list.get(position).getDChi());
        holder.txt_Coins.setText(String.valueOf(list.get(position).getSotien()));
        Picasso.get().load(tv.getAvataTV()).into(holder.ImgAnhTV);
        // phân quyền
        SharedPreferences sharedPreferences = context.getSharedPreferences("DANGNHAPTV", Context.MODE_PRIVATE);
        String Loai = sharedPreferences.getString("Loai","");
        if(Loai.equalsIgnoreCase("admin")){
            holder.imgDelete_tv.setVisibility(View.VISIBLE);
            holder.imgChinhSua_tv.setVisibility(View.VISIBLE);
        }
        if(Loai.equalsIgnoreCase("Nhân Viên")){
            holder.imgDelete_tv.setVisibility(View.VISIBLE);
            holder.imgChinhSua_tv.setVisibility(View.VISIBLE);



        }
        if(Loai.equalsIgnoreCase("Khách Hàng")){
            holder.imgDelete_tv.setVisibility(View.GONE);
            holder.imgChinhSua_tv.setVisibility(View.GONE);

        }
        holder.imgDelete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("CẢNH BÁO");// set tieu de
                builder.setIcon(R.drawable.baseline_warning_amber_24);
                builder.setMessage("BẠN CÓ CHẮC CHẮN MUỐN XÓA LOẠI SÁCH NÀY KHÔNG");
                /// tạo nut buttun yes , xuli su kien cho nut
                builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check =dao.delete(list.get(holder.getAdapterPosition()).getId());
                        if (check ==1){
                            list.clear();
                            list.addAll(dao.selectAllthanhVien());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                        }else if (check == -1) {
                            Toast.makeText(context, "Không thể xóa (kich thước) này vì đã có (sản phẩm) thuộc thể loại này", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "XÓA THẤT BẠI", Toast.LENGTH_SHORT).show();
                    }
                }) ;
                AlertDialog di = builder.create();
                di.show();
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                opendialogsua(tv);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaTV_tv, txtHoten_tv, txtSDT_tv,txtEmail_tv,txtDchi_tv,txt_Coins;
        ImageView ImgAnhTV, imgDelete_tv,imgChinhSua_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaTV_tv = itemView.findViewById(R.id.txtMaTV_tv);
            txtHoten_tv = itemView.findViewById(R.id.txtHoten_tv);
            txtSDT_tv = itemView.findViewById(R.id.txtSDT_tv);
            txtEmail_tv = itemView.findViewById(R.id.txtEmail_tv);
            txtDchi_tv = itemView.findViewById(R.id.txtDchi_tv);
            txt_Coins = itemView.findViewById(R.id.txt_Coins);

            ImgAnhTV = itemView.findViewById(R.id.ImgAnhTV);
            imgDelete_tv = itemView.findViewById(R.id.imgDelete_tv);
            imgChinhSua_tv = itemView.findViewById(R.id.imgChinhSua_tv);

        }
    }

    public void opendialogsua(ThanhVien tv){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_thanhvien, null);

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();


        TextInputLayout in_updateMaTV = view.findViewById(R.id.in_updateMaTV);
        TextInputLayout in_updateAvata = view.findViewById(R.id.in_updateAvata);
        TextInputLayout in_updateName = view.findViewById(R.id.in_updateName);
        TextInputLayout in_updateMk = view.findViewById(R.id.in_updateMk);
        TextInputLayout in_updateSDT = view.findViewById(R.id.in_updateSDT);
        TextInputLayout in_updateEmail = view.findViewById(R.id.in_updateEmail);
        TextInputLayout in_updateDiachi = view.findViewById(R.id.in_updateDiachi);


        TextInputEditText ed_updateMaTV = view.findViewById(R.id.ed_updateMaTV);
        TextInputEditText ed_updateAvata = view.findViewById(R.id.ed_updateAvata);
        TextInputEditText ed_updateName = view.findViewById(R.id.ed_updateName);
        TextInputEditText ed_updateMk = view.findViewById(R.id.ed_updateMk);
        TextInputEditText ed_updateSDT = view.findViewById(R.id.ed_updateSDT);
        TextInputEditText ed_updateEmail = view.findViewById(R.id.ed_updateEmail);
        TextInputEditText ed_updateDiachi = view.findViewById(R.id.ed_updateDiachi);

        Button TV_update = view.findViewById(R.id.TV_update);
        Button TV_Cancelupdtae = view.findViewById(R.id.TV_Cancelupdtae);

        ed_updateAvata.setText(tv.getAvataTV());
        ed_updateMaTV.setText(tv.getMaTV());
        ed_updateName.setText(tv.getHoTen());
        ed_updateMk.setText(String.valueOf(tv.getSotien()));
        ed_updateSDT.setText(tv.getSDT());
        ed_updateEmail.setText(tv.getEmail());
        ed_updateDiachi.setText(tv.getDChi());




        TV_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maTV = ed_updateMaTV.getText().toString();
                String avataTV = ed_updateAvata.getText().toString();
                String name = ed_updateName.getText().toString();
                int matkhau = Integer.parseInt(ed_updateMk.getText().toString());
                String sdt = ed_updateSDT.getText().toString();
                String email = ed_updateEmail.getText().toString();
                String dchi = ed_updateDiachi.getText().toString();

                //MaTV
                if (maTV.isEmpty()){
                    in_updateMaTV.setError("Vui lòng không để trống mã thành viên");
                    return;
                }else {
                    in_updateMaTV.setError(null);
                }
                if (maTV.trim().length() < 2) {
                    Toast.makeText(context, "Mã Thành viên phải có tối thiểu 2 ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Name
                if (name.isEmpty()){
                    in_updateName.setError("Vui lòng không để trống họ và tên");
                    return;
                }else {
                    in_updateName.setError(null);
                }
                if (name.trim().length() < 2) {
                    Toast.makeText(context, "Họ và tên phải có tối thiểu 2 ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Mật khẩu
//                if (matkhau.isEmpty()){
//                    in_updateMk.setError("Vui lòng không để trống mật khẩu");
//                    return;
//                }else {
//                    in_updateMk.setError(null);
//                }
//                if (matkhau.trim().length() < 4) {
//                    Toast.makeText(context, "Mật khẩu phải có tối thiểu 4 ký tự", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                //Số điện thoại
                if (sdt.isEmpty()){
                    in_updateSDT.setError("Vui lòng không để trống số điện thoại");
                    return;
                }else {
                    in_updateSDT.setError(null);
                }
                if (!isValidPhoneNumber(sdt)) {
                    Toast.makeText(context, "Số điện thoại phải có từ 10 số hoặc 12 số", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Email
                if (email.isEmpty()){
                    in_updateEmail.setError("Vui lòng không để trống E-mail");
                    return;
                }else {
                    in_updateEmail.setError(null);
                }
                if (!isValidEmail(email)) {
                    Toast.makeText(context, "E-mail sai định dạng", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Địa chỉ
                if (dchi.isEmpty()){
                    in_updateDiachi.setError("Vui lòng không để trống địa chỉ");
                    return;
                }else {
                    in_updateDiachi.setError(null);
                }
                if (dchi.trim().length() < 2) {
                    Toast.makeText(context, "Địa chỉ phải có tối thiểu 2 ký tự", Toast.LENGTH_SHORT).show();
                }
                else {
                    tv.setMaTV(maTV);
                    tv.setAvataTV(avataTV);
                    tv.setHoTen(name);
                    tv.setSotien(matkhau);
                    tv.setSDT(sdt);
                    tv.setEmail(email);
                    tv.setDChi(dchi);
                    if (dao.update(tv)){
                        list.clear();
                        list.addAll(dao.selectAllthanhVien());
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Update thành viên thành công!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Update thành viên thất bại!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        TV_Cancelupdtae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_updateMaTV.setText("");
                ed_updateAvata.setText("");
                ed_updateName.setText("");
                ed_updateMk.setText("");
                ed_updateSDT.setText("");
                ed_updateEmail.setText("");
                ed_updateDiachi.setText("");
            }
        });
    }
    public boolean isValidPhoneNumber(String phoneNumber) {
        // Kiểm tra số điện thoại có bắt đầu bằng số 0 và có từ 10 đến 12 chữ số
        return phoneNumber.matches("^0\\d{9,11}$");
    }
    public boolean isValidEmail(String email) {
        // Kiểm tra email có đúng định dạng
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailPattern);
    }
}
