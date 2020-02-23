<%-- 
    Document   : View
    Created on : 21/02/2020, 11:30:40 AM
    Author     : DGSP1
--%>

<%@page import="logicaDelNegocio.entidades.Carrera"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mantenimiento de Cursos</title>
        <base href="http://localhost:8080/Lab01-02/" >

        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/bootstrap-grid.css">
        <link rel="stylesheet" href="css/themify-icons.css">
        <script src="js/jquery.js"></script> 
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/ajax.js"></script>  
        <script src="js/ajax.js"></script>
    </head>
    <body>
        <% List<Carrera> model = (List<Carrera>) request.getAttribute("model");%>
        <h1>Mantenimiento de Cursos</h1>
        <div class="card" style="width: 75%;">  
            <h3>Registro de Curso</h3>

            <form method="POST" name="formulario" action="Controlador/curso/create">
                <table border=0 cellpadding=3 cellspacing=4 >
                    <tr>
                        <td colspan=3 bgcolor="#ffe6e6"><b>Información de Curso</b></td>
                    </tr>
                    <tr> 
                        <td id="codC">Codigo de Curso: </td>
                        <td >
                            <input id="codigoCurso" type="text" name="codigoCurso" size=15 maxlength=40 value="">
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Carrera a la que pertenece</td>
                        <!--td>
                            <select name="IdCarrera" id="IdCarrera">    
                                <!% for (Carrera c : model) {%>
                                  <option name="idP" value="<!%=c.getCodCarrera() %>"><!%=c.getNombre()%></option>  
                                <!%}%>
                            </select> 
                        </td-->
                        <td><input id="IdCarrera" type="text" name="IdCarrera" size=15 maxlength=40 value=""></td>
                        <td></td>
                    </tr>		
                    <tr>
                        <td id="nom">Nombre: </td>
                        <td >
                            <input id="nombre" type="text" name="nombre" size=15 maxlength=40 value="">
                        </td>
                        <td></td>                        
                    </tr>			 
                    <tr>
                        <td >Creditos: </td>
                        <td><input type="number" id="creditos" name="creditos" size="2" maxlength="10"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td >Año: </td>
                        <td><input type="number" id="anio" name="anio" size="2" maxlength="10"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td >Ciclo: </td>
                        <td><input type="text" id="ciclo" name="ciclo" size="2" maxlength="10"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td >Horas Semanales: </td>
                        <td><input type="number" id="hSemanales" name="hSemanales" size="2" maxlength="10"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td height="55" colspan="3" align="center">
                            <input type="submit" name="Submit" value="Agregar"> 
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>
