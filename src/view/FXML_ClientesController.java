package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.bean.Clientes;
import model.dao.ClientesDAO;
import util.Atual;
import util.ThreadDialog;

/**
 * FXML Controller class
 *
 * @author Matheus - DELL
 */
public class FXML_ClientesController implements Initializable {
    @FXML
    private AnchorPane apClientes;
    @FXML
    private Insets x1;
    @FXML
    private Label txtClientes;
    @FXML
    private TextField txtPesquisarClientes;
    @FXML
    private Button btnPesquisarClientes;
    @FXML
    private AnchorPane apCorpoClientes;
    @FXML
    private TableView<Clientes> tabelaClientes;
    @FXML
    private ImageView imgAddClientes;
    @FXML
    private TableColumn<Clientes, String> colunaNome;
    @FXML
    private TableColumn<Clientes, String> colunaCpf;
    @FXML
    private ImageView imgAtualizar;

    /**
     * Initializes the controller class.
     */
    private final KeyCombination ENTER = (KeyCombination) new KeyCodeCombination(KeyCode.ENTER);
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("## FXML CLIENTES INICIOU ##");
 
        try {
            iniciarAnchor((AnchorPane) FXMLLoader.load(getClass().getResource("/view/FXML_AdicionarCliente.fxml")));
        } catch (IOException e) {
            new ThreadDialog("Erro: " + e.getMessage());
            System.err.println("Erro: " + e.getMessage());
        }
        
        imgAddClientes.setOnMouseClicked(k -> {
            
            try {
                Atual.setEditarCliente(false);
                iniciarAnchor((AnchorPane) FXMLLoader.load(getClass().getResource("/view/FXML_AdicionarCliente.fxml")));
            } catch (IOException e) {
                new ThreadDialog("Erro: " + e.getMessage());
                System.err.println("Erro: " + e.getMessage());
            }
            
        });
        
        // SETAR ALTURA PARA A MESMA DO ANCHORPANE PRINCIPAL
        tabelaClientes.prefHeightProperty().bind(apClientes.heightProperty());
        
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        
        Thread t = new Thread(){

            @Override
            public void run() {

                tabelaClientes.getItems().clear();
                tabelaClientes.setItems(listaClientes());
                
            }

        };

        t.start();
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtClientes.setText("Clientes: " + Atual.getListaClientes().size());
            }
        });
        
        btnPesquisarClientes.setOnMouseClicked(k -> {
            
            pesquisarClientes();

        });
        
        imgAtualizar.setOnMouseClicked(k -> {
            
            pesquisarClientes();

        });
        
        txtPesquisarClientes.setOnKeyPressed(k -> {
            
            if(ENTER.match(k)){
                
                pesquisarClientes();
                
            }
            
        });
        
    }
    
    // PESQUISAR COMPONENTES
    private void pesquisarClientes(){
        
        imgAtualizar.setDisable(true);
        
        if(txtPesquisarClientes.getText().isEmpty()){
                    
            tabelaClientes.getItems().clear();
            tabelaClientes.setItems(listaClientes());

            imgAtualizar.setDisable(false);
            
        }else{
                    
            tabelaClientes.getItems().clear();
            tabelaClientes.setItems(listaClientesPesquisa(txtPesquisarClientes.getText()));

            imgAtualizar.setDisable(false);
            
        }
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtClientes.setText("Clientes: " + Atual.getListaClientes().size());
            }
        });
        
    }
    
    private void iniciarAnchor(AnchorPane apFilho){
        
        try {

            apCorpoClientes.getChildren().clear();
            
            apCorpoClientes.setTopAnchor(apFilho, 0.0);
            apCorpoClientes.setBottomAnchor(apFilho, 0.0);
            apCorpoClientes.setLeftAnchor(apFilho, 0.0);
            apCorpoClientes.setRightAnchor(apFilho, 0.0);
            apCorpoClientes.getChildren().add(apFilho);
            
        } catch (Exception ex) {
            
            new ThreadDialog("Erro ao iniciar AnchorPane: " + ex.getMessage());
            System.out.println("Erro ao iniciar AnchorPane: " + ex.getMessage());
            
        }
        
    }
    
    // LISTA DE CLIENTES
    private ObservableList<Clientes> listaClientes(){
        Atual.getListaClientes().clear();
        
        ClientesDAO c = new ClientesDAO();
        
        for(Clientes cli : c.listarClientes()){
            Atual.setListaClientes(cli);
        }
        
        return FXCollections.observableArrayList(
                Atual.getListaClientes()
        );
        
    }
    
    // LISTA DE CLIENTES PESQUISA
    private ObservableList<Clientes> listaClientesPesquisa(String pesquisa){
        Atual.getListaClientes().clear();
        
        ClientesDAO c = new ClientesDAO();
        
        for(Clientes cli : c.pesquisar(pesquisa)){
            Atual.setListaClientes(cli);
        }
        
        return FXCollections.observableArrayList(
                Atual.getListaClientes()
        );
        
    }
    
    // CLICK TABELA
    @FXML
    private void onMouseClickedTable(MouseEvent event) {

        try {
            
            if(txtPesquisarClientes.getText().isEmpty()){
                
            }
            
            Atual.setCliente(Atual.getListaClientes().get(tabelaClientes.getSelectionModel().getFocusedIndex()));
            
            AnchorPane ap = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FXML_Cliente.fxml"));
            
            iniciarAnchor(ap);
            
        } catch (IOException e) {
            
            new ThreadDialog("Erro: " + e.getMessage());
            System.err.println("Erro: " + e.getMessage());
            
        }
        
    }
    
}
