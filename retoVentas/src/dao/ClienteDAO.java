package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelos.Cliente;
import util.ConexionBD;

public class ClienteDAO implements GenericDAO<Cliente>{

	@Override
	public boolean insertar(Cliente objeto) {
		/*
		 * Faltaria implementar un proceso para que solo se haga inser si ambas funcionan
		 */
		String sql1 = "INSERT INTO cliente (direccion) VALUES (?)";
		String sql2= "INSERT INTO persona (dni, nombre) VALUES (?,?)";
		try (Connection con = ConexionBD.getConnection();
				PreparedStatement ps = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS)) {
			
			ps.setInt(1, obtenerIdParaInsert());		
			ps.setString(2, objeto.getDireccion());
			System.out.println(ps);
			if (ps.executeUpdate() > 0) {
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						objeto.setIdCliente(rs.getInt(1)); // Asigna el ID [cite: 160]
						return true;
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Error insertando persona: " + e.getMessage());
		}
		
		try (Connection con = ConexionBD.getConnection();
				PreparedStatement ps = con.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)) {
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
			System.err.println("Error insertando cliente: " + e.getMessage());
		}
		return false;
	}

	@Override
	public List<Cliente> obtenerTodos() {
		List<Cliente> lista = new ArrayList<>();
		String sql = "SELECT * FROM cliente";
		try (Connection con = ConexionBD.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next())
				lista.add(mapear(rs));
		} catch (SQLException e) {
			System.err.println("Error obteniendo clientes: " + e.getMessage());
		}
		return lista;
	}

	@Override
	public Cliente obtenerPorId(int id) {
		String sql = "SELECT * FROM cliente WHERE id=?";
		try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return mapear(rs);
			}
		} catch (SQLException e) {
			System.err.println("Error buscando cliente: " + e.getMessage());
		}
		return null;
	}

	@Override
	public boolean actualizar(Cliente objeto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminar(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	public Cliente mapear(ResultSet rs) throws SQLException{
		Cliente c=new Cliente();
		//c.setId(rs.getInt("id"));
		//c.setDni(rs.getString("dni"));
		//c.setNombre(rs.getString("nombre"));
		c.setIdCliente(rs.getInt("id"));
		c.setDireccion(rs.getString("direccion"));
		return c;
	}
	public int obtenerIdParaInsert() {
		String sql = "SELECT id FROM cliente order by id desc limit 1";
		try (Connection con = ConexionBD.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				return rs.getInt(1)+1;
			}
		} catch (SQLException e) {
			System.err.println("Error obteniendo clientes: " + e.getMessage());
		}
		return 0;
	}

}
