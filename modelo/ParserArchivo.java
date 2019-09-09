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
            
            // parseo de filas y columnas ___ pasarlo a un metodo?
            String filasYcolumnas = br.readLine();
            int iComa = filasYcolumnas.indexOf(",");
            int filas = Integer.parseInt(filasYcolumnas.substring(0, iComa));
            int columnas = Integer.parseInt(filasYcolumnas.substring(iComa+1));
            
            String nombresColumnas = br.readLine();
            
            ArrayList<String> lineas = new ArrayList<String>();
            String linea;
            boolean numerico = true; // por defecto, el conjunto de datos es numerico
            
            while((linea = br.readLine()) != null) // leo una linea
            {
                // busco un caracter no numérico, a menos que ya haya encontrado uno
                if(numerico && noNumerico(linea)) 
                {
                    numerico = false;
                }
                lineas.add(linea);
            }
            fr.close();
            
            if(numerico) 
            {
                ret = new ConjuntoDatosNumericos(titulo, parsearNumeros(filas, columnas, lineas));
            }
            else
            {
                // ret = new ConjuntoDatosNoNumericos(titulo, parsearNoNumeros(filas, columnas, lineas));
            }
        }
        catch(Exception e)
        {
            System.out.println("Excepcion leyendo fichero "+ archivo + ": " + e);
        }
        return ret;
    }
    
    // chequea si una línea posee un caracter no numerico
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
    
    // aceptas lineas (filas) leidas de un archivo y devuelve una matriz de Double
    private double[][] parsearNumeros(int filas, int columnas, ArrayList<String> lineas)
    {
        double[][] matNumeros = new double[filas][columnas];
        for(int i = 0; i < filas; i++)
        {
            matNumeros[i] = parsearLineaNumeros(lineas.get(i));
        }
        return matNumeros;
    }
    
    // acepta una linea (fila) y devuelve un array de Double
    private double[] parsearLineaNumeros(String linea) 
    {
        ArrayList<String> numerosString = new ArrayList<>();
        int iComa = linea.indexOf(",");
        while (iComa > 0) 
        {
            numerosString.add(linea.substring(0, iComa)); // agarro un numero
            linea = linea.substring(iComa+1); // recorto la linea (le saco el numero que agarré)
            iComa = linea.indexOf(",");
        }
        numerosString.add(linea); // agarro el ultimo numero
        
        double[] numeros = new double[numerosString.size()];
        for(int i = 0; i < numeros.length; i++)
        {
            numeros[i] = Double.parseDouble(numerosString.get(i));
        }
        
        return numeros;
    }
}
