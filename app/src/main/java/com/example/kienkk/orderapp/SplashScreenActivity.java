package com.example.kienkk.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.kienkk.orderapp.Controller.NhanVienController;
import com.example.kienkk.orderapp.Controller.QuyenController;
import com.example.kienkk.orderapp.Model.NhanVien;
import com.example.kienkk.orderapp.Database.CreateDatabase;

public class SplashScreenActivity extends AppCompatActivity {
    private SharedPreferences mMoLanDau;
    private SharedPreferences.Editor editor;

    private QuyenController quyenController;
    private NhanVienController nhanVienController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        @SuppressLint("CommitPrefEdits") Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                Log.d("kiemtra", e.getMessage());
            } finally {
                mMoLanDau = getSharedPreferences("SPR_MOLANDAU", 0);
                if (mMoLanDau != null) {
                    boolean firstOpen = mMoLanDau.getBoolean("MOLANDAU", true);

                    if (firstOpen){
                        CreateDatabase createDatabase = new CreateDatabase(this);
                        createDatabase.open();

                        quyenController = new QuyenController(this);
                        quyenController.ThemQuyen("Quản lý");
                        quyenController.ThemQuyen("Nhân viên");

                        nhanVienController = new NhanVienController(this);
                        NhanVien nhanVien = new NhanVien();
                        nhanVien.setTENDANGNHAP("a");
                        nhanVien.setCMND(123456789);
                        nhanVien.setGIOITINH("Nam");
                        nhanVien.setMATKHAU("a");
                        nhanVien.setNGAYSINH("08/08/2001");
                        nhanVien.setMAQUYEN(1);

                        nhanVienController.ThemNV(nhanVien);

                        editor = mMoLanDau.edit();
                        editor.putBoolean("MOLANDAU", false);
                        editor.apply();
                    }

                    Intent iDangNhap = new Intent(this, DangNhapActivity.class);
                    startActivity(iDangNhap);
                    finish();
                }
            }
        });
        thread.start();
    }
}