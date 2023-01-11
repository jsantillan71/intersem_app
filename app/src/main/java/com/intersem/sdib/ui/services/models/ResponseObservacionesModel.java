package com.intersem.sdib.ui.services.models;

import java.util.ArrayList;

public class ResponseObservacionesModel {

    private int response_flag;
    private String trace;
    private ArrayList<Response> response;


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


    public ArrayList<Response> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Response> response) {
        this.response = response;
    }

    public static class Response{
        private Integer id;
        private String observacion;
        private Integer id_tipo_observacion;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getObservacion() {
            return observacion;
        }

        public void setObservacion(String observacion) {
            this.observacion = observacion;
        }

        public Integer getId_tipo_observacion() {
            return id_tipo_observacion;
        }

        public void setId_tipo_observacion(Integer id_tipo_observacion) {
            this.id_tipo_observacion = id_tipo_observacion;
        }

        @Override
        public String toString() {
            return observacion;
        }
    }
}
