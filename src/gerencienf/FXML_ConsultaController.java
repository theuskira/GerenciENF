package gerencienf;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import model.bean.Clientes;
import model.bean.Usuario;
import util.Atual;
import util.ThreadDataHora;
import util.Util;
import view.FXML_ClienteController;

/**
 * FXML Controller class
 *
 * @author Matheus - DELL
 */
public class FXML_ConsultaController implements Initializable {

    @FXML
    private Label txtNomeEnfermeiro;
    @FXML
    private Font x1;
    @FXML
    private Insets x2;
    @FXML
    private Label txtNomeCliente;
    @FXML
    private Label txtDataNascimentoCliente;
    @FXML
    private Label txtSexoCliente;
    @FXML
    private Label txtPesoCliente;
    @FXML
    private Label txtAlturaCliente;
    @FXML
    private Label txtDataSistema;
    @FXML
    private ImageView imgLogo;
    
    /**
     * Initializes the controller class.
     */
    private Usuario usuario;
    private Clientes cliente;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("## FXML CONSULTA INICIOU ##");
        // USUARIO
        usuario = new Usuario(Atual.getUsuario());
        txtNomeEnfermeiro.setText(usuario.getNome());
        
        iniciarCliente();
        
        // VERIFICAR CONSULTA
        if(Atual.isVerConsulta()){
            System.out.println("--Visualizar Consulta--");
        }else{
            System.out.println("--Nova Consulta--");
        }
        
        new ThreadDataHora(txtDataSistema);
        
    }
    // CLIENTE
    private void iniciarCliente(){
        
        cliente = new Clientes(Atual.getCliente());
        txtNomeCliente.setText(cliente.getNome());
        
        if(cliente.getDataNascimento() != null){
            try {
                txtDataNascimentoCliente.setText(
                        cliente.getDataNascimento()
                        + " ("
                        + Util.calculaIdade(cliente.getDataNascimento())
                        + " Anos)"
                );
            } catch (ParseException ex) {
                Logger.getLogger(FXML_ClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(cliente.getSexo() != null){
            txtSexoCliente.setText("Sexo: " + cliente.getSexo());
        }
        
        if(cliente.getPeso() != null && cliente.getPeso() > 0){
            txtPesoCliente.setText("Peso: " + cliente.getPeso() + " Kg");
        }
        
        if(cliente.getAltura()!= null && cliente.getAltura() > 0){
            txtAlturaCliente.setText("Altura: " + cliente.getAltura() + " m");
        }
        
    }
    
}
