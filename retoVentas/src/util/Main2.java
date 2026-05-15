package util;

import dao.*;
import modelos.*;
import java.util.Scanner;
import java.util.List;
import java.sql.Date;

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
			System.out.println("\n--- MENÚ GESTIÓN VENTAS ---");
			System.out.println("3. Detalle de factura por ID");
			System.out.println("4. Facturas por mes y total facturado");
			System.out.println("5. Facturas por fecha concreta");
			System.out.println("7. Añadir nuevo producto (validar nombre)");
			System.out.println("8. Actualizar precio de producto");
			System.out.println("9. CREAR FACTURA COMPLETA");
			System.out.println("10. Buscar facturas por un producto");
			System.out.println("11. Eliminar producto (si no tiene ventas)");
			System.out.println("13. Duplicar factura en fecha actual");
			System.out.println("14. Eliminar línea y recalcular totales");
			System.out.println("0. Salir");
			System.out.print("Selección: ");
			opcion = Integer.parseInt(sc.nextLine());

			switch (opcion) {
			case 3:
				ejercicio03_verFacturaDetalle();
				break;
			case 4:
				ejercicio04_facturasPorMes();
				break;
			case 5:
				ejercicio05_facturasPorFecha();
				break;
			case 7:
				ejercicio07_insertarProductoUnico();
				break;
			case 8:
				ejercicio08_actualizarPrecio();
				break;
			case 9:
				ejercicio09_procesoCrearFactura();
				break;
			case 10:
				ejercicio10_facturasDeUnProducto();
				break;
			case 11:
				ejercicio11_borrarProductoSeguro();
				break;
			case 13:
				ejercicio13_duplicarFactura();
				break;
			case 14:
				ejercicio14_quitarLineaYRehacer();
				break;
			}
		} while (opcion != 0);
	}

	private static void ejercicio03_verFacturaDetalle() {
		System.out.print("ID Factura: ");
		int id = Integer.parseInt(sc.nextLine());
		Factura f = facturaDAO.obtenerPorId(id);
		if (f != null) {
			System.out.println(f);
			lineaDAO.obtenerPorFactura(id).forEach(System.out::println);
		} else
			System.out.println("No existe.");
	}

	private static void ejercicio04_facturasPorMes() {
		System.out.print("Número de mes (1-12): ");
		int mes = Integer.parseInt(sc.nextLine());
		List<Factura> lista = facturaDAO.obtenerPorMes(mes);
		double totalMes = 0;
		for (Factura f : lista) {
			System.out.println(f);
			totalMes += f.getTotal();
		}
		System.out.println("TOTAL FACTURADO EN MES " + mes + ": " + totalMes + "€");
	}

	private static void ejercicio05_facturasPorFecha() {
		System.out.print("Fecha (YYYY-MM-DD): ");
		Date fecha = Date.valueOf(sc.nextLine());
		facturaDAO.obtenerPorFecha(fecha).forEach(System.out::println);
	}

	private static void ejercicio07_insertarProductoUnico() {
		System.out.print("Nombre: ");
		String nom = sc.nextLine();
		if (productoDAO.obtenerPorNombre(nom) != null) {
			System.out.println("Error: El producto ya existe.");
		} else {
			System.out.print("Precio: ");
			double pr = Double.parseDouble(sc.nextLine());
			System.out.print("Stock: ");
			int st = Integer.parseInt(sc.nextLine());
			productoDAO.insertar(new Producto(0, nom, pr, st));
			System.out.println("Insertado.");
		}
	}

	private static void ejercicio08_actualizarPrecio() {
		productoDAO.obtenerTodos().forEach(System.out::println);
		System.out.print("ID a modificar: ");
		int id = Integer.parseInt(sc.nextLine());
		Producto p = productoDAO.obtenerPorId(id);
		if (p != null) {
			System.out.print("Nuevo precio: ");
			p.setPrecio(Double.parseDouble(sc.nextLine()));
			productoDAO.actualizar(p);
		}
	}

	private static void ejercicio09_procesoCrearFactura() {
		clienteDAO.obtenerTodos().forEach(System.out::println);
		System.out.print("ID Cliente: ");
		int idC = Integer.parseInt(sc.nextLine());

		empleadoDAO.obtenerTodos().forEach(System.out::println);
		System.out.print("ID Empleado: ");
		int idE = Integer.parseInt(sc.nextLine());

		Factura f = new Factura(new Date(System.currentTimeMillis()), idC, idE, 0, 0, 0);
		facturaDAO.insertar(f);

		double subtotal = 0;
		int idProd;
		do {
			productoDAO.obtenerTodos().forEach(p -> {
				if (p.getStock() > 0)
					System.out.println(p);
			});
			System.out.print("ID Producto (0 para terminar): ");
			idProd = Integer.parseInt(sc.nextLine());
			if (idProd != 0) {
				Producto p = productoDAO.obtenerPorId(idProd);
				System.out.print("Cantidad: ");
				int cant = Integer.parseInt(sc.nextLine());
				double imp = cant * p.getPrecio();
				lineaDAO.insertar(new LineaFactura(f.getId(), cant, p.getPrecio(), imp, idProd, 0));
				subtotal += imp;
				p.setStock(p.getStock() - cant);
				productoDAO.actualizar(p);
			}
		} while (idProd != 0);

		f.setSubtotal(subtotal);
		f.setIva(subtotal * 0.21);
		f.setTotal(f.getSubtotal() + f.getIva());
		facturaDAO.actualizar(f);
		System.out.println("FACTURA CREADA:\n" + f);
		lineaDAO.obtenerPorFactura(f.getId()).forEach(System.out::println);
	}

	private static void ejercicio10_facturasDeUnProducto() {
		productoDAO.obtenerTodos().forEach(System.out::println);
		System.out.print("ID Producto: ");
		int id = Integer.parseInt(sc.nextLine());
		facturaDAO.obtenerFacturasPorProducto(id).forEach(System.out::println);
	}

	private static void ejercicio11_borrarProductoSeguro() {
		productoDAO.obtenerTodos().forEach(System.out::println);
		System.out.print("ID a eliminar: ");
		int id = Integer.parseInt(sc.nextLine());
		if (lineaDAO.productoTieneLineas(id)) {
			System.out.println("No se puede eliminar: tiene ventas asociadas.");
		} else {
			productoDAO.eliminar(id);
			System.out.println("Eliminado.");
		}
	}

	private static void ejercicio13_duplicarFactura() {
		System.out.print("ID Factura a copiar: ");
		int idOri = Integer.parseInt(sc.nextLine());
		Factura ori = facturaDAO.obtenerPorId(idOri);
		if (ori != null) {
			Factura nueva = new Factura(0, new Date(System.currentTimeMillis()),
					ori.getSubtotal(), ori.getIva(), ori.getTotal(), ori.getIdCliente(), ori.getIdEmpleado());
			facturaDAO.insertar(nueva);
			List<LineaFactura> lineas = lineaDAO.obtenerPorFactura(idOri);
			for (LineaFactura l : lineas) {
				l.setIdFactura(nueva.getId());
				lineaDAO.insertar(l);
			}
			System.out.println("Duplicada con éxito. Nueva Factura ID: " + nueva.getId());
		}
	}

	private static void ejercicio14_quitarLineaYRehacer() {
		ejercicio03_verFacturaDetalle();
		System.out.print("ID Línea a eliminar: ");
		int idL = Integer.parseInt(sc.nextLine());
		LineaFactura lf = lineaDAO.obtenerPorId(idL);
		if (lf != null) {
			int idF = lf.getIdFactura();
			lineaDAO.eliminar(idL);

			List<LineaFactura> restantes = lineaDAO.obtenerPorFactura(idF);
			double sub = restantes.stream().mapToDouble(LineaFactura::getImporte).sum();
			Factura f = facturaDAO.obtenerPorId(idF);
			f.setSubtotal(sub);
			f.setIva(sub * 0.21);
			f.setTotal(sub + f.getIva());
			facturaDAO.actualizar(f);
			System.out.println("Actualizada:\n" + f);
		}
	}
}