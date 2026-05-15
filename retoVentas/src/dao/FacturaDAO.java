package dao;

import modelos.Factura;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO implements GenericDAO<Factura> {

	public boolean insertar(Factura objeto) {
		String sql = "INSERT INTO factura (fecha, id_cliente, id_empleado, subtotal, iva, total) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection con = ConexionBD.getConnection();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setDate(1, objeto.getFecha());
			ps.setInt(2, objeto.getIdCliente());
			ps.setInt(3, objeto.getIdEmpleado());
			ps.setDouble(4, objeto.getSubtotal());
			ps.setDouble(5, objeto.getIva());
			ps.setDouble(6, objeto.getTotal());

			if (ps.executeUpdate() > 0) {
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						objeto.setId(rs.getInt(1)); // Asigna el ID [cite: 160]
						return true;
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Error insertando factura: " + e.getMessage());
		}
		return false;
	}

	public List<Factura> obtenerTodos() {
		List<Factura> lista = new ArrayList<>();
		String sql = "SELECT * FROM factura";
		try (Connection con = ConexionBD.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next())
				lista.add(mapear(rs));
		} catch (SQLException e) {
			System.err.println("Error obteniendo facturas: " + e.getMessage());
		}
		return lista;
	}

	public Factura obtenerPorId(int id) {
		String sql = "SELECT * FROM factura WHERE id=?";
		try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return mapear(rs);
			}
		} catch (SQLException e) {
			System.err.println("Error buscando factura: " + e.getMessage());
		}
		return null;
	}

	public boolean actualizar(Factura objeto) {
		String sql = "UPDATE factura SET fecha=?, id_cliente=?, id_empleado=?, subtotal=?, iva=?, total=? WHERE id=?";
		try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setDate(1, objeto.getFecha());
			ps.setInt(2, objeto.getIdCliente());
			ps.setInt(3, objeto.getIdEmpleado());
			ps.setDouble(4, objeto.getSubtotal());
			ps.setDouble(5, objeto.getIva());
			ps.setDouble(6, objeto.getTotal());
			ps.setInt(7, objeto.getId());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error actualizando factura: " + e.getMessage());
		}
		return false;
	}

	public boolean eliminar(int id) {
		String sql = "DELETE FROM factura WHERE id=?";
		try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error eliminando factura: " + e.getMessage());
		}
		return false;
	}

	public List<Factura> obtenerPorMes(int mes) {
		List<Factura> lista = new ArrayList<>();
		String sql = "SELECT * FROM factura WHERE MONTH(fecha) = ?";
		try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, mes);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next())
					lista.add(mapear(rs));
			}
		} catch (SQLException e) {
			System.err.println("Error buscando facturas por mes: " + e.getMessage());
		}
		return lista;
	}

	public List<Factura> obtenerPorEmpleado(int idEmpleado) {
		List<Factura> lista = new ArrayList<>();
		String sql = "SELECT * FROM factura WHERE id_empleado = ?";
		try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idEmpleado);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next())
					lista.add(mapear(rs));
			}
		} catch (SQLException e) {
			System.err.println("Error buscando facturas por empleado: " + e.getMessage());
		}
		return lista;
	}

	private Factura mapear(ResultSet rs) throws SQLException {
		return new Factura(rs.getInt("id"), rs.getDate("fecha"), rs.getDouble("subtotal"), rs.getDouble("iva"),
				rs.getDouble("total"), rs.getInt("id_cliente"), rs.getInt("id_empleado"));
	}
}
