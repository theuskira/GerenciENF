package gerencienf;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import util.ThreadConexao;
import util.ThreadLogarUsuario;

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
    
    private final KeyCombination ENTER = (KeyCombination) new KeyCodeCombination(KeyCode.ENTER);
    @FXML
    private Font x1;
    @FXML
    private Button btnEntrar;
    
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
                
                handleButtonAction(null);
                
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

            ThreadLogarUsuario t = new ThreadLogarUsuario(txtUsuario, txtSenha, imgStatus, txtStatus);
            t.run();
            
        }
        
    }
    
}
