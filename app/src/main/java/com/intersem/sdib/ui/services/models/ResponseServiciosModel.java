package com.intersem.sdib.ui.services.models;

import java.util.ArrayList;

public class ResponseServiciosModel {
    private int response_flag;
    private String trace;
    private Response response;
    ArrayList<Response> datos;

    public ArrayList<Response> getDatos() {
        return datos;
    }

    public void setDatos(ArrayList<Response> datos) {
        this.datos = datos;
    }


    public int getResponse_flag() {
        return response_flag;
    }

    public void setResponse_flag(int response_flag) {
        this.response_flag = response_flag;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public Response getResponse() {  return response; }

    public void setResponse(Response response) { this.response = response; }

    public static class Response{
        private Integer id;
        private String no_reporte;
        private Integer id_tipo_servicio;
        private String fecha;
        private Integer id_estatus;
        private Integer id_equipo;
        private  String no_serie;

        public String getNo_serie() { return no_serie; }

        public void setNo_serie(String no_serie) { this.no_serie = no_serie; }

        public Integer getId() { return id; }

        public void setId(Integer id) { this.id = id; }

        public String getNo_reporte() { return no_reporte; }

        public void setNo_reporte(String no_reporte) { this.no_reporte = no_reporte; }

        public Integer getId_tipo_servicio() { return id_tipo_servicio; }

        public void setId_tipo_servicio(Integer id_tipo_servicio) {this.id_tipo_servicio = id_tipo_servicio; }

        public String getFecha() { return fecha; }

        public void setFecha(String fecha) { this.fecha = fecha; }

        public Integer getId_estatus() { return id_estatus; }

        public void setId_estatus(Integer id_estatus) { this.id_estatus = id_estatus; }

        public Integer getId_equipo() { return id_equipo;  }

        public void setId_equipo(Integer id_equipo) { this.id_equipo = id_equipo; }
    }
}
