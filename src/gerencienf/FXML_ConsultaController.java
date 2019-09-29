package gerencienf;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import model.bean.Clientes;
import model.bean.Evolucao;
import model.bean.Retorno;
import model.bean.Sae;
import model.bean.Solicitacao;
import model.bean.Usuario;
import model.dao.SaeDAO;
import util.Atual;
import util.ThreadDataHora;
import util.Util;
import view.FXML_ClienteController;
import view.FXML_ClientesController;

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
    @FXML
    private Font x4;
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
    @FXML
    private TextArea txtSolicitacao;
    @FXML
    private TextArea txtEvolucao;
    @FXML
    private TextArea txtRetornoTipo;
    @FXML
    private TextField txtRetornoData;
    @FXML
    private TextField txtRetornoHora;
    @FXML
    private TextArea txtRetornoProcedimento;
    @FXML
    private TextArea txtRetornoMotivo;  
    @FXML
    private TextField txtEvolucaoData1;
    @FXML
    private TextField txtEvolucaoData2;
    @FXML
    private PasswordField txtSenha;
    @FXML
    private HBox hBoxSalvar;
    @FXML
    private HBox hBoxImprimir;
    @FXML
    private ImageView imgEvolucao1;
    @FXML
    private ImageView imgEvolucao2;
    
    
    /**
     * Initializes the controller class.
     */
    private Usuario usuario;
    private Clientes cliente;
    private Sae sae;
    private Solicitacao solicitacao;
    private Evolucao evolucao;
    private Retorno retorno;
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
            hBoxSalvar.setDisable(true);
            hBoxSalvar.setOpacity(0.5);
            hBoxImprimir.setDisable(false);
            hBoxImprimir.setOpacity(1.0);
        }else{
            System.out.println("~~ Nova Consulta ~~");
            hBoxSalvar.setDisable(false);
            hBoxSalvar.setOpacity(1.0);
            hBoxImprimir.setDisable(true);
            hBoxImprimir.setOpacity(0.5);
            
            imgEvolucao1.setOnMouseClicked(k -> {
            
                abrirFile(imgEvolucao1);
            
            });
            
            imgEvolucao2.setOnMouseClicked(k -> {
            
                abrirFile(imgEvolucao2);
            
            });
            
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
            if(sae.getDiagnostico() != null && !sae.getDiagnostico().isEmpty()){
                
                txtSaeDE.setText(sae.getDiagnostico());
                
            }
            
            // SAE - PLANEJAMENTO DE ENFERMAGEM
            if(sae.getPlanejamento() != null && !sae.getPlanejamento().isEmpty()){
                
                txtSaePE.setText(sae.getPlanejamento());
                
            }
            
            // SAE - IMPLEMENTACAO DE ENFERMAGEM
            if(sae.getImplementacao() != null && !sae.getImplementacao().isEmpty()){
                
                txtSaeIE.setText(sae.getImplementacao());
                
            }
            
            // SAE - AVALIACAO DE ENFERMAGEM
            if(sae.getAvalicacaoEvolucao() != null && !sae.getAvalicacaoEvolucao().isEmpty()){
                
                txtSaeAE.setText(sae.getAvalicacaoEvolucao());
                
            }
            
        }
        
        txtSaeHE.setEditable(false);
        txtSaeDE.setEditable(false);
        txtSaePE.setEditable(false);
        txtSaeIE.setEditable(false);
        txtSaeAE.setEditable(false);
        
        // SOLICITACAO
        solicitacao = new Solicitacao(Atual.getSolicitacao());
        
        if(solicitacao != null){
            
            System.out.println("-- SOLICITACAO NAO NULA --");
            
            // SOLICITACAO - SOLICITO
            if(solicitacao.getSolicitacao() != null && !solicitacao.getSolicitacao().isEmpty()){
                
                txtSolicitacao.setText(solicitacao.getSolicitacao());
                
            }
            
        }
        
        txtSolicitacao.setEditable(false);
        
        // EVOLUCAO
        evolucao = new Evolucao(Atual.getEvolucao());
        
        if(evolucao != null){
            
            System.out.println("-- EVOLUCAO NAO NULA --");
            
            // EVOLUCAO - EVOLUCAO
            if(evolucao.getEvolucao() != null && !evolucao.getEvolucao().isEmpty()){
                
                txtEvolucao.setText(evolucao.getEvolucao());
                
            }
            
            // EVOLUCAO - FOTO 1
            if(evolucao.getCaminhoFoto1() != null && !evolucao.getCaminhoFoto1().isEmpty()){
                
                imgEvolucao1.setImage(new Image("file:" + evolucao.getCaminhoFoto1()));
                
            }
            
            if(evolucao.getFoto1() != null && !evolucao.getFoto1().isEmpty()){
                
                txtEvolucaoData1.setText(evolucao.getFoto1());
                
            }
            
            // EVOLUCAO - FOTO 2
            if(evolucao.getCaminhoFoto2() != null && !evolucao.getCaminhoFoto2().isEmpty()){
                
                imgEvolucao2.setImage(new Image("file:" + evolucao.getCaminhoFoto2()));
                
            }
            
            if(evolucao.getFoto2() != null && !evolucao.getFoto2().isEmpty()){
                
                txtEvolucaoData2.setText(evolucao.getFoto2());
                
            }
            
        }
        
        txtEvolucao.setEditable(false);
        txtEvolucaoData1.setEditable(false);
        txtEvolucaoData2.setEditable(false);
        
        // RETORNO
        retorno = new Retorno(Atual.getRetorno());
        
        if(retorno != null){
            
            System.out.println("-- RETORNO NAO NULO --");
            
            // RETORNO - TIPO
            if(retorno.getTipo() != null && !retorno.getTipo().isEmpty()){
                
                txtRetornoTipo.setText(retorno.getTipo());
                
            }
            
            // RETORNO - DATA
            if(retorno.getDataRetorno() != null && !retorno.getDataRetorno().isEmpty()){
                
                txtRetornoData.setText(retorno.getDataRetorno());
                
            }
            
            // RETORNO - HORA
            if(retorno.getHoraRetorno() != null && !retorno.getHoraRetorno().isEmpty()){
                
                txtRetornoHora.setText(retorno.getHoraRetorno());
                
            }
            
            // RETORNO - PROCEDIMENTO
            if(retorno.getProcedimento() != null && !retorno.getProcedimento().isEmpty()){
                
                txtRetornoProcedimento.setText(retorno.getProcedimento());
                
            }
            
            // RETORNO - MOTIVO
            if(retorno.getMotivo() != null && !retorno.getMotivo().isEmpty()){
                
                txtRetornoMotivo.setText(retorno.getMotivo());
                
            }
            
        }
        
        txtRetornoTipo.setEditable(false);
        txtRetornoData.setEditable(false);
        txtRetornoHora.setEditable(false);
        txtRetornoProcedimento.setEditable(false);
        txtRetornoMotivo.setEditable(false);
        
    }
    
    // ABRIR IMAGENS
    private void abrirFile(ImageView imageView) {
      
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fc.showOpenDialog(null);
        
        if(selectedFile != null){
            
            System.out.println("Arquivo Selecionado: " + selectedFile.getName());
            System.out.println("Diretório do Arquivo: " + selectedFile.getPath());
            //txtCaminhoLogo.setText(selectedFile.getPath());
            
            try {
                
                Image img = new Image("file:" + selectedFile.getPath());
                
                imageView.setImage(img);
                
            } catch (Exception e) {
                
                System.err.println("Erro ao pegar imagem: " + e.getMessage());
            
            }
            
        }else{
            
            System.err.println("Arquivo não é válido!");
            
        }
        
    
    }
    
}
