package com.intersem.sdib.ui.archivos_auxiliares.models;

public class ArchivoAuxialiarModel {
    int id;
    String ruta_archivo;
    String nombre_archvio;
    String tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRuta_archivo() {
        return ruta_archivo;
    }

    public void setRuta_archivo(String ruta_archivo) {
        this.ruta_archivo = ruta_archivo;
    }

    public String getNombre_archvio() {
        return nombre_archvio;
    }

    public void setNombre_archvio(String nombre_archvio) {
        this.nombre_archvio = nombre_archvio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
