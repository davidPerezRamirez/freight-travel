package com.example.freight_travel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    private ConductorService appService;

    @BindView(R.id.tv_nombre_conductor)
    TextView tvNombreConductor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appService = RetrofitService.connectToHerokuApp();
        try {
            Conductor conductor = appService.all().execute().body().get(0);
            tvNombreConductor.setText(conductor.getNombre());
        } catch (Exception ex) {
            tvNombreConductor.setText(ex.getCause().toString());
        }
    }
}
