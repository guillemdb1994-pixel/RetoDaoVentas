package modelos;

import java.io.Serializable;

public class Cliente extends Persona implements Serializable{
	public int idCliente;
	public String direccion;
	
	public Cliente(int idPersona, String dni, String nombre, int idCliente, String direccion) {
		super(idPersona, dni, nombre);
		this.idCliente = idCliente;
		this.direccion = direccion;
	}
	
	


	public Cliente() {
		super();
	}





	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}




	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", direccion=" + direccion + ", idPersona=" + idPersona + ", dni="
				+ dni + ", nombre=" + nombre + "]";
	}

	
	
}
