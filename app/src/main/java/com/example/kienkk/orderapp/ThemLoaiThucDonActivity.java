package com.example.kienkk.orderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kienkk.orderapp.Controller.LoaiMonAnController;

public class ThemLoaiThucDonActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnDongYThemLoaiThucDon;
    private EditText edTenLoai;
    private LoaiMonAnController loaiMonAnController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themloaithucdon);

        loaiMonAnController = new LoaiMonAnController(this);

        btnDongYThemLoaiThucDon = findViewById(R.id.btnDongYThemLoaiThucDon);
        edTenLoai = findViewById(R.id.edThemLoaiThucDon);

        btnDongYThemLoaiThucDon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String sTenLoaiThucDon = edTenLoai.getText().toString();
        if (sTenLoaiThucDon != null || sTenLoaiThucDon.equals("")){
            boolean kiemtra = loaiMonAnController.ThemLoaiMonAn(sTenLoaiThucDon);
            Intent iDuLieu = new Intent();
            iDuLieu.putExtra("kiemtraloaithucdon", kiemtra);
            setResult(Activity.RESULT_OK, iDuLieu);
            finish();
        }else
            Toast.makeText(this, getResources().getString(R.string.vuilongnhapdulieu), Toast.LENGTH_SHORT).show();
    }
}
