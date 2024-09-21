package com.example.pomodoro;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;

import com.example.pomodoro.databinding.ActivityMainBinding;
import com.example.pomodoro.databinding.ActivityPomodoroBinding;
import com.example.pomodoro.dto.Tareas;
import com.example.pomodoro.dto.Users;
import com.example.pomodoro.services.DummyjsonService;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PomodoroActivity extends AppCompatActivity {
    ActivityPomodoroBinding binding;
    private boolean estadoPlay = false;
    private UUID uuid = UUID.randomUUID();
    DummyjsonService dummyjsonService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPomodoroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        String apellido = getIntent().getStringExtra("apellido");
        String email = getIntent().getStringExtra("email");
        String gender = getIntent().getStringExtra("gender");
        binding.tvUserName.setText(nombreUsuario+" "+apellido);
        binding.tvUserEmail.setText(email);
        if(Objects.equals(gender, "female")){
            binding.iconoPerfil.setImageResource(R.drawable.woman);
        }else{
            binding.iconoPerfil.setImageResource(R.drawable.people);
        }

        binding.btnLogout.setOnClickListener(view -> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });



        binding.btnPlayPause.setOnClickListener(view -> {
            if (estadoPlay) {
                // Cambiar icono a 'play'
                binding.btnPlayPause.setImageResource(android.R.drawable.ic_media_play);
                WorkManager.getInstance(binding.getRoot().getContext()).cancelWorkById(uuid);
                uuid=UUID.randomUUID();
                binding.tvTimer.setText("25:00");

            } else {
                // Cambiar icono a 'reiniciar'
                binding.btnPlayPause.setImageResource(android.R.drawable.ic_menu_revert);


                WorkRequest workRequest = new OneTimeWorkRequest.Builder(ContadorWorker.class)
                        .setId(uuid)
                        .build();
                WorkManager.getInstance(binding.getRoot().getContext())
                                .enqueue(workRequest);
                WorkManager.getInstance(binding.getRoot().getContext())
                        .getWorkInfoByIdLiveData(uuid)
                        .observe(PomodoroActivity.this, workInfo -> {
                            if(workInfo != null){
                                if(workInfo.getState() == WorkInfo.State.RUNNING){
                                    Data progress = workInfo.getProgress();
                                    String contador = progress.getString("contador");
                                    binding.tvTimer.setText(contador);
                                } else if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                    Data outputData = workInfo.getOutputData();
                                    String texto = outputData.getString("info");
                                    Log.d("msg-test",texto);
                                    Intent intent = new Intent(this,TareasActivity.class);
                                    //id de la persona
                                    int id = getIntent().getIntExtra("id",0);

                                    //Usando Retrofit
                                    dummyjsonService = new Retrofit.Builder()
                                            .baseUrl("https://dummyjson.com")
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build()
                                            .create(DummyjsonService.class);
                                    Log.d("msg-test","Se inicia la consulta");
                                    dummyjsonService.getTareasPorUsuario(id).enqueue(new Callback<Tareas>() {
                                        @Override
                                        public void onResponse(Call<Tareas> call, Response<Tareas> response) {
                                            if (response.isSuccessful()) {
                                                Tareas body = response.body();
                                                if(body.getTotal()==0){
                                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(PomodoroActivity.this);
                                                    alertDialog.setTitle("¡Felicidades!");
                                                    alertDialog.setMessage("Empezo el tiempo de descanso");
                                                    alertDialog.setPositiveButton("Entendido",
                                                            new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    Log.d("msgAlerta","Psositive");
                                                                }
                                                            });
                                                    alertDialog.show();

                                                }else{
                                                    //intent.putExtra("tarea", body.getTodos());
                                                    startActivity(intent);
                                                }
                                            } else {
                                                int statusCode = response.code();
                                                if (statusCode == 400) {
                                                    Log.d("msg-test", "error");
                                                }
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<Tareas> call, Throwable t) {
                                            t.printStackTrace();
                                        }
                                    });
                                } else if (workInfo.getState() == WorkInfo.State.CANCELLED) {
                                    Data outputData = workInfo.getOutputData();
                                    String texto = outputData.getString("info");
                                    Log.d("msg-test",texto);
                                }

                            }else {
                                Log.d("msg-test","work info == null");
                            }
                        });
            }
            estadoPlay = !estadoPlay;  // Cambiar el estado
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}