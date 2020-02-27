/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos.curso;

import accesoDatos.GlobalException;
import accesoDatos.NoDataException;
import accesoDatos.ServicioCurso;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "modelos.curso.list", urlPatterns = {"/modelos/curso/list"})
public class CursoTablaModel extends HttpServlet {

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
            throws ServletException, IOException, GlobalException, NoDataException {
        if (request.getServletPath().equals("/modelos/curso/list")) {
            this.list(request, response);
        }
    }
    
    protected void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, GlobalException, NoDataException {
        ServicioCurso miSC = new ServicioCurso();
        ArrayList<Curso>listaCursos = new ArrayList<>();
        listaCursos = (ArrayList<Curso>) miSC.listar_curso();
        //request.setAttribute("model", miSC.listar_curso());
        request.setAttribute("model", listaCursos);
        request.getRequestDispatcher("/modelos/curso/preparaCreate").forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (GlobalException ex) {
            Logger.getLogger(CursoTablaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoDataException ex) {
            Logger.getLogger(CursoTablaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (GlobalException ex) {
            Logger.getLogger(CursoTablaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoDataException ex) {
            Logger.getLogger(CursoTablaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
