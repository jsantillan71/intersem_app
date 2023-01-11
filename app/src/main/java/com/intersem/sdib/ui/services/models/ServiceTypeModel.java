package com.intersem.sdib.ui.services.models;

public class ServiceTypeModel {
    int tipo_servicio_id;
    String tipo_servicio;

    public int getTipo_servicio_id() {
        return tipo_servicio_id;
    }

    public void setTipo_servicio_id(int tipo_servicio_id) {
        this.tipo_servicio_id = tipo_servicio_id;
    }

    public String getTipo_servicio() {
        return tipo_servicio;
    }

    public void setTipo_servicio(String tipo_servicio) {
        this.tipo_servicio = tipo_servicio;
    }

    @Override
    public String toString() {
        return getTipo_servicio();
    }
}
