package org.aldob.java.jdbc;

import org.aldob.java.jdbc.models.Producto;
import org.aldob.java.jdbc.repository.ProductoRepository;
import org.aldob.java.jdbc.repository.Repository;
import org.aldob.java.jdbc.util.ConexionBD;

import java.sql.*;

public class EjemploDelete {
    public static void main(String[] args) {

        try (Connection conn = ConexionBD.getConexion()) {

            Repository<Producto> repository = new ProductoRepository();
            System.out.println("============ Productos ============");
            repository.findAll().forEach(System.out::println);

            System.out.println("============ Obtener por id ============");
            System.out.println(repository.porId(1L));

            System.out.println("============ Insertar ============");
            repository.delete(5L);
            System.out.println("Producto eliminado con exito");

            System.out.println("============ Actualizado ============");
            repository.findAll().forEach(System.out::println);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
