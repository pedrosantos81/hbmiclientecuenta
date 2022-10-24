package com.luv2code.hibernate.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Cliente;
import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Cuenta;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;
import com.luv2code.hibernate.demo.entity.Student;
import com.luv2code.hibernate.demo.entity.TipoCuenta;

public class CreateCuentaDemo {

	public static void main(String[] args) {
		//create session factory
		SessionFactory factory = new Configuration()
				                 .configure("hibernate.cfg.xml")
				                 .addAnnotatedClass(Cliente.class)
				                 //.addAnnotatedClass(InstructorDetail.class)
				                 .addAnnotatedClass(Cuenta.class)
				                 .buildSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {
			
			
			//start a transaction 
			session.beginTransaction();
			
			//get the instructor from db
			int theId=3;
			//Instructor tempInstructor = session.get(Instructor.class, theId);
			Cliente tempCliente = session.get(Cliente.class,theId);
			
			System.out.println(tempCliente.toString());
			
			
			
			Cuenta tempCuenta1 = new Cuenta(TipoCuenta.AHORRO,8500,true);
			Cuenta tempCuenta2 = new Cuenta(TipoCuenta.CORRIENTE,4000,true);
			
			tempCliente.addCuenta(tempCuenta1);
			tempCliente.addCuenta(tempCuenta2);
			session.save(tempCuenta1);
			session.save(tempCuenta2);
			
//			//create some courses
//			Course tempCourse1 = new Course("Air Guitar - The ultimate guide");
//			Course tempCourse2 = new Course("The Pinball Masterclass");
//			
//			//add courses to instructor
//			tempInstructor.add(tempCourse1);
//			tempInstructor.add(tempCourse2);
//			
//			//save the courses
//			session.save(tempCourse1);
//			session.save(tempCourse2);
			
			//commit transaction
			session.getTransaction().commit();
			
			System.out.println("Success save cuenta!");
		}catch (Exception exc) {
            exc.printStackTrace();
        }finally {
        	//add clean up code
        	session.close();
        	
			factory.close();
		}

	}

}
