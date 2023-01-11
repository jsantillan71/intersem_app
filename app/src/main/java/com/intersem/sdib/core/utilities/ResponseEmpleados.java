package com.intersem.sdib.core.utilities;

import java.util.ArrayList;

public class ResponseEmpleados {
    ArrayList<Empleado> response;
    int response_flag;

    public ArrayList<Empleado> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Empleado> response) {
        this.response = response;
    }

    public int getResponse_flag() {
        return response_flag;
    }

    public void setResponse_flag(int response_flag) {
        this.response_flag = response_flag;
    }
}
