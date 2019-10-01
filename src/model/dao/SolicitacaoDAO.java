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
import model.bean.Sae;
import model.bean.Solicitacao;
import util.ThreadDialog;

/**
 *
 * @author Matheus - DELL
 */
public class SolicitacaoDAO {
    
    private Connection con = Conexao.getConnection();
    private PreparedStatement stmt = null;
    
    public boolean criar(Solicitacao solicitacao){
        
        System.out.println("** CRIAR SOLICITACAO **");
        
        boolean retorno = false;
        
        try {
            
            stmt = con.prepareStatement("INSERT INTO"
                    + " solicitacao "
                    + "(usuarioId, clienteId, solicitacao) "
                    + "VALUES "
                    + "(?, ?, ?);");
            
            stmt.setString(1, solicitacao.getUsuarioId());
            stmt.setInt(2, solicitacao.getClienteId());
            stmt.setString(3, solicitacao.getSolicitacao());
            
            stmt.executeUpdate();
            
            System.out.println("Solicitacão cadastrada!");
            
            retorno = true;
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao inserir a Solicitacão: " + e.getMessage());
            System.err.println("Erro ao inserir a Solicitacão: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt);
            
        }
        
        return retorno;
        
    }
    
    public List<Solicitacao> listar(){
        
        System.out.println("** LISTAR SOLICITAÇÕES **");
        
        ResultSet rs = null;
        
        List<Solicitacao> listaSolicitacao = new ArrayList<>();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM solicitacao");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Solicitacao solicitacao = new Solicitacao();
                
                solicitacao.setId(rs.getInt("id"));
                solicitacao.setUsuarioId(rs.getString("usuarioId"));
                solicitacao.setClienteId(rs.getInt("clienteId"));
                solicitacao.setSolicitacao(rs.getString("solicitacao"));
                solicitacao.setData(rs.getString("data"));
                
                System.out.println("* Solicitação encontrada: " + solicitacao.getId());
                
                listaSolicitacao.add(solicitacao);
                
            }
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao Listar as Solicitações: " + e.getMessage());
            System.err.println("Erro ao Listar as Solicitações: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        System.out.println(listaSolicitacao.size() + " solicitações encontrada(s)!");
        
        return listaSolicitacao;
        
    }
    
    public List<Solicitacao> listarPorCliente(Clientes cliente){
        
        System.out.println("** LISTAR SOLICITAÇÕES POR CLIENTE **");
        
        ResultSet rs = null;
        
        List<Solicitacao> listaSolicitacao = new ArrayList<>();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM solicitacao WHERE clienteId = ?");
            stmt.setInt(1, cliente.getId());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Solicitacao solicitacao = new Solicitacao();
                
                solicitacao.setId(rs.getInt("id"));
                solicitacao.setUsuarioId(rs.getString("usuarioId"));
                solicitacao.setClienteId(rs.getInt("clienteId"));
                solicitacao.setSolicitacao(rs.getString("solicitacao"));
                solicitacao.setData(rs.getString("data"));
                
                System.out.println("* Solicitação encontrada: " + solicitacao.getId());
                
                listaSolicitacao.add(solicitacao);
                
            }
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao Listar a Solicitação: " + e.getMessage());
            System.err.println("Erro ao Listar a Solicitação: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        System.out.println(listaSolicitacao.size() + " solicitações encontrada(s)!");
        
        return listaSolicitacao;
        
    }
    
    public Solicitacao solicitacaoPorConsulta(Consulta consulta){
        
        System.out.println("** SOLICITAÇÃO POR CONSULTA **");
        
        ResultSet rs = null;
        
        Solicitacao solicitacao = new Solicitacao();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM solicitacao WHERE id = ?");
            stmt.setInt(1, consulta.getSolicitacaoId());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                solicitacao.setId(rs.getInt("id"));
                solicitacao.setUsuarioId(rs.getString("usuarioId"));
                solicitacao.setClienteId(rs.getInt("clienteId"));
                solicitacao.setSolicitacao(rs.getString("solicitacao"));
                solicitacao.setData(rs.getString("data"));
                
            }

        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao recuperar a Solicitação: " + e.getMessage());
            System.err.println("Erro ao recuperar a Solicitação: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        return solicitacao;
        
    }
    
}
