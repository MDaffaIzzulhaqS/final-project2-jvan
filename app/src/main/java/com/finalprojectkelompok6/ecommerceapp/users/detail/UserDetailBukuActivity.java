package com.finalprojectkelompok6.ecommerceapp.users.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.finalprojectkelompok6.ecommerceapp.R;
import com.finalprojectkelompok6.ecommerceapp.users.detail.adapter.BukuAdapter;
import com.finalprojectkelompok6.ecommerceapp.users.detail.model.Buku;
import com.google.firebase.firestore.FirebaseFirestore;

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
        db=FirebaseFirestore.getInstance();

    }
}