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
import java.util.HashMap;
import java.util.Map;
import javafx.scene.shape.Arc;
import logicaDelNegocio.entidades.Carrera;
import logicaDelNegocio.entidades.Curso;
import logicaDelNegocio.entidades.Profesor;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author DGSP1
 */
public class ServicioCurso extends Servicio {

    //Cadenas para invocar los SP y Cursores
    private static final String INSERTAR_CURSO = "{call INSERTCURSO(?,?,?,?,?,?,?,?)}";
    private static final String MODIFICAR_CURSO = "{call UPDATECURSO(?,?,?,?,?,?)}";
    private static final String BUSCAR_CURSO = "{?=call FINDCURSO(?)}";
    private static final String BUSCAR_CURSO_NOMBRE = "{?=call FINDCURSOPORNOMBRE(?)}";
    private static final String LISTAR_CURSO = "{?=call LISTCURSO()}";
    private static final String LISTAR_CARRERA = "{?=call LISTCARRERA()}";
    private static final String LISTAR_PROFESOR = "{?=call LISTPROFESOR()}";
    private static final String ELIMINAR_CURSO = "{call DELETECURSO(?)}";

    private static ArrayList<Curso> coleccionCursos = new ArrayList<>();

    public ServicioCurso() {
    }

    /*Insertar Curso*/
    public void insertar_curso(Curso curso, String cedula) throws GlobalException, NoDataException, SQLException {
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
            pstmt.setString(8, cedula);
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

    /*Buscar cursos por Codigo*/
    public ArrayList buscar_curso(String id) throws GlobalException, NoDataException {
        coleccionCursos.clear();

        if (id.isEmpty()) {
            coleccionCursos = (ArrayList) listar_curso();
        } else {
            try {
                conectar();
            } catch (ClassNotFoundException e) {
                throw new GlobalException("No se ha localizado el driver");
            } catch (SQLException e) {
                throw new NoDataException("La base de datos no se encuentra disponible");
            }
            ResultSet rs = null;

            Curso elCurso = null;
            Profesor elProfeAsignado = null;
            CallableStatement pstmt = null;
            try {
                pstmt = conexion.prepareCall(BUSCAR_CURSO);
                pstmt.registerOutParameter(1, OracleTypes.CURSOR);
                pstmt.setString(2, id);//Se pasa id de curso que se busca
                pstmt.execute();
                rs = (ResultSet) pstmt.getObject(1);//Se saca el objeto sacado
                while (rs.next()) {
                    elProfeAsignado = new Profesor(
                            rs.getString("CEDULA"),
                            rs.getString("NOMBREPROFESOR"),
                            rs.getString("TELEFONO"),
                            rs.getString("EMAIL"));
                    elCurso = new Curso(
                            rs.getString("CODIGO"),
                            rs.getString("CODCARRERA"),
                            rs.getString("NOMBRE"),
                            rs.getString("CREDITOS"),
                            rs.getString("ANIO"),
                            rs.getString("CICLO"),
                            rs.getString("HORA_SEMANALES"),
                            elProfeAsignado);
                    coleccionCursos.add(elCurso);
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
            if (coleccionCursos == null || coleccionCursos.size() == 0) {
                throw new NoDataException("No hay datos");
            }
        }
        return coleccionCursos;
    }

    /*Buscar cursos por Nombre*/
    public ArrayList buscar_curso_nombre(String nombre) throws GlobalException, NoDataException {
        //coleccionCursos.clear();
        coleccionCursos = new ArrayList<>();

        if (nombre.isEmpty()) {
            coleccionCursos = (ArrayList) listar_curso();
        } else {
            try {
                conectar();
            } catch (ClassNotFoundException e) {
                throw new GlobalException("No se ha localizado el driver");
            } catch (SQLException e) {
                throw new NoDataException("La base de datos no se encuentra disponible");
            }
            ResultSet rs = null;

            Curso elCurso = null;
            Profesor elProfeAsignado = null;
            CallableStatement pstmt = null;
            try {
                pstmt = conexion.prepareCall(BUSCAR_CURSO_NOMBRE);
                pstmt.registerOutParameter(1, OracleTypes.CURSOR);
                pstmt.setString(2, nombre);//Se pasa el nombre de curso que se busca
                pstmt.execute();
                rs = (ResultSet) pstmt.getObject(1);//Se saca el objeto sacado
                while (rs.next()) {
                    elProfeAsignado = new Profesor(
                            rs.getString("CEDULA"),
                            rs.getString("NOMBREPROFESOR"),
                            rs.getString("TELEFONO"),
                            rs.getString("EMAIL"));  
                    elCurso = new Curso(
                            rs.getString("CODIGO"),
                            rs.getString("CODCARRERA"),
                            rs.getString("NOMBRE"),
                            rs.getString("CREDITOS"),
                            rs.getString("ANIO"),
                            rs.getString("CICLO"),
                            rs.getString("HORA_SEMANALES"),
                            elProfeAsignado);
                    coleccionCursos.add(elCurso);
                    System.out.println("Codigo: " + elCurso.getCodigo());
                    System.out.println("NOMBRE: " + elCurso.getNombre());
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
            if (coleccionCursos == null || coleccionCursos.size() == 0) {
                throw new NoDataException("No hay datos");
            }
        }
        System.out.println("Cantidad de cursos encontrador: " + coleccionCursos.size());
        return coleccionCursos;
    }

    /*Listar Cursos*/
    //public Collection listar_curso() throws GlobalException, NoDataException {
    public Collection listar_curso() throws GlobalException, NoDataException {//ArrayList<Curso>
        coleccionCursos = new ArrayList<>();
        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ResultSet rs = null;
        //ArrayList coleccion = new ArrayList();
        Curso elCurso = null;
        Profesor elProfeAsignado = null;
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(LISTAR_CURSO);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            System.out.println("Intento");
            while (rs.next()) {
                elProfeAsignado = new Profesor(
                        rs.getString("CEDULA"),
                        rs.getString("NOMBREPROFESOR"),
                        rs.getString("TELEFONO"),
                        rs.getString("EMAIL"));
                elCurso = new Curso(
                        rs.getString("CODIGO"),
                        rs.getString("CODCARRERA"),
                        rs.getString("NOMBRE"),
                        rs.getString("CREDITOS"),
                        rs.getString("ANIO"),
                        rs.getString("CICLO"),
                        rs.getString("HORA_SEMANALES"),
                        elProfeAsignado);
                coleccionCursos.add(elCurso);
                System.out.println("Cod: " + elCurso.getCodigo());
                System.out.println("Nombre: " + elCurso.getNombre());
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
        if (coleccionCursos == null || coleccionCursos.size() == 0) {
            //coleccionCursos = new ArrayList();
            //throw new NoDataException("No hay datos");
        }
        //return coleccion;
        return coleccionCursos;
    }

    /*Listar Carreras*/
    public Collection listar_carrera() throws GlobalException, NoDataException {//ArrayList<Curso>
        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Carrera laCarrera = null;
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(LISTAR_CARRERA);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                laCarrera = new Carrera(
                        rs.getString("CODCARRERA"),
                        rs.getString("NOMBRE"),
                        rs.getString("TITULO"));
                coleccion.add(laCarrera);
                System.out.println("Cod Carrera: " + laCarrera.getCodCarrera());
                System.out.println("Nombre: " + laCarrera.getNombre());
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

    /*Listar Profesores*/
    public Collection listar_profesor() throws GlobalException, NoDataException {//ArrayList<Curso>
        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Profesor elProfesor = null;
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(LISTAR_PROFESOR);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                elProfesor = new Profesor(
                        rs.getString("CEDULA"),
                        rs.getString("NOMBREPROFESOR"),
                        rs.getString("TELEFONO"),
                        rs.getString("EMAIL")
                );
                coleccion.add(elProfesor);
                System.out.println("Ced Profe: " + elProfesor.getCedula());
                System.out.println("Nombre: " + elProfesor.getNombre());
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
