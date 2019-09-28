/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import model.bean.Clientes;
import model.dao.ClientesDAO;
import util.Atual;
import util.ThreadDialog;
import util.Util;

/**
 * FXML Controller class
 *
 * @author Matheus - DELL
 */
public class FXML_AdicionarClienteController implements Initializable {
    @FXML
    private TextField txtNome;
    @FXML
    private Font x1;
    @FXML
    private TextArea txtEndereco;
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtNumero;
    @FXML
    private HBox hBoxConsulta;
    @FXML
    private Font x2;
    @FXML
    private HBox hBoxSalvar;
    @FXML
    private RadioButton rbMasculino;
    @FXML
    private RadioButton rbFeminino;
    @FXML
    private TextField txtPeso;
    @FXML
    private TextField txtAltura;
    @FXML
    private TextField txtDataNascimento;

    /**
     * Initializes the controller class.
     */
    
    Clientes clienteAtual;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("## FXML ADICIONAR CLIENTE INICIOU ##");
        if(Atual.isEditarCliente()){
            
            System.out.println("--Editar Cliente--");
            
            clienteAtual = new Clientes(Atual.getCliente());
            
            inserirCampos();
            
        }else{
            
            System.out.println("--Novo Cliente--");
            
        }
        
        Util.cpfCnpjField(txtCpf);
        Util.dateField(txtDataNascimento);
        Util.numericField(txtNumero);
        //Util.numericField(txtPeso);
        //Util.numericField(txtAltura);
        
    }
    
    @FXML
    private void cadastrar(){
        if (verificarCampos()) {
            
            Clientes cliente = new Clientes();
            cliente.setNome(txtNome.getText());
            
            if(!txtEndereco.getText().equals(" ") && !txtEndereco.getText().isEmpty()){
                cliente.setEndereco(txtEndereco.getText());
            }
            
            if(!txtCpf.getText().equals(" ") && !txtCpf.getText().isEmpty()){
                cliente.setCpf(txtCpf.getText());
            }
            
            if(!txtNumero.getText().equals(" ") && !txtNumero.getText().isEmpty()){
                cliente.setNumero(txtNumero.getText());
            }
            
            if(rbMasculino.isSelected() && !rbFeminino.isSelected()){
                cliente.setSexo("Masculino");
            }
            
            if(rbFeminino.isSelected() && !rbMasculino.isSelected()){
                 cliente.setSexo("Feminino");
            }
            
            if(!txtPeso.getText().equals(" ") && !txtPeso.getText().isEmpty()){
                cliente.setPeso(Double.parseDouble(txtPeso.getText()));
            }
            
            if(!txtAltura.getText().equals(" ") && !txtAltura.getText().isEmpty()){
                cliente.setAltura(Double.parseDouble(txtAltura.getText()));
            }
            
            if(!txtDataNascimento.getText().equals(" ") && !txtAltura.getText().isEmpty()){
                cliente.setDataNascimento(txtDataNascimento.getText());
            }
            
            ClientesDAO c = new ClientesDAO();
            
            if(Atual.isEditarCliente()){
                
                cliente.setId(clienteAtual.getId());
                
                if(c.atualizar(cliente)){
                    hBoxSalvar.setDisable(true);
                    hBoxConsulta.setDisable(false);
                }
                
            }else{
                
                if(c.criar(cliente)){
                    hBoxSalvar.setDisable(true);
                    hBoxConsulta.setDisable(false);
                }
                
            }
            
        }
    }
    
    private boolean verificarCampos(){
        
        if(txtNome.getText().equals(" ") || txtNome.getText().isEmpty()){
            new ThreadDialog("Digite um nome válido!");
            System.err.println("Digite um nome válido!");
            txtNome.requestFocus();
        }else if(rbMasculino.isSelected()&& rbFeminino.isSelected()){
            new ThreadDialog("Escolha apenas um dos sexos!");
            System.err.println("Escolha apenas um dos sexos!");
        }else{
            return true;
        }
        
        return false;
    }
    
    private void inserirCampos(){
        
        txtNome.setText(clienteAtual.getNome());
        
        if(clienteAtual.getEndereco() != null){
            txtEndereco.setText(clienteAtual.getEndereco());
        }
        
        if(clienteAtual.getCpf() != null){
            txtCpf.setText(clienteAtual.getCpf());
        }
        
        if(clienteAtual.getNumero() != null){
            txtNumero.setText(clienteAtual.getNumero());
        }
        
        if(clienteAtual.getSexo() != null){
            if(clienteAtual.getSexo().equals("Masculino")){
                rbMasculino.setSelected(true);
            }
            if(clienteAtual.getSexo().equals("Feminino")){
                rbFeminino.setSelected(true);
            }
        }
        
        if(clienteAtual.getPeso() != null && clienteAtual.getPeso() > 0){
            txtPeso.setText(clienteAtual.getPeso().toString());
        }
        
        if(clienteAtual.getAltura() != null && clienteAtual.getAltura() > 0){
            txtAltura.setText(clienteAtual.getAltura().toString());
        }
        
        if(clienteAtual.getDataNascimento() != null){
            txtDataNascimento.setText(clienteAtual.getDataNascimento());
        }
        
    }
    
}
