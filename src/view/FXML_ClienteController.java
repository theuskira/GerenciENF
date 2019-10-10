package view;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.bean.Clientes;
import model.bean.Consulta;
import model.bean.ConsultaTable;
import model.dao.ConsultasDAO;
import model.dao.EvolucaoDAO;
import model.dao.RetornoDAO;
import model.dao.SaeDAO;
import model.dao.SolicitacaoDAO;
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
    private TableView<ConsultaTable> tabelaSae;
    private Clientes clienteSelecionado;
    @FXML
    private Font x3;
    @FXML
    private TableColumn<ConsultaTable, String> colunaSae;
    @FXML
    private TableColumn<ConsultaTable, String> colunaSolicitacao;
    @FXML
    private TableColumn<ConsultaTable, String> colunaEvolucao;
    @FXML
    private TableColumn<ConsultaTable, String> colunaRetornoTipo;
    @FXML
    private TableColumn<ConsultaTable, String> colunaRetornoMotivo;
    @FXML
    private TableColumn<ConsultaTable, String> colunaRetornoData;
    @FXML
    private TableColumn<ConsultaTable, String> colunaData;
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
    private List<ConsultaTable> listaConsulta = new ArrayList<>();
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
        
        colunaSae.setCellValueFactory(new PropertyValueFactory<>("sae"));
        colunaSolicitacao.setCellValueFactory(new PropertyValueFactory<>("solicitacao"));
        colunaEvolucao.setCellValueFactory(new PropertyValueFactory<>("evolucao"));
        colunaRetornoTipo.setCellValueFactory(new PropertyValueFactory<>("tipoRetorno"));
        colunaRetornoMotivo.setCellValueFactory(new PropertyValueFactory<>("motivoRetorno"));
        colunaRetornoData.setCellValueFactory(new PropertyValueFactory<>("dataRetorno"));
        colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
        
        Thread t = new Thread(){

            @Override
            public void run() {
                tabelaSae.setItems(consultasCliente());
            }
            
        };
        
        t.start();
        
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
        
        if(totalConsultas > 0){
            txtRetorno.setText(
                "Retorno: " 
                + Util.ultimaConsultaCliente(clienteSelecionado)
            );
        }
        
        
        
        
        tabelaSae.setRowFactory( tv -> {
            TableRow<ConsultaTable> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    
                    ConsultaTable rowData = row.getItem();
                    
                    System.out.println("Consulta Selecionada: " + listaConsulta.get(row.getIndex()).getConsulta().getId());
                    Atual.setConsulta(listaConsulta.get(row.getIndex()).getConsulta());
                    System.out.println("Cliente Selecionado: " + clienteSelecionado.getNome());
                    Atual.setCliente(clienteSelecionado);
                    
                    iniciarConsulta();
                    
                }
            });
            return row ;
        });
        
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
    
    private ObservableList<ConsultaTable> consultasCliente() {
            
        listaConsulta.clear();
        
        List<ConsultaTable> consulta1 = new ArrayList<>();
        
        for(Consulta con : new ConsultasDAO().listarConsultasCliente(clienteSelecionado)){
            
            ConsultaTable consultaTable = new ConsultaTable();
            
            consultaTable.setConsulta(con);
            consultaTable.setData(Util.formatarData(con.getData()));
            
            int sae1 = new SaeDAO().saePorConsulta(con).getId();
            
            if(sae1 > 0){

                consultaTable.setSae("SAE");
            
            }
            
            String soli1 = new SolicitacaoDAO().solicitacaoPorConsulta(con).getSolicitacao();
            
            if(soli1 != null){

                consultaTable.setSolicitacao(soli1);
            
            }
            
            String evo1 = new EvolucaoDAO().evolucaoPorConsulta(con).getEvolucao();
            
            if(evo1 != null){

                consultaTable.setEvolucao(evo1);
            
            }
            
            String retTipo = new RetornoDAO().retornoPorConsulta(con).getTipo();
            
            if(retTipo != null){

                consultaTable.setTipoRetorno(retTipo);
            
            }
            
            String retMotivo = new RetornoDAO().retornoPorConsulta(con).getMotivo();
            
            if(retMotivo != null){

                consultaTable.setMotivoRetorno(retMotivo);
            
            }
            
            String retData = new RetornoDAO().retornoPorConsulta(con).getDataRetorno();
            
            if(retData != null){

                consultaTable.setDataRetorno(retData);
            
            }
            
            consulta1.add(consultaTable);
            
        }
        
        for ( int i =  consulta1.size() - 1 ; i >= 0 ; i-- ) {

            listaConsulta.add(consulta1.get(i));

        }
                
        return FXCollections.observableArrayList(
                
                listaConsulta
                
        );
        
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
