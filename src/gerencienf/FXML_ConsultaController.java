package gerencienf;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
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
import model.bean.Consulta;
import model.bean.Evolucao;
import model.bean.Retorno;
import model.bean.Sae;
import model.bean.Solicitacao;
import model.bean.Usuario;
import model.dao.ConsultasDAO;
import model.dao.EvolucaoDAO;
import model.dao.RetornoDAO;
import model.dao.SaeDAO;
import model.dao.SolicitacaoDAO;
import util.Atual;
import util.ThreadDataHora;
import util.ThreadDialog;
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
    @FXML
    private ImageView imgRemoverEvoucao1;
    @FXML
    private ImageView imgRemoverEvoucao2;
    @FXML
    private Label txtEvolucaoCaminhoFoto1;
    @FXML
    private Font x5;
    @FXML
    private Label txtEvolucaoCaminhoFoto2;
    
    
    /**
     * Initializes the controller class.
     */
    private Usuario usuario;
    private Clientes cliente;
    private Consulta consulta;
    
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
            
        }else{
            
            System.out.println("~~ Nova Consulta ~~");
            iniciarNovaConsulta();
            new ThreadDataHora(txtDataSistema);
            
        }
        
    }
    
    private void iniciarComponentes(){
        
        // SETAR LARGURA PARA A MESMA DO ANCHORPANE PRINCIPAL
        vbSae.prefWidthProperty().bind(apSae.widthProperty());
        //spSae.prefWidthProperty().bind(apSae.widthProperty());
        
    }
    
    // INICIAR NOVA CONSULTA
    private void iniciarNovaConsulta(){
        
        hBoxSalvar.setDisable(false);
        hBoxSalvar.setOpacity(1.0);
        hBoxImprimir.setDisable(true);
        hBoxImprimir.setOpacity(0.5);

        imgEvolucao1.setOnMouseClicked(k -> {

            abrirFile(imgEvolucao1, txtEvolucaoCaminhoFoto1);
            System.out.println("TESTE FOTO 1 CAMINHO: " + txtEvolucaoCaminhoFoto1.getText());

        });

        imgEvolucao2.setOnMouseClicked(k -> {

            abrirFile(imgEvolucao2, txtEvolucaoCaminhoFoto2);
            System.out.println("TESTE FOTO 2 CAMINHO: " + txtEvolucaoCaminhoFoto2.getText());

        });

        // EVOLUCAO - REMOVER 1
        imgRemoverEvoucao1.setOnMouseClicked(k -> {

            txtEvolucaoData1.setText("");
            imgEvolucao1.setImage(new Image("/img/image_red.png"));
            txtEvolucaoCaminhoFoto1.setText("");
            
            System.out.println("REMOVER EVOLUCAO 1");

        });
        
        // EVOLUCAO - REMOVER 2
        imgRemoverEvoucao2.setOnMouseClicked(k -> {

            txtEvolucaoData2.setText("");
            imgEvolucao2.setImage(new Image("/img/image_red.png"));
            txtEvolucaoCaminhoFoto1.setText("");
            
            System.out.println("REMOVER EVOLUCAO 2");

        });
        
        // HBOX SALVAR
        hBoxSalvar.setOnMouseClicked(k -> {

            if(txtSenha.getText().equals(usuario.getSenha())){
                
                System.out.println("~~ SALVAR CONSULTA ~~");
                salvarConsulta();
                
            }else{
                
                new ThreadDialog("Senha Inválida!");
                txtSenha.requestFocus();
                System.err.println("Senha Inválida!");
                
            }

        });
            
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
        
        consulta = new Consulta(Atual.getConsulta());
        
        System.out.println("-- Consulta ID: " + consulta.getId());
        
        hBoxSalvar.setDisable(true);
        hBoxSalvar.setOpacity(0.5);
        hBoxImprimir.setDisable(false);
        hBoxImprimir.setOpacity(1.0);
        
        txtDataSistema.setText(Util.formatarData(consulta.getData()));
        
        // SISTEMATIZAÇÃO DA ASSISTÊNCIA DE ENFERMAGEM (SAE)
        if(consulta.getSaeId() > 0){
            sae = new Sae(new SaeDAO().saePorConsulta(consulta));
        }
        
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
        if(consulta.getSolicitacaoId() > 0){
            solicitacao = new Solicitacao(new SolicitacaoDAO().solicitacaoPorConsulta(consulta));
        }
        
        if(solicitacao != null){
            
            System.out.println("-- SOLICITACAO NAO NULA --");
            
            // SOLICITACAO - SOLICITO
            if(solicitacao.getSolicitacao() != null && !solicitacao.getSolicitacao().isEmpty()){
                
                txtSolicitacao.setText(solicitacao.getSolicitacao());
                
            }
            
        }
        
        txtSolicitacao.setEditable(false);
        
        // EVOLUCAO
        if(consulta.getEvolucaoId() > 0){
            evolucao = new Evolucao(new EvolucaoDAO().evolucaoPorConsulta(consulta));
        }
        
        if(evolucao != null){
            
            System.out.println("-- EVOLUCAO NAO NULA --");
            
            // EVOLUCAO - EVOLUCAO
            if(evolucao.getEvolucao() != null && !evolucao.getEvolucao().isEmpty()){
                
                txtEvolucao.setText(evolucao.getEvolucao());
                
            }
            
            // EVOLUCAO - CAMINHO FOTO 1
            if(evolucao.getCaminhoFoto1() != null && !evolucao.getCaminhoFoto1().isEmpty()){
                
                imgEvolucao1.setImage(new Image("file:" + evolucao.getCaminhoFoto1()));
                txtEvolucaoCaminhoFoto1.setText(evolucao.getCaminhoFoto1());
                
            }
            
            // EVOLUCAO - DATA FOTO 1
            if(evolucao.getFoto1() != null && !evolucao.getFoto1().isEmpty()){
                
                txtEvolucaoData1.setText(evolucao.getFoto1());
                
            }
            
            // EVOLUCAO - CAMINHO FOTO 2
            if(evolucao.getCaminhoFoto2() != null && !evolucao.getCaminhoFoto2().isEmpty()){
                
                imgEvolucao2.setImage(new Image("file:" + evolucao.getCaminhoFoto2()));
                txtEvolucaoCaminhoFoto2.setText(evolucao.getCaminhoFoto2());
                
            }
            
            // EVOLUCAO - DATA FOTO 2
            if(evolucao.getFoto2() != null && !evolucao.getFoto2().isEmpty()){
                
                txtEvolucaoData2.setText(evolucao.getFoto2());
                
            }
            
        }
        
        txtEvolucao.setEditable(false);
        txtEvolucaoData1.setEditable(false);
        txtEvolucaoData2.setEditable(false);
        
        // RETORNO
        if(consulta.getRetornoId() > 0){
            retorno = new Retorno(new RetornoDAO().retornoPorConsulta(consulta));
        }
        
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
    private void abrirFile(ImageView imageView, Label caminhoFoto) {
      
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fc.showOpenDialog(null);
        
        if(selectedFile != null){
            
            System.out.println("Arquivo Selecionado: " + selectedFile.getName());
            System.out.println("Diretório do Arquivo: " + selectedFile.getPath());
            //txtCaminhoLogo.setText(selectedFile.getPath());
            
            try {
                
                caminhoFoto.setText(selectedFile.getPath());
                
                Image img = new Image("file:" + caminhoFoto.getText());
                
                imageView.setImage(img);
                
            } catch (Exception e) {
                
                System.err.println("Erro ao pegar imagem: " + e.getMessage());
                new ThreadDialog("Erro ao pegar imagem: " + e.getMessage());
            
            }
            
        }else{
            
            System.err.println("Arquivo não é válido!");
            
        }
    
    }
    
    // SALVAR
    private void salvarConsulta(){
        
        Consulta consultaCad = new Consulta();
        consultaCad.setUsuario(usuario.getUsuario());
        consultaCad.setClienteId(cliente.getId());
        
        // SISTEMATIZAÇÃO DA ASSISTÊNCIA DE ENFERMAGEM (SAE)
        Sae saeCad = new Sae();
        saeCad.setUsuario(usuario.getUsuario());
        saeCad.setClienteId(cliente.getId());
        boolean saeCad1 = false;
        
        if(!txtSaeHE.getText().equals(" ") && !txtSaeHE.getText().isEmpty()){
            
            saeCad.setHistorico(txtSaeHE.getText());
            saeCad1 = true;
            
        }
        
        if(!txtSaeDE.getText().equals(" ") && !txtSaeDE.getText().isEmpty()){
            
            saeCad.setDiagnostico(txtSaeDE.getText());
            saeCad1 = true;
            
        }
        
        if(!txtSaePE.getText().equals(" ") && !txtSaePE.getText().isEmpty()){
            
            saeCad.setPlanejamento(txtSaePE.getText());
            saeCad1 = true;
            
        }
        
        if(!txtSaeIE.getText().equals(" ") && !txtSaeIE.getText().isEmpty()){
            
            saeCad.setImplementacao(txtSaeIE.getText());
            saeCad1 = true;
            
        }
        
        if(!txtSaeAE.getText().equals(" ") && !txtSaeAE.getText().isEmpty()){
            
            saeCad.setAvalicacaoEvolucao(txtSaeAE.getText());
            saeCad1 = true;
            
        }
        
        // VERIFICAR SAE
        if(saeCad1){
            
            System.out.println("~~ SAE NAO NULA ~~");
            
            // CADASTRAR SAE
                    if(new SaeDAO().criar(saeCad)){
                        
                        int t = new SaeDAO().listarSaePorCliente(cliente).size();
                        
                        int id = new SaeDAO().listarSaePorCliente(cliente).get(t-1).getId();
                        
                        consultaCad.setSaeId(id);
                        
                        System.out.println("~~ SAE ID: " + consultaCad.getSaeId());

                    }
            
        }else{
            System.err.println("~~ SAE NULA ~~");
            consultaCad.setSaeId(0);
        }
        
        // SOLICITACAO
        Solicitacao solicitacaoCad = new Solicitacao();
        solicitacaoCad.setUsuarioId(usuario.getUsuario());
        solicitacaoCad.setClienteId(cliente.getId());
        boolean solicitacaoCad1 = false;
        
        if(!txtSolicitacao.getText().equals(" ") && !txtSolicitacao.getText().isEmpty()){
            
            solicitacaoCad.setSolicitacao(txtSolicitacao.getText());
            solicitacaoCad1 = true;
            
        }
        
        // VERIFICAR SOLICITACAO
        if(solicitacaoCad1){
            
            System.out.println("~~ SOLICITACAO NAO NULA ~~");
            
            // CADASTRAR SOLICITACAO
                    if(new SolicitacaoDAO().criar(solicitacaoCad)){
                        
                        int t = new SolicitacaoDAO().listarPorCliente(cliente).size();
                        
                        int id = new SolicitacaoDAO().listarPorCliente(cliente).get(t-1).getId();
                        
                        consultaCad.setSolicitacaoId(id);
                        
                        System.out.println("~~ SOLICITACAO ID: " + consultaCad.getSolicitacaoId());

                    }
            
        }else{
            System.err.println("~~ SOLICITACAO NULA ~~");
            consultaCad.setSolicitacaoId(0);
        }
        
        // EVOLUCAO
        Evolucao evolucaoCad = new Evolucao();
        evolucaoCad.setUsuarioId(usuario.getUsuario());
        evolucaoCad.setClienteId(cliente.getId());
        boolean evolucaoCad1 = false;
        
        if(txtEvolucaoCaminhoFoto1.getText() != null && !txtEvolucaoCaminhoFoto1.getText().isEmpty()){
            
            if(!txtEvolucaoData1.getText().equals(" ") && !txtEvolucaoData1.getText().isEmpty()){
            
                evolucaoCad.setCaminhoFoto1(txtEvolucaoCaminhoFoto1.getText());
                evolucaoCad.setFoto1(txtEvolucaoData1.getText());
                evolucaoCad1 = true;

            }
            
        }
        
        if(txtEvolucaoCaminhoFoto2.getText() != null && !txtEvolucaoCaminhoFoto2.getText().isEmpty()){
            
            if(!txtEvolucaoData2.getText().equals(" ") && !txtEvolucaoData2.getText().isEmpty()){
            
                evolucaoCad.setCaminhoFoto2(txtEvolucaoCaminhoFoto2.getText());
                evolucaoCad.setFoto2(txtEvolucaoData2.getText());
                evolucaoCad1 = true;

            }
            
        }
        
        if(!txtEvolucao.getText().equals(" ") && !txtEvolucao.getText().isEmpty()){
            
            evolucaoCad.setEvolucao(txtEvolucao.getText());
            evolucaoCad1 = true;
            
        }
        
        // VERIFICAR EVOLUCAO
        if(evolucaoCad1){
            
            System.out.println("~~ EVOLUCAO NAO NULA ~~");
            
            // CADASTRAR EVOLUCAO
                    if(new EvolucaoDAO().criar(evolucaoCad)){
                        
                        int t = new EvolucaoDAO().listarPorCliente(cliente).size();
                        
                        int id = new EvolucaoDAO().listarPorCliente(cliente).get(t-1).getId();
                        
                        consultaCad.setEvolucaoId(id);
                        
                        System.out.println("~~ EVOLUCAO ID: " + consultaCad.getEvolucaoId());

                    }
            
        }else{
            System.err.println("~~ EVOLUCAO NULA ~~");
            consultaCad.setEvolucaoId(0);
        }
        
        // RETORNO
        Retorno retornoCad = new Retorno();
        retornoCad.setUsuario(usuario.getUsuario());
        retornoCad.setClienteId(cliente.getId());
        boolean retornoCad1 = false;
        
        if(!txtRetornoTipo.getText().equals(" ") && !txtRetornoTipo.getText().isEmpty()){
            
            retornoCad.setTipo(txtRetornoTipo.getText());
            
        }
        
        if(!txtRetornoProcedimento.getText().equals(" ") && !txtRetornoProcedimento.getText().isEmpty()){
            
            retornoCad.setProcedimento(txtRetornoProcedimento.getText());
            
        }
        
        if(!txtRetornoMotivo.getText().equals(" ") && !txtRetornoMotivo.getText().isEmpty()){
            
            retornoCad.setMotivo(txtRetornoMotivo.getText());
            
        }
        
        if(!txtRetornoData.getText().equals(" ") && !txtRetornoData.getText().isEmpty()){
            
            retornoCad.setDataRetorno(txtRetornoData.getText());
                
            if(!txtRetornoHora.getText().equals(" ") && !txtRetornoHora.getText().isEmpty()){
                
                retornoCad.setHoraRetorno(txtRetornoHora.getText());

            }
            
            retornoCad1 = true;
            
        }else{
            
            retornoCad1 = false;
            
        }
        
        // VERIFICAR RETORNO
        if(retornoCad1){
            
            System.out.println("~~ RETORNO NAO NULO ~~");
            
            // CADASTRAR RETORNO

                    if(new RetornoDAO().criar(retornoCad)){
                        
                        int t = new RetornoDAO().listarPorCliente(cliente).size();
                        
                        int id = new RetornoDAO().listarPorCliente(cliente).get(t-1).getId();
                        
                        consultaCad.setRetornoId(id);
                        
                        System.out.println("~~ RETORNO ID: " + consultaCad.getRetornoId());

                    }
            
        }else{
            System.err.println("~~ RETORNO NULO ~~");
            consultaCad.setRetornoId(0);
        }
        
        if(saeCad1 || solicitacaoCad1 || evolucaoCad1 || retornoCad1){

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    
                    if(new ConsultasDAO().criar(consultaCad)){
                    
                        new ThreadDialog("Consulta de " + cliente.getNome() + " cadastrada!");
                        hBoxSalvar.setDisable(true);

                    }
                    
                }
            });
            
//            Thread salvarConsulta = new Thread(){
//
//                @Override
//                public void run() {
//                    
//                    
//                    
//                }
//                
//            };
//            
//            salvarConsulta.start();

        }else{

            System.err.println("Nada para salvar!");
            new ThreadDialog("Nada para salvar!");

        }
        
    }
    
}
