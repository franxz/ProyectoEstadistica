package client;

import java.util.ArrayList;
import java.util.Collections;

public class Parser
{
    private String textField;
   
    public Parser(String textField)
    {
        this.textField = textField;
    }
    
    public ArrayList<String> obtenerLineas()
    {
        ArrayList<String> lineas = new ArrayList<String>();
        String[] aux = this.textField.split("\\r\\n\\");
        Collections.addAll(lineas, aux);
        return lineas;
    }
    
    public ArrayList<String> obtenerTokens(int i)
    {
        ArrayList<String> tokens = new ArrayList<String>();
        String[] aux = this.textField.split(" ");
        Collections.addAll(tokens, aux);
        return tokens;
    }
    
    public String obtenerOperacion(int i)
    {
        return obtenerTokens(i).get(0);
    }
}
