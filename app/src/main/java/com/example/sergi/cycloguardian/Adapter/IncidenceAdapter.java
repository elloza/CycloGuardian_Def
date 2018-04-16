package com.example.sergi.cycloguardian.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergi.cycloguardian.Files.Photo;
import com.example.sergi.cycloguardian.Models.Incidence;
import com.example.sergi.cycloguardian.R;

import java.util.List;

/**
 * Created by sergi on 14/04/2018.
 */

public class IncidenceAdapter extends RecyclerView.Adapter<IncidenceAdapter.MyViewHolder> {

    public List<Incidence> incidenceList;
    private int layout;
    private Activity activity;
    private OnItemClickListener listener;

    //Constructor de la clase
    public IncidenceAdapter(Incidence incidence, OnItemClickListener listener, int layout, Activity activity) {
        this.layout = layout;
        this.activity = activity;
        this.incidenceList.add(incidence);
        this.listener = listener;

    }

    //Constructor sin argumentos
    public IncidenceAdapter() {

    }

    public interface OnItemClickListener {
        void onItemClick(Incidence item);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, ubi, fecha;
        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            ubi = (TextView) view.findViewById(R.id.ubicacionPhoto);
            fecha = (TextView) view.findViewById(R.id.fecha);
            imageView = (ImageView) view.findViewById(R.id.imagePhoto);
            view.setOnClickListener((View.OnClickListener) this);

        }

        public void bind(final Incidence item, final OnItemClickListener listener) {
            title.setText(item.getImage().getNamePhoto());
            ubi.setText(item.getIncidenceDirection());
            fecha.setText((CharSequence) item.getTimeIncidence());
            //Picasso.with(itemView.getContext()).load(item.imageUrl).into(image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public void updateData(List<Incidence> dataset) {
        incidenceList.clear();
        incidenceList.addAll(dataset);
        notifyDataSetChanged();
    }

    @Override
    public IncidenceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IncidenceAdapter.MyViewHolder holder, int position) {
        holder.bind(incidenceList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
