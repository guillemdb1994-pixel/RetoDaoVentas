package modelos;

public class Empleado extends Persona{
	protected int idEmpleado;
	protected String puesto;
	protected Double salario;
	public Empleado(int idEmpleado, String puesto, Double salario) {
		super();
		this.idEmpleado = idEmpleado;
		this.puesto = puesto;
		this.salario = salario;
	}
	public Empleado() {
		super();
	}
	public int getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public String getPuesto() {
		return puesto;
	}
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	public Double getSalario() {
		return salario;
	}
	public void setSalario(Double salario) {
		this.salario = salario;
	}
	@Override
	public String toString() {
		return "Empleado [idEmpleado=" + idEmpleado + ", puesto=" + puesto + ", salario=" + salario + "]";
	}
	
	
	
}
