package modelo;

public class ConjuntoDatosNumericos extends ConjuntoDatos
{
    private final double[][] filas;
    
    public ConjuntoDatosNumericos(String nombre, double[][] filas)
    {
        super(nombre);
        this.filas = filas;
    }
    
    public Double[] getColumna(int index) 
    {
	assert(this.filas != null);
	assert(index >= 0);
	assert(index < this.filas[0].length);
	
        Double[] columna = new Double[filas.length];
	for (int i = 0; i < filas.length; i++) {
	    columna[i] = filas[i][index];
	}
        return columna;
    }
    
    public int cantCol(){
        return this.filas[0].length;
    }
}
