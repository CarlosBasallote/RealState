package com.carlosbt.carlosbtrealstate.fragment;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.carlosbt.carlosbtrealstate.response.PropertyResponseOne;
import com.carlosbt.carlosbtrealstate.response.Rows;
import com.carlosbt.carlosbtrealstate.retrofit.generator.ServiceGenerator;
import com.carlosbt.carlosbtrealstate.retrofit.services.PropertyService;
import com.carlosbt.carlosbasalloteteba_realstate.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PropertyDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private Rows mItem;


    private TextView deDescripcion, dePrecio;




    public PropertyDetailFragment() {
        //
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.content_property_detail, container, false);

        // Show the dummy content as text in a TextView.

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.


            final String idProperty= getArguments().getString(ARG_ITEM_ID);

            Activity activity = this.getActivity();
            final CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            PropertyService service = ServiceGenerator.createService(PropertyService.class);

            Call<PropertyResponseOne> call = service.oneProperty(idProperty);
            call.enqueue(new Callback<PropertyResponseOne>() {
                @Override
                public void onResponse(Call<PropertyResponseOne> call, Response<PropertyResponseOne> response) {
                    if (response.isSuccessful()) {
                        mItem = response.body().getRows();
                        appBarLayout.setTitle(mItem.getTitle());
                        if(mItem.getPhotos().size() == 0) {
                            Glide.with(getContext())
                                    .load("http://www.sclance.com/pngs/legal-png/legal_png_780684.png")
                                    .into(new SimpleTarget<Drawable>(){

                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                            appBarLayout.setBackground(resource);
                                        }
                                    });

                        } else {
                            Glide
                                    .with(getContext())
                                    .load(mItem.getPhotos().get(0))
                                    .into(new SimpleTarget<Drawable>(){

                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                            appBarLayout.setBackground(resource);
                                        }
                                    });
                        }



                        ((TextView) rootView.findViewById(R.id.deDescripcion)).setText("Descripcion: " + mItem.getDescription());
                        ((TextView) rootView.findViewById(R.id.dePrecio)).setText("Precio: " + String.valueOf(mItem.getPrice()));
                        ((TextView) rootView.findViewById(R.id.deHab)).setText("Nº Habitaciones: " + String.valueOf(mItem.getRooms()));
                        ((TextView) rootView.findViewById(R.id.cod)).setText("Direccion: " + mItem.getAddress());
                        ((TextView) rootView.findViewById(R.id.deCiudad)).setText("Ciudad: " + mItem.getCity());
                    } else {
                        // Toast
                    }


                }

                @Override
                public void onFailure(Call<PropertyResponseOne> call, Throwable t) {
                    // Toast
                    Log.i("onFailure", "error en retrofit");
                }
            });


        }


        return rootView;
    }
}