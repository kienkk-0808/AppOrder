package com.example.kienkk.orderapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kienkk.orderapp.Model.NhanVien;
import com.example.kienkk.orderapp.R;

import java.util.List;

public class AdapterHienThiNhanVien extends BaseAdapter{
    private final Context context;
    private final int layout;
    private final List<NhanVien> nhanVienList;

    private ViewHolderNhanVien viewHolderNhanVien;

    public AdapterHienThiNhanVien(Context context, int layout, List<NhanVien> nhanVienList){
        this.context = context;
        this.layout = layout;
        this.nhanVienList = nhanVienList;
    }

    @Override
    public int getCount() {
        return nhanVienList.size();
    }

    @Override
    public Object getItem(int position) {
        return nhanVienList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return nhanVienList.get(position).getMANV();
    }

    public static class ViewHolderNhanVien{
        ImageView imHinhNhanVien;
        TextView txtTenNhanVien, txtCMND;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderNhanVien = new ViewHolderNhanVien();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false) ;

            viewHolderNhanVien.imHinhNhanVien = view.findViewById(R.id.imHinhNhanVien);
            viewHolderNhanVien.txtTenNhanVien = view.findViewById(R.id.txtTenNhanVien);
            viewHolderNhanVien.txtCMND = view.findViewById(R.id.txtCMND);

            view.setTag(viewHolderNhanVien);
        }else {
            viewHolderNhanVien = (ViewHolderNhanVien) view.getTag();
        }

        NhanVien nhanVien = nhanVienList.get(position);

        viewHolderNhanVien.txtTenNhanVien.setText(nhanVien.getTENDANGNHAP());
        viewHolderNhanVien.txtCMND.setText(String.valueOf(nhanVien.getCMND()));

        return view;
    }
}
