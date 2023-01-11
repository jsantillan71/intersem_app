package com.intersem.sdib.ui.agenda.models;

import java.util.Calendar;

public class NotaModel {
    int id;
    String nota;
    String fecha;
    String cliente;
    Calendar fecha_nota_calendario;

    public Calendar getFecha_nota_calendario() {
        return fecha_nota_calendario;
    }

    public void setFecha_nota_calendario(Calendar fecha_nota_calendario) {
        this.fecha_nota_calendario = fecha_nota_calendario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
}
