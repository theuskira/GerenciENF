package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.ThreadDialog;

public class Conexao {
    
    private static final String DRIVE = "com.mysql.jdbc.Driver";

    // CONFIGURACOES REDE
    private static final String LOCAL = "192.168.2.150";
    private static final String USER = "theuskira";
    private static final String PASSWORD = "pw123456";

    private static final String URL = "jdbc:mysql://" + LOCAL + ":3306/gerenciadorhospitalar?useTimezone=true&serverTimezone=UTC";
    
    public static Connection getConnection(){
        
        try{
            
            Class.forName(DRIVE);
            
            return DriverManager.getConnection(URL, USER, PASSWORD);
            
        }catch(SQLException | ClassNotFoundException e){
            
            System.err.println("Erro: " + e.getMessage());
            new ThreadDialog("Erro: " + e.getMessage());
            return null;
            
        }
    
    }
    
    public static void closeConnection(Connection con){
        
        if(con != null){
            
            try {
                
                con.close();
                
            } catch (SQLException e) {
                
                System.err.println("Erro: " + e.getMessage());
                new ThreadDialog("Erro: " + e.getMessage());
                
            }
            
        }
        
    }
    
    public static void closeConnection(Connection con, PreparedStatement stmt){
        
        closeConnection(con);
            
        if(stmt != null){
            
            try {
                
                stmt.close();
                
            } catch (SQLException e) {
                
                System.err.println("Erro: " + e.getMessage());
                new ThreadDialog("Erro: " + e.getMessage());
                
            }
            
        }
        
    }
    
    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs){
        
        closeConnection(con, stmt);
            
        if(rs != null){
            
            try {
                
                rs.close();
                
            } catch (SQLException e) {
                
                System.err.println("Erro: " + e.getMessage());
                new ThreadDialog("Erro: " + e.getMessage());
                
            }
            
        }
        
    }
    
}
