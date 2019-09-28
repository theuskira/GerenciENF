package util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 *
 * @author Matheus - DELL
 */
public class ThreadDataHora extends Thread{
    
    private Label txtDataHora;
    private String second = "";
    private String minute = "";
    private String hour = "";
    
    public ThreadDataHora(Label txtDataHora){
        this.txtDataHora = txtDataHora;
        start();
    }

    @Override
    public void run() {
        try {
            
            Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->{

                this.second = String.valueOf(LocalDateTime.now().getSecond());
                if(second.length() == 1){
                    second = "0" + second;
                }

                this.minute = String.valueOf(LocalDateTime.now().getMinute());
                if(minute.length() == 1){
                    minute = "0" + minute;
                }

                this.hour = String.valueOf(LocalDateTime.now().getHour());
                if(hour.length() == 1){
                    hour = "0" + hour;
                }

                txtDataHora.setText(hour + ":" + minute + ":" + second + "\n" + Util.getDateTime());
            }),
                new KeyFrame(Duration.seconds(1))
            );
            clock.setCycleCount(Animation.INDEFINITE);
            clock.play();
        } catch (Exception ex) {
            System.out.println("Problema na atualização da data/hora");
            ex.printStackTrace();
        }
    }
    
}
