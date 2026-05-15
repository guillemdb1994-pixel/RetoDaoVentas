package app;

import dao.ClienteDAO;
import modelos.Cliente;

public class main1 {
	public static void funcion1(int id) {
		ClienteDAO cl=new ClienteDAO();
		for (Cliente c : cl.obtenerTodos()) {
			System.out.println(c);
		}
		System.out.println();
		System.out.println(cl.obtenerPorId(id));
	}

	public static void main(String[] args) throws Exception {
		funcion1(2);
	}

}
