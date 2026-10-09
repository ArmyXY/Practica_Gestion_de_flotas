/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iescavanilles.practica_integradora_ud1.dao;

import com.iescavanilles.practica_integradora_ud1.model.Vehiculo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Gestor encargado de la exportación e importación del inventario de vehiculos
 * en formato JSON usando la libreria de Google (GSON).
 * 
 * @author Miguel Ángel Sánchez Martínez
 */
public class GestorInventarioJSON {
    //Atributos
    private static final String RUTA_JSON = "datos/json/flota.json";
    private List<Vehiculo> listaVehiculos = new ArrayList<>();
    private Map<String, Vehiculo> mapaVehiculos = new HashMap<>();
    private final Gson gson;
    
    //Constructor
    public GestorInventarioJSON() {
        this.gson = new GsonBuilder().setPrettyPrinting().create(); //Crea el Gson con sangrado/formato bonito
    }

    public GestorInventarioJSON(List<Vehiculo> listaVehiculos, Map<String, Vehiculo> mapaVehiculos) {
        this.listaVehiculos = listaVehiculos;
        this.mapaVehiculos = mapaVehiculos;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public GestorInventarioJSON(List<Vehiculo> listaVehiculos, Map<String, Vehiculo> mapaVehiculos, Gson gson) {
        this.listaVehiculos = listaVehiculos;
        this.mapaVehiculos = mapaVehiculos;
        this.gson = gson;
    }
    
    //Getters y Setters
    public List<Vehiculo> getListaVehiculos() {
        return listaVehiculos;
    }

    public void setListaVehiculos(List<Vehiculo> listaVehiculos) {
        this.listaVehiculos = listaVehiculos;
    }

    public Map<String, Vehiculo> getMapaVehiculos() {
        return mapaVehiculos;
    }

    public void setMapaVehiculos(Map<String, Vehiculo> mapaVehiculos) {
        this.mapaVehiculos = mapaVehiculos;
    }
    
    //Métodos Públicos
    /**
     * Exporta la lista completa de vehiculos a un fichero JSON
     * 
     * @param vehiculos Lista de objetos a exportar.
     */
    public void exportarJSON(List<Vehiculo> vehiculos){
        File dir = new File("datos/json");
        
        if(dir.mkdirs()){
            System.out.println("Directorio creado con exito");
        }else if(dir.exists()){    
            System.out.println("Directorio ya existente");
        }else{
            System.out.println("Error al crear el directorio");
            return;
        }
        
        try(FileWriter out = new FileWriter(RUTA_JSON, false)){
            gson.toJson(vehiculos, out);
            System.out.println("Flota Exportada correctamente");
            System.out.println("Ruta de Exportación: " + RUTA_JSON);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * Importa los vehiculos desde el archivo JSON y llena las listas dinamicas
     * de vehiculos, para optimizar las busquedas posteriores se usa HashMap (Clave-Valor).
     * 
     */
    public void importarJSON(){
        File file = new File(RUTA_JSON);
        
        if(file.exists()){
            System.out.println("Archivo JSON encontrado");
        }else{
            System.out.println("Error al buscar el archivo JSON");
            return;
        }
        
        try(FileReader fr = new FileReader(file)){
            //Definimos el tipo genérico List<Vehiculo> para que Gson sepa qué deserializar
            Type tipoLista = new TypeToken<ArrayList<Vehiculo>>(){}.getType();
            //Deserializamos el JSON a un List<Vehiculo>
            List<Vehiculo> vehiculosLeidos = gson.fromJson(fr, tipoLista);
            
            //Reiniciamos las estructuras de datos locales
            this.listaVehiculos.clear();
            this.mapaVehiculos.clear();
            
            if(vehiculosLeidos != null){
                for(Vehiculo v : vehiculosLeidos){
                    //Añadir a lista
                    this.listaVehiculos.add(v);
                    
                    //Añadimos al HashMap usando la matricula como clave
                    if(v.getMatricula() != null){
                        this.mapaVehiculos.put(v.getMatricula(), v);
                    }
                }
            }
            
            System.out.println("Importación completada");
            System.out.println("Vehiculos Cargados: " + listaVehiculos.size());
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * Realiza la búsqueda instantanea de un vehiculo segun su matricula usando
     * el HashMap.
     * 
     * @param matricula Cadena con la matricula del vehicula a buscar
     * @return El objeto tipo Vehiculo encontrado o null si no existe.
     */
    // Busqueda por matricula tiempo 0(1) via HashMap
    public Vehiculo buscarPorMatricula(String matricula){
        return this.mapaVehiculos.get(matricula);
    }
    
    //equals y hashCode
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.listaVehiculos);
        hash = 53 * hash + Objects.hashCode(this.mapaVehiculos);
        hash = 53 * hash + Objects.hashCode(this.gson);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GestorInventarioJSON other = (GestorInventarioJSON) obj;
        if (!Objects.equals(this.listaVehiculos, other.listaVehiculos)) {
            return false;
        }
        if (!Objects.equals(this.mapaVehiculos, other.mapaVehiculos)) {
            return false;
        }
        return Objects.equals(this.gson, other.gson);
    }
    
    //toString
    @Override
    public String toString() {
        return "GestorInventarioJSON{" + "listaVehiculos=" + listaVehiculos + ", mapaVehiculos=" + mapaVehiculos + ", gson=" + gson + '}';
    }
    
    //Métodos Privados
}
