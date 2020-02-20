/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import logicaDelNegocio.entidades.Curso;

/**
 *
 * @author DGSP1
 */
public class ServicioCurso extends Servicio {

    //Cadenas para invocar los SP y Cursores
    private static final String INSERTAR_CURSO = "{call INSERTCURSO(?,?,?,?,?,?)}";
    private static final String MODIFICAR_CURSO = "{call UPDATECURSO(?,?,?,?,?,?)}";
    private static final String BUSCAR_CURSO = "{?=call FINDCURSO(?)}";
    private static final String LISTAR_CURSO = "{?=call LISTCURSO()}";
    private static final String ELIMINAR_CURSO = "{call DELETECURSO(?)}";

    public ServicioCurso() {
    }

    /*Insertar Curso*/
    public void insertar_curso(Curso curso) throws GlobalException, NoDataException, SQLException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        CallableStatement pstmt = null;

        try {
            pstmt = conexion.prepareCall(INSERTAR_CURSO);
            pstmt.setString(1, curso.getCodigo());
            pstmt.setString(2, curso.getCodCarrera());
            pstmt.setString(3, curso.getNombre());
            pstmt.setString(4, curso.getCreditos());
            pstmt.setString(5, curso.getAnio());
            pstmt.setString(6, curso.getCiclo());
            pstmt.setString(7, curso.getHora_semanales());
            boolean resultado = pstmt.execute();
            if (resultado == true) {
                throw new NoDataException("No se realizo la insercion");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Llave duplicada");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }

    /*Modificar Curso*/
    public void modificar_curso(Curso curso) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        PreparedStatement pstmt = null;
        try { 
            //////////SE CARCA ES STATEMENT
            pstmt = conexion.prepareStatement(MODIFICAR_CURSO);
            pstmt.setString(1, curso.getCodigo());
            pstmt.setString(2, curso.getCodCarrera());
            pstmt.setString(3, curso.getNombre());
            pstmt.setString(4, curso.getCreditos());
            pstmt.setString(5, curso.getAnio());  
            pstmt.setString(6, curso.getCiclo());
            pstmt.setString(7, curso.getHora_semanales());
            int resultado = pstmt.executeUpdate();

            if (resultado == 0) {
                throw new NoDataException("No se realizo la actualización");
            } else {
                System.out.println("\nModificación Satisfactoria!");
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }
}
