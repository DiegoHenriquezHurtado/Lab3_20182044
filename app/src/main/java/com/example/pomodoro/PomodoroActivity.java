package com.example.pomodoro;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pomodoro.databinding.ActivityMainBinding;
import com.example.pomodoro.databinding.ActivityPomodoroBinding;

import java.util.Objects;

public class PomodoroActivity extends AppCompatActivity {
    ActivityPomodoroBinding binding;
    private boolean estadoPlay = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPomodoroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        String email = getIntent().getStringExtra("email");
        String gender = getIntent().getStringExtra("gender");
        binding.tvUserName.setText(nombreUsuario);
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
            } else {
                // Cambiar icono a 'pause'
                binding.btnPlayPause.setImageResource(android.R.drawable.ic_menu_revert);
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