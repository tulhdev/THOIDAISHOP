package com.example.duan1.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.duan1.Adapter.Adapter_SanPham;
import com.example.duan1.Adapter.Adapter_trangchu;
import com.example.duan1.Adapter.adapter_slide;
import com.example.duan1.DAO.SanPhamDAO;
import com.example.duan1.Gio_Hang_Activity;
import com.example.duan1.Model.SanPham;
import com.example.duan1.Model.Slideiten;
import com.example.duan1.R;
import com.example.duan1.TimKiem;
import com.example.duan1.sanphamchitiet;
import me.relex.circleindicator.CircleIndicator3;

public class Fragment_TrangChu extends Fragment {
    TextView txttennguoidung_tt;
    ImageView ImgGiohang;
    RecyclerView rcvsanpham_tt;
    SanPhamDAO spDAO;
    Adapter_trangchu adaptersptt;
    Adapter_trangchu adapter_slide;
    private ArrayList<SanPham> list = new ArrayList<>();
    // lider
    private List<Slideiten> slidelist;
    private Handler slideHanlder = new Handler(Looper.getMainLooper());
    ViewPager2 viewpage;
    CircleIndicator3 chamduoi;
    public Fragment_TrangChu() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__trang_chu, container, false);
        txttennguoidung_tt = view.findViewById(R.id.txttennguoidung_tt);
        EditText edttimkiem = view.findViewById(R.id.edttimkiem);
         viewpage = view.findViewById(R.id.viewpage);
         chamduoi = view.findViewById(R.id.chamduoi);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DANGNHAPTV", Context.MODE_PRIVATE);
        String loai = sharedPreferences.getString("HoTen","");
        txttennguoidung_tt.setText(loai);
        ///

        rcvsanpham_tt =view.findViewById(R.id.rcvsanpham_tt);
        spDAO= new SanPhamDAO(getContext());
        list = spDAO.selectAllSanPham();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rcvsanpham_tt.setLayoutManager(layoutManager);
        adaptersptt = new Adapter_trangchu(getContext(), list);
        rcvsanpham_tt.setAdapter(adaptersptt);

//        ImgGiohang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), Gio_Hang_Activity.class);
//                startActivity(intent);
//            }
//        });
        adaptersptt.setClick(new Adapter_SanPham.click() {
            @Override
            public void click(int pos) {
                SanPham hienthi = list.get(pos);
                Intent intent = new Intent(getContext(), sanphamchitiet.class);
                intent.putExtra("sanphamct",hienthi);
                startActivity(intent);
            }
        });
        edttimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),  TimKiem.class);
                startActivity(intent);
            }
        });
        // slider
        slidelist = new ArrayList<>(); // Khởi tạo slidelist trước khi sử dụng
        slidelist.add(new Slideiten(R.drawable.bannertt));
        slidelist.add(new Slideiten(R.drawable.slider1));
        slidelist.add(new Slideiten(R.drawable.slider2));
        slidelist.add(new Slideiten(R.drawable.slider3));
        slidelist.add(new Slideiten(R.drawable.slider4));
        slidelist.add(new Slideiten(R.drawable.slider5));
        viewpage.setAdapter(new adapter_slide(slidelist, viewpage));
        chamduoi.setViewPager(viewpage);
        //cài đặt thuộc tính viewpager 2
        viewpage.setClipToPadding(false);
        viewpage.setClipChildren(false);
        viewpage.setOffscreenPageLimit(3);///nhìn đc 3 ảnh :2 ảnh 2 bên và một ảnh ở giữa
        viewpage.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewpage.setPageTransformer(compositePageTransformer);
        viewpage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHanlder.removeCallbacks(sildeRunnable);
                slideHanlder.postDelayed(sildeRunnable, 3000);
                // Kiểm tra nếu đang ở vị trí cuối cùng
//                if (position == slidelist.size() - 1) {
//                    // Post runnable để tự động cuộn về vị trí đầu tiên
//                    slideHanlder.postDelayed(sildeRunnable, 3000);
//                } else {
//                    // Nếu không phải vị trí cuối cùng, hủy runnable
//                    slideHanlder.removeCallbacks(sildeRunnable);
//                }
            }
        });




        return view;
    }
    // s
    private Runnable sildeRunnable = new Runnable() {
        @Override
        public void run() {
//            binding.viewpage.setCurrentItem(binding.viewpage.getCurrentItem() + 1);
            int vitri = viewpage.getCurrentItem();
            if (vitri == slidelist.size() - 1) {
                viewpage.setCurrentItem(0);
            } else {
                viewpage.setCurrentItem(vitri + 1);
            }
        }
    };
    @Override
    public void onPause() {
        //khi thoat ra ngoai man home
        super.onPause();
        slideHanlder.removeCallbacks(sildeRunnable);
    }

    @Override
    public void onResume() {
        //khi quay tro lai man home
        super.onResume();
        slideHanlder.postDelayed(sildeRunnable, 3000);
    }
}