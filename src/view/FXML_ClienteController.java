package view;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.bean.Clientes;
import model.dao.ConsultasDAO;
import util.Atual;
import util.ThreadDialog;
import util.Util;

/**
 * FXML Controller class
 *
 * @author Matheus - DELL
 */
public class FXML_ClienteController implements Initializable {
    @FXML
    private Label txtNome;
    @FXML
    private Label txtEndereco;
    @FXML
    private Font x1;
    @FXML
    private Label txtTotalConsultas;
    @FXML
    private Label txtUltimaConsulta;
    @FXML
    private Label txtCadasatro;
    @FXML
    private HBox hBoxNovaConsulta;
    @FXML
    private Font x2;
    @FXML
    private HBox hBoxEditar;
    @FXML
    private HBox hBoxExcluir;
    @FXML
    private Label txtCpf;
    @FXML
    private Label txtNumero;
    @FXML
    private Label txtRetorno;
    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TableView<?> tabelaSae;
    @FXML
    private Label txtSexo;
    @FXML
    private Label txtIdade;
    @FXML
    private Label txtPeso;
    @FXML
    private Label txtAltura;
    @FXML
    private Label txtStatus;
    
    /**
     * Initializes the controller class.
     */
    private Clientes clienteSelecionado;
    @FXML
    private Font x3;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("## FXML CLIENTE INICIOU ##");
        // SETAR ALTURA PARA A MESMA DO ANCHORPANE PRINCIPAL
        tabelaSae.prefHeightProperty().bind(apPrincipal.heightProperty());
        clienteSelecionado = new Clientes(Atual.getCliente());
        System.out.println("CLIENTE SELECIONADO: " + clienteSelecionado.getNome());
  
        iniciarComponentes();

    }
    
    private void iniciarComponentes(){
        
        txtNome.setText(clienteSelecionado.getNome());
        txtCadasatro.setText("Cadastro: " + Util.formatarData(clienteSelecionado.getCadastro()));
        
        if(clienteSelecionado.getEndereco() != null){
            txtEndereco.setText(clienteSelecionado.getEndereco().replace("\n", " | "));
        }
        
        if(clienteSelecionado.getCpf() != null){
            txtCpf.setText(clienteSelecionado.getCpf());
        }
        
        if(clienteSelecionado.getNumero() != null){
            txtNumero.setText(clienteSelecionado.getNumero());
        }
        
        if(clienteSelecionado.getSexo() != null){
            txtSexo.setText("Sexo: " + clienteSelecionado.getSexo());
        }
        
        if(clienteSelecionado.getDataNascimento() != null){
            try {
                txtIdade.setText(
                        clienteSelecionado.getDataNascimento()
                        + " ("
                        + Util.calculaIdade(clienteSelecionado.getDataNascimento())
                        + " Anos)"
                );
            } catch (ParseException e) {
                System.err.println("Erro: " + e.getMessage());
                new ThreadDialog("Erro: " + e.getMessage());
            }
        }
        
        if(clienteSelecionado.getPeso() != null && clienteSelecionado.getPeso() > 0){
            txtPeso.setText("Peso: " + clienteSelecionado.getPeso() + " Kg");
        }
        
        if(clienteSelecionado.getAltura()!= null && clienteSelecionado.getAltura() > 0){
            txtAltura.setText("Altura: " + clienteSelecionado.getAltura() + " m");
        }
        
        if((clienteSelecionado.getPeso() != null && clienteSelecionado.getPeso() > 0) && (clienteSelecionado.getAltura()!= null && clienteSelecionado.getAltura() > 0)){
            txtStatus.setText(Util.calculoIMC(
                    clienteSelecionado.getPeso() / (clienteSelecionado.getAltura() * clienteSelecionado.getAltura()))
            );
        }
        
        // ConsultasDAO
        ConsultasDAO consultasDAO = new ConsultasDAO();
        int totalConsultas = consultasDAO.listarConsultasCliente(clienteSelecionado).size();
        
        txtTotalConsultas.setText(
            "Total de consultas: " 
            + totalConsultas
        );
        
        if(totalConsultas > 0){
            txtUltimaConsulta.setText(
                "Ultima consulta: " 
                + Util.formatarData(new ConsultasDAO().listarConsultasCliente(clienteSelecionado).get(totalConsultas-1).getData())
            );
        }
        
    }
    
    @FXML
    private void iniciarConsulta(MouseEvent event){
        
        Atual.setVerConsulta(false);
        
        try {
            FXMLLoader fxmlLoaderPrincipal = new FXMLLoader(getClass().getResource("/gerencienf/FXML_Consulta.fxml"));
            Parent r = (Parent) fxmlLoaderPrincipal.load();
            Stage stage = new Stage();
            
            stage.getIcons().add(new Image(Util.LOGO_2));
            stage.setTitle(
                Util.TITULO + " - " 
                + Atual.getUsuario().getNome()
                + " | Consultar: "
                + Atual.getCliente().getNome()
            );
            stage.setScene(new Scene(r));
            stage.setMaximized(true);
            stage.setMinHeight(720);
            stage.setMinWidth(1280);
            stage.show();
            
        } catch (IOException e) {
            
            new ThreadDialog("Erro: " + e.getMessage());
            System.err.println("Erro: " + e.getMessage());
            
        }
                
    }
    
    @FXML
    private void editarCliente(MouseEvent event){
        
        Atual.setEditarCliente(true);
        
        try {
            FXMLLoader fxmlLoaderPrincipal = new FXMLLoader(getClass().getResource("/view/FXML_AdicionarCliente.fxml"));
            Parent r = (Parent) fxmlLoaderPrincipal.load();
            Stage stage = new Stage();
            
            stage.getIcons().add(new Image(Util.LOGO_2));
            stage.setTitle(
                Util.TITULO 
                + " - " 
                + Atual.getUsuario().getNome()
                + " | Editar: "
                + Atual.getCliente().getNome()
            );
            stage.setScene(new Scene(r));
            stage.setMaximized(false);
            stage.setResizable(false);
            stage.show();
            
        } catch (IOException e) {
            
            new ThreadDialog("Erro: " + e.getMessage());
            System.err.println("Erro: " + e.getMessage());
            
        }
                
    }
    
}
