<%-- 
    Document   : View
    Created on : 21/02/2020, 11:30:40 AM
    Author     : DGSP1
--%>

<%@page import="logicaDelNegocio.entidades.Profesor"%>
<%@page import="logicaDelNegocio.entidades.Curso"%>
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
        <h1>Mantenimiento de Cursos</h1>
        <div style="margin-left: 10px">
            <form method="POST" name="form" action="modelos/curso/find">
                <table>
                    <tr> 
                        <td>
                            <input id="parametroDeBusqueda" type="text" name="parametroDeBusqueda" size=15 maxlength=40 value="">
                        </td>
                        <td>
                            <input type="submit" name="consultar" value="Consultar Curso"> 
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <div class="card" style="width: 75%; margin-left: 10px">  
            <h3>Registro de Curso</h3>  
            <% List<Carrera> model = (List<Carrera>) request.getAttribute("modelCarrera");%>
            <% List<Profesor> modelP = (List<Profesor>) request.getAttribute("modelProfesor");%>
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
                        <td>
                            <select name="IdCarrera" id="IdCarrera">    
                                <% for (Carrera c : model) {%>
                                <option name="idC" value="<%=c.getCodCarrera()%>"><%= c.getNombre()%></option>  
                                <%}%>
                            </select> 
                        </td>
                        <!--td><input id="IdCarrera" type="text" name="IdCarrera" size=15 maxlength=40 value=""></td-->
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
                        <td>Profesor: </td>
                        <td>
                            <select name="cedProfesor" id="cedProfesor">    
                                <% for (Profesor p : modelP) {%>
                                <option name="idP" value="<%= p.getCedula()%>"><%= p.getNombre()%></option>  
                                <%}%>
                            </select>
                        </td>
                        <!--td><input id="cedProfesor" type="text" name="cedProfesor" size=15 maxlength=40 value=""></td-->
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
        <div>
            <h4 style="margin-left: 20px">Lista de Cursos</h4>
            <% List<Curso> model2 = (List<Curso>) request.getAttribute("model");%>
            <table style="margin-left: 20px;">
                <thead><tr><th style="width: 150px">Codigo &nbsp;</th><th style="width: 150px">Cod Carrera &nbsp;</th><th style="width: 150px">Nombre &nbsp;</th><th>Creditos &nbsp;</th><th style="width: 50px">Año &nbsp;</th><th style="width: 70px">Ciclo</th><th style="width: 170px">Horas Semanales &nbsp;</th></tr></thead>
                <tbody> 
                    <% for (Curso c : model2) {%>
                    <tr>
                        <td><%=c.getCodigo()%></td>
                        <td><%=c.getCodCarrera()%></td>
                        <td><%=c.getNombre()%></td>
                        <td><%=c.getCreditos()%></td>
                        <td><%=c.getAnio()%></td>
                        <td><%=c.getCiclo()%></td>
                        <td><%=c.getHora_semanales()%></td>
                    </tr>
                    <% }%> 
                </tbody>
            </table>
        </div>
    </body>
</html>
