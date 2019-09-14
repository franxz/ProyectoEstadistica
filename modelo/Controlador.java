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
    public static final String[] Salidas = {"PANTALLA","IMPRESORA"};
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
            
            String resString=null;
            
            if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[0]))
            {
                
            }
            else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[1]))
            {
                if((lineaActual.get(1)!= null) && (isConjuntoExistente(lineaActual.get(1))))
                {
                    
                }
                else
                    throw new Error006(); // Error006: conjunto de datos inexistente
            }
            else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[2]) || 
                    lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[3]))
            {
                if((lineaActual.get(3)!= null) && isSalidaValida(lineaActual.get(3)))
                {
                    if((lineaActual.get(1)!= null) && (!isSalidaValida(lineaActual.get(1))) && (lineaActual.get(2)!= null) &&
                       (!isSalidaValida(lineaActual.get(2))))
                    {
                        //VERIFICAR SI SON DATOS NUMERICOS
                        //obtencion columna1
                        //obtencion columna2
                        if(isDimensionesIguales(columna1,columna2)) //aca tendrian que venir las dos columnas
                        {
                            if(isColumnaExistente(columna1) && isColumnaExistente(columna2))
                            {
                                if(isDatosNoFaltantes(columna1) && isDatosNoFaltantes(columna2)) //si es todo valido
                                {
                                    double[] res={0};
                                    if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[2])) //suma
                                    {
                                        res = suma(columna1,columna2);
                                        this.ventana.getVentanaResultados().agregaResultado("Suma:\n");
                                    }
                                    else //resta
                                    {
                                        res = resta(columna1,columna2);
                                        this.ventana.getVentanaResultados().agregaResultado("Resta:\n");
                                    }   
                                    resString = arrayToString(res);
                                    this.ventana.getVentanaResultados().agregaResultado(resString);
                                }
                                else
                                    throw new Error005();
                            }
                            else
                                throw new Error003();
                        }
                        else
                            throw new Error004();
                        //verificar otros errores (columnas validas,etc)
                    }
                    else
                        throw new Error000();
                }
                else
                    throw new Error002();

            }
            else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[4]))
            {
                
            }
            else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[5]) || 
                    lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[6]) ||
                    lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[7]))
            {
                if((lineaActual.get(1)!= null) && !isSalidaValida(lineaActual.get(1)) &&
                   (lineaActual.get(2)!= null) && isSalidaValida(lineaActual.get(2)))
                {
                    //obtencion columna
                    if(isColumnaExistente(columna))
                    {
                        //dato vacio?
                        if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[5]))
                        {
                            if(isDatosNumericos(columna))
                            {
                                double res = calc.promedio(columna);
                                resString = res.toString();
                                this.ventana.getVentanaResultados().agregaResultado(resString);
                                
                            }
                            else
                                throw new Error004();
                        }
                        else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[6]))
                        {
                            if(isDatosNumericos(columna))
                            {
                                //histograma de numeros
                            }
                            else
                            {
                                //histograma de strings
                            }
                        }
                        else
                        {
                            if(isDatosNumericos(columna))
                            {
                                //moda de numeros
                            }
                            else
                            {
                                //moda de strings
                            }
                        }
                    }
                    else
                        throw new Error003();
                }
                else
                    throw new Error000();            
            }
            else
                throw new Error001();
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
    
    public boolean isSalidaValida(String token)
    {
        int i=0;
        boolean retorno = token.equalsIgnoreCase(Controlador.Salidas[0]);;
        while(i < Controlador.Salidas.length)
        {
            i++;
            retorno = token.equalsIgnoreCase(Controlador.Salidas[i]);
        }
        return retorno;
    }
    
    public boolean isConjuntoExistente(String nombre)
    {
    }
    
    public boolean isColumnaExistente(double[] columna)
    {
    }
    
    public boolean isDatosNoFaltantes(double[] columna)
    {
    }
    
    public boolean isDimensionesIguales(double[] columna1, double[] columna2)
    {
        return columna1.length == columna2.length;
    }
    
    public boolean isDatosNumericos()
    {
    }
    
    public String arrayToString(double[] columna)
    {
        StringBuilder sb = new StringBuilder(columna.length);
        for(int i= 0; i < columna.length ; i++){
            sb.append(columna[i]);
            sb.append("\n");
        }
        return sb.toString();
    }
}
