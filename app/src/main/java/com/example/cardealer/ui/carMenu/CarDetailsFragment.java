package com.example.cardealer.ui.carMenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cardealer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarDetailsFragment extends Fragment {

    public CarDetailsFragment() {
    }

    public static CarDetailsFragment newInstance() {
        CarDetailsFragment fragment = new CarDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_details, container, false);
    }
}