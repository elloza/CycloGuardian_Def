package com.example.sergi.cycloguardian.Fragments;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sergi.cycloguardian.Events.ThersholdEvent;
import com.example.sergi.cycloguardian.Files.Photo;
import com.example.sergi.cycloguardian.MyApplication;
import com.example.sergi.cycloguardian.R;
import com.squareup.picasso.Picasso;

import de.greenrobot.event.EventBus;


public class FragmentGallery extends Fragment {

    ImageView imageView;
    View mView;
    ConstraintLayout constraintLayout;
    TextView textViewLoc;
    ViewGroup cont;

    //Object myAplication
    MyApplication myApplication;

    int lastImage = -1;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    public FragmentGallery() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = ((MyApplication)getActivity().getApplication());
        EventBus.getDefault().register(this); //Registro al bus de evnetos
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_galeria, container, false);
        imageView = (ImageView) mView.findViewById(R.id.imageViewPhoto);
        textViewLoc = (TextView) mView.findViewById(R.id.textViewLocalizacion);
        constraintLayout = (ConstraintLayout) mView.findViewById(R.id.relativeGallery);

        if (lastImage != -1) {
            Glide.with(this)
                    .load(myApplication.mySession.getIncidenceArryList().get(lastImage).getImage().getUrl())
                    .into(imageView);
        }


        return mView;
    }

    //Capture of event
    public void onEvent(ThersholdEvent thersholdEvent) {
        //Toast.makeText(getActivity(), myApplication.mySession.getIncidenceArryList().get(thersholdEvent.getPosIncidence()).getImage().getNamePhoto(), Toast.LENGTH_SHORT).show();;
        lastImage = thersholdEvent.getPosIncidence();
        showImage(myApplication.mySession.getIncidenceArryList().get(thersholdEvent.getPosIncidence()).getImage(), thersholdEvent.getPosIncidence());
    }


    private void showImage(final Photo photo, int index) {




        /*Uri pathUri = Uri.parse("/storage/emulated/0/CycloGuardian/SJCM0383.jpg");
        Picasso.with(imageView.getContext()).load(pathUri).noPlaceholder()
                .centerCrop().fit().into(imageView);*/


            Glide.with(imageView.getContext())
                    .load(photo.getUrl())
                    .into(imageView);


    }



}
