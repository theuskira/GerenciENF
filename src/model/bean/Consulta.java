package model.bean;

/**
 *
 * @author Matheus - DELL
 */
public class Consulta {
    
    private int id, retornoId, clienteId, saeId, solicitacaoId, evolucaoId;
    private String usuario, data;

    public Consulta() {
    }

    public Consulta(Consulta consulta) {
        this.id = consulta.getId();
        this.retornoId = consulta.getRetornoId();
        this.clienteId = consulta.getClienteId();
        this.saeId = consulta.getSaeId();
        this.solicitacaoId = consulta.getSolicitacaoId();
        this.evolucaoId = consulta.getEvolucaoId();
        this.usuario = consulta.getUsuario();
        this.data = consulta.getData();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getRetornoId() {
        return retornoId;
    }

    public void setRetornoId(int retornoId) {
        this.retornoId = retornoId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getSaeId() {
        return saeId;
    }

    public void setSaeId(int saeId) {
        this.saeId = saeId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getSolicitacaoId() {
        return solicitacaoId;
    }

    public void setSolicitacaoId(int solicitacaoId) {
        this.solicitacaoId = solicitacaoId;
    }

    public int getEvolucaoId() {
        return evolucaoId;
    }

    public void setEvolucaoId(int evolucaoId) {
        this.evolucaoId = evolucaoId;
    }
    
    
    
}
