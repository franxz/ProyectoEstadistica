package client;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;


public interface IVista
{
    public static final String EjecutarComandos = "Ejecutar comandos";
    
    public String getJTFComandos();
    public void informarAlUsuario(String msj);
    public Resultados getVentanaResultados();
    public Histograma getVentanaHistograma();
    void addActionListener(ActionListener actionListener);
    void addWindowListener(WindowListener windowListener);
}
