package client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Parser
{
    /**
     * Este metodo recibe el String el textField de la interfaz grafica y lo parsea por linea para agregarlo a un ArrayList y devolverlo.<br>
     * 
     * @Param contentTF contenido del TF.<br>
     * 
     * @return devuelve las lineas del TF por separado.<br>
     */
    public static ArrayList<String> obtenerLineas(String contentTF)
    {
        ArrayList<String> lineas = new ArrayList<String>();
        String[] aux = contentTF.split("\r\n");
        Collections.addAll(lineas, aux);
        return lineas;
    }
    
    /**
     * Este metodo recibe una linea con comandos y la divide para separar su contenido, los agrega a un HashMap y lo devuelve,<br>
     * los parametros del comando no pueden tener nombres separados por espacios.<br>
     * 
     * @Param lineaActual linea de la que se quiere obtener los comandos.<br>
     * 
     * @return Devuelve un HashMap con el comando y sus parametros.<br>
     */
    public static HashMap<Integer,String> obtenerTokens(String linea_actual)
    {
        HashMap<Integer,String> tokens = new HashMap<Integer,String>();
        String[] aux = linea_actual.split(" ");
        
        for (int i = 0; i < aux.length; i++)
            tokens.put(i, aux[i]);

        return tokens;
    }

}
