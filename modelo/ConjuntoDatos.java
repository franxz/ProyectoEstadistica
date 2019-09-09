package modelo;

public abstract class ConjuntoDatos
{
    public static byte TIPO_NUMERICO = 0;
    public static byte TIPO_CARACTER = 1;
    
    private String nombre;
    
    public ConjuntoDatos(String nombre)
    {
        this.nombre = nombre;
    }
}
