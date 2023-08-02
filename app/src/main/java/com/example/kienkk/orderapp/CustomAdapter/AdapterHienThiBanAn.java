package com.example.kienkk.orderapp.CustomAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kienkk.orderapp.Controller.BanAnController;
import com.example.kienkk.orderapp.Controller.GoiMonController;
import com.example.kienkk.orderapp.Controller.LoaiMonAnController;
import com.example.kienkk.orderapp.Model.BanAn;
import com.example.kienkk.orderapp.Model.GoiMon;
import com.example.kienkk.orderapp.Model.ThanhToan;
import com.example.kienkk.orderapp.Fragment.HienThiThucDonFragment;
import com.example.kienkk.orderapp.R;
import com.example.kienkk.orderapp.ThanhToanActivity;
import com.example.kienkk.orderapp.TrangChuActicity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterHienThiBanAn extends BaseAdapter implements View.OnClickListener{
    private final Context context;
    private final int layout;

    private final List<BanAn> banAnList;
    private ViewHolderBanAn viewHolderBanAn;

    private final BanAnController banAnController;
    private final GoiMonController goiMonController;
    private final LoaiMonAnController loaiMonAnController;

    private final FragmentManager fragmentManager;

    public AdapterHienThiBanAn(Context context, int layout, List<BanAn> banAnList){
        this.context = context;
        this.layout = layout;
        this.banAnList = banAnList;

        banAnController = new BanAnController(context);
        goiMonController = new GoiMonController(context);
        loaiMonAnController = new LoaiMonAnController(context);
        fragmentManager = ((TrangChuActicity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return banAnList.size();
    }

    @Override
    public Object getItem(int position) {
        return banAnList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return banAnList.get(position).getMaBan();
    }

    @SuppressLint({"NonConstantResourceId", "SimpleDateFormat"})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        int maban = banAnList.get((int) viewHolderBanAn.imBanAn.getTag()).getMaBan();

        viewHolderBanAn = (ViewHolderBanAn) ((View)v.getParent()).getTag();
        switch (id){
            case R.id.imBanAn:
                banAnList.get((int) v.getTag()).setDuocChon(true);
                HienThiButton();
                break;
            case R.id.imGoiMon:
                List<com.example.kienkk.orderapp.Model.LoaiMonAn> loaiMonAns = loaiMonAnController.LayDanhSachLoaiMonAn();

                if (loaiMonAns.size() > 0){
                    Intent LayiTrangChu = ((TrangChuActicity)context).getIntent();
                    int manhanvien = LayiTrangChu.getIntExtra("manhanvien", 0);

                    String tinhtrang = banAnController.LayTinhTrangBan(maban);
                    if (tinhtrang.equals("false")){
                        // thực hiện code thêm bảng gọi món va cập nhật lại tình trạng bàn
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                        String ngaygoi= dateFormat.format(calendar.getTime());

                        GoiMon goiMon = new GoiMon();
                        goiMon.setMaBan(maban);
                        goiMon.setMaNhanVien(manhanvien);
                        goiMon.setNgayGoi(ngaygoi);
                        goiMon.setTinhTrang("false");

                        long kiemtra = goiMonController.ThemGoiMon(goiMon);
                        banAnController.CapNhatTinhTrangBan(maban, "true");
                        if (kiemtra == 0)
                            Toast.makeText(context, context.getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
                    }

                    FragmentTransaction tranThucDonTransaction = fragmentManager.beginTransaction();
                    HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                    Bundle bDuLieuThucDon = new Bundle();
                    bDuLieuThucDon.putInt("maban", maban);

                    hienThiThucDonFragment.setArguments(bDuLieuThucDon);

                    tranThucDonTransaction.replace(R.id.content, hienThiThucDonFragment).addToBackStack("hienthibanan");
                    tranThucDonTransaction.commit();
                }else {
                    Toast.makeText(context, "Chưa có danh sách món ăn!!!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imThanhToan:
                int magoimon = (int) goiMonController.LayMaGoiMonTheoMaBan(maban, "false");
                List<ThanhToan> thanhToans = goiMonController.LayDanhSachMonAnTheoMaGoiMon(magoimon);

                if (magoimon != 0){
                    if (thanhToans.size() > 0){
                        Intent iThanhToan = new Intent(context, ThanhToanActivity.class);
                        iThanhToan.putExtra("maban", maban);
                        context.startActivity(iThanhToan);
                    }else
                        Toast.makeText(context, "Bàn chưa gọi món!!!", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(context, "Bàn chưa gọi món!!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imAnButton:
                AnButton(true);
                break;
        }
    }

    public static class ViewHolderBanAn{
        ImageView imBanAn, imGoiMon, imThanhToan, imAnButton;
        TextView txtTenBanAn;
    }

    private void HienThiButton(){
        viewHolderBanAn.imGoiMon.setVisibility(View.VISIBLE);
        viewHolderBanAn.imThanhToan.setVisibility(View.VISIBLE);
        viewHolderBanAn.imAnButton.setVisibility(View.VISIBLE);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.hieuung_hienthi_button_banan);
        viewHolderBanAn.imGoiMon.startAnimation(animation);
        viewHolderBanAn.imThanhToan.startAnimation(animation);
        viewHolderBanAn.imAnButton.startAnimation(animation);
    }

    private void AnButton(boolean hieuung){
        if (hieuung){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.hieuung_anbutton_banan);
            viewHolderBanAn.imGoiMon.startAnimation(animation);
            viewHolderBanAn.imThanhToan.startAnimation(animation);
            viewHolderBanAn.imAnButton.startAnimation(animation);
        }

        viewHolderBanAn.imGoiMon.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imThanhToan.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imAnButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderBanAn = new ViewHolderBanAn();
            view = inflater.inflate(R.layout.custom_layout_hienthibanan, parent, false);
            viewHolderBanAn.imBanAn = view.findViewById(R.id.imBanAn);
            viewHolderBanAn.imGoiMon = view.findViewById(R.id.imGoiMon);
            viewHolderBanAn.imThanhToan = view.findViewById(R.id.imThanhToan);
            viewHolderBanAn.imAnButton = view.findViewById(R.id.imAnButton);
            viewHolderBanAn.txtTenBanAn = view.findViewById(R.id.txtTenBanAn);

            view.setTag(viewHolderBanAn);
        }else
            viewHolderBanAn = (ViewHolderBanAn) view.getTag();

        if (banAnList.get(position).isDuocChon())    //lấy tất cả thuộc tính đc chọn có bằng true hay không
            HienThiButton();
        else
            AnButton(false);

        BanAn banAn = banAnList.get(position); // position tương ứng với mỗi giá trị khi gridview tạo ra

        String kttinhtrang = banAnController.LayTinhTrangBan(banAn.getMaBan());
        if (kttinhtrang.equals("true"))
            viewHolderBanAn.imBanAn.setImageResource(R.drawable.banantrue);
        else
            viewHolderBanAn.imBanAn.setImageResource(R.drawable.banan);

        viewHolderBanAn.txtTenBanAn.setText(banAn.getTenBan());
        viewHolderBanAn.imBanAn.setTag(position);

        viewHolderBanAn.imBanAn.setOnClickListener(this);
        viewHolderBanAn.imGoiMon.setOnClickListener(this);
        viewHolderBanAn.imThanhToan.setOnClickListener(this);
        viewHolderBanAn.imAnButton.setOnClickListener(this);

        return view;
    }
}
