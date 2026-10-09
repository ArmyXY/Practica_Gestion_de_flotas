/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iescavanilles.practica_integradora_ud1.dao;

import com.iescavanilles.practica_integradora_ud1.model.Vehiculo;
import java.io.File;
import java.io.IOException;
import java.lang.classfile.Attributes;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Gestor encargado de la generacion de informes XML jerarquicos (DOM) y del
 * procesamiento secuencial por eventos (SAX) para la auditoria de mantenimiento.
 * 
 * @author Miguel Ángel Sánchez Martínez
 */
public class GestorReportesXML {
    //Atributos
    private static final String RUTA_XML = "datos/reports/informe_flota.xml";
    
    //Constructor
    public GestorReportesXML(){}
    
    //Métodos Públicos
    /**
     * Construye y exporta un documento XML estructurado jerarquicamente a partir
     * de la lista de vehiculos en memoria empleando la tecnologia DOM.
     * 
     * @param listaVehiculos Coleccion de objetos Vehiculo a incluir en el reporte
     */
    public void generarReporteDOM(List<Vehiculo> listaVehiculos){
        File dir = new File("datos/reports");
        
        if(dir.mkdirs()){
            System.out.println("El directorio se creo satisfactoriamente");
        }else if(dir.exists()){
            System.out.println("El directorio ya existe");
        }else{
            System.out.println("Error a crear el directorio");
            return;
        }
        
        try{
           //Crear documento DOM en blanco
           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
           DocumentBuilder builder = factory.newDocumentBuilder();
           Document doc = builder.newDocument();
           
           //Elemento raíz
           Element raiz = doc.createElement("flota");
           doc.appendChild(raiz);
           
           //Recorremos la lista y creamos la jerarquía para cada vehiculo
           for(Vehiculo v: listaVehiculos){
               Element vehiculoElem = doc.createElement("vehiculo");
               
               //Atributo o subelementos
               crearYAgregarElemento(doc, vehiculoElem, "matricula", v.getMatricula());
               crearYAgregarElemento(doc, vehiculoElem, "marca", v.getMarca());
               crearYAgregarElemento(doc, vehiculoElem, "modelo",v.getModelo());
               crearYAgregarElemento(doc, vehiculoElem, "año", String.valueOf(v.getAnyo()));
               crearYAgregarElemento(doc, vehiculoElem, "kilometraje", String.valueOf(v.getKm()));
               crearYAgregarElemento(doc, vehiculoElem, "categoria_mantenimiento", v.getCatMant());
               
               raiz.appendChild(vehiculoElem);
           }
           
           //Transformar el árbol DOM en un archivo físico XML formateado
           TransformerFactory transformerFactory = TransformerFactory.newInstance();
           Transformer transformer = transformerFactory.newTransformer();
           
           //Indentar para que el XML sea legible (pretty print)
           transformer.setOutputProperty(OutputKeys.INDENT, "yes");
           transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
           
           DOMSource source  = new DOMSource(doc);
           StreamResult result = new StreamResult(new File(RUTA_XML));
           transformer.transform(source, result);
           
        }catch(Exception e){
            System.out.println(e.getMessage());//Exception general, pues no se tratar la que pide
        }
    }
    
    private void crearYAgregarElemento(Document doc, Element padre, String nombreEtiqueta, String valor){
        if(valor != null){
            Element elem = doc.createElement(nombreEtiqueta);
            elem.appendChild(doc.createTextNode(valor));
            padre.appendChild(elem);
        }
    }
    /**
     * Procesa secuencialmente el archivo XML mediante SAX y recopila.
     * todas las categorias de mantenimiento en un HashSet para prevenir duplicados
     * 
     * @return Conjunto de Sets con las categorias de mantenimiento unicas encontradas
     */
    public Set<String> procesarCategoriasSAX(){
        Set<String> categoriasUnicas = new HashSet<>();
        File  file = new File(RUTA_XML);
        
        if(!file.exists()){
            System.out.println("El archivo XML no existe");
            return categoriasUnicas;
        }else{
            System.out.println("El archivo fue encontrado");
        };
        
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            //Manejador de eventos SAX personalizado
            DefaultHandler handler = new DefaultHandler(){
                private boolean esCategoria = false;
                private StringBuilder contenidoTexto = new StringBuilder();
                
                //Se dispara al abrir una etiqueta
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
                    if(qName.equalsIgnoreCase("categoria_mantenimiento")){
                        esCategoria = true;
                        contenidoTexto.setLength(0);//Limpiar buffer
                    }
                }
                
                //Captura los caracteres dentro de la etiqueta
                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    if (esCategoria) {
                        contenidoTexto.append(ch, start, length);
                    }
                }
                
                //Se dispara al cerrar una etiqueta
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("categoria_mantenimiento")) {
                        String categoria = contenidoTexto.toString().trim();
                        if (!categoria.isEmpty()) {
                            // Al ser un HashSet, descarta duplicados automáticamente
                            categoriasUnicas.add(categoria);
                        }
                        esCategoria = false;
                    }
                }
            };
            
            //Iniciar el procesado
            saxParser.parse(file, handler);
            System.out.println("Lectura SAX finalizada. Categorias unicas detectadas: " + categoriasUnicas.size());
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return categoriasUnicas;
    }
    //Métodos Privados
}
