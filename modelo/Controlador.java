package modelo;

import client.IVista;

import client.Parser;

import errors.Error000;

import errors.Error001;
import errors.Error002;

import errors.Error003;
import errors.Error004;

import errors.Error005;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.util.ArrayList;

import java.util.HashMap;

import javax.swing.JFrame;

public class Controlador implements ActionListener
{
    public static final String[] Comandos = {"LISTAR","USAR","SUMAR","RESTAR","FRECUENCIA","PROMEDIO","HISTOGRAMA","MODA"};
    public static final String[] Salidas = {"PANTALLA","IMPRESORA"};
    public static final int MAXCARFILES = 100;
    private IVista ventana;
    private Parser parser = new Parser(); //ventana.getJTFComandos()
    private Calculador calc = new Calculador();
    HashMap<String, ConjuntoDatos> hashmap = new HashMap<String, ConjuntoDatos>();
    
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
    
    public void ejecutarComandos(ArrayList<String> lineas) throws Error000, Error001,Error002, Error003, Error004,Error005
    {
        for(int i=0; i<lineas.size(); i++) //cada iteracion es el comando siguiente
        {
            ArrayList<String> lineaActual = parser.obtenerTokens(lineas.get(i)); // aca se envia el comando correspondiente a esta vuelta para devolverlo parseado
            
            String resString=null;
            
            if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[0])) //aca entra si tiene que listar
            {
                this.ventana.getVentanaResultados().agregaResultado("Lista de archivos disponibles:\n");
                resString = this.listarArchivos();
                this.ventana.getVentanaResultados().agregaResultado(resString);
            }
            else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[1])) //aca entra si es usar
            {
                if((lineaActual.get(1)!= null) && (isConjuntoExistente(lineaActual.get(1)))) //verifico primero error de sintaxis y luego si existe el conj.
                {
                    if(hashmap.containsKey(lineaActual.get(1)))
                    {
                        //marcar conjunto de datos, para trabajar siempre con ese.
                    }
                    else
                    {
                        //abrirConjuntoDatos();
                    }
                }
                else if((lineaActual.get(1)!= null))
                {
                    throw new Error000();
                }
                else
                    throw new Error006(); // Error006: conjunto de datos inexistente
            }
            else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[2]) || 
                    lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[3])) //entro si es suma o resta
            {
                    if((lineaActual.get(1)!= null) && !isSalidaValida(lineaActual.get(1)) &&
                       (lineaActual.get(2)!= null) && !isSalidaValida(lineaActual.get(2)) &&
                       (lineaActual.get(3)!= null) ) //verifico la sintaxis
                    {
                        if(isSalidaValida(lineaActual.get(3))){ //verifico la salida valida
                            //VERIFICAR SI SON DATOS NUMERICOS!
                            
                            double[] columna1= /*VER QUE PONER ACA */.getColumna(lineaActual.get(1));
                            double[] columna2= /*VER QUE PONER ACA */.getColumna(lineaActual.get(2));
                            
                            if(isColumnaExistente(columna1) && isColumnaExistente(columna2)) //aca tendrian que venir las dos columnas
                            {
                                if(isDatosNoFaltantes(columna1) && isDatosNoFaltantes(columna2))
                                {
                                    if(isDimensionesIguales(columna1,columna2)) //si es todo valido
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
                                        throw new Error004();
                                }
                                else
                                    throw new Error005();
                            }
                            else
                                throw new Error003();
                            //verificar otros errores (columnas validas,etc)
                        }
                        else
                            throw new Error002();
                    }
                    else
                        throw new Error000();
            }
            else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[4])) // entra si es frecuencia
            {
                //FRECUENCIA
            }
            else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[5]) || 
                    lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[6]) ||
                    lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[7]))  //entra si es promedio histograma o moda
            {
                if((lineaActual.get(1)!= null) && !isSalidaValida(lineaActual.get(1)) &&
                   (lineaActual.get(2)!= null)) //verifico sintaxis
                {
                    if(isSalidaValida(lineaActual.get(2))) //verifico salida valida
                    {
                        //OBTENER COLUMNA SEGUN SEA NRO A STRING!
                        if(isColumnaExistente(columna))
                        {
                            if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[5])) //aca entra si es promedio
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
                            else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[6])) //aca entra si es histograma
                            {
                                //LLAMAR A HISTOGRAMA SEGUN EL CONJUNTO DE DATOS Y GUARDAR EN resString!
                                this.ventana.getVentanaResultados().agregaResultado(resString);
                            }
                            else //aca entra si es moda
                            {
                                //LLAMAR A MODA SEGUN EL CONJUNTO DE DATOS Y GUARDAR EN resString!
                                this.ventana.getVentanaResultados().agregaResultado(resString);
                            }
                        }
                        else
                            throw new Error003();
                        }
                    else
                        throw new Error002();
                }
                else
                    throw new Error000();            
            }
            else
                throw new Error001();
        }
    }
    
    /*public boolean existeComando(String comando)
    {
        int i=0;
        boolean existe = (comando.equalsIgnoreCase(Controlador.Comandos[0]));
        while(!existe && (i<Controlador.Comandos.length))
        {
            i++;
            existe = (comando.equalsIgnoreCase(Controlador.Comandos[i]));
        }
        return existe;
    }*/
    
    public boolean isSalidaValida(String token)
    {
        int i=0;
        boolean retorno = token.equalsIgnoreCase(Controlador.Salidas[0]);;
        /*while(i < Controlador.Salidas.length)
        {
            i++;
            retorno = token.equalsIgnoreCase(Controlador.Salidas[i]); ESTO NO IRIA
        }*/
        return retorno;
    }
    
    public boolean isConjuntoExistente(String nombre)
    {
        //habria que buscar en el contenedor de conjuntos si existe el pedido
    }
    
    public boolean isColumnaExistente(double[] columna)
    {
        //aca habria que llamar al cant col del conjuntoDatos
    }
    
    public boolean isDatosNoFaltantes(double[] columna)
    {
        boolean res=false;
        int i=0;
        
        while( i<columna.length && res == false ){
            res = columna[i] == null;
        }
        
        return res;
    }
    
    public boolean isDimensionesIguales(double[] columna1, double[] columna2)
    {
        return columna1.length == columna2.length;
    }
    
    public boolean isDatosNumericos()
    {
        
    }
    
    public String listarArchivos()
    {
        StringBuilder sb = new StringBuilder(Controlador.MAXCARFILES);
        File curDir = new File(".");
        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            if(f.isFile()){
                sb.append(f.getName());
                sb.append("\n");
            }
        }
        return sb.toString();
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
