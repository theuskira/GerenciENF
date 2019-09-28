package model.bean;

/**
 *
 * @author Matheus - DELL
 */
public class Clientes {
    
    private int id;
    private String nome, cpf, numero, endereco, cadastro, sexo, dataNascimento;
    private Double peso, altura;

    public Clientes() {
    }

    public Clientes(Clientes clientes) {
        this.id = clientes.getId();
        this.nome = clientes.getNome();
        this.cpf = clientes.getCpf();
        this.numero = clientes.getNumero();
        this.endereco = clientes.getEndereco();
        this.cadastro = clientes.getCadastro();
        this.sexo = clientes.getSexo();
        this.peso = clientes.getPeso();
        this.altura = clientes.getAltura();
        this.dataNascimento = clientes.getDataNascimento();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCadastro() {
        return cadastro;
    }

    public void setCadastro(String cadastro) {
        this.cadastro = cadastro;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
}
