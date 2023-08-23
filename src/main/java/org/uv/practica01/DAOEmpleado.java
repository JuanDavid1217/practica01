/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.practica01;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author juan
 */
public class DAOEmpleado implements IDAOGeneral<Empleado, Long>{
    private PreparedStatement query=null; 
    @Override
    public Empleado create(Empleado p) {
        ConexionDB cx=ConexionDB.getInstance();
        TransaccionDB tdb=new TransaccionDB<Empleado>(p){
            @Override
            public boolean execute(Connection con){
                try{
                    query=con.prepareStatement("insert into empleados(claveempleado, nombre, dirección)"+
                    " values (?, ?, ?);");
                    query.setLong(1, p.getClave());
                    query.setString(2, p.getNombre());
                    query.setString(3, p.getDireccion());
                    query.execute();
                    return true;
                }catch(SQLException ex){
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        boolean res=cx.execute(tdb);
        if (res)
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, "Se guardo");
        else
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, "Se guardo");
        
        return p;
    }

    @Override
    public boolean delete(Long id) {
        ConexionDB cx=ConexionDB.getInstance();
        TransaccionDB tdb=new TransaccionDB <Long>(id){
        
            @Override
            public boolean execute(Connection con){
                try{
                String sql="Delete from empleados where claveempleado=?";
                PreparedStatement pst=con.prepareStatement(sql);
                pst.setLong(1, id);
                pst.execute();
                return true;
                }catch(SQLException ex){
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, "No se realizo");
                    return false;
                }
        
            }
        };
        boolean res=cx.execute(tdb);
        if (res)
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, "Se ha borrado");
        else
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, "No se realizo la operación solicitada");
        

        return res;
    }

    @Override
    public Empleado update(Empleado p, Long id) {
        ConexionDB cx=ConexionDB.getInstance();
        TransaccionDB tdb= new TransaccionDB<Empleado>(p){
            @Override
            public boolean execute(Connection con){
                try{
                    String sql="Update empleados set nombre=?, dirección=? where claveempleado=?";
                    PreparedStatement pst=con.prepareStatement(sql);
                    pst.setString(1, p.getNombre());
                    pst.setString(2, p.getDireccion());
                    pst.setLong(3,id);
                    pst.execute();
                    return true;
                }catch(SQLException ex){
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, null, ex);
                    return false;
                }
            }
        };
        boolean res=cx.execute(tdb);
        if (res)
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, "Se ha actualizado");
        else
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, "No se realizo la operación solicitada");
        

        return p;  
    }

    @Override
    public List<Empleado> findAll() {
        List<Empleado> listaEmpleados=new ArrayList<Empleado>();
        ConexionDB cx=ConexionDB.getInstance();
        TransaccionDB tdb=new TransaccionDB<List<Empleado>>(listaEmpleados){
        @Override
            public boolean execute(Connection con){
                try{
                String sql="Select * from empleados order by claveempleado";
                PreparedStatement pst=con.prepareStatement(sql);
                ResultSet st=pst.executeQuery();
                while(st.next()){
                listaEmpleados.add(new Empleado(st.getLong("claveempleado"), st.getString("nombre"), st.getString("dirección")));
                }
                return true;
                }catch(SQLException ex){
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, "No se realizo");
                    return false;
                }
        
            }
        };
        boolean res=cx.execute(tdb);
        if (res)
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, "Se ha obtenido la info de la BD");
        else
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, "No se realizo la operación solicitada");
        
        return listaEmpleados;
    }

    @Override
    public Empleado findbyID(Long id) {
        Empleado emp=new Empleado();
        ConexionDB cx=ConexionDB.getInstance();
        TransaccionDB tdb=new TransaccionDB<Empleado>(emp){
        @Override
            public boolean execute(Connection con){
                try{
                String sql="Select * from empleados where claveempleado=?";
                PreparedStatement pst=con.prepareStatement(sql);
                pst.setLong(1,id);
                ResultSet st=pst.executeQuery();
                if (st.next()){
                emp.setClave(st.getLong("claveempleado"));
                emp.setNombre(st.getString("nombre"));
                emp.setDireccion(st.getString("dirección"));}
                return true;
                }catch(SQLException ex){
                    Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, "No se realizo", ex);
                    return false;
                }
        
            }
        };
        boolean res=cx.execute(tdb);
        if (res)
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, "Se ha obtenido la info de la BD");
        else
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.INFO, "No se realizo la operación solicitada");
        
        System.out.println("Estoy en findbyID: "+emp.getClave());
        return emp;
    }
    
}