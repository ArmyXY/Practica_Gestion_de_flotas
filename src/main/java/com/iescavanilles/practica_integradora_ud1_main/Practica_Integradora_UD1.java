/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.iescavanilles.practica_integradora_ud1_main;

import com.iescavanilles.practica_integradora_ud1.dao.GestorInventarioJSON;
import com.iescavanilles.practica_integradora_ud1.dao.GestorReportesXML;
import com.iescavanilles.practica_integradora_ud1.dao.GestorBackup;
import com.iescavanilles.practica_integradora_ud1.dao.GestorConfiguracion;
import com.iescavanilles.practica_integradora_ud1.model.Vehiculo;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author army1
 */
public class Practica_Integradora_UD1 {

    public static void main(String[] args) {
        //Crear los ficheros/directorios con NIO 2.0
        //Rutas
        Path configDir = Paths.get("datos/config");
        Path jsonDir = Paths.get("datos/json");
        Path backupDir = Paths.get("datos/backup");
        Path reportsDir = Paths.get("datos/reports");
        
        //Creación
        try{
            Files.createDirectories(configDir);
            Files.createDirectories(jsonDir);
            Files.createDirectories(backupDir);
            Files.createDirectories(reportsDir);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        //Prueba de GestorConf
        GestorConfiguracion config = new GestorConfiguracion("Pepe SL", "1.1.1", 99, 2020.20d);
        config.guardarConf();
        config.leerConf();
        
        //Prueba de GestorInventarioJSON
        Vehiculo v1 = new Vehiculo("1234ABC", "Seat", "Ibiza", 2020, 45000.5, "A");
        Vehiculo v2 = new Vehiculo("5678DEF", "Toyota", "Corolla", 2022, 18200.0, "B");
        Vehiculo v3 = new Vehiculo("9012GHI", "Volvo", "FH16", 2019, 230500.8, "C");
            //Añadir a la lista
        List<Vehiculo> listaOG = new ArrayList<>();
        listaOG.add(v1);
        listaOG.add(v2);
        listaOG.add(v3);
        
            //Crear el objeto GestorInventario
        GestorInventarioJSON gestorJSON = new GestorInventarioJSON();
            //Exportar datos
        gestorJSON.exportarJSON(listaOG);
            //Importar datos
        gestorJSON.importarJSON();
            //Prueba de busqueda 0(1)
        String matriculaB = "5678DEF";
        String matriculaBM = "5789AFD";
        Vehiculo vehiculoEncontrado = gestorJSON.buscarPorMatricula(matriculaB);
        Vehiculo vehiculoNoEncontrado = gestorJSON.buscarPorMatricula(matriculaBM);
        
        if(vehiculoEncontrado != null){
            System.out.println("Vehiculo encontrado");
        }else{
            System.out.println("Error al encontrar el vehiculo");
        }
        
        if(vehiculoNoEncontrado != null){
            System.out.println("Vehiculo encontrado");
        }else{
            System.out.println("Error al encontrar el vehiculo");
        }
        
        //Prueba GestorBackup
        GestorBackup gestorBackup = new GestorBackup();
            //Realizar un backup
        gestorBackup.realizarBackup(listaOG);
            //Restaurar lista
        List<Vehiculo> flotaRecuperada = gestorBackup.restaurarBackup();
            //Imprimir los vehiculos recuperados
        for(Vehiculo v : flotaRecuperada){
            System.out.println("Recuperado: " + v);
        }
        
        //Prueba GestorReportesXML
            //Preparamos datos de prueba (con categorías repetidas para probar el HashSet de SAX)
        List<Vehiculo> listaParaXML = new ArrayList<>();
        listaParaXML.add(new Vehiculo("1234ABC", "Seat", "Ibiza", 2020, 45000.5, "A"));
        listaParaXML.add(new Vehiculo("5678DEF", "Toyota", "Corolla", 2022, 18200.0, "B"));
        listaParaXML.add(new Vehiculo("9012GHI", "Volvo", "FH16", 2019, 230500.8, "C"));
        listaParaXML.add(new Vehiculo("3456JKL", "Renault", "Clio", 2021, 60000.0, "A")); // Categoría "A" repetida

            //Instanciamos el GestorReportesXML
        GestorReportesXML gestorXML = new GestorReportesXML();

            //Probar Generación DOM
        System.out.println("Generando informe XML con DOM");
        gestorXML.generarReporteDOM(listaParaXML);

            //Probar Lectura SAX
        System.out.println("Procesando archivo XML con SAX");
        Set<String> categoriasUnicas = gestorXML.procesarCategoriasSAX();

            //Imprimir el resultado de las categorías únicas
        System.out.println("Categorias de mantenimiento unicas encontradas: " + categoriasUnicas);
        
        
    }
}
