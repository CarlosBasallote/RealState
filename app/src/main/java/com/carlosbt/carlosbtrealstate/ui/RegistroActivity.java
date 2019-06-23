package com.carlosbt.carlosbtrealstate.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.carlosbt.carlosbtrealstate.UtilToken;
import com.carlosbt.carlosbtrealstate.model.Registro;
import com.carlosbt.carlosbtrealstate.response.LoginResponse;
import com.carlosbt.carlosbtrealstate.retrofit.generator.ServiceGenerator;
import com.carlosbt.carlosbtrealstate.retrofit.services.LoginService;
import com.carlosbt.carlosbasalloteteba_realstate.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    Button btnRegistro;
    TextView textLogin;
    EditText name, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registro);

        btnRegistro = findViewById(R.id.btnRegistro);
        textLogin = findViewById(R.id.textBack);
        name = findViewById(R.id.editUser);
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPwd);


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (name.getText().toString().matches("")
                        || email.getText().toString().matches("")
                        || password.getText().toString().matches("")) {
                    Toast.makeText(RegistroActivity.this, "Debe introducir todos los campos.", Toast.LENGTH_SHORT).show();
                } else if (!email.getText().toString().matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$")) {
                    Toast.makeText(RegistroActivity.this, "Debe introducir un correo válido.", Toast.LENGTH_SHORT).show();
                } else {

                    // Recoger los datos del formulario
                    String etName = name.getText().toString().trim();
                    String etEmail = email.getText().toString().trim();
                    String etPassword = password.getText().toString().trim();

                    Registro registro = new Registro(etName, etEmail, etPassword);

                    LoginService service = ServiceGenerator.createService(LoginService.class);

                    Call<LoginResponse> loginReponseCall = service.doRegister(registro);

                    loginReponseCall.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.code() == 201) {
                                //ServiceGenerator.jwtToken = response.body().getToken();
                                UtilToken.setToken(RegistroActivity.this, response.body().getToken());
                                UtilToken.setIdUser(RegistroActivity.this, response.body().getUser().getId());

                                startActivity(new Intent(RegistroActivity.this, DashboardActivity.class));                        // Toast.makeText(RegistroActivity.this, "Usuario registrado y logeado con éxito", Toast.LENGTH_LONG).show();
                                finish();

                            } else {
                                // error
                                Toast.makeText(RegistroActivity.this, "Error en el registro. Revise los datos introducidos", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Log.e("NetworkFailure", t.getMessage());
                            Toast.makeText(RegistroActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();

                        }
                    });





                }

            }
        });
        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegistro = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(intentRegistro);
                finish();
            }
        });

    }
}

