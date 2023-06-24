package com.finalprojectkelompok6.ecommerceapp.users.pembelian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalprojectkelompok6.ecommerceapp.R;
import com.finalprojectkelompok6.ecommerceapp.users.pembelian.model.Pembelian;

import java.util.List;

public class PembelianAdapter extends RecyclerView.Adapter<PembelianAdapter.MyViewHolder>{

    private Context context;
    private List<Pembelian> list;

    public PembelianAdapter(Context context, List<Pembelian> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pembelian, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.namaBarang.setText(list.get(position).getNamaBarang());
        holder.totalBeli.setText(list.get(position).getTotalBeli());
        holder.totalHarga.setText(list.get(position).getTotalHarga());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namaBarang, totalBeli, totalHarga;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            namaBarang = itemView.findViewById(R.id.name_barang);
            totalBeli = itemView.findViewById(R.id.total_beli);
            totalHarga = itemView.findViewById(R.id.total_harga);
        }
    }
}
