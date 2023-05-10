package com.finalprojectkelompok6.ecommerceapp.users.detail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.finalprojectkelompok6.ecommerceapp.R;
import com.finalprojectkelompok6.ecommerceapp.users.detail.model.Buku;

import java.util.List;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.ListViewHolder> {
    private final Context context;
    private final List<Buku> list;


    public BukuAdapter(Context context, List<Buku> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BukuAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_detail_buku, parent, false);
        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BukuAdapter.ListViewHolder holder, int position) {
        holder.name.setText(list.get(position).getNama());
        holder.category.setText(list.get(position).getCategory());
        holder.jumlah_barang.setText(list.get(position).getJumlah_barang());
        holder.harga_barang.setText(list.get(position).getHarga_barang());
        Glide.with(context).load(list.get(position).getImage_product()).into(holder.image_product);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView name, category, jumlah_barang, harga_barang;
        ImageView image_product;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tittle_buku);
            jumlah_barang = itemView.findViewById(R.id.stock_buku);
            harga_barang = itemView.findViewById(R.id.price_buku);
            image_product = itemView.findViewById(R.id.image_buku);
        }
    }
}
