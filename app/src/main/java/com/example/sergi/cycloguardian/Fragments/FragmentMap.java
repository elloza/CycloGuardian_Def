package com.example.sergi.cycloguardian.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sergi.cycloguardian.Events.LocationEvent;
import com.example.sergi.cycloguardian.Events.ThersholdEvent;
import com.example.sergi.cycloguardian.MyApplication;
import com.example.sergi.cycloguardian.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;

import de.greenrobot.event.EventBus;


public class FragmentMap extends Fragment implements OnMapReadyCallback{

    GoogleMap mGoogleMap;
    MarkerOptions options;
    MapView mMapView;
    SupportMapFragment mapFragment;
    View mView;
    ArrayList<LatLng> myLocations = null;
    MyApplication myApplication;

    public FragmentMap() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this); //Registro al bus de evnetos
        myApplication = ((MyApplication)getActivity().getApplication());
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


        MapsInitializer.initialize(getContext());
        mapFragment.getMapAsync(this);

        return mView;
    }


    // This method will be called when a ThersholEvent is posted
    public void onEvent(ThersholdEvent event){
        // Implementation when somo event was recive
        myLocations.add(myApplication.mySession.getIncidenceArryList().get(event.getPosIncidence()).getPosicion());
        Log.i("MAP", String.valueOf(myApplication.mySession.getIncidenceArryList().get(event.getPosIncidence()).getPosicion()));
        addMarkerToMap();
    }

   public void onEvent(LocationEvent locationEvent) {
       addMarkerToMap();
   }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        options = new MarkerOptions();


        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(40.968725, -5.663223))
                    .zoom(8).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }

    public void addMarkerToMap() {

        float minX = 0.0f;  //Para calcular aleatoriamente el color del marcador
        float maxX = 360.0f;
        float finalX;
        Random rand = new Random();
        LatLng point = new LatLng((rand.nextDouble() * -180.0) + 90.0, (rand.nextDouble() * -360.0) + 180.0);
        double latitude = point.latitude;
        double longitude = point.longitude;
        finalX = rand.nextFloat() * (maxX - minX) + minX;
        Log.i("MAP", String.valueOf(point.latitude) + " " + String.valueOf(point.longitude) + " " + String.valueOf(finalX));

        mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title("PRUEBA")
                .icon(BitmapDescriptorFactory.defaultMarker(finalX)));

        CameraPosition cameraPosition = CameraPosition.builder().target(point)
                .zoom(16).bearing(0).tilt(45).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        
    }



}
