package com.example.cardealer.ui.yourReservations;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.cardealer.R;
import com.example.cardealer.controller.CarAdapter;
import com.example.cardealer.controller.DataBaseHelper;
import com.example.cardealer.model.Car;

import java.util.ArrayList;

public class YourReservationsFragment extends Fragment {

    private YourReservationsViewModel mViewModel;
    RecyclerView recyclerView;
    CarAdapter carAdapter;
    ArrayList<Car> cars;

    public static YourReservationsFragment newInstance() {

        return new YourReservationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.your_reservations_fragment, container, false);

        DataBaseHelper dataBaseHelper =new DataBaseHelper(getActivity(),"PROJ", null,1);
//        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layout);
//        linearLayout.removeAllViews();

        recyclerView = view.findViewById(R.id.recycler_view);
        cars = Car.carsArrayList;

        carAdapter = new CarAdapter(cars, getActivity());
        recyclerView.setAdapter(carAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(llm);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(YourReservationsViewModel.class);
        //
    }

}