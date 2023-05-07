package com.finalprojectkelompok6.ecommerceapp.admin.adapter;

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
import com.finalprojectkelompok6.ecommerceapp.admin.model.Staff;
import com.finalprojectkelompok6.ecommerceapp.admin.model.Stock;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder>{

    private Context context;
    private List<Stock> stockList;
    private Dialog dialog;

    public interface Dialog {
        void onClick(int pos);
    }

    public void setDialog (StockAdapter.Dialog dialog) {
        this.dialog = dialog;
    }

    public StockAdapter (Context context, List<Stock> stockList) {
        this.context = context;
        this.stockList = stockList;
    }

    @NonNull
    @Override
    public StockAdapter.StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_stock, parent, false);
        return new StockViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StockAdapter.StockViewHolder holder, int position) {
        holder.name.setText(stockList.get(position).getNama());
        holder.category.setText(stockList.get(position).getCategory());
        holder.jumlah_barang.setText(stockList.get(position).getJumlah_barang());
        Glide.with(context).load(stockList.get(position).getImage_product()).into(holder.image_product);
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    class StockViewHolder extends RecyclerView.ViewHolder {
        TextView name, category, jumlah_barang;
        ImageView image_product;

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.stock_name);
            category = itemView.findViewById(R.id.stock_category);
            jumlah_barang = itemView.findViewById(R.id.stock_jumlah_barang);
            image_product = itemView.findViewById(R.id.image_product);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialog != null) {
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
