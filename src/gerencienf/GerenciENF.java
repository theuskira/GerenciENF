package gerencienf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.Util;

/**
 *
 * @author Matheus - DELL
 */
public class GerenciENF extends Application {
    private static Stage stage;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML_Login.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Util.LOGO_2));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(Util.TITULO);
        stage.show();
        setStage(stage);
    }
    
    public static Stage getStage(){
        return stage;    
    }
    
    public void setStage(Stage stage){
        GerenciENF.stage = stage;
    }
    
}
