package co.edu.unicauca.capaDeControladores;

import java.rmi.Remote;
import java.rmi.RemoteException;
import co.edu.unicauca.fachadaServices.DTO.PreferenciasDTORespuesta;

// Hereda de la clase Remote, lo cual la convierte en interfaz remota
public interface ControladorPreferenciasUsuariosInt extends Remote {
    // Definicion del primer metodo remoto
    public PreferenciasDTORespuesta getReferencias(Integer id) throws RemoteException;
    public Boolean registrarReferenciaAdministrador(CallBackInt objRemotoCliente) throws RemoteException;
    // Cada definición del método debe especificar que puede lanzar la excepción java.rmi.RemoteException
}
