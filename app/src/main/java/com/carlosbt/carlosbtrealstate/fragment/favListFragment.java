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
import android.widget.Toast;

import com.carlosbt.carlosbtrealstate.listener.PropertiesInteractionListener;
import com.carlosbt.carlosbtrealstate.response.PropertyResponse;
import com.carlosbt.carlosbtrealstate.response.ResponseContainer;
import com.carlosbt.carlosbtrealstate.retrofit.generator.ServiceGenerator;
import com.carlosbt.carlosbtrealstate.retrofit.generator.TipoAutenticacion;
import com.carlosbt.carlosbtrealstate.retrofit.services.PropertyService;
import com.carlosbt.carlosbasalloteteba_realstate.R;
import com.carlosbt.carlosbtrealstate.UtilToken;
import com.carlosbt.carlosbtrealstate.adapter.MyfavListRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class favListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private PropertiesInteractionListener mListener;
    private RecyclerView recyclerView;
    private Context ctx;
    private List<PropertyResponse> inmuebleFavList;
    private MyfavListRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public favListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static favListFragment newInstance(int columnCount) {
        favListFragment fragment = new favListFragment();
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
        View view = inflater.inflate(R.layout.fragment_favlist_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            inmuebleFavList = new ArrayList<>();

            PropertyService service = ServiceGenerator.createService(PropertyService.class, UtilToken.getToken(context), TipoAutenticacion.JWT);
            Call<ResponseContainer<PropertyResponse>> call = service.getFavouritesProperties();

            call.enqueue(new Callback<ResponseContainer<PropertyResponse>>() {

                @Override
                public void onResponse(Call<ResponseContainer<PropertyResponse>> call, Response<ResponseContainer<PropertyResponse>> response) {
                    if (response.code() != 200) {
                        Toast.makeText(getActivity(), "Error en petición", Toast.LENGTH_SHORT).show();
                    } else {
                        inmuebleFavList = response.body().getRows();

                        adapter = new MyfavListRecyclerViewAdapter(
                                ctx,
                                inmuebleFavList,
                                mListener
                        );
                        recyclerView.setAdapter(adapter);
                    }
                    Toast.makeText(getActivity(),"HOLA: "+adapter.toString(),Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ResponseContainer<PropertyResponse>> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(getActivity(), "Error de conexión fav", Toast.LENGTH_SHORT).show();
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
