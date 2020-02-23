package modelos;


import java.util.*;
import logicaDelNegocio.entidades.Curso;


public class CursosModel extends Observable{
    
    Curso filter;
    CursoTableModel cursos;
    HashMap<String,String> errores;
    String mensaje;
    
    public CursosModel(){
    }

    public void init(){
        
        filter = new Curso();
        List<Curso> filas = new ArrayList<>();
        this.setCurso(filas);
        clearErrors();
    }
    
    public void setCurso(List<Curso> cursos){
        
        int[] cols={CursoTableModel.CODIGO_CURSO, CursoTableModel.CODIGO_CARRERA, CursoTableModel.NO_CICLO, CursoTableModel.NOMBRE, CursoTableModel.CREDITOS, CursoTableModel.HORAS_SEMANALES};
        this.cursos = new CursoTableModel(cols, cursos);  
        setChanged();
        notifyObservers();        
    }
    
    public Curso getFilter(){
        return filter;
    }
    
    public void setFilter(Curso filter){
        this.filter = filter;
    }
    
     public CursoTableModel getCursos(){
        return cursos;
    }

    @Override
    public void addObserver(Observer o){
        super.addObserver(o);
        setChanged();
        notifyObservers();
    }

    public String getMensaje(){
        return mensaje;
    }

    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
    }

    public HashMap<String, String> getErrores(){
        return errores;
    }

    public void setErrores(HashMap<String, String> errores){
        this.errores = errores;
    }
    
    public void clearErrors(){
        setErrores(new HashMap<>());
        setMensaje(""); 
    }
}