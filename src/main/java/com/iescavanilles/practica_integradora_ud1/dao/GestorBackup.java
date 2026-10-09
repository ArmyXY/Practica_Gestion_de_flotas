/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iescavanilles.practica_integradora_ud1.dao;

import com.iescavanilles.practica_integradora_ud1.model.Vehiculo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor encargado de la serialización y deserialización de la flota de vehiculos.
 * 
 * 
 * @author Miguel Ángel Sánchez Martínez
 */
public class GestorBackup {
    //Atributos
    private static final String RUTA_BACKUP = "datos/backup/flota_backup.ser";
    
    //Constructor
    public GestorBackup() {
    }
    
    //Métodos Públicos
    /**
     * Genera un respaldo binario de la coleccion completa de vehiculos
     * 
     * @param listaVehiculos Coleccion que contiene objetos a serializar
     */
    public void realizarBackup(List<Vehiculo> listaVehiculos){
        File dir = new File("datos/backup");
        
        if(dir.mkdirs()){
            System.out.println("Se creo los directorios");
        }else if(dir.exists()){
            System.out.println("Directorios ya existentes");
        }else{
            System.out.println("Error al crear los directorios");
        }
        
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_BACKUP))){
            oos.writeObject(listaVehiculos);
            System.out.println("Backup realizado con exito");
            System.out.println("Backup guadado en: " + RUTA_BACKUP);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * Restaura la coleccion de vehiculos mediante deserializacion de objetos desde
     * un archivo .ser.
     * 
     * @return Lista de objetos Vehiculos recuperados desde el respaldo binario.
     */
    public List<Vehiculo> restaurarBackup(){
        File file = new File(RUTA_BACKUP);
        
        if(!file.exists()){
            System.out.println("Error a restaurar el backup, no se encontro el archivo");
            return new ArrayList<>();
        }
        
        List<Vehiculo> listaRestaurada = new ArrayList<>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            listaRestaurada = (List<Vehiculo>) ois.readObject();
            System.out.println("Backup restaurado con exito");
            System.out.println("Vehiculos Recuperados: " + listaRestaurada.size());
        }catch(ClassNotFoundException a){
            System.out.println(a.getMessage());
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return listaRestaurada;
    }
    
    //Métodos Privados
}
