package com.example.pomodoro;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pomodoro.databinding.ActivityMainBinding;
import com.example.pomodoro.dto.Users;
import com.example.pomodoro.services.DummyjsonService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    DummyjsonService dummyjsonService;
    String nombreIngresado;
    String contrasenaIngresada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(view -> {
            Intent intent = new Intent(this,PomodoroActivity.class);
            nombreIngresado = binding.editTextText.getText().toString();
            contrasenaIngresada = binding.editTextTextPassword.getText().toString();

            dummyjsonService.existeUser(nombreIngresado,contrasenaIngresada).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    if (response.isSuccessful()) {
                        Users body = response.body();
                        // Usuario encontrado, proceder con el login
                        intent.putExtra("nombreUsuario",nombreIngresado);
                        intent.putExtra("email",body.getEmail());
                        intent.putExtra("gender",body.getGender());
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Usuario logueado", Toast.LENGTH_SHORT).show();
                        Log.d("msg-test", "Usuario encontrado, email: " + body.getEmail());
                    } else {
                        int statusCode = response.code();
                        if (statusCode == 400) {
                            // Credenciales incorrectas
                            Log.d("msg-test", "Credenciales incorrectas");
                            Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    t.printStackTrace();
                }
            });


        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /******************************* Internet *****************************/
        Toast.makeText(this, "Tiene internet: " + tengoInternet(), Toast.LENGTH_LONG).show();

        //Usando Retrofit
        dummyjsonService = new Retrofit.Builder()
                .baseUrl("https://dummyjson.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DummyjsonService.class);



    }

    public boolean tengoInternet() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean tieneInternet = false;
        if (connectivityManager != null) {
            NetworkCapabilities capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("msg-Internet", "NetworkCapabilities.TRANSPORT_CELLULAR");
                    tieneInternet = true;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("msg-Internet", "NetworkCapabilities.TRANSPORT_WIFI");
                    tieneInternet = true;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("msg-Internet", "NetworkCapabilities.TRANSPORT_ETHERNET");
                    tieneInternet = true;
                }
            }
        }
        return tieneInternet;
    }
}