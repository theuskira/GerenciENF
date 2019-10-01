package view;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.bean.Clientes;
import model.bean.Consulta;
import model.bean.Usuario;
import model.dao.ClientesDAO;
import model.dao.ConsultasDAO;
import util.Atual;
import util.ThreadDataHora;
import util.ThreadDialog;
import util.Util;

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
    private TableView<Clientes> tabelaConsultasDia;
    @FXML
    private TableColumn<Clientes, String> colunaConsultasDiaNome;
    @FXML
    private TableColumn<Clientes, String> colunaConsultasDiaCPF;
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
    @FXML
    private Insets x4;

    /**
     * Initializes the controller class.
     */
    private Usuario usuario;
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
        
        usuario = new Usuario(Atual.getUsuario());
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
        
        colunaConsultasDiaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaConsultasDiaCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        
        tabelaConsultasDia.setItems(consultasDoDia());
        
        tabelaConsultasDia.setRowFactory( tv -> {
            TableRow<Clientes> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    
                    Clientes rowData = row.getItem();
                    
                    System.out.println("Consulta Selecionada: " + Atual.getListaConsultaDia().get(row.getIndex()).getId());
                    Atual.setConsulta(Atual.getListaConsultaDia().get(row.getIndex()));
                    System.out.println("Cliente Selecionado: " + rowData.getNome());
                    Atual.setCliente(rowData);
                    
                    iniciarConsulta();
                    
                }
            });
            return row ;
        });
        
        tabelaConsultasDia.setItems(consultasDoDia());
        txtTotalConsultas.setText(
            "Consultas do dia: "
                + Atual.getListaClientesDia().size()
        );
        
    }
    
    private ObservableList<Clientes> consultasDoDia() {
            
        int anoAtual = Integer.parseInt(getDateTime().substring(0, 4));
        int mesAtual = Integer.parseInt(getDateTime().substring(5, 7));
        int diaAtual = Integer.parseInt(getDateTime().substring(8, 10));
        
        Atual.getListaClientesDia().clear();
        Atual.getListaConsultaDia().clear();
        
        ConsultasDAO c = new ConsultasDAO();
        
        for(Consulta con : c.listarConsultasUsuario(usuario)){

            int anoValidade = Integer.parseInt(con.getData().substring(0, 4));
            int mesValidade = Integer.parseInt(con.getData().substring(5, 7));
            int diaValidade = Integer.parseInt(con.getData().substring(8, 10));

            if(anoValidade == anoAtual){

                if(mesValidade == mesAtual){

                    if(diaValidade == diaAtual){

                        Atual.setListaClientesDia(new ClientesDAO().localizar(con.getClienteId()));
                        Atual.setListaConsultaDia(con);

                    }

                }

            }

        }
                
        return FXCollections.observableArrayList(
                
                Atual.getListaClientesDia()
                
        );
    }
    
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    private void iniciarConsulta(){
        
        Atual.setVerConsulta(true);
        
        try {
            FXMLLoader fxmlLoaderPrincipal = new FXMLLoader(getClass().getResource("/gerencienf/FXML_Consulta.fxml"));
            Parent r = (Parent) fxmlLoaderPrincipal.load();
            Stage stage = new Stage();
            
            stage.getIcons().add(new Image(Util.LOGO_2));
            stage.setTitle(
                Util.TITULO + " - " 
                + Atual.getUsuario().getNome()
                + " | Consulta de: "
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
    
}
