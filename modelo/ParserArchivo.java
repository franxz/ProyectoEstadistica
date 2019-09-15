package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

public class ParserArchivo
{
    public ParserArchivo() {
        super();
    }
    
    // acepta un archivo y te devuelve un ConjuntoDatos
    public ConjuntoDatos obtenerConjuntoDatos(File archivo) throws IOException {
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
	    throw e;
        }
        return ret;
    }

    // toma la linea que contiene la cantidad de filas y columnas, y devuelve un int[] con esta información
    private int[] parsearDimensiones(String linea) {
	int iComa = linea.indexOf(",");
	return new int[] {Integer.parseInt(linea.substring(0, iComa)), Integer.parseInt(linea.substring(iComa + 1))};  // [cantFilas, cantColumnas]
    }
    
    // toma una linea que contiene elementos separados por comas, y devuelve un String[] que contiene estos elementos
    private String[] parsearLineaPorComas(String linea, int cantElementos) {
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
    private boolean noNumerico(String linea) 
    {
        for(int i = 0; i < linea.length(); i++) 
        {
            if((linea.charAt(i) < '0' || linea.charAt(i) > '9') && linea.charAt(i) != ',' && linea.charAt(i) != '.')
            {
                return true;
            }
        }
        return false;
    }
    
    // aceptas lineas (filas) leidas de un archivo y devuelve una matriz de double
    private double[][] parsearNumeros(int cantFilas, int cantColumnas, ArrayList<String> lineas)
    {
        double[][] matNumeros = new double[cantFilas][cantColumnas];
        
	for(int i = 0; i < cantFilas; i++) {
            matNumeros[i] = parsearLineaNumeros(lineas.get(i), cantColumnas);
        }
        
	return matNumeros;
    }
    
    // acepta una linea (fila) y devuelve un double[] (elementos de la fila)
    private double[] parsearLineaNumeros(String linea, int cantElementos) 
    {
        double[] numeros = new double[cantElementos];
	String[] numerosString = parsearLineaPorComas(linea, cantElementos);
	
        for(int i = 0; i < numeros.length; i++) {
            numeros[i] = Double.parseDouble(numerosString[i]);
        }
        
        return numeros;
    }
    
    // aceptas lineas (filas) leidas de un archivo y devuelve una matriz de String
    private String[][] parsearNoNumeros(int cantFilas, int cantColumnas, ArrayList<String> lineas)
    {
        String[][] matNoNumeros = new String[cantFilas][cantColumnas];
        
	for(int i = 0; i < cantFilas; i++) {
            matNoNumeros[i] = parsearLineaPorComas(lineas.get(i), cantColumnas);
        }
	
        return matNoNumeros;
    }
}
