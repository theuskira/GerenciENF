package model.bean;

/**
 *
 * @author Matheus - DELL
 */
public class Evolucao {
    
    private int saeId;
    private String data, foto1, caminhoFoto1, foto2, caminhoFoto2, evolucao;

    public Evolucao() {
    }

    public Evolucao(Evolucao evolucao) {
        this.saeId = evolucao.getSaeId();
        this.data = evolucao.getData();
        this.foto1 = evolucao.getFoto1();
        this.caminhoFoto1 = evolucao.getCaminhoFoto1();
        this.foto2 = evolucao.getFoto2();
        this.caminhoFoto2 = evolucao.getCaminhoFoto2();
        this.evolucao = evolucao.getEvolucao();
    }

    public int getSaeId() {
        return saeId;
    }

    public void setSaeId(int saeId) {
        this.saeId = saeId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    public String getEvolucao() {
        return evolucao;
    }

    public void setEvolucao(String evolucao) {
        this.evolucao = evolucao;
    }

    public String getFoto1() {
        return foto1;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }

    public String getCaminhoFoto1() {
        return caminhoFoto1;
    }

    public void setCaminhoFoto1(String caminhoFoto1) {
        this.caminhoFoto1 = caminhoFoto1;
    }

    public String getFoto2() {
        return foto2;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }

    public String getCaminhoFoto2() {
        return caminhoFoto2;
    }

    public void setCaminhoFoto2(String caminhoFoto2) {
        this.caminhoFoto2 = caminhoFoto2;
    }
    
    
    
}
