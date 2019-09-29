package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import model.dao.ConsultasDAO;
import util.Atual;
import util.ThreadDataHora;

/**
 * FXML Controller class
 *
 * @author Matheus - DELL
 */
public class FXML_HomeController implements Initializable {
    @FXML
    private AnchorPane apHome;
    @FXML
    private Label txtTotalConsultas;
    @FXML
    private Font x1;
    @FXML
    private Insets x2;
    @FXML
    private TextField txtPesquisarConsultas;
    @FXML
    private Button btnPesquisarConsultas;
    @FXML
    private Label txtNomeEnfermeiro;
    @FXML
    private TableView<?> tabelaConsultasDia;
    @FXML
    private LineChart<?, ?> lineChart7Dias;
    @FXML
    private Insets x3;
    @FXML
    private Label txtTotalConsultasEnf;
    @FXML
    private Label txtDataHora;
    @FXML
    private ImageView imgLogo;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("## FXML HOME INICIOU ##");
        
        // SETAR ALTURA PARA A MESMA DO ANCHORPANE PRINCIPAL
        tabelaConsultasDia.prefHeightProperty().bind(apHome.heightProperty());
        lineChart7Dias.prefHeightProperty().bind(apHome.heightProperty());
        
        iniciarComponentes();
        
    }
    
    private void iniciarComponentes(){
        txtNomeEnfermeiro.setText(Atual.getUsuario().getNome());
        //txtDataHora.setText(Util.getDateTime());
        new ThreadDataHora(txtDataHora);
        
        // ConsultasDAO
        ConsultasDAO consultasDAO = new ConsultasDAO();
        int totalConsultas = consultasDAO.listarConsultasUsuario(Atual.getUsuario()).size();
        
        txtTotalConsultasEnf.setText(
            "Consultas realizadas: " 
            + totalConsultas
        );
        
    }
    
}
