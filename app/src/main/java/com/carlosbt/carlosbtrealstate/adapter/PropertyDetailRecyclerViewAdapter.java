package com.carlosbt.carlosbtrealstate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carlosbt.carlosbtrealstate.listener.PropertiesInteractionListener;
import com.carlosbt.carlosbtrealstate.response.PropertyResponse;
import com.carlosbt.carlosbasalloteteba_realstate.R;

import java.util.List;

public class PropertyDetailRecyclerViewAdapter extends RecyclerView.Adapter<PropertyDetailRecyclerViewAdapter.ViewHolder>{
    private final List<PropertyResponse> mValues;
    private final PropertiesInteractionListener mListener;
    private Context ctx;

    public PropertyDetailRecyclerViewAdapter(Context ctx, List<PropertyResponse> items, PropertiesInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.ctx = ctx;

    }

    @Override
    public PropertyDetailRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_property_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PropertyDetailRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.deDescripcion.setText(mValues.get(position).getDescription());
        holder.dePrecio.setText(String.valueOf(mValues.get(position).getPrice()));
        holder.deCiudad.setText(mValues.get(position).getCity());
        holder.deHabitaciones.setText(String.valueOf(mValues.get(position).getRooms()));
        holder.itemView.setTag(mValues.get(position));
        holder.cp.setText(mValues.get(position).getZipcode());



    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView deDescripcion;
        public final TextView dePrecio;
        public final TextView deHabitaciones;
        public final TextView deCiudad;
        public final TextView cp;

        public PropertyResponse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            deDescripcion = view.findViewById(R.id.deDescripcion);
            dePrecio = view.findViewById(R.id.dePrecio);
            deHabitaciones = view.findViewById(R.id.deHab);
            cp = view.findViewById(R.id.cod);
            deCiudad = view.findViewById(R.id.deCiudad);

        }

    }
}
