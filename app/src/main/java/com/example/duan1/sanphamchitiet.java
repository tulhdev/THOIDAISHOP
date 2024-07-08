package com.example.duan1;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

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
import com.example.duan1.Model.ThanhVien;
import com.example.duan1.Viewmd.SharedViewModel;
import com.example.duan1.databinding.DialogConfilmThanhToanBinding;
import com.example.duan1.databinding.FragmentGioHangBinding;
import com.example.duan1.fragment.Fragment_confilm_thanhtoan;
import com.example.duan1.fragment.Fragment_donHang_chiTiet;

public class sanphamchitiet extends AppCompatActivity{
    TextView txttensp_ct,txtmasp_ct,txtgiasp_ct,txtsoluongsp_ct,txtsize_ct,txttenth_ct,txttenlsp_ct;
    Button btnthemgh_ct,btnMuangay_ct;
    EditText edt_soLuong_hd;
    Spinner spn_sanpham_hd,spn_thanhVien_hd;
    //HoaDonDAO hddao;
    SanPhamDAO spdao;
    GioHangDAO ghdao;
    private SharedViewModel sharedViewModel;

    private ArrayList<SanPham> listSanPham = new ArrayList<>();
    private ArrayList<GioHang> list = new ArrayList<>();

    View gView;
    Toolbar toolbar;
    RecyclerView recycleView;
    Button btnmuahang;
    //    Adapter_GioHang adapter;
    GioHangDAO ghDAO;
    DonHangDao donHangDao;
    DonHangChiTietDao chiTietDao;
    private Adapter_GioHang adapter;
    private ArrayList<DonHang> listDonHang = new ArrayList<>();
    SanPhamDAO dao;
    ImageView back, ImaSP;
     // Biến thành viên để lưu đường dẫn ảnh
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanphamchitiet);
        // ánh xạ
        back = findViewById(R.id.back);
        ImaSP = findViewById(R.id.ImaSP);
        txttensp_ct = findViewById(R.id.txttensp_ct);
        txtmasp_ct = findViewById(R.id.txtmasp_ct);
        txtgiasp_ct = findViewById(R.id.txtgiasp_ct);
        txtsoluongsp_ct = findViewById(R.id.txtsoluongsp_ct);
        txtsize_ct = findViewById(R.id.txtsize_ct);
        txttenth_ct = findViewById(R.id.txttenth_ct);
        txttenlsp_ct = findViewById(R.id.txttenlsp_ct);
        ///
        btnthemgh_ct = findViewById(R.id.btnthemgh_ct);
        btnMuangay_ct = findViewById(R.id.btnMuangay_ct);
        //hddao = new HoaDonDAO(this);
        spdao = new SanPhamDAO(this);
        ghdao = new GioHangDAO(this);
        listSanPham = spdao.selectAllSanPham();
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        adapter = new Adapter_GioHang(this, list);
        ghDAO = new GioHangDAO(this);
        ItemTouchHelper sw = new ItemTouchHelper(new swipe(adapter));

        chiTietDao = new DonHangChiTietDao(this);
        donHangDao = new DonHangDao(this);

        Intent intent = getIntent();
        if (intent != null) {
            SanPham sanPham = intent.getParcelableExtra("sanphamct");
            String anhSP = intent.getStringExtra("anhsp");
            if (sanPham != null) {
                // Load ảnh vào ImageView
                Picasso.get().load(anhSP).into(ImaSP);
                txttensp_ct.setText(sanPham.getTenSP());
                txtmasp_ct.setText(String.valueOf(sanPham.getMaSP()));
                txtgiasp_ct.setText(String.valueOf(sanPham.getGia()));
                txtsoluongsp_ct.setText(String.valueOf(sanPham.getSoLuong()));
                txtsize_ct.setText("Size " + sanPham.getSize());
                txttenth_ct.setText(sanPham.getTenthuonghieu());
                txttenlsp_ct.setText(sanPham.getTenlsp());


            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sanphamchitiet.this, Layout.class);
                startActivity(intent);
                finish();
            }
        });
    btnMuangay_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogThanhToan();
            }
        });


        btnthemgh_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SanPham selectedSanPham = intent.getParcelableExtra("sanphamct");
                if (selectedSanPham != null) {
                    addToCart(selectedSanPham);
                }
            }

        });
        // nhận dữ liệu được chuyền


    }
    private void addToCart(SanPham sp) {
        int masp = sp.getMaSP();
        int slSanPham = getSoLuongSp(masp);
        int mand = getSharedPreferences("DANGNHAPTV", MODE_PRIVATE).getInt("id", 0);
        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        if (!sharedViewModel.isProductInCart(masp)) {
            if (slSanPham > 0) {
                // Nếu số lượng sản phẩm > 0, thêm sản phẩm vào giỏ hàng với số lượng là 1
                sharedViewModel.setMasp(masp);
                sharedViewModel.setAddToCartClicked(true);
                sharedViewModel.addProductToCart(masp);
                sharedViewModel.setQuantityToAdd(1);
                ghdao.insertGioHang(new GioHang(masp, mand, 1));
                Snackbar.make(getWindow().getDecorView().getRootView(), "Đã thêm vào giỏ hàng", Snackbar.LENGTH_SHORT).show();
            } else {
                // Nếu số lượng sản phẩm <= 0, thông báo người dùng
                Toast.makeText(this, "Sản phẩm đã hết hàng", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Nếu sản phẩm đã có trong giỏ hàng
            GioHang gioHang = ghdao.getGioHangByMasp(masp, mand);
            if (gioHang != null) {
                if (slSanPham > 0 && gioHang.getSoLuongMua() < slSanPham) {
                    // Nếu có số lượng sản phẩm > 0 và số lượng trong giỏ hàng chưa đạt giới hạn
                    gioHang.setSoLuongMua(gioHang.getSoLuongMua() + 1);
                    ghdao.updateGioHang(gioHang);
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Đã cập nhật giỏ hàng thành công", Snackbar.LENGTH_SHORT).show();
                } else {
                    // Nếu số lượng trong giỏ hàng đã đạt giới hạn hoặc số lượng sản phẩm <= 0, thông báo người dùng
                    Toast.makeText(this, "Số lượng sản phẩm đã đạt giới hạn", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private int getSoLuongSp(int maSanPham) {
        for (SanPham s : listSanPham) {
            if (s.getMaSP() == maSanPham) {
                return s.getSoLuong();
            }
        }
        return 0;
    }
    private void showDialogThanhToan() {
        LayoutInflater layoutInflater = getLayoutInflater();
        DialogConfilmThanhToanBinding thanhToanBinding = DialogConfilmThanhToanBinding.inflate(layoutInflater);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(thanhToanBinding.getRoot());
        AlertDialog dialog = builder.create();
        dialog.show();
        thanhToanBinding.btnThanhToan.setOnClickListener(view -> {
            for (GioHang gioHang : list) {
                if (gioHang.getSoLuongMua() == 0) {
                    Toast.makeText(this, "Sản phẩm " + gioHang.getTenSanPham() + " đã hết hàng", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            int totalAmount = Integer.parseInt(txtgiasp_ct.getText().toString());
            SharedPreferences sharedPreferences = getSharedPreferences("DANGNHAPTV", MODE_PRIVATE);
            int mand = sharedPreferences.getInt("id", 0);
            int tienHienCo = sharedPreferences.getInt("SoTien", 0);

            LocalDate currentDate = LocalDate.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String ngayHienTai = currentDate.format(formatter);

            if (tienHienCo >= totalAmount) {
                int soTienConLai = tienHienCo - totalAmount;
                ThanhVienDAO nguoiDungDao = new ThanhVienDAO(this);
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
                                    Intent intent = getIntent();
                                    SanPham sanPhamtt = intent.getParcelableExtra("sanphamct");
                                    if (sanPhamtt != null) {
                                        DonHangChiTiet chiTietDonHan = new DonHangChiTiet(orderId, sanPhamtt.getMaSP(), 1, sanPhamtt.getGia(), sanPhamtt.getGia());
                                        chiTietDao.insertDonHangChiTiet(chiTietDonHan);
                                    } else {
                                        Toast.makeText(this, "Sản phẩm không tìm thấy trong cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
                                    }


                        } else {
                            Toast.makeText(this, "Vui lòng chọn sản phẩm để thanh toán", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        // Cập nhật số lượng sản phẩm sau khi thanh toán thành công
                        for (SanPham sanPham : listSanPham) {
                            int spm = 1;
                            int newQuantity = sanPham.getSoLuong() - spm;
                            if (newQuantity < 0) {
                                Toast.makeText(this, "Sản phẩm " + sanPham.getTenSP() + "không đủ số lượng trong kho", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            spdao.updateSlSanPham(sanPham.getMaSP(), newQuantity);
                        }

                        Bundle bundle = new Bundle();
                        bundle.putInt("maDonHang", orderId);

                        Fragment_donHang_chiTiet frgConfilmThanhToan = new Fragment_donHang_chiTiet();
                        frgConfilmThanhToan.setArguments(bundle);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(this, "Thất bại khi thêm đơn hàng!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Thất bại khi cập nhật tài khoản!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Số tiền trong tài khoản không đủ!", Toast.LENGTH_SHORT).show();
            }
        });
        thanhToanBinding.btnThoat.setOnClickListener(view -> dialog.dismiss());

    }

}