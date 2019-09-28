package model.dao;

import connection.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bean.Clientes;
import model.bean.Usuario;
import util.ThreadDialog;

/**
 *
 * @author Matheus - DELL
 */
public class ClientesDAO {
    
    private Connection con = Conexao.getConnection();
    private PreparedStatement stmt = null;
    
    public boolean criar(Clientes cliente){
        
        System.out.println("** CRIAR CLIENTE **");
        
        boolean retorno = false;
        
        try {
            
            stmt = con.prepareStatement("INSERT INTO"
                    + " clientes "
                    + "(nome, cpf, numero, endereco, "
                    + "dataNascimento, sexo, peso, altura) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?, ?, ?, ?);");
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getNumero());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getDataNascimento());
            stmt.setString(6, cliente.getSexo());
            stmt.setString(6, cliente.getSexo());
            stmt.setString(6, cliente.getSexo());
            
            if(cliente.getPeso() != null){
                if(cliente.getPeso() > 0){
                    stmt.setDouble(7, cliente.getPeso());
                }
            }else{
                stmt.setString(7, null);
            }
            if(cliente.getAltura() != null){
                if(cliente.getAltura() > 0){
                    stmt.setDouble(8, cliente.getAltura());
                }
            }else{
                stmt.setString(8, null);
            }
            
            stmt.executeUpdate();
            
            new ThreadDialog(cliente.getNome() + " cadastrado!");
            System.out.println(cliente.getNome() + " cadastrado!");
            
            retorno = true;
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao inserir " + cliente.getNome() + ": " + e.getMessage());
            System.err.println("Erro ao inserir " + cliente.getNome() + ": " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt);
            
        }
        
        return retorno;
        
    }
    
    public boolean atualizar(Clientes cliente){
        
        System.out.println("** ATUALIZAR CLIENTE **");
        
        boolean retorno = false;
        
        try {
            
            stmt = con.prepareStatement("UPDATE"
                    + " clientes "
                    + " SET "
                    + "nome = ?, cpf = ?, numero = ?, endereco = ?, "
                    + "dataNascimento = ?, sexo = ?, peso = ?, altura = ?"
                    + " WHERE id = ?");
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getNumero());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getDataNascimento());
            stmt.setString(6, cliente.getSexo());
            if(cliente.getPeso() != null){
                if(cliente.getPeso() > 0){
                    stmt.setDouble(7, cliente.getPeso());
                }
            }else{
                stmt.setString(7, null);
            }
            if(cliente.getAltura() != null){
                if(cliente.getAltura() > 0){
                    stmt.setDouble(8, cliente.getAltura());
                }
            }else{
                stmt.setString(8, null);
            }
            
            stmt.setInt(9, cliente.getId());
            
            stmt.executeUpdate();
            
            new ThreadDialog(cliente.getNome() + " atualizado!");
            System.out.println(cliente.getNome() + " atualizado!");
            
            retorno = true;
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao atualizar " + cliente.getNome() + ": " + e.getMessage());
            System.err.println("Erro ao atualizar " + cliente.getNome() + ": " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt);
            
        }
        
        return retorno;
        
    }
    
    public List<Clientes> listarClientes(){
        
        System.out.println("** LISTAR CLIENTES **");
        
        ResultSet rs = null;
        
        List<Clientes> listaClientes = new ArrayList<>();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM clientes");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Clientes cliente = new Clientes();
                
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setNumero(rs.getString("numero"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setCadastro(rs.getString("cadastro"));
                cliente.setDataNascimento(rs.getString("dataNascimento"));
                cliente.setSexo(rs.getString("sexo"));
                cliente.setPeso(rs.getDouble("peso"));
                cliente.setAltura(rs.getDouble("altura"));
                
                System.out.println("* Cliente encontrado: " + cliente.getNome());
                
                listaClientes.add(cliente);
                
            }
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao Listar Clientes: " + e.getMessage());
            System.err.println("Erro ao Listar Clientes: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        System.out.println(listaClientes.size() + " cliente(s) encontrado(s)!");
        
        return listaClientes;
        
    }
    
}
