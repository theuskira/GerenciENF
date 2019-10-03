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
    
    public boolean criar(Consulta consulta){
        
        System.out.println("** CRIAR CONSULTA **");
        
         boolean retorno = false;
        
        try {
            
            stmt = con.prepareStatement("INSERT INTO"
                    + " consulta "
                    + "(usuario, clienteId, saeId, solicitacaoId, evolucaoId, retornoId) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?, ?);");
            
            stmt.setString(1, consulta.getUsuario());
            stmt.setInt(2, consulta.getClienteId());
            stmt.setInt(3, consulta.getSaeId());
            stmt.setInt(4, consulta.getSolicitacaoId());
            stmt.setInt(5, consulta.getEvolucaoId());
            stmt.setInt(6, consulta.getRetornoId());
            
            stmt.executeUpdate();
            
            System.out.println("Consulta cadastrada!");
            
            retorno = true;
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao cadastrar a consulta: " + e.getMessage());
            System.err.println("Erro ao cadastrar a consulta: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt);
            
        }
        
        return retorno;
        
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
                
                consulta.setId(rs.getInt("id"));
                consulta.setUsuario(rs.getString("usuario"));
                consulta.setRetornoId(rs.getInt("retornoId"));
                consulta.setClienteId(rs.getInt("clienteId"));
                consulta.setSaeId(rs.getInt("saeId"));
                consulta.setSolicitacaoId(rs.getInt("solicitacaoId"));
                consulta.setEvolucaoId(rs.getInt("evolucaoId"));
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
                
                consulta.setId(rs.getInt("id"));
                consulta.setUsuario(rs.getString("usuario"));
                consulta.setRetornoId(rs.getInt("retornoId"));
                consulta.setClienteId(rs.getInt("clienteId"));
                consulta.setSaeId(rs.getInt("saeId"));
                consulta.setSolicitacaoId(rs.getInt("solicitacaoId"));
                consulta.setEvolucaoId(rs.getInt("evolucaoId"));
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
                
                consulta.setId(rs.getInt("id"));
                consulta.setUsuario(rs.getString("usuario"));
                consulta.setRetornoId(rs.getInt("retornoId"));
                consulta.setClienteId(rs.getInt("clienteId"));
                consulta.setSaeId(rs.getInt("saeId"));
                consulta.setSolicitacaoId(rs.getInt("solicitacaoId"));
                consulta.setEvolucaoId(rs.getInt("evolucaoId"));
                consulta.setData(rs.getString("data"));
                
                System.out.println("* Consulta de " + usuario.getNome() + " encontrada!");
                
                listaConsulta.add(consulta);
                
            }
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao Listar Consultas de " + usuario.getNome() + "\n" + e.getMessage());
            System.err.println("Erro ao Listar Consultas de " + usuario.getNome() + "\n" + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        System.out.println(listaConsulta.size() + " consulta(s) de " + usuario.getNome() + " encontrada(s)!");
        
        return listaConsulta;
        
    }
    
    // LISTAR CONSULTAS POR DATA E USUARIO
    public List<Consulta> porDataUsuario(Usuario usuario, String data){
        
        System.out.println("** LISTAR CONSULTAS POR DATA E USUARIO **");
        System.out.println("** DATA: " + data +" USUARIO: " + usuario.getNome());
        
       ResultSet rs = null;
        
       List<Consulta> listaConsulta = new ArrayList<>();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM consulta WHERE usuario = ? and DATE(data) = ?");
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, data);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Consulta consulta = new Consulta();
                
                consulta.setId(rs.getInt("id"));
                consulta.setUsuario(rs.getString("usuario"));
                consulta.setRetornoId(rs.getInt("retornoId"));
                consulta.setClienteId(rs.getInt("clienteId"));
                consulta.setSaeId(rs.getInt("saeId"));
                consulta.setSolicitacaoId(rs.getInt("solicitacaoId"));
                consulta.setEvolucaoId(rs.getInt("evolucaoId"));
                consulta.setData(rs.getString("data"));
                
                System.out.println("* Consulta por data e usuario de " + usuario.getNome() + " encontrada!");
                
                listaConsulta.add(consulta);
                
            }
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao Listar Consultas de " + usuario.getNome() + "\n" + e.getMessage());
            System.err.println("Erro ao Listar Consultas de " + usuario.getNome() + "\n" + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        System.out.println(listaConsulta.size() + " consulta(s) em "+ data +" de " + usuario.getNome() + " encontrada(s)!");
        
        return listaConsulta;
        
    }
    
}
