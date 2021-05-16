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
import com.example.cardealer.model.Car;

import java.util.ArrayList;

public class SpecialOfferAdapter extends RecyclerView.Adapter<SpecialOfferAdapter.SpecialOfferVH>{

    ArrayList<Car> cars;
    Context context;

    public SpecialOfferAdapter(ArrayList<Car> cars, Context context) {
        this.cars = cars;
        this.context = context;
    }

    @NonNull
    @Override
    public SpecialOfferVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.special_offer,parent, false);
        SpecialOfferVH sovh = new SpecialOfferVH(view);
        return sovh;
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialOfferVH holder, int position) {
        Car c = cars.get(position);
        holder.tvInfo.setText((c.getMake() + " " + c.getModel() + " " + c.getYear()));

        holder.buttonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, (c.getMake() + " " + c.getModel() + " " + c.getYear()) + " has been reserved", Toast.LENGTH_SHORT).show();
            }
        });

        holder.buttonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, (c.getMake() + " " + c.getModel() + " " + c.getYear()) + " has been added to favourites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    class SpecialOfferVH extends RecyclerView.ViewHolder{

        TextView tvInfo;
        Button buttonReserve, buttonFav;

        public SpecialOfferVH(@NonNull View itemView) {
            super(itemView);

            tvInfo = itemView.findViewById(R.id.tvInfo);
            buttonReserve = itemView.findViewById(R.id.btnReserveSO);
            buttonFav = itemView.findViewById(R.id.btnFavSO);
        }
    }
}



