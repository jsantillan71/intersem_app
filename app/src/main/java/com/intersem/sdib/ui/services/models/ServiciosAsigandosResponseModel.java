package com.intersem.sdib.ui.services.models;

import java.util.ArrayList;

public class ServiciosAsigandosResponseModel {
    ArrayList<ServiceRequest> response;
    int response_flag;

    public ArrayList<ServiceRequest> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<ServiceRequest> response) {
        this.response = response;
    }

    public int getResponse_flag() {
        return response_flag;
    }

    public void setResponse_flag(int response_flag) {
        this.response_flag = response_flag;
    }
}
