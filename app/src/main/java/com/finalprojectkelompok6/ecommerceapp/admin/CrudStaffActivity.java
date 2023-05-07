package com.finalprojectkelompok6.ecommerceapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.finalprojectkelompok6.ecommerceapp.R;
import com.finalprojectkelompok6.ecommerceapp.users.UserLoginActivity;
import com.finalprojectkelompok6.ecommerceapp.users.UserRegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CrudStaffActivity extends AppCompatActivity {

    private EditText editNama, editEmail, editPassword;
    private ProgressBar progressBar;
    private String id = "";
    private Button btnSave, btnReturn;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_staff);

        editNama = findViewById(R.id.nama);
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btnSave = findViewById(R.id.btn_save);
        btnReturn = findViewById(R.id.btn_return);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddStaffActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSave.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            if (editNama.getText().length()>0
                    || editEmail.getText().length()>0
                    || editPassword.getText().length()>0) {
                SaveCredential(editEmail.getText().toString(),
                        editPassword.getText().toString());
                SaveData(editNama.getText().toString(),
                        editEmail.getText().toString(),
                        String.valueOf(editPassword.getText().toString().hashCode()));
            } else {
                Toast.makeText(getApplicationContext(),
                        "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SaveData (String name, String email, String password) {
        Map<String, Object> staff = new HashMap<>();
        staff.put("nama", name);
        staff.put("email", email);
        staff.put("password", password);

        db.collection("staff")
                .add(staff)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),
                                "Data Staff Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),
                                e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void SaveCredential (String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(CrudStaffActivity.this,
                                    "Staff Account Has Been Created.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CrudStaffActivity.this,
                                    "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}