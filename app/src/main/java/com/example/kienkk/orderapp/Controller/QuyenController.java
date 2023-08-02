package com.example.kienkk.orderapp.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kienkk.orderapp.Model.Quyen;
import com.example.kienkk.orderapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class QuyenController {
    private final SQLiteDatabase database;

    public QuyenController(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public void ThemQuyen(String tenquyen){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_QUYEN_TENQUYEN, tenquyen);
        database.insert(CreateDatabase.TB_QUYEN, null, contentValues);
    }

    @SuppressLint({"Recycle", "Range"})
    public List<Quyen> LayDanhSachQuyen(){
        List<Quyen> quyens = new ArrayList<Quyen>();
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_QUYEN;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            Quyen quyen = new Quyen();
            quyen.setMaQuyen(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MAQUYEN)));
            quyen.setTenQuyen(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_QUYEN_TENQUYEN)));

            quyens.add(quyen);

            cursor.moveToNext();
        }

        return quyens;
    }
}
