package com.intersem.sdib.ui.archivos_auxiliares.models;

import java.util.ArrayList;

public class ResponseArchivosAuxiliares {
    private int response_flag;
    private String trace;
    private ArrayList<ArchivoAuxiliar> response;

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

    public ArrayList<ArchivoAuxiliar> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<ArchivoAuxiliar> response) {
        this.response = response;
    }

    public class ArchivoAuxiliar {
        private int id;
        private String nombre;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    }
}
