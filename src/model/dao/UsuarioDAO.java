package model.dao;

import connection.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bean.Consulta;
import model.bean.Solicitacao;
import model.bean.Usuario;
import util.ThreadDialog;

/**
 *
 * @author Matheus - DELL
 */
public class UsuarioDAO {
    
    private Connection con = Conexao.getConnection();
    private PreparedStatement stmt = null;
    
    public void criar(Usuario usuario){
        
        System.out.println("** CRIAR USUARIO **");
        
        try {
            
            stmt = con.prepareStatement("INSERT INTO"
                    + " login "
                    + "(nome, usuario, senha, nivel) "
                    + "VALUES "
                    + "(?, ?, ?, ?);");
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getUsuario());
            stmt.setString(3, usuario.getSenha());
            stmt.setInt(4, usuario.getNivel());
            
            stmt.executeUpdate();
            
            new ThreadDialog(usuario.getNome() + " inserido!");
            System.out.println(usuario.getNome() + " inserido!");
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao inserir " + usuario.getNome() + ": " + e.getMessage());
            System.err.println("Erro ao inserir " + usuario.getNome() + ": " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt);
            
        }
        
    }
    
    public List<Usuario> listarLogin(){
        
        System.out.println("** LISTAR USUARIOS **");
        
        ResultSet rs = null;
        
        List<Usuario> listaUsuario = new ArrayList<>();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM login");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                Usuario usuario = new Usuario();
                
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setNivel(rs.getInt("nivel"));
                
                System.out.println("* Usuario encontrado: " + usuario.getUsuario());
                
                listaUsuario.add(usuario);
                
            }
            
        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao Listar Usuarios: " + e.getMessage());
            System.err.println("Erro ao Listar Usuarios: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        System.out.println(listaUsuario.size() + " usuario(s) encontrado(s)!");
        
        return listaUsuario;
        
    }
    
    public Usuario localizar(String usuarioId){
        
        System.out.println("** USUARIO POR USUARIO **");
        
        ResultSet rs = null;
        
        Usuario usuario = new Usuario();
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM login WHERE usuario = ?");
            stmt.setString(1, usuarioId);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setNivel(rs.getInt("nivel"));
                
                System.out.println("* Usuario encontrado: " + usuario.getUsuario());
                
            }

        } catch (SQLException e) {
            
            new ThreadDialog("Erro ao recuperar o Usuario: " + e.getMessage());
            System.err.println("Erro ao recuperar o Usuario: " + e.getMessage());
            
        }finally{
            
            Conexao.closeConnection(con, stmt, rs);
            
        }
        
        return usuario;
        
    }
    
}
