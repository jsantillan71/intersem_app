package com.intersem.sdib.ui.services.models;

import java.util.ArrayList;
import java.util.Calendar;

public class ServiceRequest {
    private int imprimir;
    private String numero_reporte;
    private int tipo_servicio;
    private int id_servidor;
    private int id;
    private String path_pdf_1;
    private String path_pdf_2;
    private String path_pdf_3;
    private String path_pdf_inverso;
    private int printed;
    private String no_reporte;
    private String equipo;
    private String modelo;
    private String marca;
    private String cabms;
    private String serie;
    private int id_tipo_servicio;
    private int id_estatus;
    private int subido;
    private int terminado;
    private int reporte_subir;
    private int bitacora;
    private String observaciones_finales;
    private ArrayList<Fotografia> fotografias = new ArrayList<>();
    private ArrayList<Fotografia> fotografias_antes =  new ArrayList<>();
    private ArrayList<Fotografia> fotografias_durante =  new ArrayList<>();
    private ArrayList<Fotografia> fotografias_despues = new ArrayList<>();
    private ArrayList<Fotografia> fotografias_etiqueta = new ArrayList<>();
    private ArrayList<Fotografia> fotografias_cambs = new ArrayList<>();
    private String fecha_servicio;
    private String fecha_asignada;
    private Calendar fecha_servicio_calendario;
    private int usuario_creacion;
    private int equipo_id;
    private String razon_social;
    private String codigo_postal;
    private String calle;
    private String numero;
    private String numero_int;
    private String colonia;
    private String nombre;
    private String municipio;
    private String estado;
    private String clientes_datos;
    private String gasto;
    private String empleado_gafete;


    public String getEmpleado_gafete() {
        return empleado_gafete;
    }

    public void setEmpleado_gafete(String empleado_gafete) {
        this.empleado_gafete = empleado_gafete;
    }

    public String getPath_pdf_inverso() {
        return path_pdf_inverso;
    }

    public void setPath_pdf_inverso(String path_pdf_inverso) {
        this.path_pdf_inverso = path_pdf_inverso;
    }

    public String getFecha_asignada() {
        return fecha_asignada;
    }

    public void setFecha_asignada(String fecha_asignada) {
        this.fecha_asignada = fecha_asignada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGasto() {
        return gasto;
    }

    public void setGasto(String gasto) {
        this.gasto = gasto;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumero_int() {
        return numero_int;
    }

    public void setNumero_int(String numero_int) {
        this.numero_int = numero_int;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getClientes_datos() {
        return clientes_datos;
    }

    public void setClientes_datos(String clientes_datos) {
        this.clientes_datos = clientes_datos;
    }

    public int getEquipo_id() {
        return equipo_id;
    }

    public void setEquipo_id(int equipo_id) {
        this.equipo_id = equipo_id;
    }

    public int getUsuario_creacion() {
        return usuario_creacion;
    }

    public void setUsuario_creacion(int usuario_creacion) {
        this.usuario_creacion = usuario_creacion;
    }

    public String getFecha_servicio() {
        return fecha_servicio;
    }

    public void setFecha_servicio(String fecha_servicio) {
        this.fecha_servicio = fecha_servicio;
    }

    public Calendar getFecha_servicio_calendario() {
        return fecha_servicio_calendario;
    }

    public void setFecha_servicio_calendario(Calendar fecha_servicio_calendario) {
        this.fecha_servicio_calendario = fecha_servicio_calendario;
    }

    public int getBitacora() {
        return bitacora;
    }

    public void setBitacora(int bitacora) {
        this.bitacora = bitacora;
    }

    public int getReporte_subir() {
        return reporte_subir;
    }

    public void setReporte_subir(int reporte_subir) {
        this.reporte_subir = reporte_subir;
    }

    public int getTerminado() {
        return terminado;
    }

    public void setTerminado(int terminado) {
        this.terminado = terminado;
    }

    public int getSubido() {
        return subido;
    }

    public void setSubido(int subido) {
        this.subido = subido;
    }

    public String getCabms() {
        return cabms;
    }

    public void setCabms(String cabms) {
        this.cabms = cabms;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNo_reporte() {
        return no_reporte;
    }

    public void setNo_reporte(String no_reporte) {
        this.no_reporte = no_reporte;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getId_tipo_servicio() {
        return id_tipo_servicio;
    }

    public void setId_tipo_servicio(int id_tipo_servicio) {
        this.id_tipo_servicio = id_tipo_servicio;
    }

    public ArrayList<Fotografia> getFotografias() {
        return fotografias;
    }

    public void setFotografias(ArrayList<Fotografia> fotografias) {
        this.fotografias = fotografias;
    }

    public int getId_estatus() {
        return id_estatus;
    }

    public void setId_estatus(int id_estatus) {
        this.id_estatus = id_estatus;
    }

    private ArrayList<Fotografia> fotografias_id = new ArrayList<>();
    private ArrayList<Fotografia> fotografias_bitacora = new ArrayList<>();
    private ArrayList<Fotografia> fotografias_refaccion = new ArrayList<>();

    private ArrayList<Fotografia> fotografias_gafete = new ArrayList<>();

    public ArrayList<Fotografia> getFotografias_gafete() {
        return fotografias_gafete;
    }

    public void setFotografias_gafete(ArrayList<Fotografia> fotografias_gafete) {
        this.fotografias_gafete = fotografias_gafete;
    }

    public ArrayList<Fotografia> getFotografias_bitacora() {
        return fotografias_bitacora;
    }

    public void setFotografias_bitacora(ArrayList<Fotografia> fotografias_bitacora) {
        this.fotografias_bitacora = fotografias_bitacora;
    }

    public ArrayList<Fotografia> getFotografias_refaccion() {
        return fotografias_refaccion;
    }

    public void setFotografias_refaccion(ArrayList<Fotografia> fotografias_refaccion) {
        this.fotografias_refaccion = fotografias_refaccion;
    }

    public String getObservaciones_finales() {
        return observaciones_finales;
    }

    public void setObservaciones_finales(String observaciones_finales) {
        this.observaciones_finales = observaciones_finales;
    }

    public int getPrinted() {
        return printed;
    }
    public void setPrinted(int printed) {
        this.printed = printed;
    }

    public int getImprimir() {  return imprimir;   }

    public void setImprimir(int imprimir) { this.imprimir = imprimir;  }

    public int getId_servidor() {
        return id_servidor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_servidor(int id_servidor) {
        this.id_servidor = id_servidor;
    }

    public String getPath_pdf_1() {
        return path_pdf_1;
    }

    public void setPath_pdf_1(String path_pdf_1) {
        this.path_pdf_1 = path_pdf_1;
    }

    public String getPath_pdf_2() {
        return path_pdf_2;
    }

    public void setPath_pdf_2(String path_pdf_2) {
        this.path_pdf_2 = path_pdf_2;
    }

    public String getPath_pdf_3() {
        return path_pdf_3;
    }

    public void setPath_pdf_3(String path_pdf_3) {
        this.path_pdf_3 = path_pdf_3;
    }

    public int getTipo_servicio() {
        return tipo_servicio;
    }

    public void setTipo_servicio(int tipo_servicio) {
        this.tipo_servicio = tipo_servicio;
    }

    public String getNumero_reporte() {
        return numero_reporte;
    }

    public void setNumero_reporte(String numero_reporte) {
        this.numero_reporte = numero_reporte;
    }


    public ArrayList<Fotografia> getFotografias_antes() {
        return fotografias_antes;
    }

    public void setFotografias_antes(ArrayList<Fotografia> fotografias_antes) {
        this.fotografias_antes = fotografias_antes;
    }

    public ArrayList<Fotografia> getFotografias_durante() {
        return fotografias_durante;
    }

    public void setFotografias_durante(ArrayList<Fotografia> fotografias_durante) {
        this.fotografias_durante = fotografias_durante;
    }

    public ArrayList<Fotografia> getFotografias_despues() {
        return fotografias_despues;
    }

    public void setFotografias_despues(ArrayList<Fotografia> fotografias_despues) {
        this.fotografias_despues = fotografias_despues;
    }

    public ArrayList<Fotografia> getFotografias_etiqueta() {
        return fotografias_etiqueta;
    }

    public void setFotografias_etiqueta(ArrayList<Fotografia> fotografias_etiqueta) {
        this.fotografias_etiqueta = fotografias_etiqueta;
    }

    public ArrayList<Fotografia> getFotografias_cambs() {
        return fotografias_cambs;
    }

    public void setFotografias_cambs(ArrayList<Fotografia> fotografias_cambs) {
        this.fotografias_cambs = fotografias_cambs;
    }

    public ArrayList<Fotografia> getFotografias_id() {
        return fotografias_id;
    }

    public void setFotografias_id(ArrayList<Fotografia> fotografias_id) {
        this.fotografias_id = fotografias_id;
    }

    public static class Fotografia{
        String nombre_fotografia;
        String ruta_fotografia;
        String ruta_servidor;
        int tipo_fotografia;
        int servicio_id;
        String pdf_parcial;
        String pdf_completo;
        int orden;
        int id;
        int check;
        String ruta_calidad;

        private float rotacion;

        public float getRotacion() {
            return rotacion;
        }

        public void setRotacion(float rotacion) {
            this.rotacion = rotacion;
        }

        public String getRuta_calidad() {
            return ruta_calidad;
        }

        public void setRuta_calidad(String ruta_calidad) {
            this.ruta_calidad = ruta_calidad;
        }

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrden() {
            return orden;
        }

        public void setOrden(int orden) {
            this.orden = orden;
        }

        public int getServicio_detalle_id() {
            return servicio_detalle_id;
        }

        public void setServicio_detalle_id(int servicio_detalle_id) {
            this.servicio_detalle_id = servicio_detalle_id;
        }

        int servicio_detalle_id;

        public String getPdf_parcial() {
            return pdf_parcial;
        }

        public void setPdf_parcial(String pdf_parcial) {
            this.pdf_parcial = pdf_parcial;
        }

        public String getPdf_completo() {
            return pdf_completo;
        }

        public void setPdf_completo(String pdf_completo) {
            this.pdf_completo = pdf_completo;
        }

        public int getServicio_id() {
            return servicio_id;
        }

        public void setServicio_id(int servicio_id) {
            this.servicio_id = servicio_id;
        }

        public String getNombre_fotografia() {
            return nombre_fotografia;
        }

        public void setNombre_fotografia(String nombre_fotografia) {
            this.nombre_fotografia = nombre_fotografia;
        }

        public String getRuta_fotografia() {
            return ruta_fotografia;
        }

        public void setRuta_fotografia(String ruta_fotografia) {
            this.ruta_fotografia = ruta_fotografia;
        }

        public String getRuta_servidor() {
            return ruta_servidor;
        }

        public void setRuta_servidor(String ruta_servidor) {
            this.ruta_servidor = ruta_servidor;
        }

        public int getTipo_fotografia() {
            return tipo_fotografia;
        }

        public void setTipo_fotografia(int tipo_fotografia) {
            this.tipo_fotografia = tipo_fotografia;
        }

    }
}
