package com.intersem.sdib.ui.services.models;

public class ArchivoPath {
    String path_local;
    String path_server;

    public ArchivoPath(String path_local, String path_server) {
        this.path_local = path_local;
        this.path_server = path_server;
    }

    public String getPath_local() {
        return path_local;
    }

    public void setPath_local(String path_local) {
        this.path_local = path_local;
    }

    public String getPath_server() {
        return path_server;
    }

    public void setPath_server(String path_server) {
        this.path_server = path_server;
    }
}
