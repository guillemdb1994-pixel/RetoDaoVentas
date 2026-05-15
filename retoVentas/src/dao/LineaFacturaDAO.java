package dao;

import modelos.LineaFactura;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineaFacturaDAO implements GenericDAO<LineaFactura> {

	public boolean insertar(LineaFactura objeto) {
		String sql = "INSERT INTO lineafactura (id_factura, id_producto, cantidad, precio_unitario, importe) VALUES (?, ?, ?, ?, ?)";
		try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, objeto.getIdFactura());
			ps.setInt(2, objeto.getIdProducto());
			ps.setInt(3, objeto.getCantidad());
			ps.setDouble(4, objeto.getPrecioUnitario());
			ps.setDouble(5, objeto.getImporte());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error insertando linea de factura: " + e.getMessage());
		}
		return false;
	}

	public List<LineaFactura> obtenerTodos() {
		List<LineaFactura> lista = new ArrayList<>();
		String sql = "SELECT * FROM lineafactura";
		try (Connection con = ConexionBD.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next())
				lista.add(mapear(rs));
		} catch (SQLException e) {
			System.err.println("Error obteniendo lineas: " + e.getMessage());
		}
		return lista;
	}

	public LineaFactura obtenerPorId(int id) {
		String sql = "SELECT * FROM lineafactura WHERE id=?";
		try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return mapear(rs);
			}
		} catch (SQLException e) {
			System.err.println("Error buscando linea: " + e.getMessage());
		}
		return null;
	}

	public boolean actualizar(LineaFactura objeto) {
		String sql = "UPDATE lineafactura SET id_factura=?, id_producto=?, cantidad=?, precio_unitario=?, importe=? WHERE id=?";
		try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, objeto.getIdFactura());
			ps.setInt(2, objeto.getIdProducto());
			ps.setInt(3, objeto.getCantidad());
			ps.setDouble(4, objeto.getPrecioUnitario());
			ps.setDouble(5, objeto.getImporte());
			ps.setInt(6, objeto.getId());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error actualizando linea: " + e.getMessage());
		}
		return false;
	}

	public boolean eliminar(int id) {
		String sql = "DELETE FROM lineafactura WHERE id=?";
		try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error eliminando linea: " + e.getMessage());
		}
		return false;
	}

	public List<LineaFactura> obtenerPorFactura(int idFactura) {
		List<LineaFactura> lista = new ArrayList<>();
		String sql = "SELECT * FROM lineafactura WHERE id_factura = ?";
		try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idFactura);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next())
					lista.add(mapear(rs));
			}
		} catch (SQLException e) {
			System.err.println("Error obteniendo lineas por factura: " + e.getMessage());
		}
		return lista;
	}

	private LineaFactura mapear(ResultSet rs) throws SQLException {
		return new LineaFactura(rs.getInt("id"), rs.getInt("cantidad"), rs.getDouble("precio_unitario"),
				rs.getDouble("importe"), rs.getInt("id_factura"), rs.getInt("id_producto"));
	}
	
	public boolean productoTieneLineas(int idProducto) {
	    String sql = "SELECT COUNT(*) FROM lineafactura WHERE id_producto = ?";
	    try (Connection con = ConexionBD.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, idProducto);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        System.err.println("Error comprobando uso de producto: " + e.getMessage());
	    }
	    return false;
	}
}
