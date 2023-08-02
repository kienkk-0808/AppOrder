package com.example.kienkk.orderapp.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kienkk.orderapp.Model.NhanVien;
import com.example.kienkk.orderapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class NhanVienController {
    private final SQLiteDatabase database;

    public NhanVienController(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemNV(NhanVien nhanVien){ //do các trường manv, hoten, v.v là đối tượng của nhanvien mà đối tượng nhân viên đã khai báo trong NHANVIENDTO
        //SQLite nhận vào 1 contentValues
        ContentValues contentValues = new ContentValues();

        //put các trường dữ liệu
        contentValues.put(CreateDatabase.TB_NHANVIEN_TEDN, nhanVien.getTENDANGNHAP());
        contentValues.put(CreateDatabase.TB_NHANVIEN_CMND, nhanVien.getCMND());
        contentValues.put(CreateDatabase.TB_NHANVIEN_GIOITINH, nhanVien.getGIOITINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MATKHAU, nhanVien.getMATKHAU());
        contentValues.put(CreateDatabase.TB_NHANVIEN_NGAYSINH, nhanVien.getNGAYSINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MAQUYEN, nhanVien.getMAQUYEN());

        // thêm nhân vien
        long kiemtra = database.insert(CreateDatabase.TB_NHANVIEN, null,contentValues);
        return kiemtra != 0;
    }

    @SuppressLint("Recycle")
    public boolean KiemTraNhanVien(){
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN;
        Cursor cursor = database.rawQuery(truyvan, null); //rawQuery cho phép truy vấn câu query đơn giản
        return cursor.getCount() != 0;
    }

    @SuppressLint({"Recycle", "Range"})
    public int KiemTraDangNhap(String tendangnhap, String matkhau){
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN + " WHERE " + CreateDatabase.TB_NHANVIEN_TEDN + " = '" + tendangnhap
                + "' AND " + CreateDatabase.TB_NHANVIEN_MATKHAU + " = '" + matkhau + "'";

        int manhanvien = 0;
        Cursor cursor = database.rawQuery(truyvan, null); //rawQuery cho phép truy vấn câu query đơn giản
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            manhanvien = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MANV));
            cursor.moveToNext();
        }

        return manhanvien;
    }

    @SuppressLint({"Recycle", "Range"})
    public List<NhanVien> LayDanhSachNhanVien(){
        List<NhanVien> nhanviens = new ArrayList<>();
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            NhanVien nhanVien = new NhanVien();
            nhanVien.setGIOITINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_GIOITINH)));
            nhanVien.setNGAYSINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_NGAYSINH)));
            nhanVien.setCMND(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_CMND)));
            nhanVien.setMATKHAU(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MATKHAU)));
            nhanVien.setTENDANGNHAP(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_TEDN)));
            nhanVien.setMANV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MANV)));

            nhanviens.add(nhanVien);
            cursor.moveToNext();
        }

        return nhanviens;
    }

    public boolean XoaNhanVien(int manhanvien){
        long kiemtra = database.delete(CreateDatabase.TB_NHANVIEN, CreateDatabase.TB_NHANVIEN_MANV + " = " + manhanvien, null);
        return kiemtra != 0;
    }

    @SuppressLint({"Recycle", "Range"})
    public int LayQuyenNhanVien(int manv){
        int maquyen = 0;
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN + " WHERE " + CreateDatabase.TB_NHANVIEN_MANV + " = " + manv;
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            maquyen = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MAQUYEN));
            cursor.moveToNext();
        }

        return maquyen;
    }

    @SuppressLint({"Recycle", "Range"})
    public NhanVien LayDanhSachNhanVienTheoMa(int manhanvien){
        NhanVien nhanVien = new NhanVien();
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN + " WHERE " + CreateDatabase.TB_NHANVIEN_MANV + " = " + manhanvien;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            nhanVien.setGIOITINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_GIOITINH)));
            nhanVien.setNGAYSINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_NGAYSINH)));
            nhanVien.setCMND(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_CMND)));
            nhanVien.setMATKHAU(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MATKHAU)));
            nhanVien.setTENDANGNHAP(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_TEDN)));
            nhanVien.setMANV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MANV)));
            nhanVien.setMAQUYEN(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_QUYEN_MAQUYEN)));

            cursor.moveToNext();
        }

        return nhanVien;
    }

    public boolean SuaNV(NhanVien nhanVien){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NHANVIEN_TEDN, nhanVien.getTENDANGNHAP());
        contentValues.put(CreateDatabase.TB_NHANVIEN_CMND, nhanVien.getCMND());
        contentValues.put(CreateDatabase.TB_NHANVIEN_GIOITINH, nhanVien.getGIOITINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MATKHAU, nhanVien.getMATKHAU());
        contentValues.put(CreateDatabase.TB_NHANVIEN_NGAYSINH, nhanVien.getNGAYSINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MAQUYEN, nhanVien.getMAQUYEN());

        long kiemtra = database.update(CreateDatabase.TB_NHANVIEN, contentValues, CreateDatabase.TB_NHANVIEN_MANV + " = " + nhanVien.getMANV(), null);
        return kiemtra != 0;
    }
}