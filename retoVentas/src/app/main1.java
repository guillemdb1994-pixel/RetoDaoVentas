package app;

import dao.GenDAO;
import modelos.Cliente;

public class main1 {

	public static void main(String[] args) throws Exception {
		Cliente c=new Cliente(0,"94959694X","PACO",0,"Calle san blas");
		System.out.println(c);
		GenDAO<Cliente> asd=new GenDAO<>("cliente");
		asd.insertar2(c);

	}

}
