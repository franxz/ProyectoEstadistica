package modelo;

import java.util.Iterator;

public class ConjuntoDatosNumericos extends ConjuntoDatos
{
  private final double[][] filas;

  public ConjuntoDatosNumericos(String nombre, String[] nombresColumnas, double[][] filas) {
    super(nombre, nombresColumnas, ConjuntoDatos.CONJUNTO_NUMERICO);
    this.filas = filas;
  }

  public Object[] getColumna(String nombreColumna) 
  {
    Double[] columna = null;
    Integer iColumna = this.nombresColumnas.get(nombreColumna);
    
    if (iColumna != null)
    {
        columna = new Double[filas.length];
        for (int i = 0; i < filas.length; i++)
          columna[i] = filas[i][iColumna];
    }
    return columna;
  }
    
  @Override  
  public void actualizarColumna(String nombreColumna, Object[] columnaActualizada) {
    int iColumna = this.nombresColumnas.get(nombreColumna);

    for (int i = 0; i < filas.length; i++) {
      filas[i][iColumna] = (Double) columnaActualizada[i];
    }
  }

  public double[][] getMatriz() {
    return filas;
  }

  // metodo de debug, borrar antes de hacer la entrega !!! !!! !!! !!! !!! !!! !!! !!!
  @Override
  public void print() {
    super.print();

    System.out.println("Cantidad de filas: " + this.filas.length);
    System.out.println("Cantidad de columnas: " + this.filas[0].length);

    Iterator<String> itrColumnas = this.nombresColumnas.keySet().iterator();
    while(itrColumnas.hasNext()) {
      String nombre = itrColumnas.next();
      System.out.print(nombre + ": ");

      Double[] columna = (Double[]) this.getColumna(nombre);
      for (int i = 0; i < columna.length; i++) {
        System.out.print("" + columna[i] + ", ");
      }
      System.out.println("");
    }
  }

  public int cantCol(){
    return this.filas[0].length;
  }
  
    @Override
    public boolean isNumerico()
    {
        return true;
    }
}
