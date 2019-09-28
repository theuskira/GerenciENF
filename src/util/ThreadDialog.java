package util;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.swing.JOptionPane;

/**
 *
 * @author Matheus - DELL
 */
public class ThreadDialog extends Thread{
    
    private String texto;
    
    public ThreadDialog(String texto){
        this.texto = texto;
        start();
    }

    @Override
    public void run() {
        System.out.println("Thread Dialog Iniciou!");
        try {
            JOptionPane.showMessageDialog(null, texto);
            System.out.println("Thread Dialog Imprimiu: " + texto);
        } catch (Exception e) {
            System.err.println("Erro na Thread Dialog: " + e.getMessage());
        }finally{
            System.out.println("Thread Dialog Finalizou!");
        }
    }
    
}
