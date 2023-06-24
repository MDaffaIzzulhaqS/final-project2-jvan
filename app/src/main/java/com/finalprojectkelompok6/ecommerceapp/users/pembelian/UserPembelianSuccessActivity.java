package com.finalprojectkelompok6.ecommerceapp.users.pembelian;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.finalprojectkelompok6.ecommerceapp.R;
import com.finalprojectkelompok6.ecommerceapp.users.UserMainActivity;

public class UserPembelianSuccessActivity extends AppCompatActivity {

    private TextView namaBarang, jumlahBeli, totalHarga;
    private Button btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pembelian_success);

        namaBarang = findViewById(R.id.txt_nama_barang);
        jumlahBeli = findViewById(R.id.txt_jumlah_beli);
        totalHarga = findViewById(R.id.txt_total_harga);
        btnKembali = findViewById(R.id.btn_kembali);

        Intent intent = getIntent();
        String spnr = intent.getStringExtra("spinnerBarang");
        String jmlhBeli = intent.getStringExtra("jumlahBeli");
        String ttlHarga = intent.getStringExtra("totalHarga");

        namaBarang.setText(spnr);
        jumlahBeli.setText(jmlhBeli);
        totalHarga.setText("Rp" + ttlHarga);

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPembelianSuccessActivity.this, UserMainActivity.class);
                startActivity(intent);
            }
        });
    }
}