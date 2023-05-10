package com.finalprojectkelompok6.ecommerceapp.users.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.finalprojectkelompok6.ecommerceapp.R;
import com.finalprojectkelompok6.ecommerceapp.admin.adapter.StockAdapter;
import com.finalprojectkelompok6.ecommerceapp.admin.model.Stock;
import com.finalprojectkelompok6.ecommerceapp.users.detail.adapter.BukuAdapter;
import com.finalprojectkelompok6.ecommerceapp.users.detail.model.Buku;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserDetailBukuActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Buku> list = new ArrayList<>();
    private BukuAdapter bukuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_buku);

        recyclerView=findViewById(R.id.rv_buku);

        bukuAdapter = new BukuAdapter(getApplicationContext(), list);
        get_data();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(bukuAdapter);
    
    }

    @Override
    protected void onStart() {
        super.onStart();
        get_data();
    }
    private void get_data() {
        db.collection("stock")
                .whereEqualTo("category", "Buku")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Buku buku = new Buku(document.getString("nama"),
                                        document.getString("category"),
                                        document.getString("jumlah"),
                                        document.getString("price"),
                                        document.getString("image"));
                                buku.setId(document.getId());
                                list.add(buku);
                            }
                            bukuAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Data Gagal Dimuat", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}