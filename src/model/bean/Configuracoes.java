package model.bean;

/**
 *
 * @author Matheus - DELL
 */
public class Configuracoes {
    
    private String usuario, logo, background, tituloConsulta, validade;

    public Configuracoes() {
    }
    
    public Configuracoes(Configuracoes configuracoes) {
        this.usuario = configuracoes.getUsuario();
        this.logo = configuracoes.getLogo();
        this.background = configuracoes.getBackground();
        this.tituloConsulta = configuracoes.getTituloConsulta();
        this.validade = configuracoes.getValidade();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getTituloConsulta() {
        return tituloConsulta;
    }

    public void setTituloConsulta(String tituloConsulta) {
        this.tituloConsulta = tituloConsulta;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }
    
    
    
}
