/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesoDatos;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import logicaDelNegocio.entidades.Curso;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author DGSP1
 */
public class ServicioCurso extends Servicio {

    //Cadenas para invocar los SP y Cursores
    private static final String INSERTAR_CURSO = "{call INSERTCURSO(?,?,?,?,?,?,?)}";
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
            pstmt.setInt(7, Integer.parseInt(curso.getHora_semanales()));
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
            pstmt.setInt(7, Integer.parseInt(curso.getHora_semanales()));
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
    
    /*Buscar cursos*/
    public Curso buscar_curso(String id) throws GlobalException, NoDataException {

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Curso elCurso = null;
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(BUSCAR_CURSO);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, id);//Se pasa id de curso que se busca
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);//Se saca el objeto sacado
            while (rs.next()) {
                elCurso = new Curso(
                        rs.getString("CODIGO"),
                        rs.getString("CODCARRERA"),
                        rs.getString("NOMBRE"),
                        rs.getString("CREDITOS"),
                        rs.getString("ANIO"),
                        rs.getString("CICLO"),
                        rs.getString("HORA_SEMANALES"));
                coleccion.add(elCurso);
            }
        } catch (SQLException e) {
            e.printStackTrace();

            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
        if (coleccion == null || coleccion.size() == 0) {
            throw new NoDataException("No hay datos");
        }
        return elCurso;
    } 
   
    /*Listar Cursos*/
    //public Collection listar_curso() throws GlobalException, NoDataException {
    public ArrayList<Curso> listar_curso() throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Curso elCurso = null;
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(LISTAR_CURSO);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                elCurso = new Curso(
                        rs.getString("CODIGO"),
                        rs.getString("CODCARRERA"),
                        rs.getString("NOMBRE"),
                        rs.getString("CREDITOS"),
                        rs.getString("ANIO"),
                        rs.getString("CICLO"),
                        rs.getString("HORA_SEMANALES"));
                coleccion.add(elCurso);
            }
        } catch (SQLException e) {
            e.printStackTrace();

            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
        if (coleccion == null || coleccion.size() == 0) {
            throw new NoDataException("No hay datos");
        }
        return coleccion;
    }
    
    
    /*Eliminar Curso*/
    public void eliminar_curso(String id) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conexion.prepareStatement(ELIMINAR_CURSO);
            pstmt.setString(1, id);

            int resultado = pstmt.executeUpdate();

            if (resultado == 0) {
                throw new NoDataException("No se realizo el borrado");
            } else {
                System.out.println("\nEliminación Satisfactoria!");
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
