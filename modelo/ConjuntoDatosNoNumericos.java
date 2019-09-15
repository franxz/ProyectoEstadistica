package modelo;

public class ConjuntoDatosNoNumericos extends ConjuntoDatos
{
    private final String[][] filas;
    
    public ConjuntoDatosNoNumericos(String nombre, String[][] filas)
    {
        super(nombre);
        this.filas = filas;
    }
    
    public String[] getColumna(int index) 
    {
	assert(this.filas != null);
	assert(index >= 0);
	assert(index < this.filas[0].length);
	
        String[] columna = new String[filas.length];
	for (int i = 0; i < filas.length; i++) {
	    columna[i] = filas[i][index];
	}
        return columna;
    }
    
    public int cantCol(){
        return this.filas[0].length;
    }
}