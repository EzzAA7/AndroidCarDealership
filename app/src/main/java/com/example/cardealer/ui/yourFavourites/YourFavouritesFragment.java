package com.example.cardealer.ui.yourFavourites;

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

import com.example.cardealer.R;
import com.example.cardealer.controller.CarAdapter;
import com.example.cardealer.controller.DataBaseHelper;
import com.example.cardealer.controller.FavouriteAdapter;
import com.example.cardealer.model.Car;
import com.example.cardealer.model.Favourite;
import com.example.cardealer.model.Reservation;
import com.example.cardealer.service.SharedPrefManager;

import java.util.ArrayList;

public class YourFavouritesFragment extends Fragment {

    private YourFavouritesViewModel mViewModel;
    RecyclerView recyclerView;
    FavouriteAdapter favouriteAdapter;
    ArrayList<Favourite> favourites;
    SharedPrefManager sharedPrefManager;

    public static YourFavouritesFragment newInstance() {
        return new YourFavouritesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.your_favourites_fragment, container, false);
        DataBaseHelper dataBaseHelper =new DataBaseHelper(getActivity(),"PROJ", null,1);
//        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layout);
//        linearLayout.removeAllViews();

        recyclerView = view.findViewById(R.id.recycler_view);
        favourites = dataBaseHelper.getAllFavouritesList();

        sharedPrefManager = SharedPrefManager.getInstance(getActivity());
        String email = sharedPrefManager.readString("Session","noValue");

        ArrayList<Favourite> myFavourites = new ArrayList<Favourite>();
        for (Favourite favourite : favourites) {
            if (favourite.getEmail().equals(email)) {
                myFavourites.add(favourite);
            }
        }

        favouriteAdapter = new FavouriteAdapter(myFavourites, getActivity());
        recyclerView.setAdapter(favouriteAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(llm);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(YourFavouritesViewModel.class);
        //
    }

}