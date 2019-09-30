/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import model.bean.Clientes;
import model.dao.ClientesDAO;
import util.Atual;
import util.ThreadConexao;
import util.ThreadDataHora;
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
        
        iniciarComponentes(null);
        
    }
    
    // INICIAR COMPONENTES
    @FXML
    private void iniciarComponentes(MouseEvent event){
        tabelaClientes.getItems().clear();
        tabelaClientes.setItems(listaClientes());
        txtClientes.setText("Clientes: " + listaClientes().size());
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
    
    // CLICK TABELA
    @FXML
    private void onMouseClickedTable(MouseEvent event) {

        try {
            
            Atual.setCliente(listaClientes().get(tabelaClientes.getSelectionModel().getFocusedIndex()));
            
            AnchorPane ap = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FXML_Cliente.fxml"));
            
            iniciarAnchor(ap);
            
        } catch (IOException e) {
            
            new ThreadDialog("Erro: " + e.getMessage());
            System.err.println("Erro: " + e.getMessage());
            
        }
        
    }
    
}
