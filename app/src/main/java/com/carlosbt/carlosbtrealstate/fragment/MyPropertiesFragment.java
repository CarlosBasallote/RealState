package com.carlosbt.carlosbtrealstate.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.carlosbt.carlosbtrealstate.listener.PropertiesInteractionListener;
import com.carlosbt.carlosbtrealstate.response.Mine;
import com.carlosbt.carlosbtrealstate.response.ResponseContainer;
import com.carlosbt.carlosbtrealstate.retrofit.generator.ServiceGenerator;
import com.carlosbt.carlosbtrealstate.retrofit.generator.TipoAutenticacion;
import com.carlosbt.carlosbtrealstate.retrofit.services.PropertyService;
import com.carlosbt.carlosbasalloteteba_realstate.R;
import com.carlosbt.carlosbtrealstate.UtilToken;
import com.carlosbt.carlosbtrealstate.adapter.MyPropertiesRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPropertiesFragment extends Fragment {

    // TODO: Customize parameter argument names
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private PropertiesInteractionListener mListener;
    private List<Mine> inmuebleList;
    private MyPropertiesRecyclerViewAdapter adapter;
    private Context ctx;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyPropertiesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MyPropertiesFragment newInstance(int columnCount) {
        MyPropertiesFragment fragment = new MyPropertiesFragment();
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
        View view = inflater.inflate(R.layout.fragment_myproperties_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            ctx = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(ctx, mColumnCount));
            }
            inmuebleList = new ArrayList<>();

            PropertyService service = ServiceGenerator.createService(PropertyService.class, UtilToken.getToken(ctx), TipoAutenticacion.JWT);
            Call<ResponseContainer<Mine>> call = service.getUserProperties();

            call.enqueue(new Callback<ResponseContainer<Mine>>() {

                @Override
                public void onResponse(Call<ResponseContainer<Mine>> call, Response<ResponseContainer<Mine>> response) {
                    if (response.code() != 200) {
                        Toast.makeText(getActivity(), "Error en petición", Toast.LENGTH_SHORT).show();
                    } else {
                        inmuebleList = response.body().getRows();

                        adapter = new MyPropertiesRecyclerViewAdapter(
                                ctx,
                                inmuebleList,
                                mListener
                        );
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<ResponseContainer<Mine>> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }

            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PropertiesInteractionListener) {
            mListener = (PropertiesInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InmuebleListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }





}
