package com.example.sergi.cycloguardian.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sergi.cycloguardian.Events.LocationEvent;
import com.example.sergi.cycloguardian.Events.ThersholdEvent;
import com.example.sergi.cycloguardian.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;

import de.greenrobot.event.EventBus;


public class FragmentMap extends Fragment implements OnMapReadyCallback{

    GoogleMap mGoogleMap;
    MapView mMapView;
    SupportMapFragment mapFragment;
    View mView;
    ArrayList<LatLng> myLocations = null;

    public FragmentMap() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this); //Registro al bus de evnetos
        myLocations = new ArrayList<LatLng>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        return mView;
    }


    // This method will be called when a ThersholEvent is posted
    public void onEvent(ThersholdEvent event){
        // Implementation when somo event was recive
        //Toast.makeText(getActivity(), event.getDireccionUbicacion(), Toast.LENGTH_SHORT).show();
       // myLocations.add(event.getLocalization());
    }

    //This method will be called when a LocationEvent is poste
    public void onEvent(LocationEvent event) {
        Toast.makeText(getActivity(), (int) event.getUbicacion().latitude, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MarkerOptions options = new MarkerOptions();
        MapsInitializer.initialize(getContext());
        float minX = 0.0f;  //Para calcular aleatoriamente el color del marcador
        float maxX = 360.0f;
        float finalX;
        Random rand = new Random();

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        /*if(myLocations != null) {
            //Añadimos los distintos marcadores que tengamos en el ArrayList
            for (LatLng point : myLocations) {
                finalX = rand.nextFloat() * (maxX - minX) + minX;
                options.position(point);
                options.title("someTitle");
                options.snippet("someDesc");
                options.icon(BitmapDescriptorFactory.defaultMarker(finalX));
                googleMap.addMarker(options);
            }
            }*/

            googleMap.addMarker(new MarkerOptions().position(new LatLng(40.689247, -74.044502)).title("State of liberty"));

            CameraPosition Liberty = CameraPosition.builder().target(new LatLng(40.689247, -74.044502))
                    .zoom(16).bearing(0).tilt(45).build();
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));

    }



}
