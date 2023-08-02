package com.example.kienkk.orderapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kienkk.orderapp.Controller.ThongKeController;
import com.example.kienkk.orderapp.CustomAdapter.AdapterHienThiThongKe;
import com.example.kienkk.orderapp.Model.ThongKe;
import com.example.kienkk.orderapp.R;
import com.example.kienkk.orderapp.TrangChuActicity;

import java.util.List;

public class HienThiThongKeFrahment extends Fragment {

    ListView listThongKe;
    ThongKeController thongKeController;
    List<ThongKe> thongKeList;
    AdapterHienThiThongKe adapterHienThiThongKe;

    @Override
    public void onResume() {
        thongKeList.clear();
        thongKeList.addAll(thongKeController.LayTatCaThongKe());
        adapterHienThiThongKe.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithongke, container, false);
        setHasOptionsMenu(true);
        ((TrangChuActicity)getActivity()).getSupportActionBar().setTitle(R.string.thongke);

        listThongKe = view.findViewById(R.id.listThongKe);
        thongKeController = new ThongKeController(getActivity());

        HienThiDanhSachThongKe();

        return view;
    }

    private void HienThiDanhSachThongKe() {
        thongKeList = thongKeController.LayTatCaThongKe();

        adapterHienThiThongKe = new AdapterHienThiThongKe(getActivity(), R.layout.custom_layout_hienthithongke, thongKeList);
        listThongKe.setAdapter(adapterHienThiThongKe);
        adapterHienThiThongKe.notifyDataSetChanged();

    }
}