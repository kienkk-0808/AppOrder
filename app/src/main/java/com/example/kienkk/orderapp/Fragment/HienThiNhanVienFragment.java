package com.example.kienkk.orderapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kienkk.orderapp.CustomAdapter.AdapterHienThiNhanVien;
import com.example.kienkk.orderapp.Controller.NhanVienController;
import com.example.kienkk.orderapp.Model.NhanVien;
import com.example.kienkk.orderapp.DangKyActivity;
import com.example.kienkk.orderapp.R;
import com.example.kienkk.orderapp.TrangChuActicity;

import java.util.List;

public class HienThiNhanVienFragment extends Fragment{

    ListView listNhanVien;
    NhanVienController nhanVienController;
    List<NhanVien> nhanVienList;
    AdapterHienThiNhanVien adapterHienThiNhanVien;

    int maquyen;
    SharedPreferences sharedPreferences;

    @Override
    public void onResume() {
        nhanVienList.clear();
        nhanVienList.addAll(nhanVienController.LayDanhSachNhanVien());
        adapterHienThiNhanVien.notifyDataSetChanged();
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthinhanvien, container, false);
        setHasOptionsMenu(true);
        ((TrangChuActicity)getActivity()).getSupportActionBar().setTitle(R.string.nhanvien);

        listNhanVien = view.findViewById(R.id.listNhanVien);

        nhanVienController = new NhanVienController(getActivity());

        HienThiDanhSachNhanVien();

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);

        if (maquyen == 1){
            registerForContextMenu(listNhanVien);
        }

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
    }

    private void HienThiDanhSachNhanVien(){
        nhanVienList = nhanVienController.LayDanhSachNhanVien();

        adapterHienThiNhanVien = new AdapterHienThiNhanVien(getActivity(), R.layout.custom_layout_hienthinhanvien, nhanVienList);
        listNhanVien.setAdapter(adapterHienThiNhanVien);
        adapterHienThiNhanVien.notifyDataSetChanged();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int manhavien = nhanVienList.get(vitri).getMANV();

        switch (id){
            case R.id.itSua:
                Intent iDangKy = new Intent(getActivity(), DangKyActivity.class);
                iDangKy.putExtra("manhanvien", manhavien);
                startActivity(iDangKy);
                break;
            case R.id.itXoa:
                boolean kiemtra = nhanVienController.XoaNhanVien(manhavien);
                if (kiemtra){
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.xoathanhcong), Toast.LENGTH_SHORT).show();
                    nhanVienList.clear();
                    nhanVienList.addAll(nhanVienController.LayDanhSachNhanVien());
                    adapterHienThiNhanVien.notifyDataSetChanged();
                }else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (maquyen == 1){
            MenuItem itThemNhanVien = menu.add(1, R.id.itThemNhanVien, 1, R.string.themnhanvien);
            itThemNhanVien.setIcon(R.drawable.nhanvien);
            itThemNhanVien.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemNhanVien:
                Intent iDangKy = new Intent(getActivity(), DangKyActivity.class);
                startActivity(iDangKy);
                break;
        }
        return true;
    }
}
