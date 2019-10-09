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
    
    // acepta un archivo y te devuelve un ConjuntoDatos
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

    /**El metodo se encarga de grabar todos los conjuntos numericos.
     * @param conjuntos : Es una lista que contiene a todos los conjuntos numericos.
     * pre : conjuntos != null
     * post : Cada conjunto numerico actualizo los valores de su matriz en un archivo cuyo nombre
     *        es el del conjunto. 
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
        
        //Retorna una fila de la matriz en formato de string,con sus valores separados por coma.
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

    // toma la linea que contiene la cantidad de filas y columnas, y devuelve un int[] con esta información
    private static int[] parsearDimensiones(String linea) {
	int iComa = linea.indexOf(",");
	return new int[] {Integer.parseInt(linea.substring(0, iComa)), Integer.parseInt(linea.substring(iComa + 1))};  // [cantFilas, cantColumnas]
    }
    
    // toma una linea que contiene elementos separados por comas, y devuelve un String[] que contiene estos elementos
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
    
    // chequea si una linea posee un caracter no numerico
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
    
    // aceptas lineas (filas) leidas de un archivo y devuelve una matriz de double
    private static double[][] parsearNumeros(int cantFilas, int cantColumnas, ArrayList<String> lineas)
    {
        double[][] matNumeros = new double[cantFilas][cantColumnas];
        
	for(int i = 0; i < cantFilas; i++) {
            matNumeros[i] = parsearLineaNumeros(lineas.get(i), cantColumnas);
        }
        
	return matNumeros;
    }
    
    // acepta una linea (fila) y devuelve un double[] (elementos de la fila)
    private static double[] parsearLineaNumeros(String linea, int cantElementos) 
    {
        double[] numeros = new double[cantElementos];
	String[] numerosString = parsearLineaPorComas(linea, cantElementos);
	
        for(int i = 0; i < numeros.length; i++) {
            numeros[i] = Double.parseDouble(numerosString[i]);
        }
        
        return numeros;
    }
    
    // aceptas lineas (filas) leidas de un archivo y devuelve una matriz de String
    private static String[][] parsearNoNumeros(int cantFilas, int cantColumnas, ArrayList<String> lineas)
    {
        String[][] matNoNumeros = new String[cantFilas][cantColumnas];
        
	for(int i = 0; i < cantFilas; i++) {
            matNoNumeros[i] = parsearLineaPorComas(lineas.get(i), cantColumnas);
        }
	
        return matNoNumeros;
    }
}
