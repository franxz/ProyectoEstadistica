package modelo;

import java.util.Iterator;

public class ConjuntoDatosNumericos extends ConjuntoDatos
{
  private final double[][] filas;

  public ConjuntoDatosNumericos(String nombre, String[] nombresColumnas, double[][] filas) {
    super(nombre, nombresColumnas, ConjuntoDatos.CONJUNTO_NUMERICO);
    this.filas = filas;
  }

  public Double[] getColumna(String nombreColumna) {
    Double[] columna = new Double[filas.length];
    int iColumna = this.nombresColumnas.get(nombreColumna);

    for (int i = 0; i < filas.length; i++) {
      columna[i] = filas[i][iColumna];
    }

    return columna;
  }

  public void actualizarColumna(String nombreColumna, double[] columnaActualizada) {
    int iColumna = this.nombresColumnas.get(nombreColumna);

    for (int i = 0; i < filas.length; i++) {
      filas[i][iColumna] = columnaActualizada[i];
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

      Double[] columna = this.getColumna(nombre);
      for (int i = 0; i < columna.length; i++) {
        System.out.print("" + columna[i] + ", ");
      }
      System.out.println("");
    }
  }

  public int cantCol(){
    return this.filas[0].length;
  }
}
