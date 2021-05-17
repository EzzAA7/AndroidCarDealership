package com.example.cardealer.ui.deleteCustomers;

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
import com.example.cardealer.controller.DataBaseHelper;
import com.example.cardealer.controller.UserAdapter;
import com.example.cardealer.model.Car;
import com.example.cardealer.model.User;

import java.util.ArrayList;

public class DeleteCustomersFragment extends Fragment {

    private DeleteCustomersViewModel mViewModel;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    ArrayList<User> users, customers;

    public static DeleteCustomersFragment newInstance() {
        return new DeleteCustomersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_customers_fragment, container, false);

        DataBaseHelper dataBaseHelper =new DataBaseHelper(getActivity(),"PROJ", null,1);

        recyclerView = view.findViewById(R.id.recycler_view);
        users = dataBaseHelper.getAllUsersList();

        customers = new ArrayList<User>();
        for (User user : users) {
            if (user.getRole().equals("customer")) {
                customers.add(user);
            }
        }

        userAdapter = new UserAdapter(customers, getActivity());
        recyclerView.setAdapter(userAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(llm);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DeleteCustomersViewModel.class);
    }

}