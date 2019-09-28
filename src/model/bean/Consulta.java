package model.bean;

/**
 *
 * @author Matheus - DELL
 */
public class Consulta {
    
    private int retornoId, clienteId, saeId;
    private String usuario, data;

    public Consulta() {
    }

    public Consulta(Consulta consulta) {
        this.retornoId = consulta.getRetornoId();
        this.clienteId = consulta.getClienteId();
        this.saeId = consulta.getSaeId();
        this.usuario = consulta.getUsuario();
        this.data = consulta.getData();
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
    
}
