package com.finalprojectkelompok6.ecommerceapp.users.pembelian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.finalprojectkelompok6.ecommerceapp.R;
import com.finalprojectkelompok6.ecommerceapp.users.UserMainActivity;
import com.finalprojectkelompok6.ecommerceapp.users.pembelian.adapter.PembelianAdapter;
import com.finalprojectkelompok6.ecommerceapp.users.pembelian.model.Pembelian;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserHistoryPembelianActivity extends AppCompatActivity {

    private Button btnKembali;
    private RecyclerView rvHistoryPembelian;
    private ProgressDialog progressDialog;
    private List<Pembelian> list = new ArrayList<>();
    private PembelianAdapter pembelianAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history_pembelian);

        rvHistoryPembelian = findViewById(R.id.rv_history_pembelian);
        btnKembali = findViewById(R.id.btn_kembali);

        progressDialog = new ProgressDialog(UserHistoryPembelianActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait....");

        pembelianAdapter = new PembelianAdapter(getApplicationContext(), list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        rvHistoryPembelian.setLayoutManager(layoutManager);
        rvHistoryPembelian.addItemDecoration(itemDecoration);
        rvHistoryPembelian.setAdapter(pembelianAdapter);

        progressDialog.show();
        db.collection("transaction")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Pembelian pembelian = new Pembelian(
                                        document.getString("nama_barang"),
                                        document.getString("jumlah_beli"),
                                        document.getString("total_harga"));
                                list.add(pembelian);
                            }
                            pembelianAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), "Data Gagal Dimuat", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHistoryPembelianActivity.this, UserMainActivity.class);
                startActivity(intent);
            }
        });
    }
}