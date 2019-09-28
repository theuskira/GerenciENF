package model.dao;

import connection.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bean.Clientes;
import model.bean.Sae;
import util.ThreadDialog;

/**
 *
 * @author Matheus - DELL
 */
public class SaeDAO {
    
    private Connection con = Conexao.getConnection();
    private PreparedStatement stmt = null;
    
    public boolean criar(Sae sae){
        
        System.out.println("** CRIAR SAE **");
        
        boolean retorno = false;
        
        try {
            
            stmt = con.prepareStatement("INSERT INTO"
                    + " sae "
                    + "(usuario, clienteId, historico, diagnostico, "
                    + "planejaento, implementacao, avaliacaoEvolucao) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?, ?, ?);");
            
            stmt.setString(1, sae.getUsuario());
            stmt.setInt(2, sae.getClienteId());
            stmt.setString(3, sae.getHistorico());
            stmt.setString(4, sae.getDiagnostico());
            stmt.setString(5, sae.getPlanejamento());
            stmt.setString(6, sae.getImplementacao());
            stmt.setString(7, sae.getAvalicacaoEvolucao());
            
            stmt.executeUpdate();
            
            new ThreadDialog("(SAE) cadastrada!");
            System.out.println("Sistematização da Assistência de Enfermagem (SAE) cadastrada!");
            
            retorno = true;
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao inserir a (SAE): " + e.getMessage());
            System.err.println("Erro ao inserir a (SAE): " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt);
            
        }
        
        return retorno;
        
    }
    
    public List<Sae> listarSae(){
        
        System.out.println("** LISTAR SAE **");
        
        ResultSet rs = null;
        
        List<Sae> listaSae = new ArrayList<>();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM sae");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Sae sae = new Sae();
                
                sae.setId(rs.getInt("id"));
                sae.setUsuario(rs.getString("usuario"));
                sae.setClienteId(rs.getInt("clienteId"));
                sae.setHistorico(rs.getString("historico"));
                sae.setDiagnostico(rs.getString("diagnostico"));
                sae.setPlanejamento(rs.getString("planejamento"));
                sae.setImplementacao(rs.getString("implementacao"));
                sae.setAvalicacaoEvolucao(rs.getString("avalicacaoEvolucao"));
                sae.setData(rs.getString("data"));
                
                System.out.println("* (SAE) encontrada: " + sae.getId());
                
                listaSae.add(sae);
                
            }
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao Listar a (SAE): " + e.getMessage());
            System.err.println("Erro ao Listar a (SAE): " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        System.out.println(listaSae.size() + " (SAE) encontrada(s)!");
        
        return listaSae;
        
    }
    
    public List<Sae> listarSaePorCliente(Clientes cliente){
        
        System.out.println("** LISTAR SAE POR CLIENTE **");
        
        ResultSet rs = null;
        
        List<Sae> listaSae = new ArrayList<>();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM sae WHERE clienteId = ?");
            stmt.setInt(1, cliente.getId());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Sae sae = new Sae();
                
                sae.setId(rs.getInt("id"));
                sae.setUsuario(rs.getString("usuario"));
                sae.setClienteId(rs.getInt("clienteId"));
                sae.setHistorico(rs.getString("historico"));
                sae.setDiagnostico(rs.getString("diagnostico"));
                sae.setPlanejamento(rs.getString("planejamento"));
                sae.setImplementacao(rs.getString("implementacao"));
                sae.setAvalicacaoEvolucao(rs.getString("avalicacaoEvolucao"));
                sae.setData(rs.getString("data"));
                
                System.out.println("* (SAE) encontrada: " + sae.getId());
                
                listaSae.add(sae);
                
            }
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao Listar a (SAE): " + e.getMessage());
            System.err.println("Erro ao Listar a (SAE): " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        System.out.println(listaSae.size() + " (SAE) encontrada(s)!");
        
        return listaSae;
        
    }
    
}
