package gerencienf;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import util.ThreadConexao;
import util.ThreadDialog;

/**
 * FXML Controller class
 *
 * @author Matheus - DELL
 */
public class FXML_PrincipalController implements Initializable {
    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private AnchorPane apLateral;
    @FXML
    private HBox hBoxInicio;
    @FXML
    private Insets x2;
    @FXML
    private Font x1;
    @FXML
    private HBox hBoxClientes;
    @FXML
    private HBox hBoxConsultas;
    @FXML
    private AnchorPane apCorpo;
    @FXML
    private ImageView imgStatus;
    @FXML
    private ImageView imgInicio;
    @FXML
    private Label txtInicio;
    @FXML
    private ImageView imgClientes;
    @FXML
    private Label txtClientes;
    @FXML
    private ImageView imgConsultas;
    @FXML
    private Label txtConsultas;
    @FXML
    private HBox hBoxRetornos;
    @FXML
    private ImageView imgRetornos;
    @FXML
    private Label txtRetornos;
    

    /**
     * Initializes the controller class.
     */
    
    
    private AnchorPane apInicio;
    private AnchorPane apClientes;
    private AnchorPane apConsultas;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("## FXML PRINCIPAL INICIOU ##");
        
        try {
            
            apInicio = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FXML_Home.fxml"));
            
            Thread t = new Thread(){

                @Override
                public void run() {
                    try {
                        
                        apClientes = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FXML_Clientes.fxml"));
                        apConsultas = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FXML_Consultas.fxml"));
                        
                    } catch (Exception e) {
                        
                        System.err.println("Erro AnchorPane: " + e.getMessage());
                        
                    }
                    
                }
            };
            
            t.start();
            
        } catch (Exception e) {
            System.err.println("Erro AnchorPane: " + e.getMessage());
        }
        
        new ThreadConexao(imgStatus);
        
        imgStatus.setOnMouseClicked(k -> {
            
            new ThreadConexao(imgStatus);
            
        });
        
        iniciarHBox(0);
        
    }
    
    private void iniciarAnchor(AnchorPane apFilho){
        
        try {

            apCorpo.setTopAnchor(apFilho, 0.0);
            apCorpo.setBottomAnchor(apFilho, 0.0);
            apCorpo.setLeftAnchor(apFilho, 0.0);
            apCorpo.setRightAnchor(apFilho, 0.0);
            apCorpo.getChildren().add(apFilho);
            
        } catch (Exception ex) {
            
            new ThreadDialog("Erro ao iniciar AnchorPane: " + ex.getMessage());
            System.out.println("Erro ao iniciar AnchorPane: " + ex.getMessage());
            
        }
        
    }
    
    private void iniciarHBox(int index){
        try {
            switch(index){
                case 0: // INICIO
                    hBoxInicio.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtInicio.setTextFill(Color.web("RED")); // RED
                    imgInicio.setImage(new Image("/img/home_red.png"));
                    removerHBox(index);
                    apCorpo.getChildren().clear();
                    iniciarAnchor(apInicio);
                    break;
                case 1: // CLIENTES
                    hBoxClientes.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtClientes.setTextFill(Color.web("RED")); // RED
                    imgClientes.setImage(new Image("/img/customer_red.png"));
                    removerHBox(index);
                    apCorpo.getChildren().clear();
                    iniciarAnchor(apClientes);
                    break;
                case 2: // CONSULTAS
                    hBoxConsultas.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtConsultas.setTextFill(Color.web("RED")); // RED
                    imgConsultas.setImage(new Image("/img/card_red.png"));
                    removerHBox(index);
                    apCorpo.getChildren().clear();
                    iniciarAnchor(apConsultas);
                    break;
                case 3: // Retornos
                    hBoxRetornos.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtRetornos.setTextFill(Color.web("RED")); // RED
                    imgRetornos.setImage(new Image("/img/return_red.png"));
                    removerHBox(index);
                    break;
                default:
                    hBoxInicio.setStyle("-fx-background-color: WHITE; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtInicio.setTextFill(Color.web("RED")); // RED
                    imgInicio.setImage(new Image("/img/home_red.png"));
                    removerHBox(index);
                    break;
            }
        } catch (Exception e) {
            new ThreadDialog("Erro ao iniciar HBox: " + e.getMessage());
            System.out.println("Erro ao iniciar HBox: " + e.getMessage());
        }
        
    }
    
    private void removerHBox(int index){
        try {
            switch(index){
                case 0: // INICIO
                    // REMOVER CLIENTES
                    hBoxClientes.setStyle("-fx-background-color: RED; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtClientes.setTextFill(Color.web("WHITE")); // WHITE
                    imgClientes.setImage(new Image("/img/customer.png"));
                    // REMOVER CONSULTAS
                    hBoxConsultas.setStyle("-fx-background-color: RED; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtConsultas.setTextFill(Color.web("WHITE")); // WHITE
                    imgConsultas.setImage(new Image("/img/card.png"));
                    // REMOVER RETORNOS
                    hBoxRetornos.setStyle("-fx-background-color: RED; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtRetornos.setTextFill(Color.web("WHITE")); // WHITE
                    imgRetornos.setImage(new Image("/img/return.png"));
                    break;
                case 1: // CLIENTES
                    // REMOVER INICIO
                    hBoxInicio.setStyle("-fx-background-color: RED; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtInicio.setTextFill(Color.web("WHITE")); // WHITE
                    imgInicio.setImage(new Image("/img/home.png"));
                    // REMOVER CONSULTAS
                    hBoxConsultas.setStyle("-fx-background-color: RED; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtConsultas.setTextFill(Color.web("WHITE")); // WHITE
                    imgConsultas.setImage(new Image("/img/card.png"));
                    // REMOVER RETORNOS
                    hBoxRetornos.setStyle("-fx-background-color: RED; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtRetornos.setTextFill(Color.web("WHITE")); // WHITE
                    imgRetornos.setImage(new Image("/img/return.png"));
                    break;
                case 2: // CONSULTAS
                    // REMOVER INICIO
                    hBoxInicio.setStyle("-fx-background-color: RED; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtInicio.setTextFill(Color.web("WHITE")); // WHITE
                    imgInicio.setImage(new Image("/img/home.png"));
                    // REMOVER CLIENTES
                    hBoxClientes.setStyle("-fx-background-color: RED; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtClientes.setTextFill(Color.web("WHITE")); // WHITE
                    imgClientes.setImage(new Image("/img/customer.png"));
                    // REMOVER RETORNOS
                    hBoxRetornos.setStyle("-fx-background-color: RED; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtRetornos.setTextFill(Color.web("WHITE")); // WHITE
                    imgRetornos.setImage(new Image("/img/return.png"));
                    break;
                case 3: // RETORNOS
                    // REMOVER INICIO
                    hBoxInicio.setStyle("-fx-background-color: RED; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtInicio.setTextFill(Color.web("WHITE")); // WHITE
                    imgInicio.setImage(new Image("/img/home.png"));
                    // REMOVER CLIENTES
                    hBoxClientes.setStyle("-fx-background-color: RED; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtClientes.setTextFill(Color.web("WHITE")); // WHITE
                    imgClientes.setImage(new Image("/img/customer.png"));
                    // REMOVER CONSULTAS
                    hBoxConsultas.setStyle("-fx-background-color: RED; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtConsultas.setTextFill(Color.web("WHITE")); // WHITE
                    imgConsultas.setImage(new Image("/img/card.png"));
                    break;
                default:
                    // REMOVER CLIENTES
                    hBoxClientes.setStyle("-fx-background-color: RED; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtClientes.setTextFill(Color.web("WHITE")); // WHITE
                    imgClientes.setImage(new Image("/img/customer.png"));
                    // REMOVER CONSULTAS
                    hBoxConsultas.setStyle("-fx-background-color: RED; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtConsultas.setTextFill(Color.web("WHITE")); // WHITE
                    imgConsultas.setImage(new Image("/img/card.png"));
                    // REMOVER RETORNOS
                    hBoxRetornos.setStyle("-fx-background-color: RED; -fx-background-radius: 5; -fx-cursor: hand;");
                    txtRetornos.setTextFill(Color.web("WHITE")); // WHITE
                    imgRetornos.setImage(new Image("/img/return.png"));
                    break;
            }
        } catch (Exception e) {
            new ThreadDialog("Erro ao remover HBox: " + e.getMessage());
            System.out.println("Erro ao remover HBox: " + e.getMessage());
        }
    }

    @FXML
    private void iniciarHBoxInicio(MouseEvent event) {
        iniciarHBox(0);
        System.out.println("Clicou em Inicio");
    }
    
    @FXML
    private void iniciarHBoxClientes(MouseEvent event) {
        iniciarHBox(1);
        System.out.println("Clicou em Clientes");
    }
    
    @FXML
    private void iniciarHBoxConsultas(MouseEvent event) {
        iniciarHBox(2);
        System.out.println("Clicou em Consultas");
    }

    @FXML
    private void iniciarHBoxRetornos(MouseEvent event) {
        iniciarHBox(3);
        System.out.println("Clicou em Retornos");
    }
    
}
