package com.example.duan1.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.example.duan1.Adapter.Adapter_SanPham;
import com.example.duan1.Adapter.Adapter_ThanhVien;
import com.example.duan1.DAO.KichThuocDAO;
import com.example.duan1.DAO.LoaiSanPhamDAO;
import com.example.duan1.DAO.SanPhamDAO;
import com.example.duan1.DAO.ThanhVienDAO;
import com.example.duan1.DAO.ThuongHieuDAO;
import com.example.duan1.Model.KichThuoc;
import com.example.duan1.Model.LoaiSanPham;
import com.example.duan1.Model.SanPham;
import com.example.duan1.Model.ThanhVien;
import com.example.duan1.Model.ThuongHieu;
import com.example.duan1.R;
import com.example.duan1.sanphamchitiet;

public class Fragment_san_pham extends Fragment {
    SanPhamDAO spDAO;
    Adapter_SanPham adaptersp;
    RecyclerView rcv_sanpham;
    private ArrayList<SanPham> list = new ArrayList<>();
    FloatingActionButton fltadd;
    Spinner spnloaisp_add,spnthuong_add,spnKichthuoc_add;
    TextInputEditText edtTensp_sp_add,edtgia_sp_add, edtAnhsp_sp_add;
    TextInputLayout in_Anh_sp_add, in_Ten_sp_add, in_gia_sp_add;
    public Fragment_san_pham() {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_san_pham, container, false);
        rcv_sanpham = view.findViewById(R.id.rcv_sanpham);
        fltadd = view.findViewById(R.id.fltadd);
       loaddata();
        fltadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogadd();
            }
        });
        adaptersp.setClick(new Adapter_SanPham.click() {
            @Override
            public void click(int pos) {
                SanPham hienthi = list.get(pos);
                Intent intent = new Intent(getContext(), sanphamchitiet.class);
                intent.putExtra("sanphamct",hienthi);
                startActivity(intent);
            }
        });
        // phân quyền
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DANGNHAPTV", Context.MODE_PRIVATE);
        String Loai = sharedPreferences.getString("Loai","");
        if(Loai.equalsIgnoreCase("admin")){
            fltadd.setVisibility(view.VISIBLE);

        }
        if(Loai.equalsIgnoreCase("Nhân Viên")){
            fltadd.setVisibility(view.VISIBLE);



        }
        if(Loai.equalsIgnoreCase("Khách Hàng")){
            fltadd.setVisibility(view.GONE);

        }
        return view;
    }
    private  void loaddata(){
        /// setlayout
        spDAO= new SanPhamDAO(getContext());
        list = spDAO.selectAllSanPham();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv_sanpham.setLayoutManager(linearLayoutManager);
        rcv_sanpham.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adaptersp = new Adapter_SanPham(getContext(), list);
        rcv_sanpham.setAdapter(adaptersp);

    }
    private void dialogadd (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_sanpham, null);

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        in_Anh_sp_add = view.findViewById(R.id.in_Anh_sp_add);
        in_Ten_sp_add = view.findViewById(R.id.in_Ten_sp_add);
        in_gia_sp_add = view.findViewById(R.id.in_gia_sp_add);
        edtAnhsp_sp_add = view.findViewById(R.id.edtAnhsp_sp_add);
        edtTensp_sp_add = view.findViewById(R.id.edtTensp_sp_add);
        edtgia_sp_add = view.findViewById(R.id.edtgia_sp_add);

        spnKichthuoc_add = view.findViewById(R.id.spnKichthuoc_add);
        spnthuong_add = view.findViewById(R.id.spnthuong_add);
        spnloaisp_add = view.findViewById(R.id.spnloaisp_add);
        Button SP_add = view.findViewById(R.id.SP_Add);
        Button SP_cancel = view.findViewById(R.id.SP_Cancel);

        getDataKichThuoc(spnKichthuoc_add);
        getDataLoai(spnloaisp_add);
        getDataThuong(spnthuong_add);

        SP_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lay ma Kich Thuoc
                HashMap<String , String> hsKT = (HashMap<String, String>) spnKichthuoc_add.getSelectedItem();
                int id= Integer.parseInt(hsKT.get("id"));
                int sluong = Integer.parseInt(hsKT.get("SoLuong"));
                // lay ma thuong hiêu
                HashMap<String , String> hsTH = (HashMap<String, String>) spnthuong_add.getSelectedItem();
                int MaTH= Integer.parseInt(hsTH.get("MaTH"));
                // lay ma loai san pham
                HashMap<String , String> hsLSP = (HashMap<String, String>) spnloaisp_add.getSelectedItem();
                int MaLSP= Integer.parseInt(hsLSP.get("MaLSP"));
                String tensp = edtTensp_sp_add.getText().toString();
//                int gia = Integer.parseInt(edtgia_sp_add.getText().toString());
                String avatasp = edtAnhsp_sp_add.getText().toString();
                //int soluong = Integer.parseInt(edtSoLuong_sp_add.getText().toString());

                // Link ảnh Sản phẩm
                if (avatasp.isEmpty()){
                    in_Anh_sp_add.setError("Vui lòng không để trống Link ảnh Sản phẩm");
                    return;
                }else {
                    in_Anh_sp_add.setError(null);
                }
                // Tên sản phẩm
                if (tensp.isEmpty()){
                    in_Ten_sp_add.setError("Vui lòng không để trống Tên Sản phẩm");
                    return;
                }else {
                    in_Ten_sp_add.setError(null);
                }
                // Giá sản phẩm
                String giaStr = edtgia_sp_add.getText().toString();
                if (giaStr.trim().isEmpty()) {
                    in_gia_sp_add.setError("Vui lòng nhập giá sản phẩm");
                    return;
                }

                int giaStrr = Integer.parseInt(giaStr);


                if (giaStrr <= 0) { // Kiểm tra giá có lớn hơn 0 không
                    in_gia_sp_add.setError("Giá sản phẩm phải lớn hơn 0");
                    return;
                } else {
                    in_gia_sp_add.setError(null);
                }
                // Gọi hàm sửa sản phẩm khi tất cả các điều kiện đều hợp lệ
                themsanpham(avatasp,tensp,giaStrr,sluong,id,MaTH,MaLSP);
                dialog.dismiss();
            }
        });
    }

    private void getDataKichThuoc(Spinner spnKichthuoc_add) {
        KichThuocDAO DAO = new KichThuocDAO(getContext());
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
        SimpleAdapter adapter = new SimpleAdapter(getContext(),listHM,android.R.layout.simple_list_item_1,new String[]{"MaKT"}, new int[]{android.R.id.text1});
        spnKichthuoc_add.setAdapter(adapter);
    }
    private void getDataLoai(Spinner spnloaisp_add) {
        LoaiSanPhamDAO DAO = new LoaiSanPhamDAO(getContext());
        ArrayList<LoaiSanPham> list = DAO.getDSLoaiSanPham();
        ArrayList<HashMap<String,String>> listHM = new ArrayList<>();
        for ( LoaiSanPham s : list) {
            HashMap<String , String> hs = new HashMap<>();
            hs.put("MaLSP" , String.valueOf(s.getMaLSP()));
            hs.put("TenLSP", s.getTenLSP());
            listHM.add(hs);
        }
        SimpleAdapter adapter = new SimpleAdapter(getContext(),listHM,android.R.layout.simple_list_item_1,new String[]{"TenLSP"}, new int[]{android.R.id.text1});
        spnloaisp_add.setAdapter(adapter);
    }

    private void getDataThuong(Spinner spnthuong_add) {
        ThuongHieuDAO DAO = new ThuongHieuDAO(getContext());
        ArrayList<ThuongHieu> list = DAO.getDSThuongHieu();
        ArrayList<HashMap<String,String>> listHM = new ArrayList<>();
        for (ThuongHieu tv : list) {
            HashMap<String , String> hs = new HashMap<>();
            hs.put("MaTH" , String.valueOf(tv.getMaTH()));
            hs.put("TenTH", tv.getTenTH());
            hs.put("SDT",String.valueOf(tv.getSDT()));
            listHM.add(hs);
        }
        SimpleAdapter adapter = new SimpleAdapter(getContext(),listHM,android.R.layout.simple_list_item_1,new String[]{"TenTH"}, new int[]{android.R.id.text1});
        spnthuong_add.setAdapter(adapter);
    }
    private void themsanpham(String AvataSP, String tensp , int gia,int soluong ,int id ,int MaTH  ,int MaLSP ){
        SanPham  sp = new SanPham(AvataSP, tensp ,gia,soluong,id, MaTH,MaLSP);
        boolean kiemtra =spDAO.insert(sp);
        if (kiemtra == true){
            Toast.makeText(getContext(), "Thêm sản phẩm thành  thành công!", Toast.LENGTH_SHORT).show();
            loaddata();
        }else{
            Toast.makeText(getContext(), "Thêm Sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
        }

    }

}