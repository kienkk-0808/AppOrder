package com.example.kienkk.orderapp.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kienkk.orderapp.Model.MonAn;
import com.example.kienkk.orderapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class MonAnController {
    private final SQLiteDatabase database;

    public MonAnController(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemMonAn(MonAn monAn){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_MONAN_TENMONAN, monAn.getTenMonAn());
        contentValues.put(CreateDatabase.TB_MONAN_GIATIEN, monAn.getGiaTien());
        contentValues.put(CreateDatabase.TB_MONAN_MALOAI, monAn.getMaLoai());
        contentValues.put(CreateDatabase.TB_MONAN_HINHANH, monAn.getHinhAnh());

        long kiemtra = database.insert(CreateDatabase.TB_MONAN,null,contentValues);
        return kiemtra != 0;
    }

    @SuppressLint({"Recycle", "Range"})
    public List<MonAn> LayDanhSachMonAnTheoLoai(int maloai){
        List<MonAn> monAns = new ArrayList<MonAn>();

        String truyvan = "SELECT * FROM " + CreateDatabase.TB_MONAN + " WHERE " + CreateDatabase.TB_MONAN_MALOAI + " = '" + maloai + "' ";
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            MonAn monAn = new MonAn();
            monAn.setHinhAnh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH)));
            monAn.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));
            monAn.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            monAn.setMaMonAn(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MAMON)));
            monAn.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MALOAI)));

            monAns.add(monAn);
            cursor.moveToNext();
        }

        return monAns;
    }
}
