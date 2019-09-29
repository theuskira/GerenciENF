package view;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import model.bean.Clientes;
import model.dao.ClientesDAO;
import model.dao.ConsultasDAO;
import util.Atual;
import util.ThreadIniciar;
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("## FXML CLIENTE INICIOU ##");
        // SETAR ALTURA PARA A MESMA DO ANCHORPANE PRINCIPAL
        tabelaSae.prefHeightProperty().bind(apPrincipal.heightProperty());
        clienteSelecionado = new Clientes(Atual.getCliente());
        System.out.println("CLIENTE SELECIONADO: " + clienteSelecionado.getNome());
  
        iniciarComponentes(null);

    }
    
    private void iniciarComponentes(MouseEvent event){
        
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
        
        if(clienteSelecionado.getDataNascimento() != null){
            try {
                txtIdade.setText(
                        clienteSelecionado.getDataNascimento()
                        + " ("
                        + Util.calculaIdade(clienteSelecionado.getDataNascimento())
                        + " Anos)"
                );
            } catch (ParseException ex) {
                Logger.getLogger(FXML_ClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(clienteSelecionado.getSexo() != null){
            txtSexo.setText("Sexo: " + clienteSelecionado.getSexo());
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
                "Ultima de consulta: " 
                + new ConsultasDAO().listarConsultasCliente(clienteSelecionado).get(totalConsultas).getData()
            );
        }
        
    }
    
    @FXML
    private void iniciarConsulta(MouseEvent event){
        Atual.setVerConsulta(false);
        new ThreadIniciar(
                Util.TITULO 
                + " - " 
                + Atual.getUsuario().getNome()
                + " | Consultar: "
                + Atual.getCliente().getNome(), 
                "/gerencienf/FXML_Consulta.fxml", 
                true
        ).run();
                
    }
    
    @FXML
    private void editarCliente(MouseEvent event){
        Atual.setEditarCliente(true);
        new ThreadIniciar(
                Util.TITULO 
                + " - " 
                + Atual.getUsuario().getNome()
                + " | Editar: "
                + Atual.getCliente().getNome(), 
                "/view/FXML_AdicionarCliente.fxml", 
                false
        ).run();
                
    }
    
}
