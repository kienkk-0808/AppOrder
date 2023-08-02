package com.example.kienkk.orderapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kienkk.orderapp.CustomAdapter.AdapterHienThiThanhToan;
import com.example.kienkk.orderapp.Controller.BanAnController;
import com.example.kienkk.orderapp.Controller.GoiMonController;
import com.example.kienkk.orderapp.Model.ThanhToan;

import java.util.List;

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener{
    private GridView gridView;
    private Button btnThanhToan, btnThoat;
    private TextView txtTongTien;
    private GoiMonController goiMonController;
    private List<ThanhToan> thanhToanList;
    private AdapterHienThiThanhToan adapterHienThiThanhToan;
    private BanAnController banAnController;
    private FragmentManager fragmentManager;

    long tongtien = 0;
    int maban;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhtoan);

        gridView = findViewById(R.id.gvThanhToan);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnThoat = findViewById(R.id.btnThoatThanhToan);
        txtTongTien = findViewById(R.id.txtTongTien);

        goiMonController = new GoiMonController(this);
        banAnController = new BanAnController(this);

        fragmentManager = getSupportFragmentManager();

        maban = getIntent().getIntExtra("maban", 0);
        if (maban != 0){
            HienThiThanhToan();

            for (int i = 0; i < thanhToanList.size(); i++){
                int soluong = thanhToanList.get(i).getSoLuong();
                int giatien = thanhToanList.get(i).getGiatien();

                tongtien += (soluong * giatien);
            }
            txtTongTien.setText(getResources().getString(R.string.tongcong) + " " + tongtien);
        }

        btnThanhToan.setOnClickListener(this);
        btnThoat.setOnClickListener(this);
    }

    private void HienThiThanhToan(){
        int magoimon = (int) goiMonController.LayMaGoiMonTheoMaBan(maban, "false");
        thanhToanList = goiMonController.LayDanhSachMonAnTheoMaGoiMon(magoimon);

        adapterHienThiThanhToan = new AdapterHienThiThanhToan(this, R.layout.custom_layout_hienthithanhtoan, thanhToanList);
        gridView.setAdapter(adapterHienThiThanhToan);
        adapterHienThiThanhToan.notifyDataSetChanged();
    }

    @SuppressLint({"NonConstantResourceId", "ShowToast"})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnThanhToan:
                boolean kiemrabanan = banAnController.CapNhatTinhTrangBan(maban, "false");
                boolean kiemtragoimom = goiMonController.CapNhatTrangThaiGoiMonTheoMaBan(maban, "true");

                if(kiemrabanan && kiemtragoimom){
                    Toast.makeText(ThanhToanActivity.this, getResources().getString(R.string.thanhtoanthanhcong), Toast.LENGTH_SHORT);
                    HienThiThanhToan();
                }else
                    Toast.makeText(ThanhToanActivity.this, getResources().getString(R.string.loi), Toast.LENGTH_SHORT);
                break;
            case R.id.btnThoatThanhToan:
                finish();
                break;
        }
    }
}
