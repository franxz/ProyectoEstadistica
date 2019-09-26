package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

public class Calculador
{
    public static Double[] suma(Double[] columna1, Double[] columna2)
    {
        assert columna1.length == columna2.length : "No coinciden longitudes de arreglos";

        int i;

        for (i = 0; i < columna1.length; i++)
            columna1[i] += columna2[i];

        return columna1;
    }

    public static Double[] resta(Double[] columna1, Double[] columna2)
    {
        assert columna1.length == columna2.length : "No coinciden longitudes de arreglos";

        int i;

        for (i = 0; i < columna1.length; i++)
            columna1[i] -= columna2[i];

        return columna1;
    } 
    
    public static double frecuenciaPorcentual(Object[] columna, Object valor)
    {
        int i; 
        double frec = 0;
        
        for (i = 0; i < columna.length; i++)
            if (columna[i].equals(valor))
                frec++;
        if (columna.length != 0)
            frec /= columna.length; 
    
        return frec * 100.0; /* % */
    }

    public static double promedio(Double[] columna)
    {
        double prom = 0;
        int i;

        for (i = 0; i < columna.length; i++)
            prom += columna[i];
        if (columna.length != 0)
            prom /= columna.length;

        return prom;
    }
    
    public static String moda(Object[] columna)
    {
        HashMap<Object, Integer> contador = new HashMap<Object, Integer>();
        int max = -1, cant, i;
        Iterator<Object> iter;
        Object valor;
        String moda = "";
        
        for (i = 0; i < columna.length; i++)
        {
            if (contador.containsKey(columna[i]))
                cant = contador.get(columna[i]) + 1;
            else
                cant = 1;
            contador.put(columna[i], cant);
            if (cant > max)
                max = cant;
        }
        iter = contador.keySet().iterator();
        if (max != -1)
            moda = Integer.toString(max)  + " apariciones de: ";
        while (iter.hasNext())
        {
            valor = iter.next();
            if (contador.get(valor) == max)
                moda = moda + valor + " ";
        }

        return moda;
    }
    
    public static String histograma(String[] columna)
    {
        TreeMap<String,Integer> contador = new TreeMap<String,Integer>();
        int i, cant;
        String actual;
        Iterator<String> iter;
        StringBuilder builder = new StringBuilder();
        
        /* Calcula frecuencia de cada dato */
        for (i = 0; i < columna.length; i++)
        {
            if (contador.containsKey(columna[i]))
                cant = contador.get(columna[i]) + 1;
            else
                cant = 1;
            contador.put(columna[i], cant);
        }
        
        /* Armo histograma */
        iter = contador.keySet().iterator();
        while (iter.hasNext())
        {
            actual = iter.next();
            cant = contador.get(actual);
            builder.append("\n" + actual + " : ");
            for (i = 1; i <= cant; i++)
                builder.append('=');
        }
        
        return builder.toString();
    }
    
    public static String histograma(Double[] columna)
    {
        TreeMap<Double,Integer> contador = new TreeMap<Double,Integer>();
        int i, cant;
        Double actual;
        Iterator<Double> iter;
        StringBuilder builder = new StringBuilder();
        
        /* Calcula frecuencia de cada dato */
        for (i = 0; i < columna.length; i++)
        {
            if (contador.containsKey(columna[i]))
                cant = contador.get(columna[i]) + 1;
            else
                cant = 1;
            contador.put(columna[i], cant);
        }
        
        /* Armo histograma */
        iter = contador.keySet().iterator();
        while (iter.hasNext())
        {
            actual = iter.next();
            cant = contador.get(actual);
            builder.append("\n" + actual + " : ");
            for (i = 1; i <= cant; i++)
                builder.append('=');
        }
        
        return builder.toString();
    }

}
