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
import com.finalprojectkelompok6.ecommerceapp.users.detail.model.Kue;

import java.util.List;

public class KueAdapter extends RecyclerView.Adapter<KueAdapter.kueViewHolder> {
    private final Context context;
    private final List<Kue> list;

    public KueAdapter(Context context, List<Kue> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public kueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_kue, parent, false);
        return new kueViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull kueViewHolder holder, int position) {
        holder.name.setText(list.get(position).getNama());
        holder.jumlah_barang.setText(list.get(position).getJumlah_barang());
        holder.harga_barang.setText("Rp"+list.get(position).getHarga_barang());
        Glide.with(context).load(list.get(position).getImage_product()).into(holder.image_product);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class kueViewHolder extends RecyclerView.ViewHolder {
        TextView name, jumlah_barang, harga_barang;
        ImageView image_product;
        public kueViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tittle_kue);
            jumlah_barang = itemView.findViewById(R.id.stock_kue);
            harga_barang = itemView.findViewById(R.id.price_kue);
            image_product = itemView.findViewById(R.id.image_kue);
        }
    }
}
