package com.example.cardealer.ui.carMenu;

import androidx.lifecycle.ViewModelProvider;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cardealer.R;
import com.example.cardealer.controller.DataBaseHelper;
import com.example.cardealer.view.MainActivity;

public class CarMenuFragment extends Fragment {

    private CarMenuViewModel mViewModel;

    public static CarMenuFragment newInstance() {
        return new CarMenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_menu_fragment, container, false);

        DataBaseHelper dataBaseHelper =new DataBaseHelper(getActivity(),"PROJ", null,1);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layout);
        linearLayout.removeAllViews();
        Cursor allCarsCursor = dataBaseHelper.getAllCars();
        linearLayout .removeAllViews();
        while (allCarsCursor.moveToNext()){
            TextView textView =new TextView(getActivity());
            textView.setText(
                    "Year= "+allCarsCursor.getString(0)
                            +"\nMake= "+allCarsCursor.getString(1)
                            +"\nDistance= "+allCarsCursor.getString(2)
                            +"\nPrice= "+allCarsCursor.getString(3)
                            +"\nAccidents= "+allCarsCursor.getString(4)
                            +"\nOffers= "+allCarsCursor.getString(5)
                            +"\n\n"
            );
            linearLayout.addView(textView);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CarMenuViewModel.class);
        // TODO: Use the ViewModel
    }

}