package com.finalprojectkelompok6.ecommerceapp.admin.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalprojectkelompok6.ecommerceapp.R;
import com.finalprojectkelompok6.ecommerceapp.admin.model.Staff;

import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> {

    private Context context;
    private List<Staff> staffList;
    private Dialog dialog;

    public interface Dialog {
        void onClick(int pos);
    }

    public void setDialog (Dialog dialog) {
        this.dialog = dialog;
    }

    public StaffAdapter (Context context, List<Staff> staffList) {
        this.context = context;
        this.staffList = staffList;
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_staff, parent, false);
        return new StaffViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffViewHolder holder, int position) {
        holder.name.setText(staffList.get(position).getNama());
        holder.email.setText(staffList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    class StaffViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, password;

        public StaffViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.staff_name);
            email = itemView.findViewById(R.id.staff_email);
            password = itemView.findViewById(R.id.staff_password);

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
