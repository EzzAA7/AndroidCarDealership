package com.example.cardealer.ui.carMenu;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cardealer.R;
import com.example.cardealer.controller.CarAdapter;
import com.example.cardealer.controller.DataBaseHelper;
import com.example.cardealer.model.Car;
import com.example.cardealer.view.MainActivity;

import java.util.ArrayList;

public class CarMenuFragment extends Fragment {

    private CarMenuViewModel mViewModel;
    RecyclerView recyclerView;
    CarAdapter carAdapter;
    ArrayList<Car> cars;

    public static CarMenuFragment newInstance() {

        return new CarMenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_menu_fragment, container, false);

        DataBaseHelper dataBaseHelper =new DataBaseHelper(getActivity(),"PROJ", null,1);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layout);
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
        setHasOptionsMenu(true);
        mViewModel = new ViewModelProvider(this).get(CarMenuViewModel.class);
        //
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.car_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}