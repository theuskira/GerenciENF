package model.bean;

/**
 *
 * @author Matheus - DELL
 */
public class Sae {
    
    private int id, clienteId;
    private String usuario, historico, diagnostico, 
            planejamento, implementacao, avalicacaoEvolucao, data;

    public Sae() {
    }

    public Sae(Sae sae) {
        this.id = sae.getId();
        this.clienteId = sae.getClienteId();
        this.usuario = sae.getUsuario();
        this.historico = sae.getHistorico();
        this.diagnostico = sae.getDiagnostico();
        this.planejamento = sae.getPlanejamento();
        this.implementacao = sae.getImplementacao();
        this.avalicacaoEvolucao = sae.getAvalicacaoEvolucao();
        this.data = sae.getData();
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

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getPlanejamento() {
        return planejamento;
    }

    public void setPlanejamento(String planejamento) {
        this.planejamento = planejamento;
    }

    public String getImplementacao() {
        return implementacao;
    }

    public void setImplementacao(String implementacao) {
        this.implementacao = implementacao;
    }

    public String getAvalicacaoEvolucao() {
        return avalicacaoEvolucao;
    }

    public void setAvalicacaoEvolucao(String avalicacaoEvolucao) {
        this.avalicacaoEvolucao = avalicacaoEvolucao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
}
