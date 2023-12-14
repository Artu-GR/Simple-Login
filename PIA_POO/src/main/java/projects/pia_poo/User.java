/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projects.pia_poo;
import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author artur
 */

public class User {
    private int id;
    private String username;
    private String password;
    private Component rootPane;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    public void insertUser(){
        try{
            char[] specialChar = {'~','`','!','@','#','$','%','^','&','*','(',')','_','-','+','=','{','[','}',']','|','\\',':',';','\"','<',',','>','.','?','/'};
            int dotCounter = 0;
            boolean validUser = true;
            boolean validPassword = false;
            
            if(this.username.equals("") || this.password.equals("")){
                JOptionPane.showMessageDialog(rootPane, "Debe llenar todos los campos");
                return;
            }
            //terminacion "
            if(this.username.length() - this.username.indexOf('@') != 10 || !this.username.contains("@gmail.com")){
                JOptionPane.showMessageDialog(rootPane, "Usuaio debe ser de la forma '@gmail.com'");
                return;
            }
            //user normal char
            for(char letter : this.username.toCharArray()){
                if(letter == '@') break;
                if((letter >= 65 && letter <=90)||(letter >= 97 && letter <= 122)||(letter >= 48 && letter <= 57)){
                }else if(letter == '.'){
                    dotCounter += 1;
                }else{
                    validUser = false;
                    break;
                }
                if(dotCounter > 1){
                    validUser = false;
                    break;
                }
            }
            if(!validUser){
                JOptionPane.showMessageDialog(rootPane, "El correo no puede contener caracteres especiales");
                return;
            }
            //password specialChar
            for(char letter : specialChar){
                if(this.password.indexOf(letter) != -1){
                    validPassword = true;
                    break;
                }
            }
            if(!validPassword){
                JOptionPane.showMessageDialog(rootPane, "La contraseña debe contener al menos 1 caracter espeical");
                return;
            }
            //tamano de la contrasena
            if(this.password.length() > 16 || this.password.length() < 8){
                JOptionPane.showMessageDialog(rootPane, "Constraseña debe tener entre 8 y 16 caracteres");
                return;
            }
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tareas_pia_poo?enabledTLSProtocols=TLSv1.2","root","ijklmnop582#");
            
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM logininfo WHERE username=\""+this.username+"\"");
            if(rs.next()){
                JOptionPane.showMessageDialog(rootPane, "Ya existe un usuario registrado con ese correo");
                return;
            }
            
            PreparedStatement st = con.prepareStatement("INSERT INTO logininfo(id, username, password)"
                    + "VALUES(null,?,?)");
            st.setString(1, this.username);
            st.setString(2, this.password);
            
            st.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, "Usuario registrado correctamente :)");
        }catch(ClassNotFoundException | SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
    
    public boolean validateUser(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tareas_pia_poo?enabledTLSProtocols=TLSv1.2","root","ijklmnop582#");
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM logininfo WHERE username=\""+this.username+"\" and password=\""+this.password+"\"");
            if(!rs.next()){
                JOptionPane.showMessageDialog(rootPane, "Usuario y/o constraseña incorrectos");
                return false;
            }else{
                return true;
            }
        }catch(ClassNotFoundException | SQLException ex){
            System.err.println(ex.getMessage());
            return false;
        }
    }
    
    public void defineID(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tareas_pia_poo?enabledTLSProtocols=TLSv1.2","root","ijklmnop582#");

            ResultSet rs = con.createStatement().executeQuery("SELECT id FROM logininfo WHERE username=\""+this.username+"\"");
            if(rs.next()) this.id = rs.getInt(1);
        }catch(ClassNotFoundException | SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
    
}
