package modelo;

public class ConjuntoDatosNoNumericos extends ConjuntoDatos
{
  private final String[][] filas;

  public ConjuntoDatosNoNumericos(String nombre, String[] nombresColumnas, String[][] filas) {
    super(nombre, nombresColumnas, ConjuntoDatos.CONJUNTO_NO_NUMERICO);
    this.filas = filas;
  }

  public String[] getColumna(String nombreColumna) {
    String[] columna = new String[filas.length];
    int iColumna = this.nombresColumnas.get(nombreColumna);

    for (int i = 0; i < filas.length; i++) {
      columna[i] = filas[i][iColumna];
    }

    return columna;
  }
    
  public void actualizarColumna(String nombreColumna, String[] columnaActualizada) {
    int iColumna = this.nombresColumnas.get(nombreColumna);

    for (int i = 0; i < filas.length; i++) {
      filas[i][iColumna] = columnaActualizada[i];
    }
  }

  public String[][] getMatriz() {
    return filas;
  }
  
  public int cantCol(){
    return this.filas[0].length;
  }
}