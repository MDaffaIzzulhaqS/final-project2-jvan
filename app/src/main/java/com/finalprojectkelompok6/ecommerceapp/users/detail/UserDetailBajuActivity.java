package com.finalprojectkelompok6.ecommerceapp.users.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.finalprojectkelompok6.ecommerceapp.R;
import com.finalprojectkelompok6.ecommerceapp.admin.adapter.StaffAdapter;
import com.finalprojectkelompok6.ecommerceapp.admin.model.Staff;
import com.finalprojectkelompok6.ecommerceapp.users.detail.adapter.BajuAdapter;
import com.finalprojectkelompok6.ecommerceapp.users.detail.model.Baju;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UserDetailBajuActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Baju> list = new ArrayList<>();
    private BajuAdapter bajuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_baju);
    }
}