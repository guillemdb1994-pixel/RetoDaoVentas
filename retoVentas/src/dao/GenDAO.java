package dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


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
	public void insertar2(T objeto) throws Exception {

	    Class<?> clase = objeto.getClass();

	    Field[] atributos = clase.getDeclaredFields();

	    String sql = "INSERT INTO municipio (...) VALUES (...)";
	    Connection conn=ConexionBD.getConnection();
	    PreparedStatement pstmt = conn.prepareStatement(sql);

	    int i = 1;

	    for(Field f : atributos) {
	        f.setAccessible(true);
	        Object valor = f.get(objeto);
	        pstmt.setObject(i++, valor);
	    }

	    pstmt.executeUpdate();
	}
	/*
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
	*/



	

}