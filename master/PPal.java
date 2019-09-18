package master;

import client.IVista;
import client.Ventana;

import modelo.Controlador;

public class PPal
{
    public static void main(String[] args)
    {
        IVista ventana = new Ventana();
        Controlador controlador = new Controlador(ventana);
        ventana.arrancar();
    }
}
