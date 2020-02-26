/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.usuario;

import accesoDatos.GlobalException;
import accesoDatos.NoDataException;
import accesoDatos.ServicioUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logicaDelNegocio.entidades.Usuario;

/**
 *
 * @author DGSP1
 */
@WebServlet(name = "Controlador.curso.login", urlPatterns = {"/Controlador/curso/login", "/Controlador/curso/logout"})
public class UsuarioController extends HttpServlet {

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
        if (request.getServletPath().equals("/Controlador/curso/login")) {
            this.login(request, response);
        }
        if (request.getServletPath().equals("/Controlador/curso/logout")) {
            this.logout(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, GlobalException, NoDataException {
        if (this.verificar(request)) {
            Map<String, String> errors = this.validar(request);
            if (errors.isEmpty()) {
                Usuario model = new Usuario();
                updateModel(model, request);
                request.setAttribute("model", model);
                Usuario logged = new Usuario();
                ServicioUsuario miSU = new ServicioUsuario();
                Map<String, Usuario> usuarios = miSU.listar_usuarios();
                try {
                    logged = miSU.login(model);
                    if (logged != null) {
                        request.getSession(true).setAttribute("logged", logged);
                        request.getRequestDispatcher("/presentacion/inicio/View.jsp").forward(request, response);//CAMBIAR AQUI 
                    }else{ 
                        request.getRequestDispatcher("/index.html").forward(request, response);
                    }
                } catch (Exception ex) {
                    request.getRequestDispatcher("/index.html").forward(request, response);
                }
            } else {
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/index.html").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("/index.html").forward(request, response);
        }
    }

    protected void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        session.removeAttribute("logged");
        session.invalidate();
        request.getRequestDispatcher("/index.html").forward(request, response);
    }

    boolean verificar(HttpServletRequest request) {
        if (request.getParameter("usuario") == null) {
            return false;
        }
        if (request.getParameter("clave") == null) {
            return false;
        }
        return true;
    }

    Map<String, String> validar(HttpServletRequest request) {
        Map<String, String> errores = new HashMap<>();
        if (request.getParameter("usuario").isEmpty()) {
            errores.put("usuario", "Usuario requerido");
        }

        if (request.getParameter("clave").isEmpty()) {
            errores.put("clave", "Clave requerida");
        }
        return errores;
    }

    void updateModel(Usuario model, HttpServletRequest request) {
        model.setUsuario(request.getParameter("usuario"));
        model.setClave(request.getParameter("clave"));
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
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoDataException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoDataException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
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
