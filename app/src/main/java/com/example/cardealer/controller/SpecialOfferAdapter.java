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
import com.example.cardealer.model.User;
import com.example.cardealer.service.SharedPrefManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SpecialOfferAdapter extends RecyclerView.Adapter<SpecialOfferAdapter.SpecialOfferVH>{

    ArrayList<Car> cars;
    Context context;
    SharedPrefManager sharedPrefManager;

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
        holder.tvDist.setText(c.getDistance());

//        holder.buttonReserve.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, (c.getMake() + " " + c.getModel() + " " + c.getYear()) + " has been reserved", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        holder.buttonFav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, (c.getMake() + " " + c.getModel() + " " + c.getYear()) + " has been added to favourites", Toast.LENGTH_SHORT).show();
//            }
//        });

        holder.buttonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataBaseHelper dataBaseHelper =new DataBaseHelper(context,"PROJ", null,1);

                // get current session's user
                sharedPrefManager = SharedPrefManager.getInstance(context);
                String email = sharedPrefManager.readString("Session","noValue");
                User currentUser = dataBaseHelper.getUser(email);

                // get current formated date
                LocalDateTime myDateObj = LocalDateTime.now();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formattedDate = myDateObj.format(myFormatObj);

                // once everything is ready we can create a reservation
                boolean result = dataBaseHelper.createReservation(holder.tvInfo.getText().toString(),
                        holder.tvDist.getText().toString(),
                        c.getPrice(),
                        (currentUser.getfName()+ " " + currentUser.getlName()),
                        currentUser.getPhoneNumber(), currentUser.getEmail(),
                        formattedDate);

                // check if db reservation action succeeded
                if(result){

                    // send cofirmation toast
                    Toast.makeText(context, ("The " + c.getMake() + " " + c.getModel() + " " + c.getYear()) + " has been reserved", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Reservation Failed, try Again!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.buttonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper =new DataBaseHelper(context,"PROJ", null,1);

                // get current session's user
                sharedPrefManager = SharedPrefManager.getInstance(context);
                String email = sharedPrefManager.readString("Session","noValue");
                User currentUser = dataBaseHelper.getUser(email);

                // once everything is ready we can create a favourite
                boolean result = dataBaseHelper.createFavourite(holder.tvInfo.getText().toString(),
                        holder.tvDist.getText().toString(),
                        c.getPrice(),
                        (currentUser.getfName()+ " " + currentUser.getlName()),
                        currentUser.getPhoneNumber(), currentUser.getEmail());

                // check if db favourite action succeeded
                if(result){
                    // send cofirmation toast
                    Toast.makeText(context, ("The " + c.getMake() + " " + c.getModel() + " " + c.getYear())+ " has been favourited ❤", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Favourite Failed, try Again!", Toast.LENGTH_SHORT).show();
                }            }
        });
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    class SpecialOfferVH extends RecyclerView.ViewHolder{

        TextView tvInfo, tvDist;
        Button buttonReserve, buttonFav;

        public SpecialOfferVH(@NonNull View itemView) {
            super(itemView);

            tvInfo = itemView.findViewById(R.id.tvInfo);
            tvDist = itemView.findViewById(R.id.tvCarDistSO);
            buttonReserve = itemView.findViewById(R.id.btnReserveSO);
            buttonFav = itemView.findViewById(R.id.btnFavSO);
        }
    }
}



