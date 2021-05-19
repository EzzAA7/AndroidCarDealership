package com.example.cardealer.ui.contact;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cardealer.R;

public class ContactFragment extends Fragment {

    private ContactViewModel mViewModel;

    public static ContactFragment newInstance() {

        return new ContactFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.contact_fragment, container, false);
        Button dialButton = (Button) view.findViewById(R.id.button_dial);
        Button gmailButton = (Button) view.findViewById(R.id.button_gmail);
        Button mapsButton = (Button) view.findViewById(R.id.button_maps);

        ImageView img = (ImageView) view.findViewById(R.id.imageViewCar);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.translate));
            }
        });

        dialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dialIntent =new Intent();
                dialIntent.setAction(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:+972599000000”"));
                startActivity(dialIntent);
            }
        });

        gmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Intent gmailIntent =new Intent();
                String recipient = "CarDealer@cars.com";
                gmailIntent.setAction(Intent.ACTION_SENDTO);
                gmailIntent.setType("message/rfc822");
                gmailIntent.setData(Uri.parse("mailto:"));
                gmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
                gmailIntent.putExtra(Intent.EXTRA_SUBJECT,"Contacting your car dealership");
                gmailIntent.putExtra(Intent.EXTRA_TEXT,"I have an enquiry");
                startActivity(gmailIntent);
            }
        });

        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Intent mapsIntent =new Intent();
                mapsIntent.setAction(Intent.ACTION_VIEW);
                mapsIntent.setData(Uri.parse("geo:35.076,31.8777"));
                startActivity(mapsIntent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        //
    }

}