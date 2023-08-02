package com.example.kienkk.orderapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kienkk.orderapp.Model.ThanhToan;
import com.example.kienkk.orderapp.R;

import java.util.List;

public class AdapterHienThiThanhToan extends BaseAdapter{
    private final Context context;
    private final int layout;
    private final List<ThanhToan> thanhToans;
    private ViewHolderThanhToan viewHolderThanhToan;

    public AdapterHienThiThanhToan(Context context, int layout, List<ThanhToan> thanhToans){
        this.context = context;
        this.layout = layout;
        this.thanhToans = thanhToans;
    }

    @Override
    public int getCount() {
        return thanhToans.size();
    }

    @Override
    public Object getItem(int position) {
        return thanhToans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolderThanhToan{
        TextView txtTenMonAn, txtSoLuong, txtGiaTien;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderThanhToan = new ViewHolderThanhToan();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            viewHolderThanhToan.txtTenMonAn = view.findViewById(R.id.txtTenMonAnThanToan);
            viewHolderThanhToan.txtGiaTien = view.findViewById(R.id.txtGiaTienThanhToan);
            viewHolderThanhToan.txtSoLuong = view.findViewById(R.id.txtSoLuongThanhToan);

            view.setTag(viewHolderThanhToan);
        }else {
            viewHolderThanhToan = (ViewHolderThanhToan) view.getTag();
        }

        ThanhToan thanhToan = thanhToans.get(position);

        viewHolderThanhToan.txtTenMonAn.setText(thanhToan.getTenMonAn());
        viewHolderThanhToan.txtSoLuong.setText(String.valueOf(thanhToan.getSoLuong()));
        viewHolderThanhToan.txtGiaTien.setText(String.valueOf(thanhToan.getGiatien()));

        return view ;
    }
}
