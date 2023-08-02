package com.example.kienkk.orderapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kienkk.orderapp.Controller.GoiMonController;
import com.example.kienkk.orderapp.Model.ChiTietGoiMon;

public class SoLuongActivity extends AppCompatActivity implements View.OnClickListener{
    int maban, mamonan;
    Button btnDongYThemSoLuong;
    EditText edSoLuong;
    GoiMonController goiMonController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themsoluong);

        btnDongYThemSoLuong = findViewById(R.id.btnDongYThemSoLuong);
        edSoLuong = findViewById(R.id.edSoLuongMonAn);

        goiMonController = new GoiMonController(this);

        Intent intent = getIntent();
        maban = intent.getIntExtra("maban", 0);
        mamonan = intent.getIntExtra("mamonan", 0);

        btnDongYThemSoLuong.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int magoimon = (int) goiMonController.LayMaGoiMonTheoMaBan(maban, "false");
        boolean kiemtra = goiMonController.KiemTraMonAnDaTonTai(magoimon, mamonan);
        if (kiemtra){
            //tiến hành cập nhật món đã tồn tại
            int soluongcu = goiMonController.LaySoLuongMonAnTheoMaGoiMon(magoimon, mamonan);
            int soluongmoi = 0;
            try {
                soluongmoi = Integer.parseInt(edSoLuong.getText().toString());
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Vui longf nhập số",Toast.LENGTH_SHORT).show();
            }


            int tongsoluong = soluongcu + soluongmoi;

            ChiTietGoiMon chiTietGoiMon = new ChiTietGoiMon();
            chiTietGoiMon.setMaGoiMon(magoimon);
            chiTietGoiMon.setMaMonAn(mamonan);
            chiTietGoiMon.setSoLuong(tongsoluong);

            boolean kiemtracapnhat = goiMonController.CapNhatSoLuong(chiTietGoiMon);
            if (kiemtracapnhat)
                Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
        }else {
            //thêm món ăn
            int soluonggoi = Integer.parseInt(edSoLuong.getText().toString());

            ChiTietGoiMon chiTietGoiMon = new ChiTietGoiMon();
            chiTietGoiMon.setMaGoiMon(magoimon);
            chiTietGoiMon.setMaMonAn(mamonan);
            chiTietGoiMon.setSoLuong(soluonggoi);

            boolean kiemtrathem = goiMonController.ThemChiTietGoiMon(chiTietGoiMon);
            if (kiemtrathem)
                Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
