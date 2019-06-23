package com.carlosbt.carlosbtrealstate.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.carlosbt.carlosbtrealstate.UtilToken;
import com.carlosbt.carlosbtrealstate.model.PropertyDTO;
import com.carlosbt.carlosbtrealstate.response.CategoryId;
import com.carlosbt.carlosbtrealstate.response.PropertyResponseOne;
import com.carlosbt.carlosbtrealstate.retrofit.generator.ServiceGenerator;
import com.carlosbt.carlosbtrealstate.retrofit.generator.TipoAutenticacion;
import com.carlosbt.carlosbtrealstate.retrofit.services.PropertyService;
import com.carlosbt.carlosbasalloteteba_realstate.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPropertyActivity extends AppCompatActivity {

    private EditText addTitulo, addDescripcion, addHab, addPrice, addSize, addCod, addDireccion, addCiudad, addProvincia;
    private Button btnAddProperty;
    private ImageView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        addTitulo = findViewById(R.id.addTitle);
        addDescripcion = findViewById(R.id.addDescription);
        addHab = findViewById(R.id.addRooms);
        addPrice = findViewById(R.id.addPrice);
        addHab = findViewById(R.id.addRooms);
        addSize = findViewById(R.id.addSize);
        addCod = findViewById(R.id.addZipcode);
        addDireccion = findViewById(R.id.addAddress);
        addCiudad = findViewById(R.id.addCity);
        addProvincia = findViewById(R.id.addProvince);
        btnAddProperty = findViewById(R.id.buttonAddProperty);
        cancel=findViewById(R.id.buttonCancel);


                btnAddProperty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PropertyDTO newProperty = new PropertyDTO(
                                addTitulo.getText().toString(),
                                addDescripcion.getText().toString(),
                                Integer.valueOf(addPrice.getText().toString()),
                                Integer.valueOf(addHab.getText().toString()),
                                Integer.valueOf(addSize.getText().toString()),
                                addCod.getText().toString(),
                                addDireccion.getText().toString(),
                                addCiudad.getText().toString(),
                                addProvincia.getText().toString(),
                                "1142424, 1142242424");


                        PropertyService service = ServiceGenerator.createService(PropertyService.class, UtilToken.getToken(AddPropertyActivity.this), TipoAutenticacion.JWT);
                        Call<PropertyResponseOne> call = service.addProperty(newProperty);

                        call.enqueue(new Callback<PropertyResponseOne>() {
                            @Override
                            public void onResponse(Call<PropertyResponseOne> call, Response<PropertyResponseOne> response) {
                                if(response.isSuccessful()) {
                                    Toast.makeText(AddPropertyActivity.this, "Vivienda registrada", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(AddPropertyActivity.this, DashboardActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(AddPropertyActivity.this, "Error de registro", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<PropertyResponseOne> call, Throwable t) {
                                Toast.makeText(AddPropertyActivity.this, "Error de registro", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegistro = new Intent(AddPropertyActivity.this, DashboardActivity.class);
                startActivity(intentRegistro);
                finish();
            }
        });
    }
}
