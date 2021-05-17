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
import com.example.cardealer.model.Favourite;
import com.example.cardealer.model.User;
import com.example.cardealer.service.SharedPrefManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteVH>{

    ArrayList<Favourite> favourites;
    Context context;
    SharedPrefManager sharedPrefManager;

    public FavouriteAdapter(ArrayList<Favourite> favourites, Context context) {
        this.favourites = favourites;
        this.context = context;
    }

    @NonNull
    @Override
    public FavouriteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.favourite_overview,parent, false);
        FavouriteVH fvh = new FavouriteVH(view);
        return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteVH holder, int position) {
        Favourite f = favourites.get(position);
        holder.tvCarInfo.setText((f.getCarInfo()));
        holder.tvCarDist.setText(f.getCarDistance());
        holder.tvCarPrice.setText(f.getCarPrice() + " Shekel" );
        holder.tvCarFavName.setText(f.getName());
        holder.tvCarFavPhone.setText(f.getPhone());

        DataBaseHelper dataBaseHelper =new DataBaseHelper(context,"PROJ", null,1);

        holder.buttonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get current session's user
                sharedPrefManager = SharedPrefManager.getInstance(context);
                String email = sharedPrefManager.readString("Session","noValue");
                User currentUser = dataBaseHelper.getUser(email);

                // get current formated date
                LocalDateTime myDateObj = LocalDateTime.now();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formattedDate = myDateObj.format(myFormatObj);

                // once everything is ready we can create a reservation
                boolean result = dataBaseHelper.createReservation(holder.tvCarInfo.getText().toString(),
                        holder.tvCarDist.getText().toString(),
                        f.getCarPrice(),
                        (currentUser.getfName()+ " " + currentUser.getlName()),
                        currentUser.getPhoneNumber(), currentUser.getEmail(), formattedDate);

                // check if db reservation action succeeded
                if(result){

                    // send confirmation toast
                    Toast.makeText(context, ("The " + f.getCarInfo()) + " has been reserved", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Reservation Failed, try Again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }

    class FavouriteVH extends RecyclerView.ViewHolder{

        TextView tvCarInfo, tvCarDist, tvCarPrice, tvCarFavName, tvCarFavPhone;
        Button buttonReserve;

        public FavouriteVH(@NonNull View itemView) {
            super(itemView);

            tvCarInfo = itemView.findViewById(R.id.tvCarInfoFav);
            tvCarDist = itemView.findViewById(R.id.tvCarDistFav);
            tvCarPrice = itemView.findViewById(R.id.tvCarPriceFav);
            tvCarFavName = itemView.findViewById(R.id.tvCarFavName);
            tvCarFavPhone = itemView.findViewById(R.id.tvCarFavPhone);

            buttonReserve = itemView.findViewById(R.id.btnReserveFav);

        }
    }
}



