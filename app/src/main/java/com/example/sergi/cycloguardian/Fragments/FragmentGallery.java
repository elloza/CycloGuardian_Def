package com.example.sergi.cycloguardian.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
        EventBus.getDefault().register(this); //Registro al bus de evnetos
        myApplication = ((MyApplication)getActivity().getApplication());
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

    // This method will be called when a HelloWorldEvent is posted
    public void onEvent(ThersholdEvent event) {
        Log.i("GALL", myApplication.mySession.getIncidenceArryList().get(event.getPosIncidence()).getImage().getNamePhoto());
        extractBitmap(myApplication.mySession.getIncidenceArryList().get(event.getPosIncidence()).getImage());
    }


    private void extractBitmap(final Photo photo) {
        Glide.with(imageView.getContext())
                .load(photo.getUrl())
                .into(imageView);
        Glide
                .with(this)
                .load(photo.getUrl())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(300,300) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        String ruta = saveToInternalStorage(resource, photo.getNamePhoto());
                        photo.setRutaInterna(ruta);
                    }
                });
    }

    private String saveToInternalStorage(Bitmap bitmap, String name){
        //imageView.setImageBitmap(bitmap);
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/CycloGuardian");

        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    (Activity) getContext(),
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        myDir.mkdirs();

        File file = new File (myDir, name);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }


}
