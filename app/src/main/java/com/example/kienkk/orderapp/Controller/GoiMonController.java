package com.example.kienkk.orderapp.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kienkk.orderapp.Model.ChiTietGoiMon;
import com.example.kienkk.orderapp.Model.GoiMon;
import com.example.kienkk.orderapp.Model.ThanhToan;
import com.example.kienkk.orderapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class GoiMonController {
    private final SQLiteDatabase database;

    public GoiMonController(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemGoiMon(GoiMon goiMon){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_MABAN, goiMon.getMaBan());
        contentValues.put(CreateDatabase.TB_GOIMON_MANV, goiMon.getMaNhanVien());
        contentValues.put(CreateDatabase.TB_GOIMON_NGAYGOI, goiMon.getNgayGoi());
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG, goiMon.getTinhTrang());

        return database.insert(CreateDatabase.TB_GOIMON, null, contentValues);
    }

    @SuppressLint({"Recycle", "Range"})
    public long LayMaGoiMonTheoMaBan(int maban, String tinhtrang){
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_GOIMON + " WHERE " + CreateDatabase.TB_GOIMON_MABAN + " = '" + maban + "' AND "
                + CreateDatabase.TB_GOIMON_TINHTRANG + " = '" + tinhtrang + "'";

        long magoimon = 0;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            magoimon = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_MAGOIMON));
            cursor.moveToNext();
        }
        return magoimon;
   }

    @SuppressLint("Recycle")
    public boolean KiemTraMonAnDaTonTai(int magoimon, int mamonan){
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN
                + " = " + mamonan + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + magoimon;

        Cursor cursor = database.rawQuery(truyvan, null);
        return cursor.getCount() != 0;
    }

    @SuppressLint({"Recycle", "Range"})
    public int LaySoLuongMonAnTheoMaGoiMon(int magoimon, int mamonan){
        int soluong = 0;
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN
                + " = " + mamonan + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + magoimon;

        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            soluong = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG));
            cursor.moveToNext();
        }
        return soluong;
    }

    public boolean CapNhatSoLuong(ChiTietGoiMon chiTietGoiMon){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG, chiTietGoiMon.getSoLuong());

        long kiemtra = database.update(CreateDatabase.TB_CHITIETGOIMON, contentValues ,CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + chiTietGoiMon.getMaGoiMon() + " AND "
                + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = " + chiTietGoiMon.getMaMonAn(), null);
        return kiemtra != 0;
    }

    public boolean ThemChiTietGoiMon(ChiTietGoiMon chiTietGoiMon){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG, chiTietGoiMon.getSoLuong());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAGOIMON, chiTietGoiMon.getMaGoiMon());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAMONAN, chiTietGoiMon.getMaMonAn());

        long kiemtra =  database.insert(CreateDatabase.TB_CHITIETGOIMON, null, contentValues);
        return kiemtra != 0;
    }

    @SuppressLint({"Recycle", "Range"})
    public List<ThanhToan> LayDanhSachMonAnTheoMaGoiMon(int magoimon){
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " ct," + CreateDatabase.TB_MONAN + " ma WHERE "
                + "ct." + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = ma." + CreateDatabase.TB_MONAN_MAMON
                + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + magoimon;

        List<ThanhToan> thanhToans = new ArrayList<>();
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ThanhToan thanhToan = new ThanhToan();
            thanhToan.setSoLuong(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG)));
            thanhToan.setGiatien(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            thanhToan.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));

            thanhToans.add(thanhToan);
            cursor.moveToNext();
        }
        return thanhToans;
    }

    public boolean CapNhatTrangThaiGoiMonTheoMaBan(int maban, String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG, tinhtrang);

        long kiemtra = database.update(CreateDatabase.TB_GOIMON, contentValues, CreateDatabase.TB_GOIMON_MABAN + " = " + maban, null);
        return kiemtra != 0;
    }
}
