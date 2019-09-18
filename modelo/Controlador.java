package modelo;

import client.IVista;

import client.Parser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import java.io.File;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;

public class Controlador extends WindowAdapter implements ActionListener
{
    public static final String[] Comandos = {"LISTAR","USAR","SUMAR","RESTAR","FRECUENCIA","PROMEDIO","HISTOGRAMA","MODA"};
    public static final String[] Salidas = {"PANTALLA","IMPRESORA"};
    public static final int MAXCARFILES = 100;
    private IVista ventana;
    private HashMap<String, ConjuntoDatos> conjuntosDatos = new HashMap<String, ConjuntoDatos>();
    private ConjuntoDatos conjuntoActivo = null;
    
    public Controlador(IVista ventana)
    {
        this.ventana = ventana;
        this.ventana.addActionListener(this);
        this.ventana.addWindowListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento)
    {
        if(evento.getActionCommand().equalsIgnoreCase(IVista.EjecutarComandos))
        {
            ArrayList<String> lineas = Parser.obtenerLineas(ventana.getJTFComandos());
            this.ejecutarComandos(lineas);
        }
    }
    
    public void ejecutarComandos(ArrayList<String> lineas)
    {
        Object[] columna;
        String resString=null;
        try
        {
            for(int i=0; i<lineas.size(); i++) //cada iteracion es el comando siguiente
            {
                ArrayList<String> lineaActual = Parser.obtenerTokens(lineas.get(i)); // aca se envia el comando correspondiente a esta vuelta para devolverlo parseado
                
                if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[0])) //aca entra si tiene que listar
                {
                    this.ventana.getVentanaResultados().agregaResultado("Lista de archivos disponibles:\n");
                    resString = this.listarArchivos();
                    this.ventana.getVentanaResultados().agregaResultado(resString);
                }
                else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[1])) //aca entra si es usar
                {
                    if((lineaActual.get(1)!= null)) //verifico primero error de sintaxis y luego si existe el conj.
                    {
                        if(conjuntosDatos.containsKey(lineaActual.get(1)))
                        {
                            this.conjuntoActivo = conjuntosDatos.get(lineaActual.get(1));
                        }
                        else
                        {
                            File archivoActual = this.abrirArchivo(lineaActual.get(1));
                            if(archivoActual != null) //si está en la carpeta
                            {
                                conjuntoActivo = ParserArchivo.obtenerConjuntoDatos(archivoActual);
                            }
                            else
                            {
                                this.ventana.informarAlUsuario("No se pudo abrir el archivo, o no existe el archivo.");
                                throw new CorteEjecucion();
                            }
                                   
                        }  
                    }
                    else
                    {
                        this.ventana.informarAlUsuario("Error de sintaxis, debe indicar el nombre del conjunto de datos");
                        throw new CorteEjecucion();
                    }
                }
                else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[2]) || 
                        lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[3])) //entro si es suma o resta
                {
                        if((lineaActual.get(1)!= null) && !isSalidaValida(lineaActual.get(1)) &&
                           (lineaActual.get(2)!= null) && !isSalidaValida(lineaActual.get(2))) //verifico la sintaxis
                        {
                                if(conjuntoActivo.isNumerico())
                                {
                                    Double[] columna1= (Double[]) conjuntoActivo.getColumna(lineaActual.get(1));
                                    Double[] columna2= (Double[]) conjuntoActivo.getColumna(lineaActual.get(2));
                                    
                                    if(columna1 != null && columna2 != null) //aca tendrian que venir las dos columnas
                                    {
                                        if(isDatosNoFaltantes(columna1) && isDatosNoFaltantes(columna2))
                                        {
                                            if (columna1.length == columna2.length) //Pablo: creo que sobra este if
                                             //si es todo valido
                                            {
                                                Double[] res= null;
                                                if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[2])) //suma
                                                    res = Calculador.suma(columna1,columna2);
                                                else //resta
                                                    res = Calculador.resta(columna1,columna2);
                                                this.conjuntoActivo.actualizarColumna(lineaActual.get(1), res);
                                                if (lineaActual.get(3) != null) /* Esta habilitada alguna salida */
                                                    if (this.isSalidaValida(lineaActual.get(3)))
                                                    {
                                                        if (lineaActual.get(0).equalsIgnoreCase(Comandos[2]))
                                                            resString = "Suma:\n";
                                                        else
                                                            resString = "Resta:\n";
                                                        resString += arrayToString(res);
                                                        this.ventana.getVentanaResultados().agregaResultado(resString);
                                                    }
                                                    else
                                                    {
                                                        this.ventana.informarAlUsuario("Error002: Dispositivo desconocido");
                                                        throw new CorteEjecucion();
                                                    }
                                            }
                                            else
                                            {
                                                this.ventana.informarAlUsuario("Operacion no realizable: dimensiones distintas");
                                                throw new CorteEjecucion();
                                            }   
                                        }
                                        else
                                        {
                                            this.ventana.informarAlUsuario("Datos vacios.");
                                            throw new CorteEjecucion();
                                        }
                                    }
                                    else
                                    {
                                        this.ventana.informarAlUsuario("Error: Columnas inexistentes");
                                        throw new CorteEjecucion();
                                    }
                                    //verificar otros errores (columnas validas,etc)
                                }
                                else
                                {
                                    this.ventana.informarAlUsuario("Operación no realizable: argumentos inconsistentes");
                                    throw new CorteEjecucion();
                                }  
                        }
                        else
                        {
                            this.ventana.informarAlUsuario("Error de sintaxis: <suma/resta> <columna_1> <columna_2> <salida>");
                            throw new CorteEjecucion();
                        }
                }
                else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[4])) // entra si es frecuencia
                {
                    if((lineaActual.get(1)!= null) && !isSalidaValida(lineaActual.get(1)) && (lineaActual.get(2)!= null) 
                       && !isSalidaValida(lineaActual.get(2)) && (lineaActual.get(3)!= null)) /* verifico sintaxis */
                        if (isSalidaValida(lineaActual.get(3)))
                        {
                            columna = conjuntoActivo.getColumna(lineaActual.get(1));
                            if (columna != null)
                            {
                                resString = "Frecuencia: ";
                                if (conjuntoActivo.isNumerico())
                                    resString += Calculador.frecuenciaPorcentual(columna, Double.parseDouble(lineaActual.get(2)));
                                else
                                    resString += Calculador.frecuenciaPorcentual(columna, lineaActual.get(2));
                                resString += " %";
                                this.ventana.informarAlUsuario(resString);
                            }
                            else
                            {
                                this.ventana.informarAlUsuario("Error: Columna inexistente");
                                throw new CorteEjecucion();
                            }
                        }
                        else
                        {
                            this.ventana.informarAlUsuario("Error: salida no valida");
                            throw new CorteEjecucion();
                        }
                            
                    else
                    {
                        this.ventana.informarAlUsuario("Error de sintaxis:");
                        throw new CorteEjecucion();
                    }
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
                            columna = conjuntoActivo.getColumna(lineaActual.get(1));

                            if(columna != null)
                            {
                                if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[5])) //aca entra si es promedio
                                {
                                    if(conjuntoActivo.isNumerico())
                                    {
                                        double res = Calculador.promedio((Double[]) columna);
                                        this.ventana.getVentanaResultados().agregaResultado("Promedio: " + Double.toString(res));
                                    }
                                    else
                                    {
                                        this.ventana.informarAlUsuario("Operacion no realizable: conjunto no numerico");
                                        throw new CorteEjecucion();
                                    }
                                }
                                else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[6])) //aca entra si es histograma
                                {
                                    if(conjuntoActivo.isNumerico())
                                        resString = Calculador.histograma((Double[]) columna);
                                    else
                                        resString = Calculador.histograma((String[]) columna);
                                    this.ventana.getVentanaHistograma().agregaHistograma(resString);
                                }
                                else //aca entra si es moda
                                {
                                    this.ventana.getVentanaResultados().agregaResultado(Calculador.moda(columna));
                                }
                            }
                            else
                            {
                                this.ventana.informarAlUsuario("Error: columna inexistente");
                                throw new CorteEjecucion();
                            }
                                
                        }
                        else
                        {
                            this.ventana.informarAlUsuario("Error: salida no valida");
                            throw new CorteEjecucion();
                        }
                    }
                    else
                    {
                        this.ventana.informarAlUsuario("Error de sintaxis");     
                        throw new CorteEjecucion();
                    }     
                }
                else
                {
                    this.ventana.informarAlUsuario("Error: operacion no conocida");
                    throw new CorteEjecucion();
                }
            }
            this.ventana.informarAlUsuario("*** LA EJECUCION HA TERMINADO CON EXITO ***");
        }
        catch(CorteEjecucion e)
        {
            this.ventana.informarAlUsuario("*** ERROR - LA EJECUCION SE HA INTERRUMPIDO ***");
        }

    }
    
   
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
    
    public boolean isDatosNoFaltantes(Double[] columna)
    {
        boolean res=false;
        int i=0;
        
        while( i<columna.length && res == false ){
            res = columna[i] == null;
        }
        
        return res;
    }
    
    public File abrirArchivo(String nombre)
    {
        File curDir = new File(".");
        File[] filesList = curDir.listFiles();
        int i = 0;
        File retorno = null;
        while((i < filesList.length) && (retorno == null))
        {
            if(filesList[i].isFile() && filesList[i].getName().equalsIgnoreCase(nombre)){ 
                retorno = filesList[i];
            }
        }
        return retorno;
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
    
    
    public String arrayToString(Double[] columna)
    {
        StringBuilder sb = new StringBuilder(columna.length);
        for(int i= 0; i < columna.length ; i++){
            sb.append(columna[i]);
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
       ArrayList<ConjuntoDatosNumericos> conjuntosNumericos = new ArrayList<ConjuntoDatosNumericos>();
       Iterator<ConjuntoDatos> it = this.conjuntosDatos.values().iterator();
       while(it.hasNext()){
               ConjuntoDatos conjuntoActual = it.next();
               if(conjuntoActual.isNumerico())
                       conjuntosNumericos.add((ConjuntoDatosNumericos)conjuntoActual);
       }
       ParserArchivo.grabarDatos(conjuntosNumericos);
    }
}
