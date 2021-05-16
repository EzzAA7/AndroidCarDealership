package com.example.cardealer.ui.specialOffers;

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
import android.widget.TextView;

import com.example.cardealer.R;
import com.example.cardealer.controller.CarAdapter;
import com.example.cardealer.controller.DataBaseHelper;
import com.example.cardealer.controller.SpecialOfferAdapter;
import com.example.cardealer.model.Car;

import java.util.ArrayList;

public class SpecialOffersFragment extends Fragment {

    private SpecialOffersViewModel mViewModel;
    RecyclerView recyclerView;
    SpecialOfferAdapter specialOfferAdapter;
    ArrayList<Car> cars;

    public static SpecialOffersFragment newInstance() {

        return new SpecialOffersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.special_offers_fragment, container, false);

        DataBaseHelper dataBaseHelper =new DataBaseHelper(getActivity(),"PROJ", null,1);

        recyclerView = view.findViewById(R.id.recycler_view);
        cars = Car.carsArrayList;

        ArrayList<Car> carsWithSpecialOffers = new ArrayList<Car>();
        for (Car car : cars) {
            if (car.getOffers() == true) {
                carsWithSpecialOffers.add(car);
            }
        }

        specialOfferAdapter = new SpecialOfferAdapter(carsWithSpecialOffers, getActivity());
        recyclerView.setAdapter(specialOfferAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(llm);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SpecialOffersViewModel.class);
        //
    }

}