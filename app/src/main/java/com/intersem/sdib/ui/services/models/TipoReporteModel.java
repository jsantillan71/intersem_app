package com.intersem.sdib.ui.services.models;

public class TipoReporteModel {
    private int id;
    private String tipo_reporte;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo_reporte() {
        return tipo_reporte;
    }

    public void setTipo_reporte(String tipo_reporte) {
        this.tipo_reporte = tipo_reporte;
    }

    @Override
    public String toString() {
        return tipo_reporte;
    }
}
