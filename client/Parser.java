package client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Parser
{
    public static ArrayList<String> obtenerLineas(String contentTF)
    {
        ArrayList<String> lineas = new ArrayList<String>();
        String[] aux = contentTF.split("\r\n");
        Collections.addAll(lineas, aux);
        return lineas;
    }
    
    public static HashMap<Integer,String> obtenerTokens(String linea_actual)
    {
        HashMap<Integer,String> tokens = new HashMap<Integer,String>();
        String[] aux = linea_actual.split(" ");
        
        for (int i = 0; i < aux.length; i++)
            tokens.put(i, aux[i]);

        return tokens;
    }

}
