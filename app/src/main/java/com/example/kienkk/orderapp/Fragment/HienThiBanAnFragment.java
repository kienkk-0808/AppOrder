package com.example.kienkk.orderapp.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.GridView;
import android.widget.Toast;

import com.example.kienkk.orderapp.CustomAdapter.AdapterHienThiBanAn;
import com.example.kienkk.orderapp.Controller.BanAnController;
import com.example.kienkk.orderapp.DangNhapActivity;
import com.example.kienkk.orderapp.Model.BanAn;
import com.example.kienkk.orderapp.R;
import com.example.kienkk.orderapp.SuaBanAnActivity;
import com.example.kienkk.orderapp.ThemBanAnActivity;
import com.example.kienkk.orderapp.TrangChuActicity;

import java.util.List;

public class HienThiBanAnFragment extends Fragment {
    public static int RESQUEST_CODE_THEM = 111;
    public static int RESQUEST_CODE_SUA = 16;

    private GridView gvHienThiBanAn;
    private List<BanAn> banAnList;
    private BanAnController banAnController;
    private AdapterHienThiBanAn adapterHienThiBanAn;

    private int maquyen = 0;

    @Override
    public void onResume() {
        banAnList.clear();
        banAnList.addAll(banAnController.LayTatCaBanAn());
        adapterHienThiBanAn.notifyDataSetChanged();
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthibanan, container, false);
        setHasOptionsMenu(true);
        ((TrangChuActicity)getActivity()).getSupportActionBar().setTitle(R.string.banan); //khi gọi getActivity thì hệ thống không hiểu là của activity nào
                                                                                    //mà trong TrangChuActivity chứa tất cả Fragment nên ta ép kiểu cho nó về TrangChuActivity

        gvHienThiBanAn = view.findViewById(R.id.gvHienBanAn);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);

        banAnController = new BanAnController(getActivity());
        banAnController.LayTatCaBanAn();

        HienThiDanhSachBanAn();
        if (maquyen == 1) registerForContextMenu(gvHienThiBanAn);

        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int maban = banAnList.get(vitri).getMaBan();

        switch (id){
            case R.id.itSua:
                Intent intent = new Intent(getActivity(), SuaBanAnActivity.class);
                intent.putExtra("maban", maban);
                startActivityForResult(intent, RESQUEST_CODE_SUA);
                break;
            case R.id.itXoa:
                boolean kiemtra = banAnController.XoaBanAn(maban);
                if (kiemtra){
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathanhcong), Toast.LENGTH_SHORT).show();
                    banAnList.clear();
                    banAnList.addAll(banAnController.LayTatCaBanAn());
                    adapterHienThiBanAn.notifyDataSetChanged();
                }else
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.loi) + maban, Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (maquyen == 1){
            //là quản lý
            MenuItem itThemBanAn = menu.add(1, R.id.itThemBanAn, 1, R.string.thembanan);
            itThemBanAn.setIcon(R.drawable.thembanan);
            itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            MenuItem itDanXuat = menu.add(2, R.id.itDangXuat, 2, R.string.dangxuat);
            itDanXuat.setIcon(R.drawable.ic_action_name);
            itDanXuat.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }else {
            MenuItem itDanXuat = menu.add(1, R.id.itDangXuat, 1, R.string.dangxuat);
            itDanXuat.setIcon(R.drawable.ic_action_name);
            itDanXuat.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itThemBanAn) {
            Intent iThemBanAn = new Intent(getActivity(), ThemBanAnActivity.class);
            startActivityForResult(iThemBanAn, RESQUEST_CODE_THEM);
        }
        if (id == R.id.itDangXuat) {
            Intent iDangXuat = new Intent(getActivity(), DangNhapActivity.class);
            iDangXuat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(iDangXuat);
        }
        return true;
    }

    private void HienThiDanhSachBanAn(){
        banAnList = banAnController.LayTatCaBanAn();
        adapterHienThiBanAn = new AdapterHienThiBanAn(getActivity(), R.layout.custom_layout_hienthibanan, banAnList);
        gvHienThiBanAn.setAdapter(adapterHienThiBanAn);
        adapterHienThiBanAn.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESQUEST_CODE_THEM){
            if (resultCode == Activity.RESULT_OK){
                boolean kiemtra = data.getBooleanExtra("ketquathem", false);
                if(kiemtra){
                    banAnList.clear();
                    banAnList.addAll(banAnController.LayTatCaBanAn());
                    adapterHienThiBanAn.notifyDataSetChanged();
                    Toast.makeText(getActivity(), getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getActivity(), getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == RESQUEST_CODE_SUA){
            if (resultCode == Activity.RESULT_OK){
                boolean kiemtra = data.getBooleanExtra("kiemtra", false);
                banAnList.clear();
                banAnList.addAll(banAnController.LayTatCaBanAn());
                adapterHienThiBanAn.notifyDataSetChanged();
                if (kiemtra)
                    Toast.makeText(getActivity(), getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
