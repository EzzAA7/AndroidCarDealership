package com.example.cardealer.ui.specialOffers;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cardealer.R;

public class SpecialOffersFragment extends Fragment {

    private SpecialOffersViewModel mViewModel;

    public static SpecialOffersFragment newInstance() {
        return new SpecialOffersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.special_offers_fragment, container, false);

        View firstOffer = view.findViewById(R.id.include1);
        // geting the percentage textview declared in second offer specialOffer.xml
        TextView percentText1 = (TextView) firstOffer.findViewById(R.id.percentage);
        percentText1.setText("5% OFF");
        // geting the percentage textview declared in second offer specialOffer.xml
        TextView infoText1 = (TextView) firstOffer.findViewById(R.id.info);
        infoText1.setText("MAZDA 6");
        // geting the expiration textview declared in second offer specialOffer.xml
        TextView expireText1 = (TextView) firstOffer.findViewById(R.id.expiration);
        expireText1.setText("Offer is valid until 25/6/2021");

        View secondOffer = view.findViewById(R.id.include2);
        // geting the percentage textview declared in second offer specialOffer.xml
        TextView percentText2 = (TextView) secondOffer.findViewById(R.id.percentage);
        percentText2.setText("7% OFF");
        // geting the percentage textview declared in second offer specialOffer.xml
        TextView infoText2 = (TextView) secondOffer.findViewById(R.id.info);
        infoText2.setText("MAZDA 3");
        // geting the expiration textview declared in second offer specialOffer.xml
        TextView expireText2 = (TextView) secondOffer.findViewById(R.id.expiration);
        expireText2.setText("Offer is valid until 30/6/2021");

        View thirdOffer = view.findViewById(R.id.include3);
        // geting the percentage textview declared in thirdOffer specialOffer.xml
        TextView percentText3 = (TextView) thirdOffer.findViewById(R.id.percentage);
        percentText3.setText("8% OFF");
        // geting the percentage textview declared in thirdOffer specialOffer.xml
        TextView infoText3 = (TextView) thirdOffer.findViewById(R.id.info);
        infoText3.setText("MAZDA 2");
        // geting the expiration textview declared in thirdOffer specialOffer.xml
        TextView expireText3 = (TextView) thirdOffer.findViewById(R.id.expiration);
        expireText3.setText("Offer is valid until 5/7/2021");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SpecialOffersViewModel.class);
        // TODO: Use the ViewModel
    }

}