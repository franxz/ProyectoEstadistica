package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;

public class ParserArchivo
{
    public ParserArchivo()
    {
        super();
    }
    
    // acepta un archivo y te devuelve un ConjuntoDatos
    public ConjuntoDatos obtenerConjuntoDatos(File archivo)
    {
        ConjuntoDatos ret = null;
        try {
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            
            String titulo = br.readLine(); // obtengo el titulo
            br.readLine(); // salteo la descripcion
            int[] dimensiones = parsearFilasColumnas(br.readLine()); // [cantFilas, cantColumnas]
	    String nombresColumnas = br.readLine();
            
            ArrayList<String> lineas = new ArrayList<>();
            String linea;
            boolean numerico = true; // por defecto, el conjunto de datos es numerico
	    for (int i = 0; i < dimensiones[0]; i++) {
		linea = br.readLine(); // leo una linea
		if (numerico && noNumerico(linea)) {  // busco un caracter no numerico, a menos que ya haya encontrado uno
                    numerico = false; // si encuentro un caracter no numerico, establezco que el conjunto de datos es no numerico
                }
                lineas.add(linea);
	    }
	    fr.close();
            
            if(numerico) {
                ret = new ConjuntoDatosNumericos(titulo, parsearNumeros(dimensiones[0], dimensiones[1], lineas));
            } else {
                ret = new ConjuntoDatosNoNumericos(titulo, parsearNoNumeros(dimensiones[0], dimensiones[1], lineas));
            }
        }
        catch(Exception e)
        {
	    // ESTO HAY QUE MODIFICARLO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	    // ESTO HAY QUE MODIFICARLO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	    // ESTO HAY QUE MODIFICARLO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	    
            System.out.println("Excepcion leyendo fichero "+ archivo + ": " + e);
	    // ESTO HAY QUE MODIFICARLO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	    // ESTO HAY QUE MODIFICARLO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	    // ESTO HAY QUE MODIFICARLO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	    
        }
        return ret;
    }

    // toma la linea que contiene la cantidad de filas y columnas, y devuelve un int[] con esta información
    private int[] parsearFilasColumnas(String linea) {
	int iComa = linea.indexOf(",");
	return new int [] {Integer.parseInt(linea.substring(0, iComa)), Integer.parseInt(linea.substring(iComa + 1))};  // [cantFilas, cantColumnas]
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
    private double[][] parsearNumeros(int filas, int columnas, ArrayList<String> lineas)
    {
        double[][] matNumeros = new double[filas][columnas];
        for(int i = 0; i < filas; i++)
        {
            matNumeros[i] = parsearLineaNumeros(lineas.get(i), columnas);
        }
        return matNumeros;
    }
    
    // acepta una linea (fila) y devuelve un array de double (elementos de la fila)
    private double[] parsearLineaNumeros(String linea, int cant_numeros) 
    {
        String[] numerosString = new String[cant_numeros]; // array que contiene los numeros en formato String
        int iComa = linea.indexOf(",");
        for (int i = 0; i < numerosString.length - 1; i++) {
            numerosString[i] = linea.substring(0, iComa); // agarro un numero
            linea = linea.substring(iComa + 1); // recorto la linea (le saco el numero que agarre)
            iComa = linea.indexOf(",");
        }
        numerosString[numerosString.length - 1] = linea; // agarro el ultimo numero
        
        double[] numeros = new double[cant_numeros];
        for(int i = 0; i < numeros.length; i++)
        {
            numeros[i] = Double.parseDouble(numerosString[i]);
        }
        
        return numeros;
    }
    
    // aceptas lineas (filas) leidas de un archivo y devuelve una matriz de String
    private String[][] parsearNoNumeros(int filas, int columnas, ArrayList<String> lineas)
    {
        String[][] matNoNumeros = new String[filas][columnas];
        for(int i = 0; i < filas; i++)
        {
            matNoNumeros[i] = parsearLineaNoNumeros(lineas.get(i), columnas);
        }
        return matNoNumeros;
    }
    
    // acepta una linea (fila) y devuelve un array de String (elementos de la fila)
    private String[] parsearLineaNoNumeros(String linea, int cant_datos) 
    {
        String[] datosString = new String[cant_datos]; // array que contiene los datos en formato String
        int iComa = linea.indexOf(",");
        for (int i = 0; i < datosString.length - 1; i++) {
            datosString[i] = linea.substring(0, iComa); // agarro un dato
            linea = linea.substring(iComa + 1); // recorto la linea (le saco el dato que agarre)
            iComa = linea.indexOf(",");
        }
        datosString[datosString.length - 1] = linea; // agarro el ultimo dato
        return datosString;
    }
}
