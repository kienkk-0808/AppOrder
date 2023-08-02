package com.example.kienkk.orderapp.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kienkk.orderapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiMonAnController {
    private final SQLiteDatabase database;

    public LoaiMonAnController(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemLoaiMonAn(String tenloai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_LOAIMONAN_TENLOAI, tenloai);

        long kiemtra = database.insert(CreateDatabase.TB_LOAIMONAN, null, contentValues);

        return kiemtra != 0;
    }

    @SuppressLint({"Recycle", "Range"})
    public List<com.example.kienkk.orderapp.Model.LoaiMonAn> LayDanhSachLoaiMonAn(){
        List<com.example.kienkk.orderapp.Model.LoaiMonAn> loaiMonAns = new ArrayList<>();

        String truyvan = "SELECT * FROM " + CreateDatabase.TB_LOAIMONAN;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            com.example.kienkk.orderapp.Model.LoaiMonAn loaiMonAn = new com.example.kienkk.orderapp.Model.LoaiMonAn();
            loaiMonAn.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_MALOAI)));
            loaiMonAn.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_TENLOAI)));

            loaiMonAns.add(loaiMonAn);

            cursor.moveToNext();
        }

        return loaiMonAns;
    }

    @SuppressLint({"Recycle", "Range"})
    public String LayHinhLoaiMonAn(int maloai){
        String hinhanh = "";
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_MONAN + " WHERE " + CreateDatabase.TB_MONAN_MALOAI + " = '" + maloai + "' "
                + " AND " + CreateDatabase.TB_MONAN_HINHANH + " != '' ORDER BY " + CreateDatabase.TB_MONAN_MAMON + " LIMIT 1";

        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            hinhanh = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH));
            cursor.moveToNext();
        }
        return hinhanh;
    }
}
