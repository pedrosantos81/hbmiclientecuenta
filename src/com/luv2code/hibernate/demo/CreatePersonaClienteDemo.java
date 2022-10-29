package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Cliente;
import com.luv2code.hibernate.demo.entity.Cuenta;
import com.luv2code.hibernate.demo.entity.Movimientos;
import com.luv2code.hibernate.demo.entity.Persona;

public class CreatePersonaClienteDemo {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Cliente.class)
				.addAnnotatedClass(Persona.class)
				.addAnnotatedClass(Cuenta.class)
				.addAnnotatedClass(Movimientos.class)
				.buildSessionFactory();

		// create session
		Session session = factory.getCurrentSession();

		try {

			// create the objects
			Cliente tempCliente = new Cliente("Bernabe Luna","Masculino",41,"ife","Calle Altamirano Col Quevedo","8777","222",true);
			
			//tempCliente.addCuenta(new Cuenta(TipoCuenta.AHORRO,3000,true));
			
//			List<Cuenta> list = new ArrayList<Cuenta>();
//			list.add(new Cuenta(TipoCuenta.AHORRO,3000,true));
			
			//tempCliente.addCuenta(new Cuenta(TipoCuenta.AHORRO,3000,true));
			
			
			
			// start a transaction
			session.beginTransaction();

			// save the student object
			System.out.println("Saving the persona y cliente...");
			session.save(tempCliente);
		

			// commit transaction
			session.getTransaction().commit();

			System.out.println("Done!");
		} finally {

			// add clean up code
			session.close();

			factory.close();
		}
	}

}
