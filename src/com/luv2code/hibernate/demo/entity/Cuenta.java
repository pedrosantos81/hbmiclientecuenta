package com.luv2code.hibernate.demo.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="cuenta")
public class Cuenta implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="NumeroCuenta")
	private int numerocuenta;
	
	@Enumerated(EnumType.STRING)
	@Column(name="tipocuenta")
	private TipoCuenta tipocuenta;
	
	@Column(name="saldoinicial")
	private double saldoinicial;
	
	@Column(name="estado",columnDefinition = "boolean default true")
	private boolean statuscuenta;
	
	@Column(name="id_cliente",insertable = false,updatable = false)
	private int idcliente;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,optional = true)
	@JoinColumn(name="id_cliente")
	private Cliente clientes;

	public Cuenta() {
	}
	
	public Cuenta(TipoCuenta tipocuenta, double saldoinicial, boolean estadocuenta) {
		this.tipocuenta = tipocuenta;
		this.saldoinicial = saldoinicial;
		this.statuscuenta = estadocuenta;
	}

	public int getNumerocuenta() {
		return numerocuenta;
	}

	public void setNumerocuenta(int numerocuenta) {
		this.numerocuenta = numerocuenta;
	}

	public TipoCuenta getTipocuenta() {
		return tipocuenta;
	}

	public void setTipocuenta(TipoCuenta tipocuenta) {
		this.tipocuenta = tipocuenta;
	}

	public double getSaldoinicial() {
		return saldoinicial;
	}

	public void setSaldoinicial(double saldoinicial) {
		this.saldoinicial = saldoinicial;
	}

	public boolean isStatuscuenta() {
		return statuscuenta;
	}

	public void setStatuscuenta(boolean statuscuenta) {
		this.statuscuenta = statuscuenta;
	}
	
	public String getClienteName() {
		return clientes.getNombre();
	}

	@JsonIgnore
	public Cliente getClientes() {
		return clientes;
	}

	@JsonIgnore
	public void setCliente(Cliente cliente) {
		this.clientes = cliente;
	}
	

}
