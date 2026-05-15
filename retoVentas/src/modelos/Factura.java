package modelos;

import java.sql.Date;

public class Factura {
	protected int id;
	protected Date fecha;
	protected double subtotal;
	protected double iva;
	protected double total;
	public int idCliente;
	public int idEmpleado;

	public Factura(int id, Date fecha, double subtotal, double iva, double total, int d, int e) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.subtotal = subtotal;
		this.iva = iva;
		this.total = total;
		this.idCliente = d;
		this.idEmpleado = e;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getIva() {
		return iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	@Override
	public String toString() {
		return "Factura [id=" + id + ", fecha=" + fecha + ", subtotal=" + subtotal + ", iva=" + iva + ", total=" + total
				+ ", idCliente=" + idCliente + ", idEmpleado=" + idEmpleado + "]";
	}

}
