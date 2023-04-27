package com.finalprojectkelompok6.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.finalprojectkelompok6.ecommerceapp.users.UserLoginActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnAdmin, btnStaff, btnUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnUsers = findViewById(R.id.btn_user);
        btnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}