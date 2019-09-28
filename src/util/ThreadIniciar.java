package util;

import java.awt.Component;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Matheus - DELL
 */
public class ThreadIniciar implements Runnable{
    
    private static ImageView imgStatus;
    private String caminho, titulo;
    private boolean maximized;
    
    public ThreadIniciar(String titulo, String caminho, boolean maximized){
        this.titulo = titulo;
        this.caminho = caminho;
        this.maximized = maximized;
    }
    
    public ThreadIniciar(String titulo, String caminho, boolean maximized, ImageView imgStatus){
        this.titulo = titulo;
        this.caminho = caminho;
        this.maximized = maximized;
        this.imgStatus = imgStatus;
    }

    @Override
    public void run() {
        
        if(imgStatus != null){
            imgStatus.setImage(new Image("/img/loading.gif"));
        }
        
        try {
            FXMLLoader fxmlLoaderPrincipal = new FXMLLoader(getClass().getResource(caminho));
            Parent r = (Parent) fxmlLoaderPrincipal.load();
            Stage stage = new Stage();
            
            stage.getIcons().add(new Image(Util.LOGO_2));
            stage.setTitle(titulo);
            stage.setScene(new Scene(r));
            stage.setMaximized(maximized);
            stage.setMinHeight(720);
            stage.setMinWidth(1280);
            stage.show();
            
            Stage stageAtual = (Stage) imgStatus.getScene().getWindow();
            stageAtual.close();
            
        } catch (IOException e) {
            
            new ThreadDialog("Erro: " + e.getMessage());
            
        }
        
    }
    
}
