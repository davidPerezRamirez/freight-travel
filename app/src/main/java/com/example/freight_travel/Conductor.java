package com.example.freight_travel;

import com.google.gson.annotations.SerializedName;

public class Conductor {

    @SerializedName("idConductor")
    int id;
    @SerializedName("nombre")
    String nombre;
    @SerializedName("apellido")
    String apellido;
    @SerializedName("dni")
    Integer dni;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
