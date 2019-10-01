/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.F1;
import static javafx.scene.input.KeyCode.F10;
import static javafx.scene.input.KeyCode.F11;
import static javafx.scene.input.KeyCode.F12;
import static javafx.scene.input.KeyCode.F2;
import static javafx.scene.input.KeyCode.F3;
import static javafx.scene.input.KeyCode.F4;
import static javafx.scene.input.KeyCode.F5;
import static javafx.scene.input.KeyCode.F6;
import static javafx.scene.input.KeyCode.F7;
import static javafx.scene.input.KeyCode.F8;
import static javafx.scene.input.KeyCode.F9;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Matheus - DELL
 */
public class Util {
    
    public static final String TITULO = "GerenciENF";
    public static final String LOGO = "/img/GerenciENF.png";
    public static final String LOGO_2 = "/img/GerenciENF 2.png";
    
    // RETORNA A DATA DO SISTEMA
    public static final String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    // RETORNA A DATA PARA O MODELO BR
    public static final String formatarData(String data) {
        String ano = data.substring(0, 4);
        String mes = data.substring(5, 7);
        String dia = data.substring(8, 10);
        return dia + "/" + mes + "/" + ano;
    }
    // RETORNA O IMC
    public static final String calculoIMC(double imc){
        if(imc < 16){
            return "Desnutrição Severa";
        }
        else if(imc >= 16 && imc <= 16.99){
            return "Desnutrição Moderada";
        }
        else if(imc >= 17 && imc <= 18.49){
            return "Desnutrição Leve";
        }
        else if(imc >= 18.5 && imc <= 24.99){
            return "Peso Normal!";
        }
        else if(imc >= 25 && imc <= 26.99){
            return "Sobrepeso Grau I";
        }
        else if(imc >= 27 && imc <= 29.99){
            return "Sobrepeso Grau II (Pré-obeso)";
        }
        else if(imc >= 30 && imc <= 34.99){
            return "Obesidade I";
        }
        else if(imc >= 35 && imc <= 39.99){
            return "Obesidade II";
        }
        else if(imc >= 40 && imc <= 49.99){
            return "Obesidade III (Mórbida)";
        }
        else if(imc >= 50){
            return "Obesidade IV (Extrema)";
        }
        else{
            return "Valor Inválido!";
        }
    }
    
    /*
    // RETORNA A IDADE
    public static final String idade(String data){
        
        int ano = Integer.parseInt(data.substring(0, 4));
        int mes = Integer.parseInt(data.substring(5, 7));
        int dia = Integer.parseInt(data.substring(8, 10));
        
        return null;
    }
    */
    // MASCARAS DE TEXTO
    private static List<KeyCode> ignoreKeyCodes;

    static {
        ignoreKeyCodes = new ArrayList<>();
        Collections.addAll(ignoreKeyCodes, new KeyCode[]{F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12});
    }

    public static void ignoreKeys(final TextField textField) {
        textField.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (ignoreKeyCodes.contains(keyEvent.getCode())) {
                    keyEvent.consume();
                }
            }
        });
    }

    /**
    * Monta a mascara para Data (dd/MM/yyyy).
    *
    * @param textField TextField
    */
   public static void dateField(final TextField textField) {
       maxField(textField, 10);

       textField.lengthProperty().addListener(new ChangeListener<Number>() {
           @Override
           public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
               if (newValue.intValue() < 11) {
                   String value = textField.getText();
                   value = value.replaceAll("[^0-9]", "");
                   value = value.replaceFirst("(\\d{2})(\\d)", "$1/$2");
                   value = value.replaceFirst("(\\d{2})\\/(\\d{2})(\\d)", "$1/$2/$3");
                   textField.setText(value);
                   positionCaret(textField);
               }
           }
       });
   }
   
   /**
    * Campo que aceita somente numericos.
    *
    * @param textField TextField
    */
   public static void numericField(final TextField textField) {
       textField.lengthProperty().addListener(new ChangeListener<Number>() {
           @Override
           public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
               if (newValue.intValue() > oldValue.intValue()) {
                   char ch = textField.getText().charAt(oldValue.intValue());
                   if (!(ch >= '0' && ch <= '9')) {
                       textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
                   }
               }
           }
       });
   }
   
   /**
    * Monta a mascara para Moeda.
    *
    * @param textField TextField
    */
   public static void monetaryField(final TextField textField) {
       textField.setAlignment(Pos.CENTER_RIGHT);
       textField.lengthProperty().addListener(new ChangeListener<Number>() {
           @Override
           public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
               String value = textField.getText();
               value = value.replaceAll("[^0-9]", "");
               value = value.replaceAll("([0-9]{1})([0-9]{14})$", "$1.$2");
               value = value.replaceAll("([0-9]{1})([0-9]{11})$", "$1.$2");
               value = value.replaceAll("([0-9]{1})([0-9]{8})$", "$1.$2");
               value = value.replaceAll("([0-9]{1})([0-9]{5})$", "$1.$2");
               value = value.replaceAll("([0-9]{1})([0-9]{2})$", "$1,$2");
               textField.setText(value);
               positionCaret(textField);

               textField.textProperty().addListener(new ChangeListener<String>() {
                   @Override
                   public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                       if (newValue.length() > 17)
                           textField.setText(oldValue);
                   }
               });
           }
       });

       textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
           @Override
           public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean fieldChange) {
               if (!fieldChange) {
                   final int length = textField.getText().length();
                   if (length > 0 && length < 3) {
                       textField.setText(textField.getText() + "00");
                   }
               }
           }
       });
   }
   
   /**
    * Monta as mascara para CPF/CNPJ. A mascara eh exibida somente apos o campo perder o foco.
    *
    * @param textField TextField
    */
   public static void cpfCnpjField(final TextField textField) {

       textField.focusedProperty().addListener(new ChangeListener<Boolean>() {

           @Override
           public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean fieldChange) {
               String value = textField.getText();
               if (!fieldChange) {
                   if (textField.getText().length() == 11) {
                       value = value.replaceAll("[^0-9]", "");
                       value = value.replaceFirst("([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})$", "$1.$2.$3-$4");
                   }
                   if (textField.getText().length() == 14) {
                       value = value.replaceAll("[^0-9]", "");
                       value = value.replaceFirst("([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})$", "$1.$2.$3/$4-$5");
                   }
               }
               textField.setText(value);
               if (textField.getText() != value) {
                   textField.setText("");
                   textField.insertText(0, value);
               }

           }
       });

       maxField(textField, 18);
   }

   /**
    * Devido ao incremento dos caracteres das mascaras eh necessario que o cursor sempre se posicione no final da string.
    *
    * @param textField TextField
    */
   private static void positionCaret(final TextField textField) {
       Platform.runLater(new Runnable() {
           @Override
           public void run() {
               // Posiciona o cursor sempre a direita.
               textField.positionCaret(textField.getText().length());
           }
       });
   }

   /**
    * @param textField TextField.
    * @param length    Tamanho do campo.
    */
   private static void maxField(final TextField textField, final Integer length) {
       textField.textProperty().addListener(new ChangeListener<String>() {
           @Override
           public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
               if (newValue.length() > length)
                   textField.setText(oldValue);
           }
       });
   }
   
   // TESTE IDADE ************************************************************************************************************
   
   // metodo que retorna o intervalo de dias entre duas datas
   public static final String contaDias(String dataInicialBR, String dataFinalBR) throws ParseException {  
  
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
        df.setLenient(false);  
  
        Date dataInicio = df.parse(dataInicialBR);  
        Date dataFim = df.parse(dataFinalBR);  
        long dt = (dataFim.getTime() - dataInicio.getTime()) + 3600000;  
        Long diasCorridosAnoLong = (dt / 86400000L);  
  
        Integer diasDecorridosInt = Integer.valueOf(diasCorridosAnoLong.toString());  
  
        String diasDecorridos = String.valueOf(diasDecorridosInt); //Sem numeros formatados;  
  
        return diasDecorridos;  
  
    } 

// método para pegar a data do dia
    public static final String getDataDiaBr(){
        GregorianCalendar calendario = new GregorianCalendar();
        int dia = calendario.get(calendario.DAY_OF_MONTH);
        int mes = calendario.get(calendario.MONTH) + 1;
        int ano = calendario.get(calendario.YEAR);
        String dataIguana = String.valueOf(dia + "/" + mes + "/" + ano);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String diaIguana = df.format(new Date());
        return diaIguana;
    }

// agora para calcular a idade
     public static final BigDecimal calculaIdade(String dataDoMeuNascimento) throws ParseException{
        BigDecimal qtdDias = new BigDecimal(contaDias(dataDoMeuNascimento,getDataDiaBr()));
        BigDecimal ano = new BigDecimal(365.25);
        BigDecimal idade = qtdDias.divide(ano,0, RoundingMode.DOWN);
        
        return idade;        
    }


//**** para usar é só chamar o metodo calculaIdade e passar uma String com a data do nascimento 
 //System.out.println(calculaIdade("06/09/1979"));
 
}
