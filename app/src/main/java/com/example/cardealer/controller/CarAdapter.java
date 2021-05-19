package com.example.cardealer.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardealer.R;
import com.example.cardealer.model.Car;
import com.example.cardealer.model.User;
import com.example.cardealer.service.SharedPrefManager;
import com.example.cardealer.ui.carMenu.CarDetailsFragment;
import com.example.cardealer.view.SignUpActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarVH> implements Filterable {

    ArrayList<Car> cars;
    ArrayList<Car> carsAll;
    Context context;
    SharedPrefManager sharedPrefManager;

    public CarAdapter(ArrayList<Car> cars, Context context) {
        this.cars = cars;
        this.carsAll = new ArrayList<>(cars);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure to reserve this " + c.getMake() + "?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setCancelable(false);
                builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
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

                        if(result){
                            // send confirmation toast
                            Toast.makeText(context, ("The " + c.getMake() + " " + c.getModel() + " " + c.getYear()) + " has been reserved", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context, "Reservation Failed, try Again!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                    }
                });
                builder.show();
            }
        });

        holder.buttonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get current session's user
                sharedPrefManager = SharedPrefManager.getInstance(context);
                String email = sharedPrefManager.readString("Session","noValue");
                User currentUser = dataBaseHelper.getUser(email);

                // once everything is ready we can create a favourite
                boolean result = dataBaseHelper.createFavourite(holder.tvModelInfo.getText().toString(),
                        holder.tvDistance.getText().toString(),
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

//        holder.clickable.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CarDetailsFragment fragment = new CarDetailsFragment();
//                try {
//                    holder.Fragment(fragment);
//                    holder.tvDistance.setText(c.getDistance());
//                    holder.tvPrice.setText(c.getPrice() + " Shekel" );
//                }catch (Exception e){
////            Toast.makeText(context, "BindViewHolder"+e.getMessage(), Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<Car> filteredCars = new ArrayList<>();

            if (constraint.toString().isEmpty()){
                filteredCars.addAll(carsAll);
            }
            else{
                for (Car car: carsAll){
                    if ((car.getMake().toLowerCase().contains(constraint.toString().toLowerCase()))
                            || (car.getModel().toLowerCase().contains(constraint.toString().toLowerCase())) ||
                            (car.getYear().toLowerCase().contains(constraint.toString().toLowerCase()))){
                        filteredCars.add(car);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredCars;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cars.clear();
            cars.addAll((Collection<? extends Car>) results.values);
            notifyDataSetChanged();
        }
    };

    class CarVH extends RecyclerView.ViewHolder{

        TextView tvModelInfo, tvDistance, tvPrice;
        Button buttonReserve, buttonFav;
//        LinearLayout clickable;
//        LinearLayout container;

        public CarVH(@NonNull View itemView) {
            super(itemView);
//            container = itemView.findViewById(R.id.layoutToBeChanged);

            tvModelInfo = itemView.findViewById(R.id.tvModelInfo);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            buttonReserve = itemView.findViewById(R.id.btnReserve);
            buttonFav = itemView.findViewById(R.id.btnFav);
//            clickable = itemView.findViewById(R.id.layoutClickable);
        }

//        private void Fragment(final Fragment fragment) {
//            FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager()
//                    .beginTransaction();
//            try {
//                transaction.replace(R.id.layoutToBeChanged, fragment)
//                        .commit();
//            } catch (Exception e) {
//                Toast.makeText(context, "ViewHolder " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
    }
}
