package com.example.kienkk.orderapp.Model;

public class ThongKe {
    private String Ngay,TongTien;

    public ThongKe(String ngay, String tongTien) {
        Ngay = ngay;
        TongTien = tongTien;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String tongTien) {
        TongTien = tongTien;
    }
}
