package com.intersem.sdib.ui.services.models;

public class ObservacionesRequest {
    String observacion;

    int id_tipo_observacion;
    int id;

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getId_tipo_observacion() {
        return id_tipo_observacion;
    }

    public void setId_tipo_observacion(int id_tipo_observacion) {
        this.id_tipo_observacion = id_tipo_observacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
