package com.example.sergi.cycloguardian.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.sergi.cycloguardian.Adapter.IncidenceAdapter;
import com.example.sergi.cycloguardian.Events.ThersholdEvent;
import com.example.sergi.cycloguardian.Models.Incidence;
import com.example.sergi.cycloguardian.Models.Session;
import com.example.sergi.cycloguardian.MyApplication;
import com.example.sergi.cycloguardian.R;

import java.util.List;

import de.greenrobot.event.EventBus;


public class FragmentGaleryList extends Fragment {
    View mView;
    //RecyclerView recyclerView;
    List<Incidence> incidenceList;
    MyApplication myApplication;
    Gallery simpleGallery;
    IncidenceAdapter incidenceAdapter;
    ImageView selectedImageView;

    public FragmentGaleryList() {
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
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_fragment_galery_list, container, false);
        incidenceList = myApplication.mySession.getIncidenceArryList();
       /* recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        IncidenceAdapter incidenceAdapter = new IncidenceAdapter(incidenceList, R.layout.photo_list_row, getActivity());
        recyclerView.setAdapter(incidenceAdapter);*/

        //Gallery
        simpleGallery = (Gallery) mView.findViewById(R.id.simpleGallery); // get the reference of Gallery
        selectedImageView = (ImageView) mView.findViewById(R.id.selectedImageView);
        incidenceAdapter = new IncidenceAdapter(incidenceList, this.getContext());
        simpleGallery.setAnimationDuration(3000); // set 3000 milliseconds for animation duration between items of Gallery
        simpleGallery.setSpacing(5); // set space between the items of Gallery
        simpleGallery.setUnselectedAlpha(0.80f); // set 0.25 value for the alpha of unselected items of Gallery
        simpleGallery.setAdapter(incidenceAdapter); // set the adapter
        // perform setOnItemClickListener event on the Gallery
        simpleGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set the selected image in the ImageView
                Glide.with(getContext()).load(incidenceList.get(position).getImage().getUrl()).into(selectedImageView);
            }
        });


        return mView;

    }

    //Subscripción al evento
    public void onEvent(final ThersholdEvent event) {
        //Toast.makeText(getActivity(), "HOLA", Toast.LENGTH_SHORT).show();
      incidenceList = myApplication.mySession.getIncidenceArryList();
      //IncidenceAdapter incidenceAdapter = new IncidenceAdapter(incidenceList, R.layout.photo_list_row, getActivity());
      //recyclerView.setAdapter(incidenceAdapter);
    }



}
