package com.luv2code.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.luv2code.hibernate.demo.entity.Cliente;
import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Cuenta;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.Persona;

public class GetClientePersonaDemo {

	public static void main(String[] args) {
		//create session factory
		SessionFactory factory = new Configuration()
				                 .configure("hibernate.cfg.xml")
				                 .addAnnotatedClass(Cliente.class)
				                 .addAnnotatedClass(Persona.class)
				                 .addAnnotatedClass(Cuenta.class)
				                 .buildSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {
			
			
			//start a transaction 
			session.beginTransaction();
			
			//get the instructor from db
			int theId=1;
			Cliente tempCliente = session.get(Cliente.class, theId);
			
			System.out.println("Cliente: "+tempCliente);
			
			System.out.println(tempCliente.getCuentas());
			
			List<Cuenta> lstCuentas = tempCliente.getCuentas();
			
			lstCuentas.forEach(System.out::println);
			
			for (Cuenta cuenta:lstCuentas) {
				System.out.println(cuenta.getNumerocuenta()+","+cuenta.getTipocuenta()+","+cuenta.getSaldoinicial());
			}
			
			//System.out.println("empty? "+lst.isEmpty());
			//get course for the instructor
			//Cuenta tempCuentas = session.get(Cuenta.class, theId);
//			Long parentId = 1L;
//			//Query q = session.createQuery("SELECT n FROM Cliente n WHERE n.persona.id =  :parentId");
//			//Query q = session.createQuery("select t from Cliente c join c.cuentas t where c.idpersona=:parentId");
//			Query q = session.createQuery("select c from Cuenta c where c.clientes.idpersona=:parentId");
//			
//			q.setParameter("parentId", 1);
//			System.out.println("test: "+q.getResultList());
//			List<Cuenta> childNodes = q.getResultList();
//			
//			System.out.println("cuentas: "+childNodes.size());
			
			//commit transaction
			session.getTransaction().commit();
			
			System.out.println("Success!");
		}catch (Exception exc) {
            exc.printStackTrace();
        }finally {
        	//add clean up code
        	session.close();
        	
			factory.close();
		}

	}

}
