package model.dao;

import connection.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bean.Clientes;
import model.bean.Consulta;
import model.bean.Usuario;
import util.ThreadDialog;

/**
 *
 * @author Matheus - DELL
 */
public class ConsultasDAO {
    
    private Connection con = Conexao.getConnection();
    private PreparedStatement stmt = null;
    
    public void criar(Consulta consulta){
        
        System.out.println("** CRIAR CONSULTA **");
        
        try {
            
            stmt = con.prepareStatement("INSERT INTO"
                    + " consulta "
                    + "(usuario, retornoId, clienteId, saeId) "
                    + "VALUES "
                    + "(?, ?, ?, ?);");
            
            stmt.setString(1, consulta.getUsuario());
            stmt.setInt(2, consulta.getRetornoId());
            stmt.setInt(3, consulta.getClienteId());
            stmt.setInt(4, consulta.getSaeId());
            
            stmt.executeUpdate();
            
            new ThreadDialog("Consulta cadastrada!");
            System.out.println("Consulta cadastrada!");
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao cadastrar a consulta: " + e.getMessage());
            System.err.println("Erro ao cadastrar a consulta: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt);
            
        }
        
    }
    
    public List<Consulta> listarConsultas(){
        
        System.out.println("** LISTAR CONSULTAS **");
        
        ResultSet rs = null;
        
        List<Consulta> listaConsulta = new ArrayList<>();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM consulta");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Consulta consulta = new Consulta();
                
                consulta.setUsuario(rs.getString("usuario"));
                consulta.setRetornoId(rs.getInt("retornoId"));
                consulta.setClienteId(rs.getInt("clienteId"));
                consulta.setSaeId(rs.getInt("saeId"));
                consulta.setData(rs.getString("data"));
                
                System.out.println("* Consulta encontrada!");
                
                listaConsulta.add(consulta);
                
            }
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao Listar Consultas: " + e.getMessage());
            System.err.println("Erro ao Listar Consultas: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        System.out.println(listaConsulta.size() + " consulta(s) encontrada(s)!");
        
        return listaConsulta;
        
    }
    
    public List<Consulta> listarConsultasCliente(Clientes cliente){
        
        System.out.println("** LISTAR CONSULTAS CLIENTE **");
        
        ResultSet rs = null;
        
        List<Consulta> listaConsulta = new ArrayList<>();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM consulta WHERE clienteId = ?");
            stmt.setInt(1, cliente.getId());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Consulta consulta = new Consulta();
                
                consulta.setUsuario(rs.getString("usuario"));
                consulta.setRetornoId(rs.getInt("retornoId"));
                consulta.setClienteId(rs.getInt("clienteId"));
                consulta.setSaeId(rs.getInt("saeId"));
                consulta.setData(rs.getString("data"));
                
                System.out.println("* Consulta encontrada!");
                
                listaConsulta.add(consulta);
                
            }
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao Listar Consultas de " + cliente.getNome() + "\n" + e.getMessage());
            System.err.println("Erro ao Listar Consultas de " + cliente.getNome() + "\n" + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        System.out.println(listaConsulta.size() + " consulta(s) encontrada(s)!");
        
        return listaConsulta;
        
    }
    
    public List<Consulta> listarConsultasUsuario(Usuario usuario){
        
        System.out.println("** LISTAR CONSULTAS USUARIO **");
        
        ResultSet rs = null;
        
        List<Consulta> listaConsulta = new ArrayList<>();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM consulta WHERE usuario = ?");
            stmt.setString(1, usuario.getUsuario());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Consulta consulta = new Consulta();
                
                consulta.setUsuario(rs.getString("usuario"));
                consulta.setRetornoId(rs.getInt("retornoId"));
                consulta.setClienteId(rs.getInt("clienteId"));
                consulta.setSaeId(rs.getInt("saeId"));
                consulta.setData(rs.getString("data"));
                
                System.out.println("* Consulta encontrada!");
                
                listaConsulta.add(consulta);
                
            }
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao Listar Consultas de " + usuario.getNome() + "\n" + e.getMessage());
            System.err.println("Erro ao Listar Consultas de " + usuario.getNome() + "\n" + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        System.out.println(listaConsulta.size() + " consulta(s) encontrada(s)!");
        
        return listaConsulta;
        
    }
    
}
