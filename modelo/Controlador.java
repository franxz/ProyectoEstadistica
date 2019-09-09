package modelo;

import client.IVista;

import client.Parser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.JFrame;

public class Controlador implements ActionListener
{
    public static final String[] Comandos = {"LISTAR","USAR","SUMAR","RESTAR","FRECUENCIA","PROMEDIO","HISTOGRAMA","MODA"};
    private IVista ventana;
    private Parser parser = new Parser(); //ventana.getJTFComandos()
    private Calculador calc = new Calculador();
    
    public Controlador(IVista ventana)
    {
        this.ventana = ventana;
    }


    @Override
    public void actionPerformed(ActionEvent evento)
    {
        if(evento.getActionCommand().equalsIgnoreCase(IVista.EjecutarComandos))
        {
            ArrayList<String> lineas = parser.obtenerLineas(ventana.getJTFComandos());
            
        }
    }
    
    public void ejecutarComandos(ArrayList<String> lineas)
    {
        for(int i=0; i<lineas.size(); i++)
        {
            ArrayList<String> lineaActual = parser.obtenerTokens(ventana.getJTFComandos(),i);
            if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[0]))
            {
                
            }
            else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[1]))
            {
                
            }
            else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[2]) || 
                    lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[3]))
            {
                
            }
            else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[4]))
            {
                
            }
            else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[5]) || 
                    lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[6]) ||
                    lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[7]))
            {
                        
            }
//            else
//                throw new Error001();
        }
    }
    
    public boolean existeComando(String comando)
    {
        int i=0;
        boolean existe = (comando.equalsIgnoreCase(Controlador.Comandos[0]));
        while(!existe && (i<Controlador.Comandos.length))
        {
            i++;
            existe = (comando.equalsIgnoreCase(Controlador.Comandos[i]));
        }
        return existe;
    }

}
