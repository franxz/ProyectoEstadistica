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
    /**
     * @param Comandos Vector que contiene Strings con los posibles comandos a invocar.<br>
     * @param Salidas Vector que contiene Strings con las posibles salidas.<br>
     * @param Ventana Referencia a la ventana.<br>
     * @param conjuntoDatos Contenedor de los conjuntos relevados.<br>
     * @param conjuntoActivo Referencia al conjunto que se esta utilizando.<br>
     */
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

    /**
     * Este metodo se encarga de la ejecucion de los comandos pedidos por el usuario y de lanzar los errores correspondientes si los hay.<br>
     * 
     * <b>pre:</b> Los strings estan separados correctamente para su analisis. <br>
     * <b>post:</b> Se ejecuto la secuencia de comandos. <br>
     * @param lineas Contenedor con los Strings de comandos a ejecutar. <br>
     * 
     */
    public void ejecutarComandos(ArrayList<String> lineas)
    {
        Object[] columna;
        String resString=null;
        try
        {
            for(int i=0; i<lineas.size(); i++) //cada iteracion es el comando siguiente
            {
                HashMap<Integer, String> lineaActual = Parser.obtenerTokens(lineas.get(i)); // aca se envia el comando correspondiente a esta vuelta para devolverlo parseado
                
                if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[0])) //aca entra si tiene que listar
                {
                    this.ventana.informarAlUsuario("Lista de archivos disponibles:\n");
                    this.ventana.informarAlUsuario(this.listarArchivos());
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
                            File archivoActual = this.abrirArchivo(lineaActual.get(1)+".dat");
                            if(archivoActual != null) //si está en la carpeta
                            {
                                conjuntoActivo = ParserArchivo.obtenerConjuntoDatos(archivoActual);
                                this.conjuntosDatos.put(lineaActual.get(1), conjuntoActivo);
                            }
                            else
                                throw new CorteEjecucion("No se pudo abrir el archivo, o no existe el archivo.");
                        }
                        this.ventana.informarAlUsuario("Usando "+lineaActual.get(1));
                    }
                    else
                        throw new CorteEjecucion("Error de sintaxis, debe indicar el nombre del conjunto de datos");
                }
                else 
                {
                    if (conjuntoActivo != null)
                    {
                        if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[2]) || 
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
                                                    if(!isDatosNoFaltantes(columna1) && !isDatosNoFaltantes(columna2))
                                                    {
                                                        Double[] res= null;
                                                        if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[2])) //suma
                                                            res = Calculador.suma(columna1,columna2);
                                                        else //resta
                                                            res = Calculador.resta(columna1,columna2);
                                                        this.conjuntoActivo.actualizarColumna(lineaActual.get(1), res);
                                                        if (lineaActual.get(3) != null)/* Esta habilitada alguna salida */
                                                        { 
                                                            if (this.isSalidaValida(lineaActual.get(3)))
                                                            {
                                                                if (lineaActual.get(0).equalsIgnoreCase(Comandos[2]))
                                                                      resString = "Suma:\n";
                                                                else
                                                                    resString = "Resta:\n";
                                                                resString += arrayToString(res);
                                                                this.ventana.informarAlUsuario(resString);
                                                            }
                                                            else
                                                                throw new CorteEjecucion("Error002: Dispositivo desconocido");
                                                        }
                                                    }
                                                    else
                                                        throw new CorteEjecucion("Datos vacios.");
                                                }
                                                else
                                                    throw new CorteEjecucion("Error: Columnas inexistentes");
        
                                                //verificar otros errores (columnas validas,etc)
                                            }
                                            else
                                                throw new CorteEjecucion("Operación no realizable: argumentos inconsistentes");
                                    }
                                    else
                                        throw new CorteEjecucion("Error de sintaxis: <suma/resta> <columna_1> <columna_2> <salida>");
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
                                        throw new CorteEjecucion("Error: Columna inexistente");
                                }
                                else
                                    throw new CorteEjecucion("Error: salida no valida");  
                            else
                                throw new CorteEjecucion("Error de sintaxis:");
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
                                                this.ventana.informarAlUsuario("Promedio: " + Double.toString(res));
                                            }
                                            else
                                                throw new CorteEjecucion("Operacion no realizable: conjunto no numerico");
                                        }
                                        else if(lineaActual.get(0).equalsIgnoreCase(Controlador.Comandos[6])) //aca entra si es histograma
                                        {
                                            if(conjuntoActivo.isNumerico())
                                                resString = Calculador.histograma((Double[]) columna);
                                            else
                                                resString = Calculador.histograma((String[]) columna);
                                            this.ventana.informarAlUsuario(resString);
                                            //this.ventana.getVentanaHistograma().agregaHistograma(resString);
                                        }
                                        else //aca entra si es moda
                                        {
                                            this.ventana.informarAlUsuario(Calculador.moda(columna));
                                        }
                                    }
                                    else
                                        throw new CorteEjecucion("Error: columna inexistente");
                                }
                                else
                                    throw new CorteEjecucion("Error: salida no valida");
                            }
                            else   
                                throw new CorteEjecucion("Error de sintaxis");    
                        }
                        else
                            throw new CorteEjecucion("Error: operacion no conocida");
                    }
                    else
                        throw new CorteEjecucion("Error: no hay un conjunto activo seleccionado");
                }
            }
            this.ventana.informarAlUsuario("*** LA EJECUCION HA TERMINADO CON EXITO ***");
        }
        catch(CorteEjecucion e)
        {
            this.ventana.informarAlUsuario(e.getMessage() + "\n*** ERROR - LA EJECUCION SE HA INTERRUMPIDO ***");
        }

    }


    /**
     * El metodo valida que el String que llego corresponga a una salida valida.<br>
     * 
     * @param token String a validar.<br>
     * @return Devuelve true en caso de token coincidir con una salida valida, en caso contrario false.<br>
     */
    public boolean isSalidaValida(String token)
    {
        return token.equalsIgnoreCase(Controlador.Salidas[0]);
    }

    /**
     * El metodo valida que no haya datos vacios en las columnas.<br>
     * 
     * @param columna Array con los valores de la columna.<br>
     * @return True si ningun valor es vacio, en caso contrario false.<br>
     */
    public boolean isDatosNoFaltantes(Double[] columna)
    {
        boolean res=false;
        int i=0;
        while( i<columna.length && res == false ){
            res = columna[i] == null;
            i++;
        }
        
        return res;
    }

    /**
     * Este metodo se encarga de abrir el archivo indicado por el nombre.<br>
     * 
     * @param nombre Nombre del archivo que se desea abrir.<br>
     * @return Retorna el archivo correspondiente o null en caso de no existir.<br>
     */
    public File abrirArchivo(String nombre)
    {
        File curDir = new File(".\\Datos");
        File[] filesList = curDir.listFiles();
        int i = 0;
        File retorno = null;
        while((i < filesList.length) && (retorno == null))
        {
            if(filesList[i].isFile() && filesList[i].getName().equalsIgnoreCase(nombre)){ 
                retorno = filesList[i];
            }
            i++;
        }
        return retorno;
    }

    /**
     * Este metodo crea un String con todos los archivos abiertos ordenados adecuadamente.<br>
     * 
     * @return String con la lista de archivos.<br>
     */
    public String listarArchivos()
    {
        StringBuilder sb = new StringBuilder(Controlador.MAXCARFILES);
        File curDir = new File(".\\Datos");
        File[] filesList = curDir.listFiles();
        for(File f : filesList){
              if(f.isFile()){
                sb.append(f.getName());
                sb.append("\n");
            }
        }
        return sb.toString();
    }


    /**
     * Este metodo convierte la columna de Double en un String para poder ser mostrada.<br>
     * 
     * @param columna Array con los valores de la columna.<br>
     * @return Retorna un String que se corresponde con la columna. <br>
     */
    public String arrayToString(Double[] columna)
    {
        StringBuilder sb = new StringBuilder(columna.length);
        for(int i= 0; i < columna.length ; i++){
            sb.append(columna[i]);
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * 
     * 
     * @param e
     */
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
