package com.luv2code.hibernate.demo;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Cliente;
import com.luv2code.hibernate.demo.entity.Cuenta;
import com.luv2code.hibernate.demo.entity.Movimientos;
import com.luv2code.hibernate.demo.entity.Persona;
import com.luv2code.hibernate.utils.Utilerias;

public class GetListaMovimientos {

	public static void main(String[] args) {
		//create session factory
		SessionFactory factory = new Configuration()
				                 .configure("hibernate.cfg.xml")
				                 .addAnnotatedClass(Cliente.class)
				                 .addAnnotatedClass(Persona.class)
				                 .addAnnotatedClass(Cuenta.class)
				                 .addAnnotatedClass(Movimientos.class)
				                 .buildSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {
			
			
			//start a transaction 
			session.beginTransaction();
			
			//get the instructor from db
			int theId=4;
			Cliente tempCliente = session.get(Cliente.class, theId);
			
			System.out.println("Cliente: "+tempCliente);
			
			System.out.println(tempCliente.getCuentas().size());
			
			List<Cuenta> lstCuentas = tempCliente.getCuentas();
			
			//lstCuentas.forEach(System.out::println);
			
			for (Cuenta cuenta:lstCuentas) {
				System.out.println(cuenta.getNumerocuenta()+","+cuenta.getTipocuenta()+","+cuenta.getSaldoinicial() );
				
				List<Movimientos> lst = cuenta.getLstmovimientos();
				
				for(Movimientos m:lst) {
					System.out.println(m.getIdmovimiento()+"-"+m.getTipomovimiento()+"-"+m.getSaldo());
				}
				
//				cuenta.getLstmovimientos().stream().map(o->{
//					Movimientos m = new Movimientos();
//					m.setFecha(o.getFecha());
//					m.setIdcuenta(o.getIdcuenta());
//					m.setIdmovimiento(o.getIdmovimiento());
//					m.setSaldo(o.getSaldo());
//					m.setTipomovimiento(o.getTipomovimiento());
//					return m;
//					}).collect(Collectors.toList());
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
			
			String queryCuentaMovimientosByCliente="select "
					+ "m.fecha as fecha, "
					+ "p.nombre as cliente, "
					+ "c.numerocuenta as numerocuenta, "
					+ "c.tipocuenta as tipo, "
					+ "m.saldo as saldoinicial, "
					+ "m.valor as movimiento,  "
					+ "c.saldoinicial as saldodisponible  "
					+ "from Cliente S join Persona p on S.idpersona=p.id "
					+ "join Cuenta c on c.idcliente=S.idcliente "
					+ "join Movimientos m on m.idcuenta=c.numerocuenta "
					+ "where p.id=:id "
					+ "and DATE(m.fecha) between :startDate and :endDate";
			Query q = session.createQuery(queryCuentaMovimientosByCliente);
			q.setParameter("id", 2);
			q.setParameter("startDate", Utilerias.parseDate("2022-11-02"));
			q.setParameter("endDate",Utilerias.parseDate("2022-11-02"));
			List lsta= q.getResultList();
			
			System.out.println(lsta.size());
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
