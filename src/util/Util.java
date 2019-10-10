/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
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
import model.bean.Clientes;
import model.bean.Consulta;
import model.bean.Usuario;
import model.dao.ConsultasDAO;
import model.dao.RetornoDAO;

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


    // LINE CHART
    // LineChart 7 Dias
    public static void atualizarLineChart7Dias(LineChart lineChart7Dias, Usuario usuario, Label txtUltimos7Dias){
        
        lineChart7Dias.getData().clear();
        
        int anoAtual = Integer.parseInt(getDateTimeEn().substring(0, 4));
        int mesAtual = Integer.parseInt(getDateTimeEn().substring(5, 7));
        int diaAtual = Integer.parseInt(getDateTimeEn().substring(8, 10));
        
        XYChart.Series<String, Number> series =  new  XYChart.Series<String, Number>();
        series.setName(usuario.getNome());
        
        // Setimo
        int dia7 = diaAtual - 6;
        int mes7 = mesAtual;
        int ano7 = anoAtual;
        if(dia7 < 1){
            if(mes7 == 1){
                mes7 = 12;
                ano7 --;
            }else{
                mes7--;
            }
            dia7 = calendario(mes7) - Math.abs(dia7);
        }
        int tamanho7 = new ConsultasDAO().porDataUsuario(usuario, ano7 + "-" + mes7 + "-" + dia7 ).size();
        series.getData().add(new XYChart.Data<String, Number>(
                    dia7 + "/" + mes7 + "\n" + ano7, tamanho7)
            );
        
        // Sexto
        int dia6 = diaAtual - 5;
        int mes6 = mesAtual;
        int ano6 = anoAtual;
        if(dia6 < 1){
            if(mes6 == 1){
                mes6 = 12;
                ano6 --;
            }else{
                mes6--;
            }
            dia6 = calendario(mes6) - Math.abs(dia6);
        }
        int tamanho6 = new ConsultasDAO().porDataUsuario(usuario, ano6 + "-" + mes6 + "-" + dia6 ).size();
        series.getData().add(new XYChart.Data<String, Number>(
                    dia6 + "/" + mes6 + "\n" + ano6, tamanho6)
            );
        
        // Quinto
        int tamanho5 = 0;
        int dia5 = diaAtual - 4;
        int mes5 = mesAtual;
        int ano5 = anoAtual;
        if(dia5 < 1){
            if(mes5 == 1){
                mes5 = 12;
                ano5 --;
            }else{
                mes5--;
            }
            dia5 = calendario(mes5) - Math.abs(dia5);
        }
        for(Consulta con : new ConsultasDAO().porDataUsuario(usuario, ano5 + "-" + mes5 + "-" + dia5 )){
            
            tamanho5 ++;
            
        }
        series.getData().add(new XYChart.Data<String, Number>(
                    dia5 + "/" + mes5 + "\n" + ano5, tamanho5)
            );
        
        // Quarto
        int tamanho4 = 0;
        int dia4 = diaAtual - 3;
        int mes4 = mesAtual;
        int ano4 = anoAtual;
        if(dia4 < 1){
            if(mes4 == 1){
                mes4 = 12;
                ano4 --;
            }else{
                mes4--;
            }
            dia4 = calendario(mes4) - Math.abs(dia4);
        }
        for(Consulta con : new ConsultasDAO().porDataUsuario(usuario, ano4 + "-" + mes4 + "-" + dia4 )){
            
            tamanho4 ++;
            
        }
        series.getData().add(new XYChart.Data<String, Number>(
                    dia4 + "/" + mes4 + "\n" + ano4, tamanho4)
            );
        
        // Terceiro
        int tamanho3 = 0;
        int dia3 = diaAtual - 2;
        int mes3 = mesAtual;
        int ano3 = anoAtual;
        if(dia3 < 1){
            if(mes3 == 1){
                mes3 = 12;
                ano3 --;
            }else{
                mes3--;
            }
            dia3 = calendario(mes3) - Math.abs(dia3);
        }
        for(Consulta con : new ConsultasDAO().porDataUsuario(usuario, ano3 + "-" + mes3 + "-" + dia3 )){
            
            tamanho3 ++;
            
        }
        series.getData().add(new XYChart.Data<String, Number>(
                    dia3 + "/" + mes3 + "\n" + ano3, tamanho3)
            );
        
        // Segundo
        int tamanho2 = 0;
        int dia2 = diaAtual - 1;
        int mes2 = mesAtual;
        int ano2 = anoAtual;
        if(dia2 < 1){
            if(mes2 == 1){
                mes2 = 12;
                ano2 --;
            }else{
                mes2--;
            }
            dia2 = calendario(mes2) - Math.abs(dia2);
        }
        for(Consulta con : new ConsultasDAO().porDataUsuario(usuario, ano2 + "-" + mes2 + "-" + dia2 )){
            
            tamanho2 ++;
            
        }
        series.getData().add(new XYChart.Data<String, Number>(
                    dia2 + "/" + mes2 + "\n" + ano2, tamanho2)
            );
        
        // Primeiro
        int tamanho1 = 0;
        for(Consulta con : new ConsultasDAO().porDataUsuario(usuario, anoAtual + "-" + mesAtual + "-" + diaAtual)){
            
            tamanho1 ++;
            
        }
        series.getData().add(new XYChart.Data<String, Number>(
                    diaAtual + "/" + mesAtual + "\n" + anoAtual, tamanho1)
            );
        
        txtUltimos7Dias.setText("Consultas dos ultimos 7 dias: " + (
                tamanho1 
                + tamanho2 
                + tamanho3 
                + tamanho4 
                + tamanho5
                + tamanho6
                + tamanho7)
                );

        lineChart7Dias.getData().add(series);
    }
    
    private static int calendario(int mes){
        
        int mes1 = 31, mes3 = 31, mes5 = 31, mes7 = 31, mes8 = 31, mes10 = 31, mes12 = 31;
        int mes4 = 30, mes6 = 30, mes9 = 30, mes11 = 30;
        int mes2 = 28;
        
        switch (mes) {
            case 1:
                return mes1;
            case 2:
                return mes2;
            case 3:
                return mes3;
            case 4:
                return mes4;
            case 5:
                return mes5;
            case 6:
                return mes6;
            case 7:
                return mes7;
            case 8:
                return mes8;
            case 9:
                return mes9;
            case 10:
                return mes10;
            case 11:
                return mes11;
            case 12:
                return mes12;
            default:
                return  30;
        }
        
    }
    
    public static String getDateTimeEn() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    public static void abirArquivo(File file){
        
        try {
            //first check if Desktop is supported by Platform or not
            if(!Desktop.isDesktopSupported()){
                System.err.println("Desktop is not supported");
                return;
            }

            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) desktop.open(file);

            //let's try to open PDF file
            //file = new File("/Users/pankaj/java.pdf");
            //if(file.exists()) desktop.open(file);
        } catch (IOException e) {
            System.err.println("Erro ao abrir o arquivo: " + e.getMessage());
            new ThreadDialog("Erro ao abrir o arquivo: " + e.getMessage());
        }
        
    }
    
    public static String ultimaConsultaCliente(Clientes cliente){
        
        String ultimaConsulta = "?";
                
        for(Consulta con : new ConsultasDAO().listarConsultasCliente(cliente)){
            
            String r = new RetornoDAO().retornoPorConsulta(con).getDataRetorno();
            
            if(r != null && !r.isEmpty()){
                
                ultimaConsulta = r;
                
            }
            
        }
        
        return ultimaConsulta;
        
    }
 
}
