package com.carlosbt.carlosbtrealstate.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

import com.carlosbt.carlosbtrealstate.response.Mine;
import com.carlosbt.carlosbtrealstate.response.PropertyResponse;
import com.carlosbt.carlosbtrealstate.response.ResponseContainer;
import com.carlosbt.carlosbtrealstate.retrofit.generator.ServiceGenerator;
import com.carlosbt.carlosbtrealstate.retrofit.generator.TipoAutenticacion;
import com.carlosbt.carlosbtrealstate.retrofit.services.PropertyService;
import com.carlosbt.carlosbtrealstate.UtilToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RemoveProperty extends DialogFragment {
    public static final String ARG_ITEM_ID = "item_id";


    public static RemoveProperty newInstance() {
        return new RemoveProperty();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String idProperty= getArguments().getString(ARG_ITEM_ID);

        builder.setTitle("¿Desea eliminar el proyecto?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                deleteInmueble(idProperty, getActivity());

            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }

    public void deleteInmueble(String id, final Context ctx){
        PropertyService service = ServiceGenerator.createService(PropertyService.class, UtilToken.getToken(getActivity()), TipoAutenticacion.JWT );
        Call<ResponseContainer<PropertyResponse>> call = service.deleteProperty(id);

        call.enqueue(new Callback<ResponseContainer<PropertyResponse>>() {

            @Override
            public void onResponse(Call<ResponseContainer<PropertyResponse>> call, Response<ResponseContainer<PropertyResponse>> response) {
                if (response.code() != 204) {
                    Toast.makeText(ctx, "Error en petición", Toast.LENGTH_SHORT).show();
                }
                String idProyecto = getArguments().getString(ARG_ITEM_ID);
                getProperty(idProyecto, ctx);
            }

            @Override
            public void onFailure(Call<ResponseContainer<PropertyResponse>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(ctx, "Error de conexión", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void getProperty(String idInmueble, final Context ctx) {
        PropertyService service = ServiceGenerator.createService(PropertyService.class, UtilToken.getToken(ctx), TipoAutenticacion.JWT);
        Call<ResponseContainer<Mine>> call = service.getUserProperties();

        call.enqueue(new Callback<ResponseContainer<Mine>>() {

            @Override
            public void onResponse(Call<ResponseContainer<Mine>> call, Response<ResponseContainer<Mine>> response) {
                if (response.code() != 200) {
                    Toast.makeText(ctx, "Error en petición", Toast.LENGTH_SHORT).show();
                } else {


                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Mine>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(ctx, "Error de conexión", Toast.LENGTH_SHORT).show();

            }

        });
    }

}
