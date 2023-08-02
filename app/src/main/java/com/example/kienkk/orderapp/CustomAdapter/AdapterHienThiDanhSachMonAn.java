package com.example.kienkk.orderapp.CustomAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kienkk.orderapp.Model.MonAn;
import com.example.kienkk.orderapp.R;

import java.util.List;

public class AdapterHienThiDanhSachMonAn extends BaseAdapter{
    private final Context context;
    private final int layout;
    private final List<MonAn> monAnList;
    private ViewHolderHienThiDanhSachMonAn viewHolderHienThiDanhSachMonAn;

    public AdapterHienThiDanhSachMonAn(Context context, int layout, List<MonAn> monAnList){
        this.context = context;
        this.layout = layout;
        this.monAnList = monAnList;
    }

    @Override
    public int getCount() {
        return monAnList.size();
    }

    @Override
    public Object getItem(int position) {
        return monAnList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return monAnList.get(position).getMaMonAn();
    }

    public static class ViewHolderHienThiDanhSachMonAn{
        ImageView imHinhMonAn;
        TextView txtTenMonAn, txtGiaTien;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderHienThiDanhSachMonAn = new ViewHolderHienThiDanhSachMonAn();
            view = inflater.inflate(layout, parent, false);

            viewHolderHienThiDanhSachMonAn.imHinhMonAn = view.findViewById(R.id.imHienThiDSMonAn);
            viewHolderHienThiDanhSachMonAn.txtTenMonAn = view.findViewById(R.id.txtTenDSMonAn);
            viewHolderHienThiDanhSachMonAn.txtGiaTien = view.findViewById(R.id.txtGiaTienDSMonAn);

            view.setTag(viewHolderHienThiDanhSachMonAn);
        }else
            viewHolderHienThiDanhSachMonAn = (ViewHolderHienThiDanhSachMonAn) view.getTag();

        MonAn monAn = monAnList.get(position);
        String hinhanh = monAn.getHinhAnh();

        if(hinhanh == null || hinhanh.equals(""))
            viewHolderHienThiDanhSachMonAn.imHinhMonAn.setImageResource(R.drawable.background);
        else{
            Uri uri = Uri.parse(hinhanh);
            viewHolderHienThiDanhSachMonAn.imHinhMonAn.setImageURI(uri);
        }

        viewHolderHienThiDanhSachMonAn.txtTenMonAn.setText(monAn.getTenMonAn());
        viewHolderHienThiDanhSachMonAn.txtGiaTien.setText(context.getResources().getString(R.string.gia) + ": " + monAn.getGiaTien());

        return view;
    }
}
