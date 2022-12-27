package com.luv2code.hibernate.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
	
	private static final String DATE_FORMAT = "dd-MM-yyyy hh:mm:ss a";

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
			
			//TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			//start a transaction 
			session.beginTransaction();
			
			//get the instructor from db
			int theId=4;
			TipoCuenta tipoacc = TipoCuenta.valueOf("CORRIENTE");
			double cantidad=40;
			TipoTransaccion tt = TipoTransaccion.valueOf("ABONO"); //Tipo de transaccion
			//Instructor tempInstructor = session.get(Instructor.class, theId);
			Cliente tempCliente = session.get(Cliente.class,theId);
			
			System.out.println(tempCliente.toString());
			
			System.out.println(tempCliente.getId()+"-"+tempCliente.getIdcliente()+"-"+tipoacc.name().toString());
			Cuenta tempCuenta = session.get(Cuenta.class, tempCliente.getId());
			
			String hql="select c from Cuenta c where id_cliente=:idcliente and tipocuenta=:tipocuenta";
//			System.out.println( session.createQuery(hql)
//					.setParameter("idcliente", tempCliente.getIdcliente())
//					.setParameter("tipocuenta", TipoCuenta.CORRIENTE)
//					.getSingleResult()
//					);
			
			List<Cuenta> lst =session.createQuery(hql,Cuenta.class).setParameter("idcliente", tempCliente.getIdcliente()).setParameter("tipocuenta", TipoCuenta.CORRIENTE).getResultList();
			
			//Obtengo la cuenta de la persona
			Cuenta acc =session.createQuery(hql,Cuenta.class).setParameter("idcliente", tempCliente.getIdcliente()).setParameter("tipocuenta", TipoCuenta.CORRIENTE).getSingleResult();
			
			System.out.println("idcliente: "+acc.getIdcliente());
			
		   System.out.println("idcliente:"+lst.get(0).getIdcliente()+", numerodecuenta: "+lst.get(0).getNumerocuenta()+",tipodecuenta: "+lst.get(0).getTipocuenta()+",saldo: "+lst.get(0).getSaldoinicial());
		   
		   Movimientos movimiento = new Movimientos(tt.toString(),cantidad,lst.get(0).getSaldoinicial());
		   
			acc.addMovimiento(movimiento);
			acc.setSaldoinicial(lst.get(0).getSaldoinicial()+cantidad);
		
//			Date creacionmov = new Date();
//			movimiento.setFecha(creacionmov);
			
//			Calendar calendar = Calendar.getInstance();
//			Date date = calendar.getTime();
//
//			// format with tz
//	        TimeZone timeZone = TimeZone.getTimeZone("America/Mexico_City");
//	        SimpleDateFormat formatterWithTimeZone = new SimpleDateFormat(DATE_FORMAT);
//	        formatterWithTimeZone.setTimeZone(timeZone);
//
//	        // change tz using formatter
//	        String sDate = formatterWithTimeZone.format(date);
//
//	        // string to object date
//	        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
//	        Date dateWithTimeZone = formatter.parse(sDate); // string to Date Object
//
//			System.out.println(dateWithTimeZone);
//			 System.out.println("Object date: " + formatter.format(dateWithTimeZone));
//			 movimiento.setFecha(dateWithTimeZone);
//			System.out.println("fecha a insertar: "+movimiento.getFecha());
			
			
			
			
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
