/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos.curso;

import accesoDatos.ServicioCurso;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logicaDelNegocio.entidades.Curso;

/**
 *
 * @author DGSP1
 */
@WebServlet(name = "modelos.curso.find", urlPatterns = {"/modelos/curso/find"})
public class CursoFindModel extends HttpServlet {

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
        if (request.getServletPath().equals("/modelos/curso/find")) {
            this.find(request, response);
        }
    }

    protected void find(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        ServicioCurso miSC = new ServicioCurso();
        Curso model = new Curso();
        updateModelCod(model, request); 
        ArrayList modelConsultar = new ArrayList();
        try { 
           modelConsultar = miSC.buscar_curso_nombre(model.getCodigo());
        } catch (Exception ex) {   
        }
        request.setAttribute("model", modelConsultar);
        request.getRequestDispatcher("/modelos/curso/preparaCreate").forward(request, response);
    }

    void updateModelCod(Curso model, HttpServletRequest request) {
        model.setCodigo(request.getParameter("parametroDeBusqueda"));
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
