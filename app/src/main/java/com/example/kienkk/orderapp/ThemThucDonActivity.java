package com.example.kienkk.orderapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kienkk.orderapp.CustomAdapter.AdapterHienThiLoaiMonAn;
import com.example.kienkk.orderapp.Controller.LoaiMonAnController;
import com.example.kienkk.orderapp.Controller.MonAnController;
import com.example.kienkk.orderapp.Model.MonAn;

import java.util.List;

public class ThemThucDonActivity extends AppCompatActivity implements View.OnClickListener{
    public static int REQUEST_CODE_THEMLOAITHUCDON = 113;
    public static int REQUEST_CODE_MOHINH = 123;

    private ImageButton imThemLoaiThucDon;
    private Spinner spinLoaiThucDon;

    private LoaiMonAnController loaiMonAnController;
    private MonAnController monAnController;

    private List<com.example.kienkk.orderapp.Model.LoaiMonAn> loaiMonAns;
    private AdapterHienThiLoaiMonAn adapterHienThiLoaiMonAn;

    private ImageView imHinhThucDon;
    private Button btnDongYThemMonAn, btnThoatThemMonAn;
    private String sDuongdanhinh;
    private EditText edTenMonAn, edGiaTien;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themthucdon);

        loaiMonAnController = new LoaiMonAnController(this);
        monAnController = new MonAnController(this);

        imThemLoaiThucDon = findViewById(R.id.imThemLoaiThucDon);
        spinLoaiThucDon = findViewById(R.id.spinLoaiMonAn);
        imHinhThucDon = findViewById(R.id.imHinhThucDon);
        btnDongYThemMonAn = findViewById(R.id.btnDongYThemMonAn);
        btnThoatThemMonAn = findViewById(R.id.btnThoatThemMonAn);
        edTenMonAn = findViewById(R.id.edThemTenMonAn);
        edGiaTien = findViewById(R.id.edThemGiaTien);

        HienThiSpinnerLoaiMonAn();

        //bắt sự kiện click
        imThemLoaiThucDon.setOnClickListener(this);
        imHinhThucDon.setOnClickListener(this);
        btnDongYThemMonAn.setOnClickListener(this);
        btnThoatThemMonAn.setOnClickListener(this);
    }

    private void HienThiSpinnerLoaiMonAn(){
        loaiMonAns = loaiMonAnController.LayDanhSachLoaiMonAn();
        adapterHienThiLoaiMonAn = new AdapterHienThiLoaiMonAn(ThemThucDonActivity.this, R.layout.custom_layout_spinloaithucdon, loaiMonAns);
        spinLoaiThucDon.setAdapter(adapterHienThiLoaiMonAn);
        adapterHienThiLoaiMonAn.notifyDataSetChanged();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.imThemLoaiThucDon:
                Intent iThemLoaiMonAn = new Intent(ThemThucDonActivity.this, ThemLoaiThucDonActivity.class);
                startActivityForResult(iThemLoaiMonAn, REQUEST_CODE_THEMLOAITHUCDON);
                break;
            case R.id.imHinhThucDon:
                Intent iMoHinh = new Intent();
                iMoHinh.setType("image/*");
                iMoHinh.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(iMoHinh,"Chọn hình thực đơn"), REQUEST_CODE_MOHINH);
                break;
            case R.id.btnDongYThemMonAn:
                int vitri = spinLoaiThucDon.getSelectedItemPosition();  //trả về vị trí item đã chọn
                int maloai = loaiMonAns.get(vitri).getMaLoai();
                String tenmonan = edTenMonAn.getText().toString();
                String giatien = edGiaTien.getText().toString();
                try {
                    Integer.parseInt(edGiaTien.getText().toString());
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Vui longf nhập giá tièn là số",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                }

                if (tenmonan != null && giatien != null && !tenmonan.equals("") && !giatien.equals("")){
                    MonAn monAn = new MonAn();
                    monAn.setGiaTien(giatien);
                    monAn.setHinhAnh(sDuongdanhinh);
                    monAn.setMaLoai(maloai);
                    monAn.setTenMonAn(tenmonan);

                   boolean kiemtra = monAnController.ThemMonAn(monAn);
                    if (kiemtra)
                        Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
                    finish();
                }else
                    Toast.makeText(this, getResources().getString(R.string.loithemmonan), Toast.LENGTH_SHORT).show();

                break;
            case R.id.btnThoatThemMonAn:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_THEMLOAITHUCDON){
            if (resultCode == Activity.RESULT_OK ){
                Intent dulieu = data;
                boolean kiemtra = dulieu.getBooleanExtra("kiemtraloaithucdon", false);
                if (kiemtra){
                    HienThiSpinnerLoaiMonAn();
                    Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }
        }else if (REQUEST_CODE_MOHINH == requestCode){
            if (resultCode == Activity.RESULT_OK && data != null){
                sDuongdanhinh = data.getData().toString();
                imHinhThucDon.setImageURI(data.getData());
            }
        }
    }
}
