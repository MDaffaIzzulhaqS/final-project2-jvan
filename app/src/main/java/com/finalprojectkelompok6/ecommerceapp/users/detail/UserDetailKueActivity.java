package com.finalprojectkelompok6.ecommerceapp.users.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.finalprojectkelompok6.ecommerceapp.R;

public class UserDetailKueActivity extends AppCompatActivity {
    private ImageView kueImg;
    private TextView kueTittle;
    private TextView kueStock;
    private TextView kuePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_kue);

        kueImg = findViewById(R.id.img_kue);
        kueTittle = findViewById(R.id.tittle_kue);
        kueStock = findViewById(R.id.stock_kue);
        kuePrice = findViewById(R.id.price_kue);


    }
}