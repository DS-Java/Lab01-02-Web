/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicaDelNegocio.entidades;

/**
 *
 * @author DGSP1
 */
public class Asignacion {
    
    private String codigo;
    private String cedula;

    public Asignacion(String codigo, String cedula) {
        this.codigo = codigo;
        this.cedula = cedula;
    }

    public Asignacion() {
        codigo = new String();
        cedula = new String();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    @Override
    public String toString() {
        return "Asignacion{" + "codigo=" + codigo + ", cedula=" + cedula + '}';
    }
   
}
