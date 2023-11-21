package org.aldob.java.jdbc.repository;

import org.aldob.java.jdbc.models.Producto;
import org.aldob.java.jdbc.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepository implements Repository<Producto> {

    private Connection getConnection()throws SQLException {
        return ConexionBD.getConexion();
    }

    @Override
    public List<Producto> findAll() {
        List<Producto> productos = new ArrayList<>();
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM productos")) {
            while(rs.next()) {
                productos.add(new Producto(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getInt("precio")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productos;
    }

        @Override
    public Producto porId(Long id) {
        Producto producto = null;

        try (PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM productos WHERE id = ?"))
        {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                producto = new Producto(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getInt("precio")
                );
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return producto;
    }

    @Override
    public void save(Producto producto) {
        String sql;
        if (producto.getId() != null && producto.getId() > 0) {
            sql = "UPDATE productos SET nombre = ?, precio = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO productos (nombre, precio) VALUES (?, ?)";
        }
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setInt(2, producto.getPrecio());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
