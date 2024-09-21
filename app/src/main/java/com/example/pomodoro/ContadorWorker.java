package com.example.pomodoro;

import android.content.Context;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;

public class ContadorWorker extends Worker {
    public ContadorWorker(@NotNull Context context, @NotNull WorkerParameters workerParameters){
        super(context,workerParameters);
    }

    @NotNull
    @Override
    public Result doWork(){
        if (isStopped()) {
            Data data = new Data.Builder()
                    .putString("info","Worker cancelado")
                    .build();
            return Result.success(data); // Maneja la parada del trabajo
        }
        for(int i=1;i >= 0; i--){
            for(int j=10;j >= 0;j--){
                String j0;
                String i0;
                if(j<10){
                    j0 = "0"+j;
                }else{
                    j0 = String.valueOf(j);
                }
                if(i<10){
                    i0 = "0"+i;
                }else {
                    i0 = String.valueOf(i);
                }
                setProgressAsync(new Data.Builder().putString("contador", i0 +":"+ j0).build());
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    Data data = new Data.Builder()
                            .putString("info","Worker interrumpido")
                            .build();
                    return Result.failure(data);
                }
            }
        }
        Data data = new Data.Builder()
                .putString("info","Worker finalizado")
                .build();

        return Result.success(data);
    }
}
