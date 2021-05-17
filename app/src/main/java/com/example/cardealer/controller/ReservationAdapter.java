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
import com.example.cardealer.model.Reservation;

import java.util.ArrayList;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationVH>{

    ArrayList<Reservation> reservations;
    Context context;

    public ReservationAdapter(ArrayList<Reservation> reservations, Context context) {
        this.reservations = reservations;
        this.context = context;
    }

    @NonNull
    @Override
    public ReservationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reservation_overview,parent, false);
        ReservationVH rvh = new ReservationVH(view);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationVH holder, int position) {
        Reservation r = reservations.get(position);
        holder.tvCarInfo.setText((r.getCarInfo()));
        holder.tvCarDist.setText(r.getCarDistance());
        holder.tvCarPrice.setText(r.getCarPrice() + " Shekel" );
        holder.tvCarReserverName.setText(r.getName());
        holder.tvCarReserverPhone.setText(r.getPhone());
        holder.tvCarReserverDate.setText(r.getDateTime());

    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    class ReservationVH extends RecyclerView.ViewHolder{

        TextView tvCarInfo, tvCarDist, tvCarPrice, tvCarReserverName, tvCarReserverPhone, tvCarReserverDate;

        public ReservationVH(@NonNull View itemView) {
            super(itemView);

            tvCarInfo = itemView.findViewById(R.id.tvCarInfoRes);
            tvCarDist = itemView.findViewById(R.id.tvCarDistRes);
            tvCarPrice = itemView.findViewById(R.id.tvCarPriceRes);
            tvCarReserverName = itemView.findViewById(R.id.tvCarReserverName);
            tvCarReserverPhone = itemView.findViewById(R.id.tvCarReserverPhone);
            tvCarReserverDate = itemView.findViewById(R.id.tvCarReserverDateTime);
        }
    }
}

