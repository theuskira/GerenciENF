package util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.bean.Usuario;

/**
 *
 * @author Matheus - DELL
 */
public class ImprimirV1 {
    
    private String titulo = "Info Vet";
    private Usuario usuario;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    // CRIA A PASTA
    private void criarPasta(){
        
        java.nio.file.Path path = java.nio.file.Paths.get("C:\\CONSULTAS");
        
        try {
            
            Files.createDirectory(path);
            
        } catch (FileAlreadyExistsException e) {
            
            System.err.println("O Diretorio Ja Existe: " + e.getMessage());
            
        } catch (IOException e){
            
            System.err.println("Erro ao criar pasta: " + e.getMessage());
            
        }
        
    }
    
    public void imprimirModelo(){
        
        criarPasta();
        
        String caminho = "C:\\CONSULTAS\\MODELO - "
                + getDateTimeSeconds()
                + ".PDF";
        
        Document document = new Document(PageSize.A5);
        
        try {
            
            PdfWriter writer = PdfWriter.getInstance(document, new  FileOutputStream(caminho));
            
            document.addAuthor("sassasa");
            
            document.addSubject("GerenciENF - Criado por Matheus Viana");
            
            document.open();
            
            Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
            Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Font fSubTitle2 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
            
            document.addTitle("GerenciENF - ");
            
            Paragraph pTitulo = new Paragraph(getTitulo(), fTitle);
            pTitulo.setAlignment(Element.ALIGN_CENTER);    // CENTRO
            document.add(pTitulo);
            
            Paragraph pData = new Paragraph("sss", fSubTitle);
            pData.setAlignment(Element.ALIGN_RIGHT);  // DIREITA
            document.add(pData);
            
            document.close();
            
            writer.close();
            
        } catch (Exception e) {
            
            
            
        }
        
    }
    
    // TEMPO EM SEGUNDOS
    private String getDateTimeSeconds() {
        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
}
