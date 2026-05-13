package dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Municipio;
import modelo.Campos;
import util.ConexionBD;

public abstract class DAO {
	ArrayList<String> tablas= estructura();
	public ArrayList<String> estructura() {
		ArrayList<String> municipioLista = new ArrayList<>();
	    String sql = "show tables";


			try (Connection conn = ConexionBD.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery()) {

				int i=1;
				while (rs.next()) {
					//System.out.println(rs.getArray(i));
					municipioLista.add(rs.getString(1));
					i++;
				}


			} catch (SQLException e) {
				System.err.println("Error SQL al obtener todos las provincias: " + e.getMessage());
			}
			return municipioLista;
	}
	public ArrayList<Campos> obtenerCampos(String tabla) {
		ArrayList<Campos> camposLista= new ArrayList<>();
	       String sql = "describe " + tabla;


	       try (
	    	        Connection conn = ConexionBD.getConnection();
	    	        PreparedStatement pstmt = conn.prepareStatement(sql);
	    	        ResultSet rs = pstmt.executeQuery()
	    	    ) {

	    	        while (rs.next()) {
	    	            camposLista.add(mapTabla(rs));
	    	        }

	    	        return camposLista;

	    	    } catch (SQLException e) {
	    	        System.err.println("Error SQL: " + e.getMessage());
	    	    }

	    	    return null;
    }
	public Campos mapTabla(ResultSet rs) throws SQLException {
		Campos t=new Campos();
		t.setField(rs.getString(1));
		t.setType(rs.getString(2));
		if (rs.getString(3).equals("NO")) {t.setIsNull(false);} else {t.setIsNull(true);}
		t.setKey(rs.getString(4));
		t.setDefaultValue(rs.getString(5));
		t.setExtra(rs.getString(6));
		return t;
	}
	
	public List<String> getInsertFields(String tabla){
		List<Campos> c=obtenerCampos(tabla);
		List<String> res=new ArrayList<>();
		for (Campos campo : c) {
			if (!(campo.getExtra().equals("auto_increment"))) {
			res.add(campo.getField());
			}
		}
		return res;
	}
	public List<String> getAllFields(String tabla) {
		List<Campos> c=obtenerCampos(tabla);
		List<String> res=new ArrayList<>();
		for (Campos campo : c) {
			res.add(campo.getField());
		}
		return res;
	}
}


