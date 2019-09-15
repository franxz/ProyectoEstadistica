package modelo;

public abstract class ConjuntoDatos
{
    protected static final int CONJUNTO_NUMERICO = 0;
    protected static final int CONJUNTO_NO_NUMERICO = 1;
    
    private final String nombre;
    private final int tipo;
    
    public ConjuntoDatos(String nombre, int tipo)
    {
        this.nombre = nombre;
	this.tipo = tipo;
    }
    
    public boolean isNumerico() {
	return this.tipo == CONJUNTO_NUMERICO;
    }
}
