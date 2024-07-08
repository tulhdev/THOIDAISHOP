package com.example.duan1.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.example.duan1.Adapter.Adapter_GioHang;
import com.example.duan1.Adapter.swipe;
import com.example.duan1.DAO.DonHangChiTietDao;
import com.example.duan1.DAO.DonHangDao;
import com.example.duan1.DAO.GioHangDAO;
import com.example.duan1.DAO.SanPhamDAO;
import com.example.duan1.DAO.ThanhVienDAO;
import com.example.duan1.Model.DonHang;
import com.example.duan1.Model.DonHangChiTiet;
import com.example.duan1.Model.GioHang;
import com.example.duan1.Model.SanPham;
import com.example.duan1.R;
import com.example.duan1.databinding.ActivityGioHangBinding;
import com.example.duan1.databinding.DialogConfilmThanhToanBinding;
import com.example.duan1.databinding.FragmentGioHangBinding;

public class Fragment_gioHang extends Fragment  implements Adapter_GioHang.TotalPriceListener{

    private FragmentGioHangBinding binding;
    View gView;
    Toolbar toolbar;
    RecyclerView recycleView;
    Button btnmuahang;
    //    Adapter_GioHang adapter;
    GioHangDAO ghDAO;
    DonHangDao donHangDao;
    DonHangChiTietDao chiTietDao;
    private Adapter_GioHang adapter;
    SanPhamDAO dao;
    /// new
    private ArrayList<DonHang> listDonHang = new ArrayList<>();
    private ArrayList<GioHang> list = new ArrayList<>();
    private void displayCart(ArrayList<GioHang> cartList) {
        RecyclerView rcv = binding.recycleviewgiohang;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);

        if (adapter == null) {
            adapter = new Adapter_GioHang(getContext(), cartList);
            rcv.setAdapter(adapter);

        } else {
            adapter.updateCartList(cartList);
            adapter.notifyDataSetChanged();
        }
    }


    public Fragment_gioHang() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGioHangBinding.inflate(inflater, container, false);
        gView = binding.getRoot();

        RecyclerView rcv = binding.recycleviewgiohang;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(layoutManager);
        adapter = new Adapter_GioHang(getContext(), list);
        rcv.setAdapter(adapter);
        ghDAO = new GioHangDAO(getActivity());
        ItemTouchHelper sw = new ItemTouchHelper(new swipe(adapter));
        sw.attachToRecyclerView(rcv);
        adapter.setTotalPriceListener(this);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("DANGNHAPTV", MODE_PRIVATE);
        int mand = sharedPreferences.getInt("id", 0);

        chiTietDao = new DonHangChiTietDao(getContext());
       donHangDao = new DonHangDao(getContext());
//
       dao = new SanPhamDAO(getContext());
        list = ghDAO.getDanhSachGioHangByMaNguoiDung(mand);
        displayCart(list);
        binding.btnmuahang.setOnClickListener(view -> {
            showDialogThanhToan();

        });



        // Inflate the layout for this fragment
        return gView;
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    @Override
    public void onTotalPriceUpdated(int totalAmount) {
        if (binding != null && binding.txttongtien != null) {
            binding.txttongtien.setText(String.valueOf(totalAmount));
           // tongtien.setText(String.valueOf(totalAmount));
        }
    }
    private void showDialogThanhToan() {
        LayoutInflater layoutInflater = getLayoutInflater();
        DialogConfilmThanhToanBinding thanhToanBinding = DialogConfilmThanhToanBinding.inflate(layoutInflater);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(thanhToanBinding.getRoot());
        AlertDialog dialog = builder.create();
        dialog.show();
        thanhToanBinding.btnThanhToan.setOnClickListener(view -> {
            for (GioHang gioHang : list) {
                if (gioHang.getSoLuongMua() == 0) {
                    Toast.makeText(getContext(), "Sản phẩm " + gioHang.getTenSanPham() + " đã hết hàng", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            int totalAmount = Integer.parseInt(binding.txttongtien.getText().toString());
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("DANGNHAPTV", MODE_PRIVATE);
            int mand = sharedPreferences.getInt("id", 0);
            int tienHienCo = sharedPreferences.getInt("SoTien", 0);

            LocalDate currentDate = LocalDate.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String ngayHienTai = currentDate.format(formatter);

            if (tienHienCo >= totalAmount) {
                int soTienConLai = tienHienCo - totalAmount;
                ThanhVienDAO nguoiDungDao = new ThanhVienDAO(getContext());
                if (nguoiDungDao.updateSoTien(mand, soTienConLai)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("SoTien", soTienConLai);
                    editor.apply();
                    DonHang donHang = new DonHang(mand, ngayHienTai, totalAmount, "Chờ phê duyệt");
                    int orderId = donHangDao.insertDonHang(donHang);
                    if (orderId != 0) {
                        listDonHang.clear();
                        listDonHang.addAll(donHangDao.getDsDonHang());
                        if (totalAmount > 0) {
                            for (GioHang gioHang : list) {
                                if (gioHang.isSelected()) {
                                    SanPhamDAO sanPhamDao = new SanPhamDAO(getContext());
                                    SanPham sanPham = sanPhamDao.getSanPhamById(gioHang.getMaSP());
                                    if (sanPham != null) {
                                        DonHangChiTiet chiTietDonHan = new DonHangChiTiet(orderId, gioHang.getMaSP(), gioHang.getSoLuongMua(), sanPham.getGia(), gioHang.getSoLuongMua() * sanPham.getGia());
                                        chiTietDao.insertDonHangChiTiet(chiTietDonHan);
                                    } else {
                                        Toast.makeText(getContext(), "Sản phẩm không tìm thấy trong cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(getContext(), "Vui lòng chọn sản phẩm để thanh toán", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        // Cập nhật số lượng sản phẩm sau khi thanh toán thành công
                        for (GioHang gioHang : list) {
                            int newQuantity = gioHang.getSoLuongspcl() - gioHang.getSoLuongMua();
                            if (newQuantity < 0) {
                                Toast.makeText(getContext(), "Sản phẩm " + gioHang.getTenSanPham() + "không đủ số lượng trong kho", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            dao.updateSlSanPham(gioHang.getMaSP(), newQuantity);
                        }
                        for (GioHang selected : list) {
                            if (selected.isSelected()) {
                                ghDAO.deleteGioHang(selected);
                            }
                        }

                        binding.txttongtien.setText(String.valueOf(0));
                        list = ghDAO.selectAllGioHang();
                        adapter.updateCartList(list);
                        displayCart(list);

                        Snackbar.make(getView(), "Thanh toán thành công", Snackbar.LENGTH_SHORT).show();


                        Bundle bundle = new Bundle();
                        bundle.putInt("maDonHang", orderId);

                        Fragment_confilm_thanhtoan frgConfilmThanhToan = new Fragment_confilm_thanhtoan();
                        frgConfilmThanhToan.setArguments(bundle);
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout, frgConfilmThanhToan);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thất bại khi thêm đơn hàng!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Thất bại khi cập nhật tài khoản!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Số tiền trong tài khoản không đủ!", Toast.LENGTH_SHORT).show();
            }
        });
        thanhToanBinding.btnThoat.setOnClickListener(view -> dialog.dismiss());

    }
}