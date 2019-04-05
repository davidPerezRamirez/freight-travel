package com.example.freight_travel.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.freight_travel.R;
import com.example.freight_travel.models.Conductor;
import com.example.freight_travel.service.QueriesRestAPIService;
import com.example.freight_travel.service.RetrofitService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private QueriesRestAPIService appService;
    private Unbinder binder;
    private RetrofitService retrofitService;

    @BindView(R.id.tv_nombre_conductor)
    TextView tvNombreConductor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binder = ButterKnife.bind(this);
        this.retrofitService = new RetrofitService();
        appService = retrofitService.connectToHerokuApp();

        Call<List<Conductor>> conductores = appService.listConductores();
        conductores.enqueue(new Callback<List<Conductor>>() {
            @Override
            public void onResponse(Call<List<Conductor>> call, Response<List<Conductor>> response) {
                Conductor conductor = response.body().get(0);
                tvNombreConductor.setText(conductor.getNombre().toString());
            }

            @Override
            public void onFailure(Call<List<Conductor>> call, Throwable t) {
                tvNombreConductor.setText(t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        binder.unbind();
        super.onDestroy();
    }
}
