package com.example.sergi.cycloguardian.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sergi.cycloguardian.Adapter.IncidenceAdapter;
import com.example.sergi.cycloguardian.Events.ThersholdEvent;
import com.example.sergi.cycloguardian.Models.Incidence;
import com.example.sergi.cycloguardian.Models.Session;
import com.example.sergi.cycloguardian.R;

import java.util.List;

import de.greenrobot.event.EventBus;


public class FragmentGaleryList extends Fragment {
    View mView;
    RecyclerView recyclerView;
    List<Incidence> incidenceList;

    public FragmentGaleryList() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this); //Registro al bus de evnetos

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_fragment_galery_list, container, false);
        incidenceList = Session.getInstance().getIncidenceArryList();
        //incidenceList.addChangeListener(this);
        recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);


        return mView;

    }

    //Subscripción al evento
    public void onEvent(final ThersholdEvent event) {
        //Toast.makeText(getActivity(), "HOLA", Toast.LENGTH_SHORT).show();
      /*  IncidenceAdapter incidenceAdapter = new IncidenceAdapter(Session.getInstance().getIncidenceArryList().get(event.getPosIncidence()),
                new IncidenceAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Incidence item) {
                        item = Session.getInstance().getIncidenceArryList().get(event.getPosIncidence());
                    }
                }, R.layout.photo_list_row, this.getActivity());
        recyclerView.setAdapter(incidenceAdapter);*/
    }



}
