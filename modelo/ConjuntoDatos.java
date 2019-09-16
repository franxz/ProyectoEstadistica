package modelo;

import java.util.HashMap;

public abstract class ConjuntoDatos
{
    protected static final int CONJUNTO_NUMERICO = 0;
    protected static final int CONJUNTO_NO_NUMERICO = 1;
    private final String nombre;
    private final int tipo;
    protected HashMap<String, Integer> nombresColumnas = new HashMap<>();
    
    public ConjuntoDatos(String nombre, String[] nombresColumnas, int tipo) {
        this.nombre = nombre;
	this.tipo = tipo;
	
	for (int i = 0; i < nombresColumnas.length; i++) {
	    this.nombresColumnas.put(nombresColumnas[i], i);
	}
    }
    
    // metodo de debug, borrar antes de hacer la entrega !!! !!! !!! !!! !!! !!! !!! !!!
    protected void print() {
	System.out.println(this.nombre);
    }

    public String getNombre() {
        return nombre;
    }
    
    public abstract int cantCol();
    
    public abstract boolean isNumerico();
    
    public abstract Object[] getColumna(String nombreColumna);
    
    public abstract void actualizarColumna(String nombreColumna, Object[] columnaActualizada);
}
