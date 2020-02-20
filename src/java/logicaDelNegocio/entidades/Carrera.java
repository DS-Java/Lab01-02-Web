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
public class Carrera {
    
    private String codCarrera;
    private String nombre;
    private String titulo;

    public Carrera(String codCarrera, String nombre, String titulo) {
        this.codCarrera = codCarrera;
        this.nombre = nombre;
        this.titulo = titulo;
    }

    public Carrera() {
        codCarrera = new String();
        nombre = new String();
        titulo = new String();
    }

    public String getCodCarrera() {
        return codCarrera;
    }

    public void setCodCarrera(String codCarrera) {
        this.codCarrera = codCarrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "Carrera{" + "codCarrera=" + codCarrera + ", nombre=" + nombre + ", titulo=" + titulo + '}';
    }
    
}
