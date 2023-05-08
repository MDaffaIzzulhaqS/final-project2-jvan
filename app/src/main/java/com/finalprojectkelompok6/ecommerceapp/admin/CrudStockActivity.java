package com.finalprojectkelompok6.ecommerceapp.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.finalprojectkelompok6.ecommerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrudStockActivity extends AppCompatActivity {

    private String id = "";
    private EditText editNama, editKategori, editJumlahStok, editHarga;
    private ImageView imageProduct;
    private ProgressBar progressBar;
    private Button btnSave, btnReturn;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_stock);
        editNama = findViewById(R.id.nama);
        editKategori = findViewById(R.id.category);
        editJumlahStok = findViewById(R.id.jumlah_barang);
        editHarga = findViewById(R.id.harga_barang);
        imageProduct = findViewById(R.id.image_product);
        progressBar = findViewById(R.id.progressBar);
        btnSave = findViewById(R.id.btn_save);
        btnReturn = findViewById(R.id.btn_return);

        imageProduct.setOnClickListener(view -> {
            selectImage();
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddStockActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSave.setOnClickListener(view -> {
            if (editNama.getText().length()>0
                    || editKategori.getText().length()>0
                    || editJumlahStok.getText().length()>0
                    || editHarga.getText().length()>0) {
                UploadImage(editNama.getText().toString(),
                        editKategori.getText().toString(),
                        editJumlahStok.getText().toString(),
                        editHarga.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(),
                        "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            editNama.setText(intent.getStringExtra("name"));
            editKategori.setText(intent.getStringExtra("category"));
            editJumlahStok.setText(intent.getStringExtra("jumlah"));
            editHarga.setText(intent.getStringExtra("price"));
            Glide.with(getApplicationContext()).load(intent.getStringExtra("image")).into(imageProduct);
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Ambil Foto", "Pilih Dari Galeri", "Batal"};
        AlertDialog.Builder builder = new AlertDialog.Builder(CrudStockActivity.this);
        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Ambil Foto")) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 10);
            } else if (items[item].equals("Pilih Dari Galeri")) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), 20);
            } else if (items[item].equals("Batal")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            final Bundle extras = data.getExtras();
            Thread thread = new Thread(() -> {
                Bitmap bitmap = (Bitmap) extras.get("data");
                imageProduct.post(() -> {
                    imageProduct.setImageBitmap(bitmap);
                });
            });
            thread.start();
        }
        if (requestCode == 20 && resultCode == RESULT_OK && data != null) {
            final Uri path = data.getData();
            Thread thread = new Thread(() -> {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(path);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imageProduct.post(() -> {
                        imageProduct.setImageBitmap(bitmap);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
    }

    private void UploadImage(String name, String category, String jumlah_barang, String harga) {
        progressBar.setVisibility(View.VISIBLE);
        imageProduct.setDrawingCacheEnabled(true);
        imageProduct.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageProduct.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference("Product_Image")
                .child("IMG"+new Date().getTime()+".png");

        UploadTask uploadTask = reference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(),
                        exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (taskSnapshot.getMetadata() != null) {
                    if (taskSnapshot.getMetadata().getReference() != null) {
                        taskSnapshot.getMetadata().getReference().
                                getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.getResult() != null) {
                                    SaveData(name, category, jumlah_barang, harga, task.getResult().toString());
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Gagal Upload", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Gagal Upload", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void SaveData (String name, String category, String jumlah_barang, String harga, String image_product) {
        Map<String, Object> stock = new HashMap<>();
        stock.put("nama", name);
        stock.put("category", category);
        stock.put("jumlah", jumlah_barang);
        stock.put("price", harga);
        stock.put("image", image_product);

        progressBar.setVisibility(View.VISIBLE);
        if (id != null) {
            db.collection("stock").document(id)
                    .set(stock)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                        "Data Stock Berhasil Diedit", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Data Staff Gagal Diedit", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            db.collection("stock")
                    .add(stock)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(),
                                    "Data Stock Berhasil Disimpan", Toast.LENGTH_SHORT).show();
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
    }
}