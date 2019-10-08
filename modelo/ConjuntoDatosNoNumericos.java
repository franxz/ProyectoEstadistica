package modelo;

public class ConjuntoDatosNoNumericos extends ConjuntoDatos
{
  private final String[][] filas;

  public ConjuntoDatosNoNumericos(String nombre, String[] nombresColumnas, String[][] filas) {
    super(nombre, nombresColumnas, ConjuntoDatos.CONJUNTO_NO_NUMERICO);
    this.filas = filas;
  }

    /**
     * Devuelve la columna solicitada.<br>
     * 
     * <b>pre:</b> El nombre no es null y tampoco corresponde a una salida. <br>
     * <b>post:</b> Devuelve la columna solicitada, de no encontrarla, devolvera null. <br>
     * 
     * @param nombreColumna String que contiene el nombre de la columna solicitada.<br>
     * 
     * @return Devuelve la columna solicitada como un array de Object, null si no la encuentra. <br>
     */
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

    /**
     * Actualiza los valores de una columna existente.<br>
     * 
     * @param nombreColumna Nombre de la columna a actualizar.<br>
     * @param columnaActualizada Array con los nuevos valores. <br>
     * 
     */
    @Override
  public void actualizarColumna(String nombreColumna, Object[] columnaActualizada) {
    int iColumna = this.nombresColumnas.get(nombreColumna);

    for (int i = 0; i < filas.length; i++) {
      filas[i][iColumna] = (String) columnaActualizada[i];
    }
  }

    /**
     * El metodo retorna la matriz del conjunto. <br>
     * 
     * @return Matriz de string con los valores.<br>
     */
    public String[][] getMatriz() {
    return filas;
  }

    /**
     * El metodo devuelve la cantidad de columnas de un conjunto.<br>
     * 
     * @return Cantidad de columnas.<br>
     */
    public int cantCol(){
    return this.filas[0].length;
  }

    /**
     * Este metodo informa si el conjunto es numerico. <br>
     * 
     * @return Booleano false.
     */
    @Override
    public boolean isNumerico()
    {
        return false;
    }
}
