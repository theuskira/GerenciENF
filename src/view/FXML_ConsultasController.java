package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.bean.Clientes;
import model.bean.Consulta;
import model.bean.ConsultaTable;
import model.dao.ClientesDAO;
import model.dao.ConsultasDAO;
import model.dao.EvolucaoDAO;
import model.dao.RetornoDAO;
import model.dao.SaeDAO;
import model.dao.SolicitacaoDAO;
import model.dao.UsuarioDAO;
import util.Atual;
import util.ThreadDialog;
import util.Util;

/**
 * FXML Controller class
 *
 * @author Matheus - DELL
 */
public class FXML_ConsultasController implements Initializable {
    @FXML
    private Font x1;
    @FXML
    private TextField txtPesquisaCliente;
    @FXML
    private Insets x2;
    @FXML
    private Font x3;
    @FXML
    private TextField txtPesquisaData;
    @FXML
    private Label txtStatus;
    @FXML
    private TableView<ConsultaTable> tabelaConsulta;
    @FXML
    private TableColumn<ConsultaTable, String> colunaData;
    @FXML
    private TableColumn<ConsultaTable, String> colunaSae;
    @FXML
    private TableColumn<ConsultaTable, String> colunaSolicitacao;
    @FXML
    private TableColumn<ConsultaTable, String> colunaNomeCliente;
    @FXML
    private TableColumn<ConsultaTable, String> colunaNomeEnfermeiro;
    @FXML
    private TableColumn<ConsultaTable, String> colunaEvolucao;
    @FXML
    private TableColumn<ConsultaTable, String> colunaRetornoData;
    @FXML
    private Label txtNomeEnfermeiro;
    @FXML
    private Label txtNomeCliente;
    @FXML
    private Label txtData;
    @FXML
    private Label txtSexo;
    @FXML
    private Label txtAltura;
    @FXML
    private Label txtDataConsulta;

    /**
     * Initializes the controller class.
     */
    private List<ConsultaTable> listaConsulta = new ArrayList<>();
    @FXML
    private HBox hBoxLocalizar;
    @FXML
    private HBox hBoxTodasConsultas;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        iniciarComponentes();
        
    }
    
    private void iniciarComponentes(){
        
        colunaSae.setCellValueFactory(new PropertyValueFactory<>("sae"));
        colunaSolicitacao.setCellValueFactory(new PropertyValueFactory<>("solicitacao"));
        colunaEvolucao.setCellValueFactory(new PropertyValueFactory<>("evolucao"));
        colunaNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colunaNomeEnfermeiro.setCellValueFactory(new PropertyValueFactory<>("nomeEnfermeiro"));
        colunaRetornoData.setCellValueFactory(new PropertyValueFactory<>("dataRetorno"));
        colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
        
        atualizar(null);
        
        tabelaConsulta.setRowFactory( tv -> {
            TableRow<ConsultaTable> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    
                    ConsultaTable rowData = row.getItem();
                    
                    System.out.println("Consulta Selecionada: " + listaConsulta.get(row.getIndex()).getConsulta().getId());
                    Atual.setConsulta(listaConsulta.get(row.getIndex()).getConsulta());
                    Clientes clienteSelecionado = new ClientesDAO().localizar(Atual.getConsulta().getClienteId());
                    System.out.println("Cliente Selecionado: " + clienteSelecionado.getNome());
                    Atual.setCliente(clienteSelecionado);
                    
                    iniciarConsulta();
                    
                }
            });
            return row ;
        });
        
    }
    
    @FXML
    private void atualizar(MouseEvent event){
        Thread t = new Thread(){

            @Override
            public void run() {
                tabelaConsulta.setItems(consultasCliente());
            }
            
        };
        
        t.start();
    }
    
    @FXML
    private void localizar(MouseEvent event){
        
        // VERIFICA OS CAMPOS
        if(txtPesquisaCliente.getText().isEmpty() && txtPesquisaData.getText().isEmpty()){
           
            new ThreadDialog("Digite algo!");
            System.out.println("Digite algo!");
            txtPesquisaCliente.requestFocus();
            
        }else{
            
            // PESQUISA CLIENTE SEM DATA
            if(!txtPesquisaCliente.getText().isEmpty() && txtPesquisaData.getText().isEmpty()){
                
                Thread t = new Thread(){

                    @Override
                    public void run() {
                        tabelaConsulta.setItems(pesquisarConsultas(txtPesquisaCliente.getText()));
                    }

                };

                t.start();
                
            }else if(!txtPesquisaData.getText().isEmpty() && !txtPesquisaCliente.getText().isEmpty()){
                
                Thread t = new Thread(){

                    @Override
                    public void run() {
                        tabelaConsulta.setItems(pesquisarConsultasData(txtPesquisaCliente.getText(), txtPesquisaData.getText()));
                    }

                };

                t.start();
                
            }else if(!txtPesquisaData.getText().isEmpty() && txtPesquisaCliente.getText().isEmpty()){
                
                Thread t = new Thread(){

                    @Override
                    public void run() {
                        tabelaConsulta.setItems(pesquisarData(txtPesquisaData.getText()));
                    }

                };

                t.start();
                
            }
            
        }
        
    }
    
    private ObservableList<ConsultaTable> consultasCliente() {
            
        listaConsulta.clear();
        
        List<ConsultaTable> consulta1 = new ArrayList<>();
        
        for(Consulta con : new ConsultasDAO().listarConsultas()){
            
            ConsultaTable consultaTable = new ConsultaTable();
            
            consultaTable.setConsulta(con);
            consultaTable.setData(Util.formatarData(con.getData()));
            
            String nomeCliente = new ClientesDAO().localizar(con.getClienteId()).getNome();
            consultaTable.setNomeCliente(nomeCliente);
            String nomeEnfermeiro = new UsuarioDAO().localizar(con.getUsuario()).getNome();
            consultaTable.setNomeEnfermeiro(nomeEnfermeiro);
            
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
    
    private ObservableList<ConsultaTable> pesquisarConsultas(String pesquisa) {
            
        listaConsulta.clear();
        
        List<ConsultaTable> consulta1 = new ArrayList<>();
        
        for(Consulta con : new ConsultasDAO().pesquisar(pesquisa)){
            
            ConsultaTable consultaTable = new ConsultaTable();
            
            consultaTable.setConsulta(con);
            consultaTable.setData(Util.formatarData(con.getData()));
            
            String nomeCliente = new ClientesDAO().localizar(con.getClienteId()).getNome();
            consultaTable.setNomeCliente(nomeCliente);
            String nomeEnfermeiro = new UsuarioDAO().localizar(con.getUsuario()).getNome();
            consultaTable.setNomeEnfermeiro(nomeEnfermeiro);
            
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
    
    private ObservableList<ConsultaTable> pesquisarConsultasData(String pesquisa, String data) {
            
        listaConsulta.clear();
        
        List<ConsultaTable> consulta1 = new ArrayList<>();
        
        for(Consulta con : new ConsultasDAO().pesquisarPorData(pesquisa, data)){
            
            ConsultaTable consultaTable = new ConsultaTable();
            
            consultaTable.setConsulta(con);
            consultaTable.setData(Util.formatarData(con.getData()));
            
            String nomeCliente = new ClientesDAO().localizar(con.getClienteId()).getNome();
            consultaTable.setNomeCliente(nomeCliente);
            String nomeEnfermeiro = new UsuarioDAO().localizar(con.getUsuario()).getNome();
            consultaTable.setNomeEnfermeiro(nomeEnfermeiro);
            
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
    
    private ObservableList<ConsultaTable> pesquisarData(String data) {
            
        listaConsulta.clear();
        
        List<ConsultaTable> consulta1 = new ArrayList<>();
        
        for(Consulta con : new ConsultasDAO().pesquisarData(data)){
            
            ConsultaTable consultaTable = new ConsultaTable();
            
            consultaTable.setConsulta(con);
            consultaTable.setData(Util.formatarData(con.getData()));
            
            String nomeCliente = new ClientesDAO().localizar(con.getClienteId()).getNome();
            consultaTable.setNomeCliente(nomeCliente);
            String nomeEnfermeiro = new UsuarioDAO().localizar(con.getUsuario()).getNome();
            consultaTable.setNomeEnfermeiro(nomeEnfermeiro);
            
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
    
    // CLICK TABELA
    @FXML
    private void onMouseClickedTable(MouseEvent event) {

        try {
            
            String dataConsulta = listaConsulta.get(tabelaConsulta.getSelectionModel().getFocusedIndex()).getConsulta().getData();
            String usuario = listaConsulta.get(tabelaConsulta.getSelectionModel().getFocusedIndex()).getConsulta().getUsuario();
            String nomeEnf = new UsuarioDAO().localizar(usuario).getNome();
            int cliente = listaConsulta.get(tabelaConsulta.getSelectionModel().getFocusedIndex()).getConsulta().getClienteId();
            String nomeCliente = new ClientesDAO().localizar(cliente).getNome();
            String dataCliente = new ClientesDAO().localizar(cliente).getDataNascimento();
            String sexoCliente = new ClientesDAO().localizar(cliente).getSexo();
            double alturaCliente = new ClientesDAO().localizar(cliente).getAltura();
            
            txtNomeEnfermeiro.setText(nomeEnf);
            txtNomeCliente.setText(nomeCliente);
            txtDataConsulta.setText(Util.formatarData(dataConsulta));
            
            if(dataCliente != null){
                
                txtData.setText(
                    dataCliente
                    + " ("
                    + Util.calculaIdade(dataCliente).toString()
                    + ") Anos"
                );
                
            }else{
                
                txtData.setText("Idade: ?");
                
            }
            
            if(sexoCliente != null){
                
                txtSexo.setText(
                        "Sexo: " 
                        + sexoCliente
                );
                
            }else{
                
                txtSexo.setText("Sexo: ?");
                
            }
            
            if(alturaCliente > 0){
                
                txtAltura.setText(
                    "Altura: " 
                    + alturaCliente 
                    + " m"
                );
                
            }else{
                
                txtAltura.setText("Altura: ?");
                
            }
            
        } catch (Exception e) {
            
            new ThreadDialog("Erro: " + e.getMessage());
            System.err.println("Erro: " + e.getMessage());
            
        }
        
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