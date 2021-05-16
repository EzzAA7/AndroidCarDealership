package com.example.cardealer.ui.carMenu;

import androidx.lifecycle.ViewModelProvider;

import android.database.Cursor;
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

//        Cursor allCarsCursor = dataBaseHelper.getAllCars();
//        while (allCarsCursor.moveToNext()){
//            TextView textView =new TextView(getActivity());
//            textView.setText(
//                    "Year= "+allCarsCursor.getString(0)
//                            +"\nMake= "+allCarsCursor.getString(1)
//                            +"\nModel= "+allCarsCursor.getString(2)
//                            +"\nDistance= "+allCarsCursor.getString(3)
//                            +"\nPrice= "+allCarsCursor.getString(4)
//                            +"\nAccidents= "+allCarsCursor.getString(5)
//                            +"\nOffers= "+allCarsCursor.getString(6)
//                            +"\n\n"
//            );
//            linearLayout.addView(textView);
//        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CarMenuViewModel.class);
        // TODO: Use the ViewModel
    }

}