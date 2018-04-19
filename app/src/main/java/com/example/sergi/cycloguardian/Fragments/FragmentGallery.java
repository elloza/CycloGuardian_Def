package com.example.sergi.cycloguardian.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.sergi.cycloguardian.Events.ThersholdEvent;
import com.example.sergi.cycloguardian.Files.Photo;
import com.example.sergi.cycloguardian.Models.Session;
import com.example.sergi.cycloguardian.MyApplication;
import com.example.sergi.cycloguardian.R;
import com.google.android.gms.maps.MapView;

import java.io.File;
import java.io.FileOutputStream;

import de.greenrobot.event.EventBus;


public class FragmentGallery extends Fragment {

    ImageView imageView;
    View mView;
    ConstraintLayout constraintLayout;
    TextView textViewLoc;
    ViewGroup cont;

    //Object myAplication
    MyApplication myApplication;

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

        return mView;
    }

    //Capture of event
    public void onEvent(ThersholdEvent thersholdEvent) {
        Toast.makeText(getActivity(), myApplication.mySession.getIncidenceArryList().get(thersholdEvent.getPosIncidence()).getImage().getNamePhoto(), Toast.LENGTH_SHORT).show();;
        showImage(myApplication.mySession.getIncidenceArryList().get(thersholdEvent.getPosIncidence()).getImage(), thersholdEvent.getPosIncidence());
    }


    private void showImage(final Photo photo, int index) {
        /*String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/CycloGuardian");

        Uri file = Uri.fromFile(new File(myDir, myApplication.mySession.getIncidenceArryList().get(index).getImage().getNamePhoto()));

        if(file.toString() != null && file.toString().length()>0) { }*/
            Glide.with(imageView.getContext())
                    .load(photo.getUrl())
                    .into(imageView);


    }



}
