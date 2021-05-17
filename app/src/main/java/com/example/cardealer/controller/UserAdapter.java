package com.example.cardealer.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardealer.R;
import com.example.cardealer.model.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserVH>{

    ArrayList<User> users;
    Context context;

    public UserAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_overview,parent, false);
        UserVH uvh = new UserVH(view);
        return uvh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserVH holder, int position) {
        User u = users.get(position);
        holder.tvCustomerInfo.setText((u.getfName() + " " + u.getlName()));
        holder.tvGender.setText(u.getGender());
        holder.tvCustomerEmail.setText(u.getEmail());
        holder.tvCustomerNumber.setText(u.getPhoneNumber());
        holder.tvCustomerLocation.setText((u.getCity() + ", " + u.getCountry()));

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, (u.getfName() + " " + u.getlName() ) + " has been deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserVH extends RecyclerView.ViewHolder{

        TextView tvCustomerInfo, tvGender, tvCustomerEmail, tvCustomerNumber, tvCustomerLocation;
        Button buttonDelete;

        public UserVH(@NonNull View itemView) {
            super(itemView);

            tvCustomerInfo = itemView.findViewById(R.id.tvCustomerInfo);
            tvGender = itemView.findViewById(R.id.tvGender);
            tvCustomerEmail = itemView.findViewById(R.id.tvCustomerEmail);
            tvCustomerNumber = itemView.findViewById(R.id.tvCustomerNumber);
            tvCustomerLocation = itemView.findViewById(R.id.tvCustomerLocation);
            buttonDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}

