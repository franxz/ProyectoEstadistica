package modelo;

public abstract class ConjuntoDatos
{
    private String nombre;
    
    public ConjuntoDatos(String nombre)
    {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
    
    public abstract int cantCol();
}
