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
import com.carlosbt.carlosbtrealstate.fragment.PropertiesFragment;
import com.carlosbt.carlosbtrealstate.retrofit.generator.ServiceGenerator;
import com.carlosbt.carlosbtrealstate.response.LoginResponse;
import com.carlosbt.carlosbtrealstate.retrofit.services.LoginService;
import com.carlosbt.carlosbasalloteteba_realstate.R;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

  Button btnLogin;
  TextView textSignup, sinReg;
  EditText email, password;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_login);

    btnLogin = findViewById(R.id.btnRegistro);
    textSignup = findViewById(R.id.signup);
    email = findViewById(R.id.editUser);
    password = findViewById(R.id.editPwd);
    sinReg=findViewById(R.id.without);

    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String username_txt = email.getText().toString();
        String password_txt = password.getText().toString();

        String credentials = Credentials.basic(username_txt, password_txt);

        LoginService service = ServiceGenerator.createService(LoginService.class);
        Call<LoginResponse> call = service.doLogin(credentials);

        call.enqueue(new Callback<LoginResponse>() {
          @Override
          public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
            if (response.code() != 201) {
              // error
              Log.e("RequestError", response.message());
              Toast.makeText(LoginActivity.this, "Error de petición", Toast.LENGTH_SHORT).show();
            } else {

              UtilToken.setToken(LoginActivity.this, response.body().getToken());
              UtilToken.setIdUser(LoginActivity.this, response.body().getUser().getId());

              //startActivity(new Intent(LoginActivity.this, HuertoActivity.class));
              startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
              finish();
            }
          }

          @Override
          public void onFailure(Call<LoginResponse> call, Throwable t) {
            Log.e("NetworkFailure", t.getMessage());
            Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
          }
        });


      }
    });

    textSignup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intentRegistro = new Intent(LoginActivity.this, RegistroActivity.class);
        Toast.makeText(LoginActivity.this, "Al registro que vamos", Toast.LENGTH_SHORT).show();

        startActivity(intentRegistro);
        finish();
      }
    });


    sinReg.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intentRegistro = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intentRegistro);
        finish();
      }
    });
  }
}
