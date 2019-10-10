package view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import model.bean.Consulta;
import model.bean.ConsultaTable;
import model.dao.ClientesDAO;
import model.dao.ConsultasDAO;
import model.dao.EvolucaoDAO;
import model.dao.RetornoDAO;
import model.dao.SaeDAO;
import model.dao.SolicitacaoDAO;
import model.dao.UsuarioDAO;
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
                
            }
            
            if(sexoCliente != null){
                
                txtSexo.setText("Sexo: " + sexoCliente);
                
            }
            
            if(alturaCliente > 0){
                
                txtAltura.setText(
                    "Altura: " 
                    + alturaCliente 
                    + " m"
                );
                
            }
            
        } catch (Exception e) {
            
            new ThreadDialog("Erro: " + e.getMessage());
            System.err.println("Erro: " + e.getMessage());
            
        }
        
    }
    
}
