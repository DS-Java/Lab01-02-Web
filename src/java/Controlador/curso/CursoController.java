/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.curso;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logicaDelNegocio.entidades.Curso;
import accesoDatos.ServicioCurso;

/**
 *
 * @author DGSP1
 */
@WebServlet(name = "Controlador.curso.create", urlPatterns = {"/Controlador/curso/create"})
public class CursoController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getServletPath().equals("/Controlador/curso/create")) {
            this.createC(request, response); 
        }
    }

    protected void createC(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        ServicioCurso miSC = new ServicioCurso();
        Curso model = new Curso();
        updateModel(model, request);
        String cedula = updateCedula(request);
        try {
            miSC.insertar_curso(model,cedula);
        } catch (Exception ex) { 
        }
        request.getRequestDispatcher("/modelos/curso/list").forward(request, response);
    }

    void updateModel(Curso model, HttpServletRequest request) {
        model.setCodigo(request.getParameter("codigoCurso"));
        model.setCodCarrera(request.getParameter("IdCarrera"));
        model.setNombre(request.getParameter("nombre"));
        model.setCreditos(request.getParameter("creditos"));
        model.setAnio(request.getParameter("anio"));
        model.setCiclo(request.getParameter("ciclo"));
        model.setHora_semanales(request.getParameter("hSemanales"));
    }

    String updateCedula(HttpServletRequest request) {
        String ced = request.getParameter("cedProfesor");
        return ced;
    }
   
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
