package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import com.luv2code.hibernate.demo.entity.Cliente;

public class CreatePersonaClienteDemo {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Cliente.class)
				.buildSessionFactory();

		// create session
		Session session = factory.getCurrentSession();

		try {

			// create the objects
			Cliente tempCliente = new Cliente("Ramiro Garcia","Masculino",34,"ife","direccion","434343","2345",true);
			
			
			
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
