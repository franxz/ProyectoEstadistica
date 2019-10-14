package modelo;

import java.util.Iterator;

public class ConjuntoDatosNumericos extends ConjuntoDatos
{
  private final double[][] filas;

  public ConjuntoDatosNumericos(String nombre, String[] nombresColumnas, double[][] filas) {
    super(nombre, nombresColumnas, ConjuntoDatos.CONJUNTO_NUMERICO);
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
      assert nombreColumna!=null : "nombreColumna es null" ;
      assert nombreColumna.equalsIgnoreCase("PANTALLA") || nombreColumna.equalsIgnoreCase("IMPRESORA") : "nombreColumna es null" ;
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
    
    /**
     * Actualiza los valores de una columna existente.<br>
     * 
     * @param nombreColumna Nombre de la columna a actualizar.<br>
     * @param columnaActualizada Array con los nuevos valores. <br>
     * 
     */
  @Override  
  public void actualizarColumna(String nombreColumna, Object[] columnaActualizada) {
      assert this.filas!=null : "Matriz es nula" ;
    int iColumna = this.nombresColumnas.get(nombreColumna);

    for (int i = 0; i < filas.length; i++) {
      filas[i][iColumna] = (Double) columnaActualizada[i];
    }
  }

  public double[][] getMatriz() {
    return filas;
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
