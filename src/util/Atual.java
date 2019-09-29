package util;

import java.util.ArrayList;
import java.util.List;
import model.bean.Clientes;
import model.bean.Consulta;
import model.bean.Sae;
import model.bean.Usuario;

/**
 *
 * @author Matheus - DELL
 */
public class Atual {
    
    private static Usuario usuario;
    private static boolean editarUsuario = false;
    private static Clientes cliente;
    private static boolean editarCliente = false;
    private static List<Clientes> listaClientes = new ArrayList<>();
    private static Consulta consulta;
    private static boolean verConsulta = false;
    private static Sae sae;

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Atual.usuario = usuario;
    }

    public static List<Clientes> getListaClientes() {
        return listaClientes;
    }

    public static void setListaClientes(Clientes clientes) {
        Atual.listaClientes.add(clientes);
    }

    public static Clientes getCliente() {
        return cliente;
    }

    public static void setCliente(Clientes cliente) {
        Atual.cliente = cliente;
    }

    public static boolean isEditarUsuario() {
        return editarUsuario;
    }

    public static void setEditarUsuario(boolean editarUsuario) {
        Atual.editarUsuario = editarUsuario;
    }

    public static boolean isEditarCliente() {
        return editarCliente;
    }

    public static void setEditarCliente(boolean editarCliente) {
        Atual.editarCliente = editarCliente;
    }

    public static Consulta getConsulta() {
        return consulta;
    }

    public static void setConsulta(Consulta consulta) {
        Atual.consulta = consulta;
    }

    public static boolean isVerConsulta() {
        return verConsulta;
    }

    public static void setVerConsulta(boolean editarConsulta) {
        Atual.verConsulta = editarConsulta;
    }

    public static Sae getSae() {
        return sae;
    }

    public static void setSae(Sae sae) {
        Atual.sae = sae;
    }
    
    
    
}
