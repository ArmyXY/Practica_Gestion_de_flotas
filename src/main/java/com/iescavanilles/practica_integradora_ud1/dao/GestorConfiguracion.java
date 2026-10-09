/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iescavanilles.practica_integradora_ud1.dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * Gestor encargado de la escritura y lectura de la configuración global
 * 
 * @author Miguel Ángel Sánchez Martínez
 */
public class GestorConfiguracion {
    //Atributos
    private static final String RUTA_FICHERO = "datos/config/empresa.dat";
    private String nomEmp;
    private String vers;
    private int totalVehiculos;
    private double kms_acumulados;

    //Constructor
    public GestorConfiguracion() {
    }
    
    public GestorConfiguracion(String nomEmp, String vers, int totalVehiculos, double kms_acumulados) {
        this.nomEmp = nomEmp;
        this.vers = vers;
        this.totalVehiculos = totalVehiculos;
        this.kms_acumulados = kms_acumulados;
    }
    
    //Getters y Setters
    public String getNomEmp() {
        return nomEmp;
    }

    public void setNomEmp(String nomEmp) {
        this.nomEmp = nomEmp;
    }

    public String getVers() {
        return vers;
    }

    public void setVers(String vers) {
        this.vers = vers;
    }

    public int getTotalVehiculos() {
        return totalVehiculos;
    }

    public void setTotalVehiculos(int totalVehiculos) {
        this.totalVehiculos = totalVehiculos;
    }

    public double getKms_acumulados() {
        return kms_acumulados;
    }

    public void setKms_acumulados(double kms_acumulados) {
        this.kms_acumulados = kms_acumulados;
    }
    
    //Métodos Públicos
    /**
     * Como su nombre indica guarda la configuración en un archivo binario
     * 
     */
    public void guardarConf(){
        File datosDir = new File("datos/config");
            
        if(datosDir.mkdirs()){
            System.out.println("Directorios han sido creados");
        }else if(datosDir.exists()){
            System.out.println("Los Directorios ya existen");
        }else{
            System.out.println("Error al crear los directorios");
        }
        
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(RUTA_FICHERO, false))){
            dos.writeUTF(this.nomEmp);
            dos.writeUTF(this.vers);
            dos.writeInt(this.totalVehiculos);
            dos.writeDouble(this.kms_acumulados);
            
            System.out.println("Config guardado");
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Como su nombre indica leer la configuración global desde su archivo binario
     * 
     */
    public void leerConf(){
        File datosDir = new File("datos/config");
        
        if(!datosDir.exists()){
            System.out.println("Los directorios no existen, guarde primero su conf");
        }else{
            System.out.println("Directorios encontrados");
        }
        
        try(DataInputStream dis = new DataInputStream(new FileInputStream(RUTA_FICHERO))){
            while(true){
                this.nomEmp = dis.readUTF();
                this.vers = dis.readUTF();
                this.totalVehiculos = dis.readInt();
                this.kms_acumulados = dis.readDouble();
            }
        }catch(EOFException e){
            System.out.println("Se finalizo la lectura del archivo");
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    //equals y hashCode
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.nomEmp);
        hash = 43 * hash + Objects.hashCode(this.vers);
        hash = 43 * hash + this.totalVehiculos;
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.kms_acumulados) ^ (Double.doubleToLongBits(this.kms_acumulados) >>> 32));
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
        final GestorConfiguracion other = (GestorConfiguracion) obj;
        if (this.totalVehiculos != other.totalVehiculos) {
            return false;
        }
        if (Double.doubleToLongBits(this.kms_acumulados) != Double.doubleToLongBits(other.kms_acumulados)) {
            return false;
        }
        if (!Objects.equals(this.nomEmp, other.nomEmp)) {
            return false;
        }
        return Objects.equals(this.vers, other.vers);
    }

    //toString
    @Override
    public String toString() {
        return "GestorConfiguracion{" + "nomEmp=" + nomEmp + ", vers=" + vers + ", totalVehiculos=" + totalVehiculos + ", kms_acumulados=" + kms_acumulados + '}';
    }
    
    //Métodos Privados
    
}
