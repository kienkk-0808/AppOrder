package com.example.kienkk.orderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kienkk.orderapp.Controller.BanAnController;

public class ThemBanAnActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edTenThemBanAn;
    private Button btnDongYThemBanAn;
    private BanAnController banAnController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thembanan);

        edTenThemBanAn = findViewById(R.id.edTenThemBanAn);
        btnDongYThemBanAn = findViewById(R.id.btnDongYThemBanAn);

        banAnController = new BanAnController(this);
        btnDongYThemBanAn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String sTenBanAn = edTenThemBanAn.getText().toString();
        if (sTenBanAn != null || sTenBanAn.equals("")){
            boolean kiemtra = banAnController.ThemBanAn(sTenBanAn);
            Intent intent = new Intent();
            intent.putExtra("ketquathem", kiemtra);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}
