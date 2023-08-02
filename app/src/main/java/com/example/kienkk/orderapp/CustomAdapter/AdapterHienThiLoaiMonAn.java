package com.example.kienkk.orderapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kienkk.orderapp.Model.LoaiMonAn;
import com.example.kienkk.orderapp.R;

import java.util.List;

public class AdapterHienThiLoaiMonAn extends BaseAdapter{
    private final Context context;
    private final int layout;
    private final List<LoaiMonAn> loaiMonAnList;
    private ViewHolderLoaiMonAn viewHolderLoaiMonAn;

    public AdapterHienThiLoaiMonAn(Context context, int layout, List<LoaiMonAn> loaiMonAnList){
        this.context = context;
        this.layout = layout;
        this.loaiMonAnList = loaiMonAnList;
    }

    @Override
    public int getCount() {
        return loaiMonAnList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiMonAnList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return loaiMonAnList.get(position).getMaLoai();
    }

    public static class ViewHolderLoaiMonAn{
        TextView txtTenLoai;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderLoaiMonAn = new ViewHolderLoaiMonAn();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout_spinloaithucdon, parent, false);

            viewHolderLoaiMonAn.txtTenLoai = (TextView) view.findViewById(R.id.txtTenLoai);

            view.setTag(viewHolderLoaiMonAn);
        } else
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();

        LoaiMonAn loaiMonAn = loaiMonAnList.get(position);
        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAn.getTenLoai());
        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAn.getMaLoai());

        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderLoaiMonAn = new ViewHolderLoaiMonAn();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout_spinloaithucdon, parent, false);

            viewHolderLoaiMonAn.txtTenLoai = view.findViewById(R.id.txtTenLoai);

            view.setTag(viewHolderLoaiMonAn);
        } else
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();

        LoaiMonAn loaiMonAn = loaiMonAnList.get(position);
        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAn.getTenLoai());
        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAn.getMaLoai());

        return view;
    }
}
