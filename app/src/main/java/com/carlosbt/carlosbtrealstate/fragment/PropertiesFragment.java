package com.carlosbt.carlosbtrealstate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carlosbt.carlosbtrealstate.listener.PropertiesInteractionListener;
import com.carlosbt.carlosbtrealstate.response.PropertyResponse;
import com.carlosbt.carlosbtrealstate.response.ResponseContainer;
import com.carlosbt.carlosbtrealstate.retrofit.generator.ServiceGenerator;
import com.carlosbt.carlosbtrealstate.retrofit.services.PropertyService;
import com.carlosbt.carlosbasalloteteba_realstate.R;
import com.carlosbt.carlosbtrealstate.adapter.PropertiesRecyclerViewAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PropertiesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private PropertiesInteractionListener mListener;
    private RecyclerView recyclerView;
    private Context ctx;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PropertiesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PropertiesFragment newInstance(int columnCount) {
        PropertiesFragment fragment = new PropertiesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_properties_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = view.findViewById(R.id.listProperties);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            // Aqui listo Properties
            PropertyService service = ServiceGenerator.createService(PropertyService.class);

            Call<ResponseContainer<PropertyResponse>> call = service.listProperties();
            call.enqueue(new Callback<ResponseContainer<PropertyResponse>>() {
                @Override
                public void onResponse(Call<ResponseContainer<PropertyResponse>> call, Response<ResponseContainer<PropertyResponse>> response) {
                    if (response.isSuccessful()) {
                        recyclerView.setAdapter(new PropertiesRecyclerViewAdapter(ctx, response.body().getRows(), mListener));
                    } else {
                        // Toast
                    }


                }

                @Override
                public void onFailure(Call<ResponseContainer<PropertyResponse>> call, Throwable t) {
                    // Toast
                    Log.i("onFailure", "error en retrofit");
                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.ctx = context;
        if (context instanceof PropertiesInteractionListener) {
            mListener = (PropertiesInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
