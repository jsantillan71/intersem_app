package com.intersem.sdib.core.utilities;

import com.intersem.sdib.ui.agenda.models.NotaModel;
import com.intersem.sdib.ui.services.models.ServiceRequest;

import java.util.ArrayList;

public class ResponseAgendaServicios {
    int response_flag;
    Response response;

    public class Response {
        ArrayList<ServiceRequest> servicios_asignados;
        ArrayList<NotaModel> notas;

        public ArrayList<ServiceRequest> getServicios_asignados() {
            return servicios_asignados;
        }

        public void setServicios_asignados(ArrayList<ServiceRequest> servicios_asignados) {
            this.servicios_asignados = servicios_asignados;
        }

        public ArrayList<NotaModel> getNotas() {
            return notas;
        }

        public void setNotas(ArrayList<NotaModel> notas) {
            this.notas = notas;
        }
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public int getResponse_flag() {
        return response_flag;
    }

    public void setResponse_flag(int response_flag) {
        this.response_flag = response_flag;
    }

}
