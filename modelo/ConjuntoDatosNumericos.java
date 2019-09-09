package modelo;

public class ConjuntoDatosNumericos extends ConjuntoDatos
{
    private double[][] filas;
    
    public ConjuntoDatosNumericos(String nombre, double[][] filas)
    {
        super(nombre);
        this.filas = filas;
    }
    
    public double[] getColumna(int index) 
    {
        // implementar
        double[] columna = null;
        return columna;
    }
}
