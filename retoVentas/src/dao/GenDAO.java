package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Municipio;
import util.ConexionBD;

public class GenDAO<T> extends DAO {
	protected String tabla;
	protected List<String> insertFields;
	protected List<String> fields;
	

	public GenDAO(String tabla) {
		super();
		this.tabla = tabla;
		this.fields=getAllFields(tabla);
		this.insertFields=getInsertFields(tabla);
	}


	public boolean insertar(T objeto) {
		String campos="(";
		for (String f : fields) {
			campos+=f+",";
		}
		campos=campos.substring(0,campos.length()-1)+")";
		String values="(";
		for (int i=0;i<fields.size();i++) {
			values+="?,";
		}
		values=values.substring(0,values.length()-1)+")";

		
		String sql="INSERT INTO "+tabla+" "+campos+" VALUES "+values;
		System.out.println(sql);
		
		try(Connection conn=ConexionBD.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {


			pstmt.setString(1, objeto.getNombre());
			pstmt.setInt(2, objeto.getHabitantes());
			pstmt.setInt(4, objeto.getProvincia_id());
			pstmt.setBoolean(3, objeto.isEs_capital());


			int filas = pstmt.executeUpdate();


			if (filas > 0) {
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					if (rs.next()) {
						objeto.setId(rs.getInt(1)); // asigna el ID
						return true;
					}
				}
			}
			return false;


		} catch (SQLException e) {
			System.err.println("Error SQL al insertar '" + objeto.getNombre() + "': " + e.getMessage());
			return false;
		}
	}


	public List<Municipio> obtenerTodos() {
		List<Municipio> municipioLista = new ArrayList<>();
	    String sql = "SELECT id, nombre, habitantes, es_capital, provincia_id FROM municipios ORDER BY nombre";


			try (Connection conn = ConexionBD.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery()) {


				while (rs.next()) {
					municipioLista.add(mapear(rs));
				}


			} catch (SQLException e) {
				System.err.println("Error SQL al obtener todos las provincias: " + e.getMessage());
			}
			return municipioLista;
	}

	public Municipio obtenerPorId(int id) {
	       String sql = "SELECT id, nombre, habitantes, es_capital, provincia_id FROM municipios WHERE id = ?";


			try (Connection conn = ConexionBD.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {


				pstmt.setInt(1, id);


				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						return mapear(rs);
					}
				}


			} catch (SQLException e) {
				System.err.println("Error SQL al buscar ID " + id + ": " + e.getMessage());
			}
			return null; // no encontrado
		}

	public boolean actualizar(Municipio objeto) {
		String sql = "UPDATE municipios SET nombre=?, habitantes=?, es_capital=?, provincia_id=? WHERE id=?";


		try (Connection conn = ConexionBD.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {


			pstmt.setString(1, objeto.getNombre());
			pstmt.setInt(2, objeto.getHabitantes());
			pstmt.setBoolean(3, objeto.isEs_capital());
			pstmt.setInt(4, objeto.getProvincia_id());
			pstmt.setInt(4, objeto.getId());


			int filas = pstmt.executeUpdate();
			return filas > 0; // false si el ID no existía en la BD


		} catch (SQLException e) {
			System.err.println("Error SQL al actualizar ID " + objeto.getId() + ": " + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean eliminar(int id) {
		String sql = "DELETE FROM municipios WHERE id=?";


		try (Connection conn = ConexionBD.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {


			pstmt.setInt(1, id);
			int filas = pstmt.executeUpdate();
			return filas > 0; // false si el ID no existía


		} catch (SQLException e) {
			System.err.println("Error SQL al eliminar ID " + id + ": " + e.getMessage());
			return false;
		}
	}
	public Municipio mapear(ResultSet rs) throws SQLException {
		Municipio p=new Municipio();
		p.setId(rs.getInt("id"));
		p.setNombre(rs.getString("nombre"));
		p.setHabitantes(rs.getInt("habitantes"));
		if (rs.getInt("es_capital")==1) p.setEs_capital(true);
		else p.setEs_capital(false);
		return p;

	}
	

}