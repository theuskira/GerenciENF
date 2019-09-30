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
import model.bean.Evolucao;
import model.bean.Solicitacao;
import util.ThreadDialog;

/**
 *
 * @author Matheus - DELL
 */
public class EvolucaoDAO {
    
    private Connection con = Conexao.getConnection();
    private PreparedStatement stmt = null;
    
    public boolean criar(Evolucao evolucao){
        
        System.out.println("** CRIAR EVOLUCAO **");
        
        boolean retorno = false;
        
        try {
            
            stmt = con.prepareStatement("INSERT INTO"
                    + " evolucao "
                    + "(usuarioId, clienteId, foto1, caminhoFoto1,"
                    + " foto2, caminhoFoto2, evolucao) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?, ?, ?);");
            
            stmt.setString(1, evolucao.getUsuarioId());
            stmt.setInt(2, evolucao.getClienteId());
            stmt.setString(3, evolucao.getFoto1());
            stmt.setString(4, evolucao.getCaminhoFoto1());
            stmt.setString(5, evolucao.getFoto2());
            stmt.setString(6, evolucao.getCaminhoFoto2());
            stmt.setString(7, evolucao.getEvolucao());
            
            stmt.executeUpdate();
            
            System.out.println("Evolucao cadastrada!");
            
            retorno = true;
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao inserir a Evolução: " + e.getMessage());
            System.err.println("Erro ao inserir a Evolução: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt);
            
        }
        
        return retorno;
        
    }
    
    public List<Evolucao> listar(){
        
        System.out.println("** LISTAR EVOLUÇÕES **");
        
        ResultSet rs = null;
        
        List<Evolucao> listaEvolucao = new ArrayList<>();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM evolucao");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Evolucao evolucao = new Evolucao();
                
                evolucao.setId(rs.getInt("id"));
                evolucao.setUsuarioId(rs.getString("usuarioId"));
                evolucao.setClienteId(rs.getInt("clienteId"));
                evolucao.setFoto1(rs.getString("foto1"));
                evolucao.setCaminhoFoto1(rs.getString("caminhoFoto1"));
                evolucao.setFoto2(rs.getString("foto2"));
                evolucao.setCaminhoFoto2(rs.getString("caminhoFoto2"));
                evolucao.setEvolucao(rs.getString("evolucao"));
                evolucao.setData(rs.getString("data"));
                
                System.out.println("* Evolução encontrada!");
                
                listaEvolucao.add(evolucao);
                
            }
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao Listar as Evoluções: " + e.getMessage());
            System.err.println("Erro ao Listar as Evoluções: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        System.out.println(listaEvolucao.size() + " evoluções encontrada(s)!");
        
        return listaEvolucao;
        
    }
    
    public Evolucao evolucaoPorConsulta(Consulta consulta){
        
        System.out.println("** EVOLUÇÃO POR CONSULTA **");
        
        ResultSet rs = null;
        
        Evolucao evolucao = new Evolucao();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM evolucao WHERE id = ?");
            stmt.setInt(1, consulta.getEvolucaoId());
            rs = stmt.executeQuery();

            evolucao.setId(rs.getInt("id"));
            evolucao.setUsuarioId(rs.getString("usuarioId"));
            evolucao.setClienteId(rs.getInt("clienteId"));
            evolucao.setFoto1(rs.getString("foto1"));
            evolucao.setCaminhoFoto1(rs.getString("caminhoFoto1"));
            evolucao.setFoto2(rs.getString("foto2"));
            evolucao.setCaminhoFoto2(rs.getString("caminhoFoto2"));
            evolucao.setEvolucao(rs.getString("evolucao"));
            evolucao.setData(rs.getString("data"));

        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao recuperar a Evolução: " + e.getMessage());
            System.err.println("Erro ao recuperar a Evolução: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        return evolucao;
        
    }
    
    public List<Evolucao> listarPorCliente(Clientes cliente){
        
        System.out.println("** LISTAR EVOLUÇÕES POR CLIENTE **");
        
        ResultSet rs = null;
        
        List<Evolucao> listaEvolucao = new ArrayList<>();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM evolucao WHERE clienteId = ?");
            stmt.setInt(1, cliente.getId());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Evolucao evolucao = new Evolucao();
                
                evolucao.setId(rs.getInt("id"));
                evolucao.setUsuarioId(rs.getString("usuarioId"));
                evolucao.setClienteId(rs.getInt("clienteId"));
                evolucao.setFoto1(rs.getString("foto1"));
                evolucao.setCaminhoFoto1(rs.getString("caminhoFoto1"));
                evolucao.setFoto2(rs.getString("foto2"));
                evolucao.setCaminhoFoto2(rs.getString("caminhoFoto2"));
                evolucao.setEvolucao(rs.getString("evolucao"));
                evolucao.setData(rs.getString("data"));
                
                System.out.println("* Evolução encontrada: " + evolucao.getId());
                
                listaEvolucao.add(evolucao);
                
            }
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao Listar a Evolução: " + e.getMessage());
            System.err.println("Erro ao Listar a Evolução: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        System.out.println(listaEvolucao.size() + " evoluções encontrada(s)!");
        
        return listaEvolucao;
        
    }
    
}
