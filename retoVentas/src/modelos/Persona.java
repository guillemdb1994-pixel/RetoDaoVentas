package modelos;

public class Persona {
	protected int idPersona;
	protected String dni;
	protected String nombre;
	public Persona(int idPersona, String dni, String nombre) {
		super();
		this.idPersona = idPersona;
		this.dni = dni;
		this.nombre = nombre;
	}
	public Persona() {
		super();
	}
	public int getIdPersona() {
		return idPersona;
	}
	public void setId(int id) {
		this.idPersona = id;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return "Persona [id=" + idPersona + ", dni=" + dni + ", nombre=" + nombre + "]";
	}
	
	
}
