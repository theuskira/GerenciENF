package util;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.bean.Usuario;
import model.dao.UsuarioDAO;

/**
 *
 * @author Matheus - DELL
 */
public class ThreadLogarUsuario implements Runnable{
    
    private TextField txtUsuario;
    private PasswordField txtSenha;
    private ImageView imgStatus;
    private Label txtStatus;
    
    private boolean logar = false;
    
    public ThreadLogarUsuario(TextField txtUsuario, PasswordField txtSenha, ImageView imgStatus, Label txtStatus){
        this.txtUsuario = txtUsuario;
        this.txtSenha = txtSenha;
        this.imgStatus = imgStatus;
        this.txtStatus = txtStatus;
    }

    @Override
    public void run() {
        
        System.out.println("Executando a Thread Login!");
        
        try {
            
            imgStatus.setImage(new Image("/img/loading.gif"));
            
            UsuarioDAO u = new UsuarioDAO();
        
            for(Usuario user : u.listarLogin()){

                if(user.getUsuario().equals(txtUsuario.getText()) && user.getSenha().equals(txtSenha.getText())){
                    
                    Atual.setUsuario(user);
                    this.logar = true;

                }

            }
            
        } catch (Exception e) {
            System.err.println("Erro na Thread Logar Usuario: " + e.getMessage());
        }finally{
            System.out.println("Thread Logar Usuario Finalizou!");
            if(logar){
                //new ThreadIniciar(Util.TITULO + " - " + Atual.getUsuario().getNome(), "/gerencienf/FXML_Principal.fxml", true, imgStatus).run();
                //iniciar();
            }else{
                txtStatus.setText("Usuario e/ou Senha Inválido(s)!");
                txtStatus.setTextFill(Color.web("#FF0000")); // RED
            }
            new ThreadConexao(imgStatus);
        }
        
    }
    
}
