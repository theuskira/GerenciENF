package gerencienf;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.bean.Usuario;
import model.dao.UsuarioDAO;
import util.Atual;
import util.ThreadConexao;
import util.ThreadDialog;
import util.Util;

/**
 *
 * @author Matheus - DELL
 */
public class FXML_LoginController implements Initializable {
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtSenha;
    @FXML
    private Label txtStatus;
    @FXML
    private ImageView imgStatus;
    @FXML
    private Font x1;
    @FXML
    private Button btnEntrar;
    
    private final KeyCombination ENTER = (KeyCombination) new KeyCodeCombination(KeyCode.ENTER);
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("## FXML LOGIN INICIOU ##");
        
        new ThreadConexao(imgStatus, btnEntrar);

        txtUsuario.setOnKeyPressed(k -> {
            
            if(ENTER.match(k)){
                
                txtSenha.requestFocus();
                
            }
            
        });
        
        txtSenha.setOnKeyPressed(k -> {
            
            if(ENTER.match(k)){
                
                if(!btnEntrar.isDisable()){
                    handleButtonAction(null);
                }
                
            }
            
        });
        
        imgStatus.setOnMouseClicked(k -> {
            
            new ThreadConexao(imgStatus, btnEntrar);
            
        });
        
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        if(txtUsuario.getText().equals("") || txtUsuario.getText() == null){
            
            txtStatus.setText("Digite um usuário!");
            txtStatus.setTextFill(Color.web("#FF0000")); // RED
            txtUsuario.requestFocus();
            
        }else if(txtSenha.getText().equals("") || txtSenha.getText() == null){
            
            txtStatus.setText("Digite uma senha!");
            txtStatus.setTextFill(Color.web("#FF0000")); // RED
            txtSenha.requestFocus();
            
        }else{

            try {
            
                imgStatus.setImage(new Image("/img/loading.gif"));

                UsuarioDAO u = new UsuarioDAO();

                for(Usuario user : u.listarLogin()){

                    if(user.getUsuario().equals(txtUsuario.getText()) && user.getSenha().equals(txtSenha.getText())){

                        Atual.setUsuario(user);
                        iniciarPrincipal();
                        //new ThreadDialog("Bem vindo(a): " + Atual.getUsuario().getNome());
                        
                    }

                }
                
                txtStatus.setText("Usuario e/ou Senha Inválido(s)!");
                txtStatus.setTextFill(Color.web("#FF0000")); // RED
                
                imgStatus.setImage(new Image("/img/on.png"));

            } catch (Exception e) {
                
                System.err.println("Erro na Thread Logar Usuario: " + e.getMessage());
                
            }finally{
                
                System.out.println("Thread Logar Usuario Finalizou!");
                
            }
            
        }
        
    }
    
    private void iniciarPrincipal(){
        
        try {
            
            FXMLLoader fxmlLoaderPrincipal = new FXMLLoader(getClass().getResource("/gerencienf/FXML_Principal.fxml"));
            Parent r = (Parent) fxmlLoaderPrincipal.load();
            Stage stage = new Stage();
            
            stage.getIcons().add(new Image(Util.LOGO_2));
            stage.setTitle(Util.TITULO + " - " + Atual.getUsuario().getNome());
            stage.setScene(new Scene(r));
            stage.setMaximized(true);
            stage.setMinHeight(720);
            stage.setMinWidth(1280);
            stage.show();
            
            GerenciENF.getStage().close();
            
        } catch (Exception e) {
            
            new ThreadDialog("Erro: " + e.getMessage());
            System.err.println("Erro: " + e.getMessage());
            
        }
        
    }
    
}
