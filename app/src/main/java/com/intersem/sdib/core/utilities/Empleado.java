package com.intersem.sdib.core.utilities;

public class Empleado {

    private String nombre;
    private int empleado_id;
    private int user_id;
    private int user;
    private String foto_gafete;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFotoGafete() {
        return foto_gafete;
    }

    public void setFotoGafete(String foto_gafete) {
        this.foto_gafete = foto_gafete;
    }

    public int getEmpleado_id() {
        return empleado_id;
    }

    public void setEmpleado_id(int empleado_id) {
        this.empleado_id = empleado_id;
    }

    public int getUser_id() { return user_id; }

    public void getUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return  nombre;
    }

}
