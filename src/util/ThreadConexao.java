package util;

import connection.Conexao;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Matheus - DELL
 */
public class ThreadConexao extends Thread{
    
    private ImageView imgStatus;
    private Button entrar;
    
    public ThreadConexao(ImageView imgStatus) {
        this.imgStatus = imgStatus;
        start();
    }
    
    public ThreadConexao(ImageView imgStatus, Button entrar) {
        this.imgStatus = imgStatus;
        this.entrar = entrar;
        start();
    }

    @Override
    public void run() {
        
        System.out.println("Executando a Thread Conexao!");
        if(entrar != null){
            entrar.setDisable(true);
        }
        
        try {
            
            imgStatus.setImage(new Image("/img/loading.gif"));
            
            if(Conexao.getConnection() != null){
                
                imgStatus.setImage(new Image("/img/on.png"));
                
                System.out.println("Conectado ao Banco!");
                
                if(entrar != null){
                    entrar.setDisable(false);
                }
                
            }else{
                
                imgStatus.setImage(new Image("/img/off.png"));
                
                System.out.println("Sem conexao ao Banco!");
                
            }
            
        } catch (Exception e) {
            
            System.err.println("Erro: " + e.getMessage());
            
        }
        
    }
    
}
