package model.bean;

/**
 *
 * @author Matheus - DELL
 */
public class Usuario {
    
    private String usuario, nome, senha;
    private int nivel;
    
    public Usuario(){
        
    }
    
    public Usuario(Usuario usuario){
        this.usuario = usuario.getUsuario();
        this.nome = usuario.getNome();
        this.senha = usuario.getSenha();
        this.nivel = usuario.getNivel();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    
}
