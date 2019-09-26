package master;

import client.IVista;
import client.Ventana;

import java.io.File;

import modelo.Controlador;

public class PPal
{
    public static void main(String[] args)
    {
        IVista ventana = new Ventana();
        Controlador controlador = new Controlador(ventana);
        File theDir = new File("Datos");
        if (!theDir.exists()) 
        {
            ventana.informarAlUsuario("Creando directorio: " + theDir.getName()+"\n");
            boolean result = false;
            try{
                theDir.mkdir();
                result = true;
            } 
            catch(SecurityException se){
                ventana.informarAlUsuario("No se ha podido crear el directorio");
            }        
            if(result){
                ventana.informarAlUsuario("Directorio creado");
            }
        }
        ventana.arrancar();
    }
}
