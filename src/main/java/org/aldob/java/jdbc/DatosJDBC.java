package org.aldob.java.jdbc;

import org.aldob.java.jdbc.models.Producto;
import org.aldob.java.jdbc.repository.ProductoRepository;
import org.aldob.java.jdbc.repository.Repository;
import org.aldob.java.jdbc.util.ConexionBD;

import java.sql.*;

public class DatosJDBC {
    public static void main(String[] args) {

            try (Connection conn = ConexionBD.getConexion()) {

                Repository<Producto> repository = new ProductoRepository();
                System.out.println("============ Productos ============");
                repository.findAll().forEach(System.out::println);

                System.out.println("============ Obtener por id ============");
                System.out.println(repository.porId(1L));

                System.out.println("============ Insertar ============");
                Producto producto = new Producto();
                producto.setNombre("Camara");
                producto.setPrecio(300);
                repository.save(producto);
                System.out.println("Producto guardado con exito");

                System.out.println("============ Actualizado ============");
                repository.findAll().forEach(System.out::println);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }
}
