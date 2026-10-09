/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iescavanilles.practica_integradora_ud1.model;

import java.io.Serializable;
import java.util.Objects;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author army1
 */
public class Vehiculo implements Serializable{
    //Atributos
    private String matricula;
    private String marca;
    private String modelo;
    private int anyo;
    @SerializedName("kms_recorridos")
    private double km;
    private String catMant;
    
    //Constructores
    public Vehiculo() {
    }

    public Vehiculo(String matricula, String marca, String modelo, int anyo, double km, String catMant) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.anyo = anyo;
        this.km = km;
        this.catMant = catMant;
    }
    
    //Getters y Setters
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnyo() {
        return anyo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public String getCatMant() {
        return catMant;
    }

    public void setCatMant(String catMant) {
        this.catMant = catMant;
    }
    
    //Métodos Públicos
    
    //Equal y hashCode
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.matricula);
        hash = 47 * hash + Objects.hashCode(this.marca);
        hash = 47 * hash + Objects.hashCode(this.modelo);
        hash = 47 * hash + this.anyo;
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.km) ^ (Double.doubleToLongBits(this.km) >>> 32));
        hash = 47 * hash + Objects.hashCode(this.catMant);
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
        final Vehiculo other = (Vehiculo) obj;
        if (this.anyo != other.anyo) {
            return false;
        }
        if (Double.doubleToLongBits(this.km) != Double.doubleToLongBits(other.km)) {
            return false;
        }
        if (!Objects.equals(this.matricula, other.matricula)) {
            return false;
        }
        if (!Objects.equals(this.marca, other.marca)) {
            return false;
        }
        if (!Objects.equals(this.modelo, other.modelo)) {
            return false;
        }
        return Objects.equals(this.catMant, other.catMant);
    }
    
    //toString
    @Override
    public String toString() {
        return "Vehiculo{" + "matricula=" + matricula + ", marca=" + marca + ", modelo=" + modelo + ", anyo=" + anyo + ", km=" + km + ", catMant=" + catMant + '}';
    }
    
    //Métodos Privados
}
