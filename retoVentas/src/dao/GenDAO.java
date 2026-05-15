package dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
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
	    System.out.println("bluqle for");
	    for (Field field : atributos) {
			System.out.println(field.get(objeto));
		}

	    
		String campos="(";
		for (String f : insertFields) {
			campos+=f+",";
		}
		campos=campos.substring(0,campos.length()-1)+")";
		String values="(";
		for (int i=0;i<insertFields.size();i++) {
			values+="?,";
		}
		values=values.substring(0,values.length()-1)+")";

		
		String sql="INSERT INTO "+tabla+" "+campos+" VALUES "+values;
		System.out.println(sql);
		
	    Connection conn=ConexionBD.getConnection();
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    
	    int i = 1;
	    for(Field f : atributos) {
	        f.setAccessible(true);
	        System.out.println("fgetobjetco:");
	        System.out.println(f.get(objeto));
	        Object valor = f.get(objeto);
	        pstmt.setObject(i, valor);
	        i++;
	    }
	    pstmt.executeUpdate();
	}
	
	
	
	
	public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
	    fields.addAll(Arrays.asList(type.getDeclaredFields()));

	    if (type.getSuperclass() != null) {
	        getAllFields(fields, type.getSuperclass());
	    }

	    return fields;
	}
}