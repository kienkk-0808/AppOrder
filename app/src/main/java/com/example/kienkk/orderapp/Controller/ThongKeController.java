package com.example.kienkk.orderapp.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kienkk.orderapp.Database.CreateDatabase;
import com.example.kienkk.orderapp.Model.GoiMon;
import com.example.kienkk.orderapp.Model.ThanhToan;
import com.example.kienkk.orderapp.Model.ThongKe;

import java.util.ArrayList;
import java.util.List;

public class ThongKeController {
    private final SQLiteDatabase database;
    private GoiMonController goiMonController;

    public ThongKeController(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        goiMonController = new GoiMonController(context);
        database = createDatabase.open();
    }

    @SuppressLint("Range")
    public List<ThongKe> LayTatCaThongKe(){
        List<ThongKe> thongke = new ArrayList<>();
        List<GoiMon> goiMons = new ArrayList<>();
        List<ThanhToan> thanhToans = new ArrayList<>();
        String truyVanGoiMon= "SELECT * FROM " + CreateDatabase.TB_GOIMON;
        Cursor cursor = database.rawQuery(truyVanGoiMon, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            GoiMon goiMon = new GoiMon();
            goiMon.setNgayGoi(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_NGAYGOI)));
            goiMon.setMaGoiMon(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_MAGOIMON)));
            goiMon.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_TINHTRANG)));
            goiMons.add(goiMon);
            cursor.moveToNext();
        }

        for (int i = 0; i < goiMons.size();i++){
            int tien1Bill = 0;
            thanhToans.clear();
            if (goiMons.get(i).getTinhTrang().equals("true")){
                thanhToans = goiMonController.LayDanhSachMonAnTheoMaGoiMon(goiMons.get(i).getMaGoiMon());
                for (int j = 0; j < thanhToans.size(); j++){
                    int soluong = thanhToans.get(j).getSoLuong();
                    int giatien = thanhToans.get(j).getGiatien();

                    tien1Bill += (soluong * giatien);
                }
            }
            thongke.add(new ThongKe(goiMons.get(i).getNgayGoi(),tien1Bill+""));
        }

        for (int i = 0; i < thongke.size();i++){
            if( i >= 0 && i < thongke.size()-1){
                if (thongke.get(i).getNgay().equals(thongke.get(i+1).getNgay())){
                    int tien = Integer.parseInt(thongke.get(i).getTongTien())+Integer.parseInt(thongke.get(i+1).getTongTien());
                    thongke.get(i).setTongTien(tien+"");
                    thongke.remove(i+1);
                    i--;
                }
            }
        }

//        test.add(new ThongKe("abc","100000"));
//        test.add(new ThongKe("abc","10000"));
        return thongke;
    }
}
