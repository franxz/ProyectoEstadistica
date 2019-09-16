package modelo;

public class ConjuntoDatosNoNumericos extends ConjuntoDatos
{
  private final String[][] filas;

  public ConjuntoDatosNoNumericos(String nombre, String[] nombresColumnas, String[][] filas) {
    super(nombre, nombresColumnas, ConjuntoDatos.CONJUNTO_NO_NUMERICO);
    this.filas = filas;
  }

  public Object[] getColumna(String nombreColumna) 
  {
    String[] columna = null;
    Integer iColumna = this.nombresColumnas.get(nombreColumna);
    
    if (iColumna != null)
    {
        columna = new String[filas.length];
        for (int i = 0; i < filas.length; i++)
          columna[i] = filas[i][iColumna];
    }
    return columna;
  }
    
  @Override
  public void actualizarColumna(String nombreColumna, Object[] columnaActualizada) {
    int iColumna = this.nombresColumnas.get(nombreColumna);

    for (int i = 0; i < filas.length; i++) {
      filas[i][iColumna] = (String) columnaActualizada[i];
    }
  }

  public String[][] getMatriz() {
    return filas;
  }
  
  public int cantCol(){
    return this.filas[0].length;
  }

    @Override
    public boolean isNumerico()
    {
        return false;
    }
}
