package com.example.duan1.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.duan1.DAO.KichThuocDAO;
import com.example.duan1.DAO.LoaiSanPhamDAO;
import com.example.duan1.DAO.SanPhamDAO;
import com.example.duan1.DAO.ThuongHieuDAO;
import com.example.duan1.Model.KichThuoc;
import com.example.duan1.Model.LoaiSanPham;
import com.example.duan1.Model.SanPham;
import com.example.duan1.Model.ThuongHieu;
import com.example.duan1.R;
import com.example.duan1.sanphamchitiet;

public class Adapter_SanPham extends RecyclerView.Adapter<Adapter_SanPham.ViewHolder>{
    private Context context;
    private ArrayList<SanPham> list;
    TextInputEditText edtTensp_sp_up,edtgia_sp_up, edtAnhsp_sp_up;
    TextInputLayout in_Anh_sp_up, in_Ten_sp_up, in_gia_sp_up;
    Spinner spnloaisp_up,spnthuong_up,spnKichthuoc_up;
    SanPhamDAO dao;
    public interface click{
        void click (int pos);
    }
    private click click;
    public void setClick(click click1){
        click = click1;
    }
    public Adapter_SanPham(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
        dao = new SanPhamDAO(context);
    }

    // Thêm phương thức để thêm sản phẩm vào giỏ hàng
    public interface AddToCartListener {
        void addToCart(SanPham sanPham);
    }

    private AddToCartListener addToCartListener;

    public void setAddToCartListener(AddToCartListener listener) {
        addToCartListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_san_pham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sp = list.get(position);
        holder.txttensamphan_sp.setText(list.get(position).getTenSP());
        holder.txtgiasp_sp.setText(String.valueOf(list.get(position).getGia()));
        holder.txtTenthuonghieu_sp.setText(list.get(position).getTenthuonghieu());
        Picasso.get().load(sp.getAvataSP()).into(holder.ImgAnhsp);
        holder.imgDelete_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("CẢNH BÁO");// set tieu de
                builder.setIcon(R.drawable.baseline_warning_amber_24);
                builder.setMessage("BẠN CÓ CHẮC CHẮN MUỐN XÓA SẢN PHẨM NÀY KHÔNG");
                /// tạo nut buttun yes , xuli su kien cho nut
                builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check =dao.delete(list.get(holder.getAdapterPosition()).getMaSP());
                        if (check ==1){
                            list.clear();
                            list.addAll(dao.selectAllSanPham());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                        }else if (check == -1) {
                            Toast.makeText(context, "Không thể xóa (Sản phẩm) này vì đã có (Hóa đơn) thuộc thể loại này", Toast.LENGTH_SHORT).show();
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
        // phân quyền
        SharedPreferences sharedPreferences = context.getSharedPreferences("DANGNHAPTV", Context.MODE_PRIVATE);
        String Loai = sharedPreferences.getString("Loai","");
        if(Loai.equalsIgnoreCase("admin")){
            holder.imgDelete_sp.setVisibility(View.VISIBLE);
            holder.imgChinhSua_sp.setVisibility(View.VISIBLE);
        }
        if(Loai.equalsIgnoreCase("Nhân Viên")){
            holder.imgDelete_sp.setVisibility(View.VISIBLE);
            holder.imgChinhSua_sp.setVisibility(View.VISIBLE);


        }
        if(Loai.equalsIgnoreCase("Khách Hàng")){
            holder.imgDelete_sp.setVisibility(View.GONE);
            holder.imgChinhSua_sp.setVisibility(View.GONE);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(click != null){
                    click.click(holder.getAdapterPosition());
                    // Truyền đường dẫn ảnh qua Intent
                    Intent intent = new Intent(context, sanphamchitiet.class);
                    intent.putExtra("sanphamct", list.get(holder.getAdapterPosition()));
                    intent.putExtra("anhsp", list.get(holder.getAdapterPosition()).getAvataSP());
                    context.startActivity(intent);

                    if (addToCartListener != null) {
                        addToCartListener.addToCart(list.get(holder.getAdapterPosition()));
                    }
//                    // Giả sử cần tạo đối tượng 'Gio_Hang'
//                    GioHang gioHangItem = new GioHang();
//                    gioHangItem.setTenSanPham(list.get(holder.getAdapterPosition()).getTenSP());
//                    gioHangItem.setGiaSanPham(list.get(holder.getAdapterPosition()).getGia());
//                    gioHangItem.setSoLuong(1);  // Bạn có thể đặt số lượng phù hợp

                    // Chuyển 'gioHangItem' sang màn hình tiếp theo hoặc thêm nó vào danh sách
                    // và thông báo cho 'Adapter_GioHang'
                }
            }
        });
        holder.imgChinhSua_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogadd(sp);
            }
        });
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                dialogadd(sp);
//                return true;
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txttensamphan_sp,txtgiasp_sp,txtTenthuonghieu_sp;
        ImageView ImgAnhsp, imgDelete_sp,imgChinhSua_sp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ImgAnhsp = itemView.findViewById(R.id.ImgAnhSP);
            imgChinhSua_sp= itemView.findViewById(R.id.imgChinhSua_sp);
            imgDelete_sp = itemView.findViewById(R.id.imgDelete_sp);
            txttensamphan_sp = itemView.findViewById(R.id.txttensamphan_sp);
            txtgiasp_sp = itemView.findViewById(R.id.txtgiasp_sp);
            txtTenthuonghieu_sp = itemView.findViewById(R.id.txtTenthuonghieu_sp);

        }
    }

    private void dialogadd (SanPham sp){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_sanpham, null);

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        in_Anh_sp_up = view.findViewById(R.id.in_Anh_sp_up);
        in_Ten_sp_up = view.findViewById(R.id.in_Ten_sp_up);
        in_gia_sp_up = view.findViewById(R.id.in_gia_sp_up);
        edtAnhsp_sp_up = view.findViewById(R.id.edtAnhsp_sp_up);
        edtTensp_sp_up = view.findViewById(R.id.edtTensp_sp_up);
        edtgia_sp_up = view.findViewById(R.id.edtgia_sp_up);

        spnKichthuoc_up = view.findViewById(R.id.spnKichthuoc_up);
        spnthuong_up = view.findViewById(R.id.spnthuong_up);
        spnloaisp_up = view.findViewById(R.id.spnloaisp_up);
        Button SP_add = view.findViewById(R.id.SP_up);
        Button SP_cancel_up = view.findViewById(R.id.SP_Cancel_up);
        edtTensp_sp_up.setText(sp.getTenSP());
        edtgia_sp_up.setText(String.valueOf(sp.getGia()));
        edtAnhsp_sp_up.setText(sp.getAvataSP());

        getDataKichThuoc(spnKichthuoc_up);
        getDataLoai(spnloaisp_up);
        getDataThuong(spnthuong_up);

        SP_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lay ma Kich Thuoc
                HashMap<String , String> hsKT = (HashMap<String, String>) spnKichthuoc_up.getSelectedItem();
                int id= Integer.parseInt(hsKT.get("id"));
                int sluong = Integer.parseInt(hsKT.get("SoLuong"));
                // lay ma thuong hiêu
                HashMap<String , String> hsTH = (HashMap<String, String>) spnthuong_up.getSelectedItem();
                int MaTH= Integer.parseInt(hsTH.get("MaTH"));
                // lay ma loai san pham
                HashMap<String , String> hsLSP = (HashMap<String, String>) spnloaisp_up.getSelectedItem();
                int MaLSP= Integer.parseInt(hsLSP.get("MaLSP"));
                String tensp = edtTensp_sp_up.getText().toString();
//                int gia = Integer.parseInt(edtgia_sp_up.getText().toString());
                String avatasp = edtAnhsp_sp_up.getText().toString();
                //int soluong = Integer.parseInt(edtSoLuong_sp_add.getText().toString());
                int idd = sp.getMaSP();

                // Link ảnh Sản phẩm
                if (avatasp.isEmpty()){
                    in_Anh_sp_up.setError("Vui lòng không để trống Link ảnh Sản phẩm");
                    return;
                }else {
                    in_Anh_sp_up.setError(null);
                }
                // Tên sản phẩm
                if (tensp.isEmpty()){
                    in_Ten_sp_up.setError("Vui lòng không để trống Tên Sản phẩm");
                    return;
                }else {
                    in_Ten_sp_up.setError(null);
                }
                // Giá sản phẩm
                String giaStr = edtgia_sp_up.getText().toString();
                if (giaStr.trim().isEmpty()) {
                    in_gia_sp_up.setError("Vui lòng nhập giá sản phẩm");
                    return;
                }

                int gia = Integer.parseInt(giaStr);


                if (gia <= 0) { // Kiểm tra giá có lớn hơn 0 không
                    in_gia_sp_up.setError("Giá sản phẩm phải lớn hơn 0");
                    return;
                } else {
                    in_gia_sp_up.setError(null);
                }
                // Gọi hàm sửa sản phẩm khi tất cả các điều kiện đều hợp lệ
                suasanpham(idd,avatasp,tensp,gia,sluong,id,MaTH,MaLSP);
                dialog.dismiss();
            }
        });
    }

    private void getDataKichThuoc(Spinner spnKichthuoc_up) {
        KichThuocDAO DAO = new KichThuocDAO(context);
        ArrayList<KichThuoc> list = DAO.selectAllKichThuoc();
        ArrayList<HashMap<String,String>> listHM = new ArrayList<>();
        for ( KichThuoc s : list) {
            HashMap<String , String> hs = new HashMap<>();
            hs.put("id" , String.valueOf(s.getId()));
            hs.put("MaKT" , s.getMaKT());
            hs.put("Size", String.valueOf("Size "+ s.getSize()));
            hs.put("SoLuong", String.valueOf(s.getSoLuong()));
            listHM.add(hs);
        }
        SimpleAdapter adapter = new SimpleAdapter(context,listHM,android.R.layout.simple_list_item_1,new String[]{"MaKT"}, new int[]{android.R.id.text1});
        spnKichthuoc_up.setAdapter(adapter);
    }
    private void getDataLoai(Spinner spnloaisp_up) {
        LoaiSanPhamDAO DAO = new LoaiSanPhamDAO(context);
        ArrayList<LoaiSanPham> list = DAO.getDSLoaiSanPham();
        ArrayList<HashMap<String,String>> listHM = new ArrayList<>();
        for ( LoaiSanPham s : list) {
            HashMap<String , String> hs = new HashMap<>();
            hs.put("MaLSP" , String.valueOf(s.getMaLSP()));
            hs.put("TenLSP", s.getTenLSP());
            listHM.add(hs);
        }
        SimpleAdapter adapter = new SimpleAdapter(context,listHM,android.R.layout.simple_list_item_1,new String[]{"TenLSP"}, new int[]{android.R.id.text1});
        spnloaisp_up.setAdapter(adapter);
    }

    private void getDataThuong(Spinner spnthuong_add) {
        ThuongHieuDAO DAO = new ThuongHieuDAO(context);
        ArrayList<ThuongHieu> list = DAO.getDSThuongHieu();
        ArrayList<HashMap<String,String>> listHM = new ArrayList<>();
        for (ThuongHieu tv : list) {
            HashMap<String , String> hs = new HashMap<>();
            hs.put("MaTH" , String.valueOf(tv.getMaTH()));
            hs.put("TenTH", tv.getTenTH());
            hs.put("SDT",String.valueOf(tv.getSDT()));
            listHM.add(hs);
        }
        SimpleAdapter adapter = new SimpleAdapter(context,listHM,android.R.layout.simple_list_item_1,new String[]{"TenTH"}, new int[]{android.R.id.text1});
        spnthuong_add.setAdapter(adapter);
    }
    private void suasanpham(int  masp ,String AvataSP, String tensp , int gia,int soluong ,int id ,int MaTH  ,int MaLSP ){
        SanPham  sp = new SanPham(masp,AvataSP, tensp ,gia,soluong,id, MaTH,MaLSP);
        boolean kiemtra =dao.update(sp);
        if (kiemtra == true){
            list.clear();
            list.addAll(dao.selectAllSanPham());
            notifyDataSetChanged();
            Toast.makeText(context, "Thêm sản phẩm thành  thành công!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Thêm Sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

}
