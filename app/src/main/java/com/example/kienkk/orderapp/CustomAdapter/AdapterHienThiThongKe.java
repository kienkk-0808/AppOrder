package com.example.kienkk.orderapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kienkk.orderapp.Model.ThongKe;
import com.example.kienkk.orderapp.R;

import java.util.List;

public class AdapterHienThiThongKe extends BaseAdapter {

    private final Context context;
    private final int layout;
    private final List<ThongKe> thongKeList;

    private AdapterHienThiThongKe.ViewHolderThongKe viewHolderThongKe;

    public AdapterHienThiThongKe(Context context, int layout, List<ThongKe> thongKeList){
        this.context = context;
        this.layout = layout;
        this.thongKeList = thongKeList;
    }

    public class ViewHolderThongKe {
        TextView txtNgay, txtTien;
    }

    @Override
    public int getCount() {
        return thongKeList.size();
    }

    @Override
    public Object getItem(int position) {
        return thongKeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderThongKe = new AdapterHienThiThongKe.ViewHolderThongKe();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false) ;

            viewHolderThongKe.txtNgay = view.findViewById(R.id.txtNgay);
            viewHolderThongKe.txtTien = view.findViewById(R.id.txtTien);

            view.setTag(viewHolderThongKe);
        }else {
            viewHolderThongKe = (AdapterHienThiThongKe.ViewHolderThongKe) view.getTag();
        }

        ThongKe thongKe = thongKeList.get(position);

        viewHolderThongKe.txtNgay.setText(thongKe.getNgay());
        viewHolderThongKe.txtTien.setText(thongKe.getTongTien());

        return view;
    }
}
