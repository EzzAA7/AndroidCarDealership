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
import com.example.cardealer.view.SignUpActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarVH>{

    ArrayList<Car> cars;
    Context context;
    SharedPrefManager sharedPrefManager;

    public CarAdapter(ArrayList<Car> cars, Context context) {
        this.cars = cars;
        this.context = context;
    }

    @NonNull
    @Override
    public CarVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.car_overview,parent, false);
        CarVH cvh = new CarVH(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CarVH holder, int position) {
        Car c = cars.get(position);
        holder.tvModelInfo.setText((c.getMake() + " " + c.getModel() + " " + c.getYear()));
        holder.tvDistance.setText(c.getDistance());
        holder.tvPrice.setText(c.getPrice() + " Shekel" );

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
                boolean result = dataBaseHelper.createReservation(holder.tvModelInfo.getText().toString(),
                        holder.tvDistance.getText().toString(),
                        c.getPrice(),
                        (currentUser.getfName()+ " " + currentUser.getlName()),
                        currentUser.getPhoneNumber(), currentUser.getEmail(),
                        formattedDate);

                // check if db registeration action succeeded
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
                Toast.makeText(context, (c.getMake() + " " + c.getModel() + " " + c.getYear()) + " has been added to favourites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    class CarVH extends RecyclerView.ViewHolder{

        TextView tvModelInfo, tvDistance, tvPrice;
        Button buttonReserve, buttonFav;

        public CarVH(@NonNull View itemView) {
            super(itemView);

            tvModelInfo = itemView.findViewById(R.id.tvModelInfo);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            buttonReserve = itemView.findViewById(R.id.btnReserve);
            buttonFav = itemView.findViewById(R.id.btnFav);
        }
    }
}
