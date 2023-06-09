package com.finalprojectkelompok6.ecommerceapp.users.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.finalprojectkelompok6.ecommerceapp.R;
import com.finalprojectkelompok6.ecommerceapp.admin.adapter.StockAdapter;
import com.finalprojectkelompok6.ecommerceapp.users.detail.adapter.ElektronikAdapter;
import com.finalprojectkelompok6.ecommerceapp.users.detail.model.Handphone;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserDetailElektronikActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Handphone> list = new ArrayList<>();
    private ElektronikAdapter elektronikAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_elektronik);

        recyclerView=findViewById(R.id.rv_elektronik);

        elektronikAdapter = new ElektronikAdapter(getApplicationContext(), list);
        get_data();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(elektronikAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        get_data();
    }
    private void get_data() {
        db.collection("stock")
                .whereEqualTo("category", "Elektronik")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Handphone handphone = new Handphone(document.getString("nama"),
                                        document.getString("category"),
                                        document.getString("jumlah"),
                                        document.getString("price"),
                                        document.getString("image"));
                                handphone.setId(document.getId());
                                list.add(handphone);
                            }
                            elektronikAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Data Gagal Dimuat", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}