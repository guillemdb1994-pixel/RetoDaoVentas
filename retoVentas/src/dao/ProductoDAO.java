package dao;
import modelos.Producto;

import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements GenericDAO<Producto> {

    public boolean insertar(Producto objeto) {
        String sql = "INSERT INTO producto (nombre, precio, stock) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, objeto.getNombre());
            ps.setDouble(2, objeto.getPrecio());
            ps.setInt(3, objeto.getStock());
            
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        objeto.setId(rs.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error insertando producto: " + e.getMessage());
        }
        return false;
    }

    public List<Producto> obtenerTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error obteniendo productos: " + e.getMessage());
        }
        return lista;
    }

    public Producto obtenerPorId(int id) {
        String sql = "SELECT * FROM producto WHERE id=?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscando producto por id: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(Producto objeto) {
        String sql = "UPDATE producto SET nombre=?, precio=?, stock=? WHERE id=?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, objeto.getNombre());
            ps.setDouble(2, objeto.getPrecio());
            ps.setInt(3, objeto.getStock());
            ps.setInt(4, objeto.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizando producto: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM producto WHERE id=?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminando producto: " + e.getMessage());
        }
        return false;
    }

    public Producto obtenerPorNombre(String nombre) {
        String sql = "SELECT * FROM producto WHERE nombre = ? LIMIT 1";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error buscando producto por nombre: " + e.getMessage());
        }
        return null;
    }

    private Producto mapear(ResultSet rs) throws SQLException {
        return new Producto(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("stock"));
    }
}
