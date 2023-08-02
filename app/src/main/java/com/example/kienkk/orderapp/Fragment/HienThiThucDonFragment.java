package com.example.kienkk.orderapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.kienkk.orderapp.CustomAdapter.AdapterHienThiLoaiMonAnThucDon;
import com.example.kienkk.orderapp.Controller.LoaiMonAnController;
import com.example.kienkk.orderapp.Model.LoaiMonAn;
import com.example.kienkk.orderapp.R;
import com.example.kienkk.orderapp.ThemThucDonActivity;
import com.example.kienkk.orderapp.TrangChuActicity;

import java.util.List;

public class HienThiThucDonFragment extends Fragment{
    private GridView gridView;
    private List<LoaiMonAn> loaiMonAns;
    private LoaiMonAnController loaiMonAnController;
    private FragmentManager fragmentManager;
    private SharedPreferences sharedPreferences;
    private AdapterHienThiLoaiMonAnThucDon adapter;

    private int maban;
    private int maquyen;

    @Override
    public void onResume() {
        loaiMonAns.clear();
        loaiMonAns.addAll(loaiMonAnController.LayDanhSachLoaiMonAn());
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon, container, false);
        setHasOptionsMenu(true);
        ((TrangChuActicity) getActivity()).getSupportActionBar().setTitle(R.string.thucdon); //khi gọi getActivity thì hệ thống không hiểu là của activity nào
        //mà trong TrangChuActivity chứa tất cả Fragment nên ta ép kiểu cho nó về TrangChuActivity
        gridView = view.findViewById(R.id.gvHienThiThucDon);

        fragmentManager = getActivity().getSupportFragmentManager();

        loaiMonAnController = new LoaiMonAnController(getActivity());
        loaiMonAns = loaiMonAnController.LayDanhSachLoaiMonAn();

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);

        adapter = new AdapterHienThiLoaiMonAnThucDon(getActivity(), R.layout.custom_layout_hienloaimonan, loaiMonAns);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Bundle bDuLieuThucDon = getArguments();
        if (bDuLieuThucDon != null)
            maban = bDuLieuThucDon.getInt("maban");

        gridView.setOnItemClickListener((parent, view1, position, id) -> {
            int maloai = loaiMonAns.get(position).getMaLoai();

            HienThiDanhSachMonAnFragment hienThiDanhSachMonAnFragment = new HienThiDanhSachMonAnFragment();

            Bundle bundle = new Bundle();
            bundle.putInt("maloai", maloai);
            bundle.putInt("maban", maban);
            hienThiDanhSachMonAnFragment.setArguments(bundle);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content, hienThiDanhSachMonAnFragment).addToBackStack("hienthiloai");

            transaction.commit();
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (maquyen == 1){
            MenuItem itThemThucDon = menu.add(1, R.id.itThemThucDon, 1, R.string.themthucdon);
            itThemThucDon.setIcon(R.drawable.logo);
            itThemThucDon.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itThemThucDon) {
            Intent iThemThucDon = new Intent(getActivity(), ThemThucDonActivity.class);
            startActivity(iThemThucDon);
            getActivity().overridePendingTransition(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
        }
        return super.onOptionsItemSelected(item);
    }
}
