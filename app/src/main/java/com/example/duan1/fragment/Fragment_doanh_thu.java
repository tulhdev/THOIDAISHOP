package com.example.duan1.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

import com.example.duan1.DAO.ThongKeDAO;
import com.example.duan1.R;
public class Fragment_doanh_thu extends Fragment {
    TextView txtDoanhThu;
    EditText txtdayEnd, txtdayStart;
    Button btndayStart, btndayEnd, DoanhThu;


    public Fragment_doanh_thu() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        txtdayStart = view.findViewById(R.id.txtdayStart);
        txtdayEnd = view.findViewById(R.id.txtdayEnd);
        btndayStart = view.findViewById(R.id.dayStart);
        btndayEnd = view.findViewById(R.id.dayEnd);
        DoanhThu = view.findViewById(R.id.DoanhThu);
        txtDoanhThu = view.findViewById(R.id.ed_doanhthu);

        Calendar calendar = Calendar.getInstance();
        btndayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String Ngay = "";
                        String Thang = "";
                        if(i2 < 10){
                            Ngay = "0" + i2;
                        }else{
                            Ngay = String.valueOf(i2);
                        }

                        if((i1 + 1) < 10){
                            Thang = "0" + (i1 + 1);
                        }else{
                            Thang = String.valueOf(i1 + 1);
                        }
                        txtdayStart.setText(i + "/" + Thang + "/" + Ngay);
                    }
                },
                        calendar.get(calendar.YEAR),
                        calendar.get(calendar.MONTH),
                        calendar.get(calendar.DAY_OF_MONTH)
                );
                dialog.show();
            }
        });

        btndayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String Ngay = "";
                        String Thang = "";
                        if(i2 < 10){
                            Ngay = "0" + i2;
                        }else{
                            Ngay = String.valueOf(i2);
                        }

                        if((i1 + 1) < 10){
                            Thang = "0" + (i1 + 1);
                        }else{
                            Thang = String.valueOf(i1 + 1);
                        }
                        txtdayEnd.setText(i + "/" + Thang + "/" + Ngay);
                    }
                },
                        calendar.get(calendar.YEAR),
                        calendar.get(calendar.MONTH),
                        calendar.get(calendar.DAY_OF_MONTH)
                );
                dialog.show();
            }
        });

        DoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ThongKeDAO dao = new ThongKeDAO(getContext());
//                String ngaybatdau = txtdayStart.getText().toString();
//                String ngayketthuc = txtdayEnd.getText().toString();
//                int doanhthu1 = dao.getDoanhThu(ngaybatdau, ngayketthuc);
//                txtDoanhThu.setText(doanhthu1 + " VND");
                ThongKeDAO dao = new ThongKeDAO(getContext());
                String ngaybatdau = txtdayStart.getText().toString();
                String ngayketthuc = txtdayEnd.getText().toString();
                int doanhthu1 = dao.getDoanhThu(ngaybatdau, ngayketthuc);

                // Định dạng số tiền với dấu chấm
                DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance();
                decimalFormat.applyPattern("#,###,###,###");
                String formattedDoanhThu = decimalFormat.format(doanhthu1);

                txtDoanhThu.setText(formattedDoanhThu + " VND");
            }
        });
        return view;
    }
}