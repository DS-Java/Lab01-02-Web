/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesoDatos;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import logicaDelNegocio.entidades.Usuario;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author DGSP1
 */
public class ServicioUsuario extends Servicio {
        private static final String LISTAR_USUARIO = "{?=call LISTUSUARIOS()}";
        
        private Map<String, Usuario> coleccionUsuarios = new HashMap<>();
        
        /*Listar Usuarios*/
    public Map<String, Usuario> listar_usuarios() throws GlobalException, NoDataException {//ArrayList<Curso>
        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ResultSet rs = null;
        //ArrayList coleccion = new ArrayList();
        Usuario miUsuario = null;
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(LISTAR_USUARIO);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                miUsuario = new Usuario(
                        rs.getString("USUARIO"),
                        rs.getString("CLAVE"),
                        rs.getString("ROL")
                );
                coleccionUsuarios.put(miUsuario.getUsuario(),miUsuario);
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
        if (coleccionUsuarios == null || coleccionUsuarios.size() == 0) {
            throw new NoDataException("No hay datos");
        }
        return coleccionUsuarios;
    }
    
    public Usuario login(Usuario id) throws Exception {
        //listar_usuarios();
        Usuario result = coleccionUsuarios.get(id.getUsuario());
        Usuario logueado = new Usuario();
        if (result != null) {
            //if(coleccionUsuarios.get(id.getUsuario()).getClave().equals(id.getClave())){
            if(result.getClave().equals(id.getClave())){
                logueado = result; 
            }else{
                logueado = null;
            }
        }else{
            logueado = null;
            throw new Exception("Usuario no existe");
        }
        return logueado;
    }
}
