package modelos;

public class Cliente {
	protected int idCliente;
	protected String direccion;
	public Cliente(int idCliente, String direccion) {
		super();
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
		return "Cliente [idCliente=" + idCliente + ", direccion=" + direccion + "]";
	}
	
	
}
