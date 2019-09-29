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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.bean.Clientes;
import model.bean.Sae;
import model.bean.Usuario;
import model.dao.SaeDAO;
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
    @FXML
    private AnchorPane apSae;
    @FXML
    private ScrollPane spSae;
    @FXML
    private Font x3;
    @FXML
    private AnchorPane apCorpo;
    @FXML
    private VBox vbSae;
    
    
    
    /**
     * Initializes the controller class.
     */
    private Usuario usuario;
    private Clientes cliente;
    private Sae sae;
    @FXML
    private Insets x4;
    @FXML
    private TextArea txtSaeHE;
    @FXML
    private TextArea txtSaeDE;
    @FXML
    private TextArea txtSaePE;
    @FXML
    private TextArea txtSaeIE;
    @FXML
    private TextArea txtSaeAE;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("## FXML CONSULTA INICIOU ##");
        
        iniciarComponentes();
        
        // USUARIO
        usuario = new Usuario(Atual.getUsuario());
        txtNomeEnfermeiro.setText(usuario.getNome());
        
        iniciarCliente();
        
        // VERIFICAR CONSULTA
        if(Atual.isVerConsulta()){
            System.out.println("~~ Visualizar Consulta ~~");
            inserirCampos();
        }else{
            System.out.println("~~ Nova Consulta ~~");
        }
        
    }
    
    private void iniciarComponentes(){
        
        // SETAR LARGURA PARA A MESMA DO ANCHORPANE PRINCIPAL
        vbSae.prefWidthProperty().bind(apSae.widthProperty());
        //spSae.prefWidthProperty().bind(apSae.widthProperty());
        
        new ThreadDataHora(txtDataSistema);
        
    }
    
    // INICIAR CLIENTE
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
    
    // INSERIR CAMPOS
    private void inserirCampos(){
        
        // SISTEMATIZAÇÃO DA ASSISTÊNCIA DE ENFERMAGEM (SAE)
        sae = new Sae(Atual.getSae());
        
        if(sae != null){
            
            System.out.println("-- SAE NAO NULA --");
            
            // SAE - HISTORICO DE ENFERMAGEM
            if(sae.getHistorico() != null && !sae.getHistorico().isEmpty()){
                
                txtSaeHE.setText(sae.getHistorico());
                
            }
            
            // SAE - DIAGNOSTICO DE ENFERMAGEM
            if(sae.getDiagnostico()!= null && !sae.getDiagnostico().isEmpty()){
                
                txtSaeDE.setText(sae.getDiagnostico());
                
            }
            
            // SAE - PLANEJAMENTO DE ENFERMAGEM
            if(sae.getPlanejamento()!= null && !sae.getPlanejamento().isEmpty()){
                
                txtSaePE.setText(sae.getPlanejamento());
                
            }
            
            // SAE - IMPLEMENTACAO DE ENFERMAGEM
            if(sae.getImplementacao()!= null && !sae.getImplementacao().isEmpty()){
                
                txtSaeIE.setText(sae.getImplementacao());
                
            }
            
            // SAE - AVALIACAO DE ENFERMAGEM
            if(sae.getAvalicacaoEvolucao()!= null && !sae.getAvalicacaoEvolucao().isEmpty()){
                
                txtSaeAE.setText(sae.getAvalicacaoEvolucao());
                
            }
            
        }
        
        txtSaeHE.setEditable(false);
        txtSaeDE.setEditable(false);
        txtSaePE.setEditable(false);
        txtSaeIE.setEditable(false);
        txtSaeAE.setEditable(false);
        
    }
    
}
