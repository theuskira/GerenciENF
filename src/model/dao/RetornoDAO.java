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
import model.bean.Retorno;
import model.bean.Sae;
import util.ThreadDialog;

/**
 *
 * @author Matheus - DELL
 */
public class RetornoDAO {
    
    private Connection con = Conexao.getConnection();
    private PreparedStatement stmt = null;
    
    public boolean criar(Retorno retorno){
        
        System.out.println("** CRIAR RETORNO **");
        
        boolean retorno1 = false;
        
        try {
            
            stmt = con.prepareStatement("INSERT INTO"
                    + " retorno "
                    + "(usuario, clienteId, tipo, procedimento,"
                    + " motivo, dataRetorno, horaRetorno) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?, ?, ?);");
            
            stmt.setString(1, retorno.getUsuario());
            stmt.setInt(2, retorno.getClienteId());
            stmt.setString(3, retorno.getTipo());
            stmt.setString(4, retorno.getProcedimento());
            stmt.setString(5, retorno.getMotivo());
            stmt.setString(6, retorno.getDataRetorno());
            stmt.setString(7, retorno.getHoraRetorno());
            
            stmt.executeUpdate();
            
            System.out.println("Retorno cadastrado!");
            
            retorno1 = true;
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao inserir o Retorno: " + e.getMessage());
            System.err.println("Erro ao inserir o Retorno: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt);
            
        }
        
        return retorno1;
        
    }
    
    public List<Retorno> listar(){
        
        System.out.println("** LISTAR RETORNOS **");
        
        ResultSet rs = null;
        
        List<Retorno> listaRetorno = new ArrayList<>();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM retorno");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Retorno retorno = new Retorno();
                
                retorno.setId(rs.getInt("id"));
                retorno.setUsuario(rs.getString("usuario"));
                retorno.setClienteId(rs.getInt("clienteId"));
                retorno.setTipo(rs.getString("tipo"));
                retorno.setProcedimento(rs.getString("procedimento"));
                retorno.setMotivo(rs.getString("motivo"));
                retorno.setDataRetorno(rs.getString("dataRetorno"));
                retorno.setHoraRetorno(rs.getString("horaRetorno"));
                retorno.setData(rs.getString("data"));
                
                System.out.println("* Retorno encontrado: " + retorno.getId());
                
                listaRetorno.add(retorno);
                
            }
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao Listar os Retornos: " + e.getMessage());
            System.err.println("Erro ao Listar os Retornos: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        System.out.println(listaRetorno.size() + " retorno(s) encontrado(s)!");
        
        return listaRetorno;
        
    }
    
    public Retorno retornoPorConsulta(Consulta consulta){
        
        System.out.println("** RETORNO POR CONSULTA **");
        
        ResultSet rs = null;
        
        Retorno retorno = new Retorno();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM retorno WHERE id = ?");
            stmt.setInt(1, consulta.getRetornoId());
            rs = stmt.executeQuery();

            retorno.setId(rs.getInt("id"));
            retorno.setUsuario(rs.getString("usuario"));
            retorno.setClienteId(rs.getInt("clienteId"));
            retorno.setTipo(rs.getString("tipo"));
            retorno.setProcedimento(rs.getString("procedimento"));
            retorno.setMotivo(rs.getString("motivo"));
            retorno.setDataRetorno(rs.getString("dataRetorno"));
            retorno.setHoraRetorno(rs.getString("horaRetorno"));
            retorno.setData(rs.getString("data"));

        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao recuperar o Retorno: " + e.getMessage());
            System.err.println("Erro ao recuperar o Retorno: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        return retorno;
        
    }
    
    public List<Retorno> listarPorCliente(Clientes cliente){
        
        System.out.println("** LISTAR RETORNO POR CLIENTE **");
        
        ResultSet rs = null;
        
        List<Retorno> listaRetorno = new ArrayList<>();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM retorno WHERE clienteId = ?");
            stmt.setInt(1, cliente.getId());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Retorno retorno = new Retorno();
                
                retorno.setId(rs.getInt("id"));
                retorno.setUsuario(rs.getString("usuario"));
                retorno.setClienteId(rs.getInt("clienteId"));
                retorno.setTipo(rs.getString("tipo"));
                retorno.setProcedimento(rs.getString("procedimento"));
                retorno.setMotivo(rs.getString("motivo"));
                retorno.setDataRetorno(rs.getString("dataRetorno"));
                retorno.setHoraRetorno(rs.getString("horaRetorno"));
                retorno.setData(rs.getString("data"));
                
                System.out.println("* Retorno encontrado: " + retorno.getId());
                
                listaRetorno.add(retorno);
                
            }
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao Listar os Retornos: " + e.getMessage());
            System.err.println("Erro ao Listar os Retornos: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        System.out.println(listaRetorno.size() + " retorno(s) encontrado(s)!");
        
        return listaRetorno;
        
    }
    
}
