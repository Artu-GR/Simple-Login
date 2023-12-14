/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projects.pia_poo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author artur
 */
public class Tarea {
    private int id;
    private String nombre;
    private String descripcion;
    private java.sql.Date fecha;
    private int idUser;
    
    public void setID(int id){
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(String fecha) { 
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date due = sdf.parse(fecha);
            java.sql.Date sqlDate = new java.sql.Date(due.getTime());
            this.fecha = sqlDate;
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void insertTarea() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tareas_pia_poo?enabledTLSProtocols=TLSv1.2", "root", "ijklmnop582#");
            PreparedStatement st = con.prepareStatement("INSERT INTO tareas(id, nombre, descripcion, fecha, idUser)"
                    + "VALUES(null, ?, ?, ?, ?)");
            st.setString(1, this.nombre);
            st.setString(2, this.descripcion);
            st.setDate(3, this.fecha);
            st.setInt(4, this.idUser);

            st.executeUpdate();
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void update(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tareas_pia_poo?enabledTLSProtocols=TLSv1.2", "root", "ijklmnop582#");
            PreparedStatement st = con.prepareStatement("UPDATE tareas "
                    + "SET nombre=?,"
                    + "descripcion=?, "
                    + "fecha=? WHERE id=?");
            st.setString(1,this.nombre);
            st.setString(2,this.descripcion);
            st.setDate(3,this.fecha);
            st.setInt(4, this.id);

            st.executeUpdate();
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void delete(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tareas_pia_poo?enabledTLSProtocols=TLSv1.2", "root", "ijklmnop582#");
            PreparedStatement st = con.prepareStatement("DELETE FROM tareas WHERE id=?");
            st.setInt(1, this.id);

            st.executeUpdate();
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
}
