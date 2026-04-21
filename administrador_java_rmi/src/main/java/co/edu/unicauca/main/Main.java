package co.edu.unicauca.main;

import co.edu.unicauca.capaDeControladores.CallBackImpl;
import co.edu.unicauca.capaDeControladores.ControladorPreferenciasUsuariosInt;
import co.edu.unicauca.configuracion.lector.LectorPropiedadesConfig;
import co.edu.unicauca.configuracion.servicios.ClienteDeObjetos;

public class Main {
    public static void main(String[] args) {
        int puertoNS = Integer.parseInt(LectorPropiedadesConfig.get("ns.port"));
        String direccionIPNS = LectorPropiedadesConfig.get("ns.host");
        String identificadorObjetoRemoto = "objControladorPreferenciasUsuarios";
        
        ControladorPreferenciasUsuariosInt objRemoto = ClienteDeObjetos.
            obtenerObjetoRemoto(direccionIPNS, puertoNS, identificadorObjetoRemoto);
        
        try {
            CallBackImpl objRemotoCliente = new CallBackImpl();
            objRemoto.registrarReferenciaAdministrador(objRemotoCliente);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Esperando notificaciones del servidor...");
        while (true) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
