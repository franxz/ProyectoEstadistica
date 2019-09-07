package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Calculador
{
    public double[] suma(double[] columna1, double[] columna2)
    {
        assert columna1.length == columna2.length : "No coinciden longitudes de arreglos";
        
        int i;
        
        for (i = 0; i< columna1.length; i++)
            columna1[i] += columna2[i];
        
        return columna1;
    }
    
    public double[] resta(double[] columna1, double[] columna2)
    {
        assert columna1.length == columna2.length;
        
        int i;
        
        for (i = 0; i< columna1.length; i++)
            columna1[i] -= columna2[i];
        
        return columna1;
    } 
    
    public double frecuenciaPorcentual(double[] columna, double valor)
    {
        int i; 
        double frec = 0;
        
        for (i = 0; i < columna.length; i++)
            if (columna[i] == valor)
                frec++;
        if (columna.length != 0)
            frec /= columna.length; 
    
        return frec * 100.0; /* % */
    }
    
    public double frecuenciaPorcentual(String[] columna, String valor)
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
    
    public double promedio(double[] columna)
    {
        double prom = 0;
        int i;
        
        for (i = 0; i < columna.length; i++)
            prom += columna[i];
        if (columna.length != 0)
            prom /= columna.length;
        
        return prom;
    }
    
    public ArrayList<Double> moda(double[] columna)
    {
        ArrayList<Double> moda = new ArrayList<Double>();
        HashMap<Double, Integer> contador = new HashMap<Double, Integer>();
        int max = -1, cant, i;
        Iterator<Double> iter;
        double valor;
        
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
        while (iter.hasNext())
        {
            valor = iter.next();
            if (contador.get(valor) == max)
                moda.add(valor);
        }
        
        return moda;
    }
    
    public ArrayList<String> moda(String[] columna)
    {
        ArrayList<String> moda = new ArrayList<String>();
        HashMap<String, Integer> contador = new HashMap<String, Integer>();
        int max = -1, cant, i;
        Iterator<String> iter;
        String valor;
        
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
        while (iter.hasNext())
        {
            valor = iter.next();
            if (contador.get(valor) == max)
                moda.add(valor);
        }
        
        return moda;
    }
    
    public HashMap<Double,Integer> CantidadesHistograma(Double[] columna)
    {
        HashMap<Double, Integer> contador = new HashMap<Double, Integer>();
        int i, cant;
        
        for (i = 0; i < columna.length; i++)
        {
            if (contador.containsKey(columna[i]))
                cant = contador.get(columna[i]) + 1;
            else
                cant = 1;
            contador.put(columna[i], cant);
        }
        
        return contador;
    }
    
    public HashMap<String,Integer> CantidadesHistograma(String[] columna)
    {
        HashMap<String, Integer> contador = new HashMap<String, Integer>();
        int i, cant;
        
        for (i = 0; i < columna.length; i++)
        {
            if (contador.containsKey(columna[i]))
                cant = contador.get(columna[i]) + 1;
            else
                cant = 1;
            contador.put(columna[i], cant);
        }
        
        return contador;
    }
}
