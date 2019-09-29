package model.bean;

/**
 *
 * @author Matheus - DELL
 */
public class Retorno {
    
    private int id, clienteId;
    private String usuario, tipo, procedimento, motivo, dataRetorno, data;

    public Retorno() {
    }

    public Retorno(Retorno retorno) {
        this.id = retorno.getId();
        this.clienteId = retorno.getClienteId();
        this.usuario = retorno.getUsuario();
        this.tipo = retorno.getTipo();
        this.procedimento = retorno.getProcedimento();
        this.motivo = retorno.getMotivo();
        this.dataRetorno = retorno.getDataRetorno();
        this.data = retorno.getData();
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(String dataRetorno) {
        this.dataRetorno = dataRetorno;
    }
    
    
    
}
