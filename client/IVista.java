package client;

public interface IVista
{
    public static final String EjecutarComandos = "Ejecutar comandos";
    
    public String getJTFComandos();
    public Resultados getVentanaResultados();
    public Histograma getVentanaHistograma();
}
