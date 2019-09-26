package client;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;


public interface IVista
{
    public static final String EjecutarComandos = "Ejecutar";
    
    public String getJTFComandos();
    public void informarAlUsuario(String msj);
    public Resultados getVentanaResultados();
    public Histograma getVentanaHistograma();
    public void arrancar();
    void addActionListener(ActionListener actionListener);
    void addWindowListener(WindowListener windowListener);
}
