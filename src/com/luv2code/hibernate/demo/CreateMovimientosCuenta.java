package com.luv2code.hibernate.demo;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Cliente;
import com.luv2code.hibernate.demo.entity.Cuenta;
import com.luv2code.hibernate.demo.entity.Movimientos;
import com.luv2code.hibernate.demo.entity.TipoCuenta;
import com.luv2code.hibernate.demo.entity.TipoTransaccion;

public class CreateMovimientosCuenta {

	public static void main(String[] args) {
		//create session factory
		SessionFactory factory = new Configuration()
				                 .configure("hibernate.cfg.xml")
				                 .addAnnotatedClass(Cuenta.class)
				                 .addAnnotatedClass(Movimientos.class)
				                 .addAnnotatedClass(Cliente.class)
				                 .buildSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {
			
			
			//start a transaction 
			session.beginTransaction();
			
			//get the instructor from db
			int theId=6;
			TipoCuenta tipoacc = TipoCuenta.valueOf("CORRIENTE");
			double cantidad=500;
			TipoTransaccion tt = TipoTransaccion.valueOf("ABONO"); //Tipo de transaccion
			//Instructor tempInstructor = session.get(Instructor.class, theId);
			Cliente tempCliente = session.get(Cliente.class,theId);
			
			System.out.println(tempCliente.toString());
			
			System.out.println(tempCliente.getId()+"-"+tempCliente.getIdcliente()+"-"+tipoacc.name().toString());
			Cuenta tempCuenta = session.get(Cuenta.class, tempCliente.getId());
			
			String hql="select c from Cuenta c where id_cliente=:idcliente and tipocuenta=:tipocuenta";
			System.out.println( session.createQuery(hql)
					.setParameter("idcliente", tempCliente.getIdcliente())
					.setParameter("tipocuenta", TipoCuenta.CORRIENTE)
					.getSingleResult()
					);
			
			List<Cuenta> lst =session.createQuery(hql,Cuenta.class).setParameter("idcliente", tempCliente.getIdcliente()).setParameter("tipocuenta", TipoCuenta.CORRIENTE).getResultList();
			
			//Obtengo la cuenta de la persona
			Cuenta acc =session.createQuery(hql,Cuenta.class).setParameter("idcliente", tempCliente.getIdcliente()).setParameter("tipocuenta", TipoCuenta.CORRIENTE).getSingleResult();
			
			System.out.println("idcliente: "+acc.getIdcliente());
			
		   System.out.println("idcliente:"+lst.get(0).getIdcliente()+", numerodecuenta: "+lst.get(0).getNumerocuenta()+",tipodecuenta: "+lst.get(0).getTipocuenta()+",saldo: "+lst.get(0).getSaldoinicial());
		   
		   Movimientos movimiento = new Movimientos(tt.toString(),cantidad,lst.get(0).getSaldoinicial());
		   
			acc.addMovimiento(movimiento);
			acc.setSaldoinicial(lst.get(0).getSaldoinicial()+cantidad);
			
			session.save(movimiento);
			
			
			//commit transaction
			session.getTransaction().commit();
			
			System.out.println("Success save movimiento!");
		}catch (Exception exc) {
            exc.printStackTrace();
        }finally {
        	//add clean up code
        	session.close();
        	
			factory.close();
		}

	}

}
