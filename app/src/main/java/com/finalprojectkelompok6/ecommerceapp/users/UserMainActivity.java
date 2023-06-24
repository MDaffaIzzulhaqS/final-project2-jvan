package com.finalprojectkelompok6.ecommerceapp.users;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.finalprojectkelompok6.ecommerceapp.MainActivity;
import com.finalprojectkelompok6.ecommerceapp.R;
import com.finalprojectkelompok6.ecommerceapp.users.detail.UserDetailBajuActivity;
import com.finalprojectkelompok6.ecommerceapp.users.detail.UserDetailBukuActivity;
import com.finalprojectkelompok6.ecommerceapp.users.detail.UserDetailElektronikActivity;
import com.finalprojectkelompok6.ecommerceapp.users.detail.UserDetailKueActivity;
import com.finalprojectkelompok6.ecommerceapp.users.pembelian.UserHistoryPembelianActivity;
import com.finalprojectkelompok6.ecommerceapp.users.pembelian.UserPembelianActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserMainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button btnHistoryPembelian, btnLogout;
    private FloatingActionButton fabShopping;
    private ImageView btnBuku;
    private ImageView btnBaju;
    private ImageView btnhandphone;
    private ImageView btnKue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        auth = FirebaseAuth.getInstance();
        fabShopping = findViewById(R.id.fab_shopping);
        btnLogout = findViewById(R.id.btn_logout);
        btnBuku = findViewById(R.id.btn_buku);
        btnBaju = findViewById(R.id.btn_baju);
        btnhandphone = findViewById(R.id.btn_handphone);
        btnKue = findViewById(R.id.btn_kue);
        btnHistoryPembelian = findViewById(R.id.btn_history_pembelian);

        btnHistoryPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMainActivity.this, UserHistoryPembelianActivity.class);
                startActivity(intent);
            }
        });

        btnBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMainActivity.this, UserDetailBukuActivity.class);
                startActivity(intent);
            }
        });
        btnBaju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMainActivity.this, UserDetailBajuActivity.class);
                startActivity(intent);
            }
        });
        btnhandphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMainActivity.this, UserDetailElektronikActivity.class);
                startActivity(intent);
            }
        });
        btnKue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMainActivity.this, UserDetailKueActivity.class);
                startActivity(intent);
            }
        });

        fabShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMainActivity.this, UserPembelianActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}