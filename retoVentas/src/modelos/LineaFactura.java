package modelos;

public class LineaFactura {
	protected int id;
	protected int cantidad;
	protected double precioUnitario;
	protected double importe;
	protected int idFactura;
	protected int idProducto;

	public LineaFactura(int id, int cantidad, double precioUnitario, double importe, int idFactura, int idProducto) {
		super();
		this.id = id;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.importe = importe;
		this.idFactura = idFactura;
		this.idProducto = idProducto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public int getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	@Override
	public String toString() {
		return "LineaFactura [id=" + id + ", cantidad=" + cantidad + ", precioUnitario=" + precioUnitario + ", importe="
				+ importe + ", idFactura=" + idFactura + ", idProducto=" + idProducto + "]";
	}

}
