package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

	import modelos.Empleado;
import util.ConexionBD;

public class EmpleadoDAO implements GenericDAO<Empleado>{

	@Override
	public boolean insertar(Empleado objeto) {
		String sql1 = "INSERT INTO empleado (puesto,salario) VALUES (?,?)";
		String sql2= "INSERT INTO persona (dni, nombre) VALUES (?,?)";
		try (Connection con = ConexionBD.getConnection();
				PreparedStatement ps = con.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, objeto.getPuesto());
			ps.setDouble(2, objeto.getSalario());

			if (ps.executeUpdate() > 0) {
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						objeto.setId(rs.getInt(1)); // Asigna el ID [cite: 160]
						return true;
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Error insertando empleado: " + e.getMessage());
		}
		
		try (Connection con = ConexionBD.getConnection();
				PreparedStatement ps = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, objeto.getDni());
			ps.setString(2, objeto.getNombre());

			if (ps.executeUpdate() > 0) {
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						objeto.setId(rs.getInt(1)); // Asigna el ID [cite: 160]
						return true;
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Error insertando persona: " + e.getMessage());
		}
		return false;
	}

	@Override
	public List<Empleado> obtenerTodos() {
		List<Empleado> lista = new ArrayList<>();
		String sql = "SELECT * FROM empleado";
		try (Connection con = ConexionBD.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next())
				lista.add(mapear(rs));
		} catch (SQLException e) {
			System.err.println("Error obteniendo empleados: " + e.getMessage());
		}
		return lista;
	}

	@Override
	public Empleado obtenerPorId(int id) {
		String sql = "SELECT * FROM empleado WHERE id=?";
		try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return mapear(rs);
			}
		} catch (SQLException e) {
			System.err.println("Error buscando empleado: " + e.getMessage());
		}
		return null;
	}

	@Override
	public boolean actualizar(Empleado objeto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminar(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	public Empleado mapear(ResultSet rs) throws SQLException{
		Empleado c=new Empleado();

		c.setIdEmpleado(rs.getInt("id"));
		c.setPuesto(rs.getString("puesto"));
		c.setSalario(rs.getDouble("salario"));
		return c;
	}

}
