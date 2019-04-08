package com.example.freight_travel.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.freight_travel.R;
import com.example.freight_travel.models.Conductor;
import com.example.freight_travel.service.QueriesRestAPIService;
import com.example.freight_travel.service.RetrofitService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

        try {
            saveImage();
        } catch (Exception ex) {
            tvNombreConductor.setText(ex.getMessage());
        }


    }

    public byte[] convertImageToArrayByte() throws IOException {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

        return stream.toByteArray();
    }

    private void saveImage() throws IOException {
        //File file = new File("/storage/emulated/0/DCIM/Camera/flia.jpg");
        File file = new File(
                Environment.getExternalStorageDirectory() + "/" +
                        android.os.Environment.DIRECTORY_DCIM + "/Camera/flia.jpg");

        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), fileReqBody);
        RequestBody description = RequestBody.create(MultipartBody.FORM, "image-type");

        int request = 0;
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                request);

        if (request == PackageManager.PERMISSION_GRANTED) {
            Call<ResponseBody> req = appService.saveImage(part, description);
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ResponseBody body = response.body();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    tvNombreConductor.setText(t.getMessage());
                }
            });
        }


    }

    private void saveDriver() {
        Call<Integer> conductorCall = appService.saveDriver("Denis");
        conductorCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer conductor = response.body();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
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
