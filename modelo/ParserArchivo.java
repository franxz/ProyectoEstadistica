package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;

public class ParserArchivo
{
    public ParserArchivo() {
        super();
    }
    
    /**
     * Este metodo recibe un archivo que contiene un conjunto de datos, lo procesa y devuelve el conjunto de datos correspodiente <br>
     * 
     * <b>pre:</b>El archivo existe  <br>
     * <b>post:</b>Se devuelve un ConjuntoDatos de forma correcta.  <br>
     * 
     * @Param archivo cotiene la referencia al archivo que se utilizara.<br>
     * 
     * @return ConjuntoDatos correspondiete al archivo.<br>
     */
    public static ConjuntoDatos obtenerConjuntoDatos(File archivo)
    {
        ConjuntoDatos ret = null;
        try {
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            
	    // obtengo el titulo
            String titulo = br.readLine();
	    
	    // salteo la descripcion
	    br.readLine();
	    
	    // obtengo las dimensiones --> [cantFilas, cantColumnas]
            int[] dimensiones = parsearDimensiones(br.readLine());
	    
	    // obtengo los nombres de las columnas
	    String[] nombresColumnas = parsearLineaPorComas(br.readLine(), dimensiones[1]);
            
	    // obtengo las filas de la matriz
            ArrayList<String> lineas = new ArrayList<>();
            String linea;
            boolean numerico = true; // por defecto, el conjunto de datos es numerico
	    
	    for (int i = 0; i < dimensiones[0]; i++) {
		linea = br.readLine();			// leo una linea
		if (numerico && noNumerico(linea)) {	// busco un caracter no numerico, a menos que ya haya encontrado uno
                    numerico = false;			// si encuentro un caracter no numerico, establezco que el conjunto de datos es no numerico
                }
                lineas.add(linea);
	    }
	    fr.close();
            
            if (numerico) {
                ret = new ConjuntoDatosNumericos(titulo, nombresColumnas, parsearNumeros(dimensiones[0], dimensiones[1], lineas));
            } else {
                ret = new ConjuntoDatosNoNumericos(titulo, nombresColumnas, parsearNoNumeros(dimensiones[0], dimensiones[1], lineas));
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**El metodo se encarga de grabar todos los conjuntos numericos.<br>
     * 
     * @param conjuntos : Es una lista que contiene a todos los conjuntos numericos.<br>
     * 
     * <b>pre:</b> conjuntos != null<br>
     * <b>post:</b> Cada conjunto numerico actualizo los valores de su matriz en un archivo cuyo nombre es el del conjunto. <br>
     */
    public static void grabarDatos(ArrayList<ConjuntoDatosNumericos> conjuntos){//Los unicos que se graban son los conjuntos numericos.
                    assert conjuntos != null : "Parametro invalido,la lista de conjuntos no puede ser nula.";
                    FileWriter fw;
                    FileReader fr;
                    int i;
                    try {
                        Iterator<ConjuntoDatosNumericos> it = conjuntos.iterator();
                        while(it.hasNext()){
                            ConjuntoDatosNumericos conjuntoActual = it.next();
                            File archivo = new File(".\\Datos\\"+conjuntoActual.getNombre()+".dat");
                            File archivoTemporal = new File(".\\Datos\\"+"ArchivoTemporal.dat");
                            fr = new FileReader(archivo);
                            fw = new FileWriter(archivoTemporal);
                            BufferedReader br = new BufferedReader(fr);
                            BufferedWriter bw = new BufferedWriter(fw);
                            String titulo = br.readLine();
                            String descripcion = br.readLine();
                            String filasColumnas = br.readLine();
                            int[] dimensiones = parsearDimensiones(filasColumnas);
                            String nombreColumnas = br.readLine();
                            bw.write(titulo);
                            bw.newLine();
                            bw.write(descripcion);
                            bw.newLine();
                            bw.write(filasColumnas);
                            bw.newLine();
                            bw.write(nombreColumnas);
                            bw.newLine();
                            String convertidos;
                            int j;
                            for(i=0;i<dimensiones[0];i++)
                            {
                                convertidos = ParserArchivo.getFila(conjuntoActual.getMatriz(),i,dimensiones[1]);
                                bw.write(convertidos);
                                bw.newLine();
                            }
                            bw.close();
                            br.close();
                            archivo.delete();
                            archivoTemporal.renameTo(archivo);
                        }
                    } catch (IOException e) 
                    {
                        System.out.println(e.getStackTrace());     
                    }
        }
        
        /**
         * Este metodo toma los valores de la fila de una matriz y arma un String con los mismos para poder grabarlos, <br>
         * de forma que puedan volver a leerse desde el programa <br>
         * 
         * <b>pre:</b>La matriz no es nula y los indices se encuentran dentro de la dimension correcta.  <br>
         * <b>post:</b>Se devuelve el String con la fila convertida.  <br>
         * 
         * @Param matriz Matriz de Double que contiene los valores del conjunto.<br>
         * @Param i Fila a convertir.<br>
         * @Param k Cantidad de columnas de la matriz.<br>
         * 
         * @return String de la fila con el formato correcto.<br>
         */
        
        private static String getFila(double[][] matriz,int i,int k)
        {
            int j;
            String valores = "";
            for(j=0;j<k;j++)
                if(j!=k-1)
                    valores += String.valueOf(matriz[i][j])+",";
                else
                    valores += String.valueOf(matriz[i][j]);
            return valores;
        }

    /**
     * Este metodo recibe un String con las dimensiones del conjunto y lo parsea para retornar un vector que contendra en <br>
     * su primer componente la cantidad de filas y en el segundo la cantidad de columnas <br>
     * 
     * <b>pre:</b>La linea no es vacia.  <br>
     * <b>post:</b>Se devuelve el vector con los valores correspondientes al String.  <br>
     * 
     * @Param linea Contiene la linea a parsear<br>
     * 
     * @return Vector con los valores de fila y columna.<br>
     */
    private static int[] parsearDimensiones(String linea) {
	int iComa = linea.indexOf(",");
	return new int[] {Integer.parseInt(linea.substring(0, iComa)), Integer.parseInt(linea.substring(iComa + 1))};  // [cantFilas, cantColumnas]
    }
    
    /**
     * Este metodo recibe un String de una linea del archivo y la cantidad de elementos correspondientes a la fila, los separa <br>
     * y los coloca en un vector de String para devolverlos. <br>
     * 
     * @Param linea Contiene la linea a parsear. <br>
     * @Param cantElementos contiene la cantidad de elementos que hay en cada fila.<br>
     * 
     * @return Vector de String con los valores de la linea.<br>
     */
    private static String[] parsearLineaPorComas(String linea, int cantElementos) {
	String[] elementos = new String[cantElementos];
	int iComa = linea.indexOf(",");
	
	for (int i = 0; i < elementos.length - 1; i++) {
            elementos[i] = linea.substring(0, iComa);	// obtengo un elemento
            linea = linea.substring(iComa + 1);		// recorto la linea (le quito el elemento que obtuve)
	    iComa = linea.indexOf(",");
        }
	elementos[elementos.length - 1] = linea;	// obtengo el ultimo elemento
	
	return elementos;
    }
    
    /**
     * Este metodo corrobora si todos los elementos de la linea son numericos.<br>
     * 
     * @Param linea Contiene la linea a analizar. <br>
     * 
     * @return booleano que sera verdaderos si hay elementos no numericos.<br>
     */
    private static boolean noNumerico(String linea) 
    {
        for(int i = 0; i < linea.length(); i++) 
        {
            if((linea.charAt(i) < '0' || linea.charAt(i) > '9') && linea.charAt(i) != ',' && linea.charAt(i) != '.' 
               && linea.charAt(i) != '-')
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Este metodo recibe las lineas del conjunto y devuelve una matriz en la que se encontraran todos los valores de la matriz.<br>
     * 
     * @Param lineas ArrayList con las lineas del conjunto que hay que parsear <br>
     * @Param cantFilas cantidad de filas del conjunto<br>
     * @Param cantColumnas cantidad de columnas del conjunto<br>
     * 
     * @return Matriz con los valores del conjunto.<br>
     */
    private static double[][] parsearNumeros(int cantFilas, int cantColumnas, ArrayList<String> lineas)
    {
        double[][] matNumeros = new double[cantFilas][cantColumnas];
        
	for(int i = 0; i < cantFilas; i++) {
            matNumeros[i] = parsearLineaNumeros(lineas.get(i), cantColumnas);
        }
        
	return matNumeros;
    }
    
    /**
     * Este metodo recibe una linea del conjunto y devuelve un vector de Double con los valores que hay en el String.<br>
     * 
     * @Param linea Linea del conjunto que hay que parsear. <br>
     * @Param cantElementos cantidad de elementos en la linea.<br>
     * 
     * @return Vector con los valores de la linea<br>
     */
    private static double[] parsearLineaNumeros(String linea, int cantElementos) 
    {
        double[] numeros = new double[cantElementos];
	String[] numerosString = parsearLineaPorComas(linea, cantElementos);
	
        for(int i = 0; i < numeros.length; i++) {
            numeros[i] = Double.parseDouble(numerosString[i]);
        }
        
        return numeros;
    }
    
    /**
     * Este metodo recibe un ArrayList con las lineas del conjunto y las parsea para devolver una matriz con los elementos del conjunto.<br>
     * 
     * @Param lineas ArrayList con las lineas del conjunto que hay que parsear <br>
     * @Param cantFilas cantidad de filas del conjunto<br>
     * @Param cantColumnas cantidad de columnas del conjunto<br>
     * 
     * @return Matriz con los valores del conjunto.<br>
     */
    private static String[][] parsearNoNumeros(int cantFilas, int cantColumnas, ArrayList<String> lineas)
    {
        String[][] matNoNumeros = new String[cantFilas][cantColumnas];
        
	for(int i = 0; i < cantFilas; i++) {
            matNoNumeros[i] = parsearLineaPorComas(lineas.get(i), cantColumnas);
        }
	
        return matNoNumeros;
    }
}
