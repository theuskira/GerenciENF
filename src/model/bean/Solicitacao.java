package model.bean;

/**
 *
 * @author Matheus - DELL
 */
public class Solicitacao {
    
    private int id, clienteId;
    private String usuarioId, solicitacao, data;

    public Solicitacao() {
    }
    
    public Solicitacao(Solicitacao solicitacao) {
        this.id = solicitacao.getId();
        this.clienteId = solicitacao.getClienteId();
        this.usuarioId = solicitacao.getUsuarioId();
        this.solicitacao = solicitacao.getSolicitacao();
        this.data = solicitacao.getData();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(String solicitacao) {
        this.solicitacao = solicitacao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    
    
}
