package client;

import java.util.ArrayList;
import java.util.Collections;

public class Parser
{
    public ArrayList<String> obtenerLineas(String contentTF)
    {
        ArrayList<String> lineas = new ArrayList<String>();
        String[] aux = contentTF.split("\\r\\n\\");
        Collections.addAll(lineas, aux);
        return lineas;
    }
    
    public ArrayList<String> obtenerTokens(String linea_actual)
    {
        ArrayList<String> tokens = new ArrayList<String>();
        String[] aux = linea_actual.split(" ");
        Collections.addAll(tokens, aux);
        return tokens;
    }

}
