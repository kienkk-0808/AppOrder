package com.example.kienkk.orderapp.CustomAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kienkk.orderapp.Controller.LoaiMonAnController;
import com.example.kienkk.orderapp.R;

import java.util.List;

public class AdapterHienThiLoaiMonAnThucDon extends BaseAdapter {
    private final Context context;
    private final int layout;
    private final List<com.example.kienkk.orderapp.Model.LoaiMonAn> loaiMonAnList;
    private ViewHolderHienThiLoaiThucDon viewHolder;
    private final LoaiMonAnController loaiMonAnController;

    public AdapterHienThiLoaiMonAnThucDon(Context context, int layout, List<com.example.kienkk.orderapp.Model.LoaiMonAn> loaiMonAnList){
        this.context = context;
        this.layout = layout;
        this.loaiMonAnList = loaiMonAnList;
        loaiMonAnController = new LoaiMonAnController(context);
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

    public static class ViewHolderHienThiLoaiThucDon{
        ImageView imHinhLoaiThucDon;
        TextView txtTenLoaiThucDon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolder = new ViewHolderHienThiLoaiThucDon();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.imHinhLoaiThucDon = view.findViewById(R.id.imHienThiMonAn);
            viewHolder.txtTenLoaiThucDon = view.findViewById(R.id.txtTenLoaiThucDon);

            view.setTag(viewHolder);
        }else
            viewHolder = (ViewHolderHienThiLoaiThucDon) view.getTag();

        com.example.kienkk.orderapp.Model.LoaiMonAn loaiMonAn = loaiMonAnList.get(position);
        int maloai = loaiMonAn.getMaLoai();
        String hinhanh = this.loaiMonAnController.LayHinhLoaiMonAn(maloai);

        if (hinhanh != null || hinhanh != ""){
            Uri uri = Uri.parse(hinhanh);
            viewHolder.imHinhLoaiThucDon.setImageURI(uri);
        }

        viewHolder.txtTenLoaiThucDon.setText(loaiMonAn.getTenLoai());

        return view;
    }
}
