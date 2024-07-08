package com.example.duan1.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.duan1.DAO.KichThuocDAO;
import com.example.duan1.Model.KichThuoc;
import com.example.duan1.Model.ThuongHieu;
import com.example.duan1.R;

public class Adapter_KichThuoc extends RecyclerView.Adapter<Adapter_KichThuoc.ViewHolder> {
    private Context context;
    private ArrayList<KichThuoc> list;
    KichThuocDAO dao;

    public Adapter_KichThuoc(Context context, ArrayList<KichThuoc> list) {
        this.context = context;
        this.list = list;
        dao = new KichThuocDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_kich_thuoc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KichThuoc kt = list.get(position);
        holder.txtMaKT_kt.setText(list.get(position).getMaKT());
        Picasso.get().load(kt.getAvataKT()).into(holder.AvataKT);
        holder.txtSize_kt.setText("SIZE " + list.get(position).getSize());
        holder.txtSoLuong_kt.setText(String.valueOf(list.get(position).getSoLuong()));


        holder.imgDelete_kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("CẢNH BÁO");// set tieu de
                builder.setIcon(R.drawable.baseline_warning_amber_24);
                builder.setMessage("BẠN CÓ CHẮC CHẮN MUỐN XÓA LOẠI SÁCH NÀY KHÔNG");
                /// tạo nut buttun yes , xuli su kien cho nut
                builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        KichThuocDAO dao = new KichThuocDAO(context);
                        int check = dao.delete(list.get(holder.getAdapterPosition()).getId());
                        switch (check) {
                            case 1:
                                list.clear();
                                list = dao.selectAllKichThuoc();
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa kích thước thành công", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Không thể xóa vì đang có kích thước thuộc sản phẩm", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa kích thước không thành công", Toast.LENGTH_SHORT).show();
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
                opendialogsua(kt);
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaKT_kt, txtSize_kt, txtSoLuong_kt;
        ImageView AvataKT, imgDelete_kt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaKT_kt = itemView.findViewById(R.id.txtMaKT_kt);
            AvataKT = itemView.findViewById(R.id.AvataKT);
            txtSize_kt = itemView.findViewById(R.id.txtSize_kt);
            txtSoLuong_kt = itemView.findViewById(R.id.txtSoLuong_kt);
            imgDelete_kt = itemView.findViewById(R.id.imgDelete_kt);
        }
    }

    public void opendialogsua(KichThuoc kt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_kich_thuoc_update, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        TextInputEditText edtma_kt_up = view.findViewById(R.id.edtma_kt_up);
        TextInputEditText edtsize_kt = view.findViewById(R.id.edtsize_kt);
        TextInputEditText edtSoluong_kt = view.findViewById(R.id.edtSoluong_kt);
        TextInputEditText edtanh_kt_up = view.findViewById(R.id.edtanh_kt_up);
        TextInputLayout in_ma_up = view.findViewById(R.id.in_ma_up);
        TextInputLayout in_size = view.findViewById(R.id.in_size);
        TextInputLayout in_soluong = view.findViewById(R.id.in_soluong);
        TextInputLayout in_anh_up = view.findViewById(R.id.in_anh_up);
        Button UpdateTH = view.findViewById(R.id.KT_update);
        Button CancelTH = view.findViewById(R.id.KT_Cancelupdate);

        edtma_kt_up.setText(kt.getMaKT());
        edtanh_kt_up.setText(kt.getAvataKT());
        edtsize_kt.setText(String.valueOf(kt.getSize()));
        edtSoluong_kt.setText(String.valueOf(kt.getSoLuong()));


        edtma_kt_up.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    in_ma_up.setError("Vui lòng không để trống mã");
                } else {
                    in_ma_up.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtsize_kt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    in_size.setError("Vui lòng không để trống Size");
                } else {
                    in_size.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtSoluong_kt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    in_soluong.setError("Vui lòng không để trống Số lượng");
                } else {
                    in_soluong.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtanh_kt_up.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    in_anh_up.setError("Vui lòng không để trống link ảnh kích thước");
                } else {
                    in_anh_up.setError(null);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        UpdateTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MaKT = edtma_kt_up.getText().toString();
                String Anhkt = edtanh_kt_up.getText().toString();
                String sizeText = edtsize_kt.getText().toString();
                String soluongText = edtSoluong_kt.getText().toString();

                // MaKT
                if (MaKT.isEmpty()) {
                    in_ma_up.setError("Vui lòng không để trống mã kích thước ");
                    return;
                } else {
                    in_ma_up.setError(null);
                }
                if (MaKT.trim().length() < 4) {
                    in_ma_up.setError("Mã Kích thước phải có ít nhất 4 ký tự");
                    return;
                } else {
                    in_ma_up.setError(null);
                }
                // Link ảnh
                if (Anhkt.isEmpty()) {
                    in_anh_up.setError("Vui lòng không để trống link ảnh kích thước");
                    return;
                } else {
                    in_anh_up.setError(null);
                }
                // Size
                if (sizeText.isEmpty()) {
                    in_size.setError("Vui lòng không để trống Size!");
                    return;
                } else {
                    in_size.setError(null);
                }
                // Số lượng
                if (soluongText.isEmpty()) {
                    in_soluong.setError("Vui lòng không để trống Số lượng!");
                    return;
                } else {
                    in_soluong.setError(null);
                }
                if (soluongText.equals("")) {
                    Toast.makeText(context, "Vui lòng không để trống Số lượng!", Toast.LENGTH_SHORT).show();
                } else {

                    // Kiểm tra size có phải là số dương không
                    if (!isValidNumber(sizeText)) {
                        in_size.setError("Size phải là số dương và lớn hơn 0!");
                        return;
                    } else {
                        in_size.setError(null);
                    }

                    // Kiểm tra số lượng có phải là số dương và lớn hơn 0 không
                    if (!isValidNumber(soluongText) || Integer.parseInt(soluongText) <= 0) {
                        in_soluong.setError("Số lượng phải là số dương và lớn hơn 0!");
                        return;
                    } else {
                        in_soluong.setError(null);
                    }
                    kt.setMaKT(MaKT);
                    kt.setAvataKT(Anhkt);
                    kt.setSize(Integer.parseInt(sizeText));
                    kt.setSoLuong(Integer.parseInt(soluongText));

                    if (dao.update(kt)) {
                        list.clear();
                        list.addAll(dao.selectAllKichThuoc());
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Update thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Update thất bại!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        CancelTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    // Hàm kiểm tra xem chuỗi có phải là số dương không
    private boolean isValidNumber(String number) {
        try {
            int num = Integer.parseInt(number);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}