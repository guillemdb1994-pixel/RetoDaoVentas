package util;

import dao.*;
import modelos.*;
import java.util.Scanner;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;

public class Main2 {

	private static Scanner sc = new Scanner(System.in);

	private static ProductoDAO productoDAO = new ProductoDAO();
	private static FacturaDAO facturaDAO = new FacturaDAO();
	private static LineaFacturaDAO lineaDAO = new LineaFacturaDAO();
	private static ClienteDAO clienteDAO = new ClienteDAO();
	private static EmpleadoDAO empleadoDAO = new EmpleadoDAO();

	public static void main(String[] args) {
		int opcion;
		do {
			mostrarMenu();
			opcion = Integer.parseInt(sc.nextLine());
			switch (opcion) {
			case 1:
				verClienteYFacturas();
				break;
			case 2:
				verEmpleadoYFacturas();
				break;
			case 3:
				verDetalleFactura();
				break;
			case 4:
				listarProductos();
				break;
			case 5:
				insertarProducto();
				break;
			case 6:
				crearNuevaFactura();
				break;
			case 7:
				borrarProducto();
				break;
			case 8:
				actualizarPrecioProducto();
				break;
			case 0:
				System.out.println("Saliendo...");
				break;
			default:
				System.out.println("Opción no válida.");
			}
		} while (opcion != 0);
	}

	private static void mostrarMenu() {
		System.out.println("\n--- GESTIÓN DE VENTAS (RETO 2) ---");
		System.out.println("1. Ver Cliente y sus facturas");
		System.out.println("2. Ver Empleado y facturas emitidas");
		System.out.println("3. Ver detalle de una Factura (por ID)");
		System.out.println("4. Listar todos los Productos");
		System.out.println("5. Insertar nuevo Producto");
		System.out.println("6. CREAR NUEVA FACTURA (Proceso completo)");
		System.out.println("7. Borrar Producto");
		System.out.println("8. Actualizar precio de Producto");
		System.out.println("0. Salir");
		System.out.print("Seleccione una opción: ");
	}

	private static void listarProductos() {
		System.out.println("\n--- LISTADO DE PRODUCTOS ---");
		List<Producto> productos = productoDAO.obtenerTodos();
		for (Producto p : productos) {
			System.out.println(p);
		}
	}

	private static void insertarProducto() {
		System.out.print("Nombre del producto: ");
		String nombre = sc.nextLine();

		if (productoDAO.obtenerPorNombre(nombre) != null) {
			System.out.println("Error: Ya existe un producto con ese nombre.");
			return;
		}

		System.out.print("Precio: ");
		double precio = Double.parseDouble(sc.nextLine());
		System.out.print("Stock inicial: ");
		int stock = Integer.parseInt(sc.nextLine());

		Producto p = new Producto(0, nombre, precio, stock);
		if (productoDAO.insertar(p)) {
			System.out.println("Producto insertado con ID: " + p.getId());
		}
	}

	private static void borrarProducto() {
		System.out.print("ID del producto a borrar: ");
		int id = Integer.parseInt(sc.nextLine());
		if (productoDAO.eliminar(id)) {
			System.out.println("Producto eliminado correctamente.");
		} else {
			System.out.println("No se pudo eliminar (comprueba si tiene facturas asociadas).");
		}
	}

	private static void actualizarPrecioProducto() {
		System.out.print("ID del producto: ");
		int id = Integer.parseInt(sc.nextLine());
		Producto p = productoDAO.obtenerPorId(id);

		if (p != null) {
			System.out.print("Nuevo precio (actual: " + p.getPrecio() + "): ");
			p.setPrecio(Double.parseDouble(sc.nextLine()));
			if (productoDAO.actualizar(p)) {
				System.out.println("Precio actualizado.");
			}
		} else {
			System.out.println("Producto no encontrado.");
		}
	}

	private static void verDetalleFactura() {
		System.out.print("Introduce el ID de la factura: ");
		int id = Integer.parseInt(sc.nextLine());
		Factura f = facturaDAO.obtenerPorId(id);

		if (f != null) {
			System.out.println(f);
			System.out.println("--- LÍNEAS DE DETALLE ---");
			List<LineaFactura> lineas = lineaDAO.obtenerPorFactura(id);
			for (LineaFactura l : lineas) {
				Producto p = productoDAO.obtenerPorId(l.getIdProducto());
				System.out.println(
						" - " + (p != null ? p.getNombre() : "Desc. desconocida") + " | Cant: " + l.getCantidad()
								+ " | P.U: " + l.getPrecioUnitario() + "€" + " | Importe: " + l.getImporte() + "€");
			}
		} else {
			System.out.println("Factura no encontrada.");
		}
	}

	private static void crearNuevaFactura() {
		System.out.println("\n--- NUEVA VENTA ---");
		System.out.print("ID Cliente: ");
		int idCli = Integer.parseInt(sc.nextLine());
		System.out.print("ID Empleado: ");
		int idEmp = Integer.parseInt(sc.nextLine());

		Factura f = new Factura(0, Date.valueOf(LocalDate.now()), 0, 0, 0, idCli, idEmp);
		if (facturaDAO.insertar(f)) {
			double subtotalAcumulado = 0;
			String continuar;

			do {
				System.out.print("ID Producto a añadir: ");
				int idProd = Integer.parseInt(sc.nextLine());
				Producto p = productoDAO.obtenerPorId(idProd);

				if (p != null && p.getStock() > 0) {
					System.out.print("Cantidad (Stock disponible: " + p.getStock() + "): ");
					int cant = Integer.parseInt(sc.nextLine());

					if (cant <= p.getStock()) {
						double importe = cant * p.getPrecio();
						LineaFactura linea = new LineaFactura(f.getId(), cant, p.getPrecio(), importe, idProd, 0);

						if (lineaDAO.insertar(linea)) {
							subtotalAcumulado += importe;
							p.setStock(p.getStock() - cant);
							productoDAO.actualizar(p);
							System.out.println("Línea añadida.");
						}
					} else {
						System.out.println("No hay stock suficiente.");
					}
				} else {
					System.out.println("Producto no disponible.");
				}

				System.out.print("¿Añadir otro producto? (s/n): ");
				continuar = sc.nextLine();
			} while (continuar.equalsIgnoreCase("s"));

			double iva = subtotalAcumulado * 0.21;
			f.setSubtotal(subtotalAcumulado);
			f.setIva(iva);
			f.setTotal(subtotalAcumulado + iva);

			facturaDAO.actualizar(f);
			System.out.println("Factura finalizada. Total: " + f.getTotal() + "€");
		}
	}

	private static void verClienteYFacturas() {
		System.out.print("ID Cliente: ");
		int id = Integer.parseInt(sc.nextLine());
		System.out.println("Mostrando datos del cliente...");
	}

	private static void verEmpleadoYFacturas() {
		System.out.print("ID Empleado: ");
		int id = Integer.parseInt(sc.nextLine());
		List<Factura> facturas = facturaDAO.obtenerPorEmpleado(id);
		if (facturas.isEmpty()) {
			System.out.println("Este empleado no ha emitido facturas.");
		} else {
			facturas.forEach(System.out::println);
		}
	}
}
