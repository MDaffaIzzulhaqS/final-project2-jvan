package com.finalprojectkelompok6.ecommerceapp.users.pembelian;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.finalprojectkelompok6.ecommerceapp.R;
import com.finalprojectkelompok6.ecommerceapp.users.UserMainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserPembelianActivity extends AppCompatActivity {

    private Spinner spinnerBarang;
    private TextView txtHargaBarang, txtJumlahBarang;
    private EditText editTextBeli;
    private ArrayList<String> arrayBarang;
    private ArrayAdapter<String> adapter;
    private ProgressDialog progressDialog;
    private Button btnBeliBarang, btnKembali;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private QuerySnapshot stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pembelian);

        spinnerBarang = findViewById(R.id.spnr_barang);
        txtHargaBarang = findViewById(R.id.txt_harga_barang);
        txtJumlahBarang = findViewById(R.id.txt_jumlah_barang);
        editTextBeli = findViewById(R.id.edit_text_beli);
        btnBeliBarang = findViewById(R.id.btn_beli);
        btnKembali = findViewById(R.id.btn_kembali);

        progressDialog = new ProgressDialog(UserPembelianActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait....");

        arrayBarang = new ArrayList<>();

        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayBarang);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBarang.setAdapter(adapter);
        spinnerBarang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtHargaBarang.setText(stock.getDocuments().get(i).getString("price"));
                txtJumlahBarang.setText(stock.getDocuments().get(i).getString("jumlah"));
                Log.e("ID", stock.getDocuments().get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //
            }
        });

        btnBeliBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinnerBarang.getSelectedItem().toString().length() > 0
                        || editTextBeli.getText().toString().length() > 0
                        || hitungHarga().length() > 0) {
                    Intent intent = new Intent(UserPembelianActivity.this, UserPembelianSuccessActivity.class);
                    intent.putExtra("spinnerBarang", spinnerBarang.getSelectedItem().toString());
                    intent.putExtra("jumlahBeli", editTextBeli.getText().toString());
                    intent.putExtra("totalHarga", hitungHarga());
                    beliBarang(spinnerBarang.getSelectedItem().toString(), editTextBeli.getText().toString(), hitungHarga());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Data Tidak Lengkap", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPembelianActivity.this, UserMainActivity.class);
                startActivity(intent);
            }
        });

        getNamaBarang();
    }

    private void getNamaBarang() {
        progressDialog.show();
        db.collection("stock")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        progressDialog.hide();
                        stock = queryDocumentSnapshots;
                        if (queryDocumentSnapshots.size() > 0) {
                            arrayBarang.clear();
                            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                                arrayBarang.add(doc.getString("nama"));
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String hitungHarga() {
        String jumlahBeli = editTextBeli.getText().toString();
        int totalHarga = Integer.parseInt(jumlahBeli) * Integer.parseInt(txtHargaBarang.getText().toString());
        String txtTotalHarga = String.valueOf(totalHarga);
        return txtTotalHarga;
    }

    private void beliBarang(String spnrBarang, String jmlhBeli, String ttlHarga) {
        Map<String, Object> pembelian = new HashMap<>();
        pembelian.put("nama_barang", spnrBarang);
        pembelian.put("jumlah_beli", jmlhBeli);
        pembelian.put("total_harga", ttlHarga);

        progressDialog.show();
        db.collection("transaction")
                .add(pembelian)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                        //kurangiStock();
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

//    private void kurangiStock() {
//        db.collection("stock")
//                .document("jumlah")
//                .collection("transaction")
//                .get();
//    }
}