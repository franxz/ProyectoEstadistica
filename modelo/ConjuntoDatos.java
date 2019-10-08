package modelo;

import java.util.HashMap;

public abstract class ConjuntoDatos
{
    protected static final int CONJUNTO_NUMERICO = 0;
    protected static final int CONJUNTO_NO_NUMERICO = 1;
    private final String nombre;
    private final int tipo;
    protected HashMap<String, Integer> nombresColumnas = new HashMap<>();

    /**
     * Constructor de la clase.<br>
     * 
     * @param nombre Nombre de el conjunto de datos.<br>
     * @param nombresColumnas Nombre de las columnas del conjunto. <br>
     * @param tipo Tipo de datos del conjunto.<br>
     */
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

    /**
     * Metodo que devuelve el nombre del conjunto. <br>
     * 
     * @return Nombre del conjunto. <br>
     */
    public String getNombre() {
        return nombre;
    }
    
    public abstract int cantCol();
    
    public abstract boolean isNumerico();
    
    public abstract Object[] getColumna(String nombreColumna);
    
    public abstract void actualizarColumna(String nombreColumna, Object[] columnaActualizada);
}
