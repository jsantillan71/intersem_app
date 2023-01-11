package com.intersem.sdib.ui.services.models;

import java.util.ArrayList;

public class PendantServiceRequest {
    private ArrayList<ServiceRequest> servicios = new ArrayList<>();

    public ArrayList<ServiceRequest> getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList<ServiceRequest> servicios) {
        this.servicios = servicios;
    }
}
